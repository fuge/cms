package foo.common.security.rememberme;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;

import foo.common.security.UsernameNotFoundException;
import foo.common.security.userdetails.UserDetails;

/**
 * Identifies previously remembered users by a Base-64 encoded cookie.
 * 
 * <p>
 * This implementation does not rely on an external database, so is attractive
 * for simple applications. The cookie will be valid for a specific period from
 * the date of the last
 * {@link #loginSuccess(HttpServletRequest, HttpServletResponse, Authentication)}
 * . As per the interface contract, this method will only be called when the
 * principal completes a successful interactive authentication. As such the time
 * period commences from the last authentication attempt where they furnished
 * credentials - not the time period they last logged in via remember-me. The
 * implementation will only send a remember-me token if the parameter defined by
 * {@link #setParameter(String)} is present.
 * <p>
 * An {@link org.springframework.security.core.userdetails.UserDetailsService}
 * is required by this implementation, so that it can construct a valid
 * <code>Authentication</code> from the returned
 * {@link org.springframework.security.core.userdetails.UserDetails}. This is
 * also necessary so that the user's password is available and can be checked as
 * part of the encoded cookie.
 * <p>
 * The cookie encoded by this implementation adopts the following form:
 * 
 * <pre>
 * username + &quot;:&quot; + expiryTime + &quot;:&quot;
 * 		+ Md5Hex(username + &quot;:&quot; + expiryTime + &quot;:&quot; + password + &quot;:&quot; + key)
 * </pre>
 * 
 * <p>
 * As such, if the user changes their password, any remember-me token will be
 * invalidated. Equally, the system administrator may invalidate every
 * remember-me token on issue by changing the key. This provides some reasonable
 * approaches to recovering from a remember-me token being left on a public
 * machine (e.g. kiosk system, Internet cafe etc). Most importantly, at no time
 * is the user's password ever sent to the user agent, providing an important
 * security safeguard. Unfortunately the username is necessary in this
 * implementation (as we do not want to rely on a database for remember-me
 * services). High security applications should be aware of this occasionally
 * undesired disclosure of a valid username.
 * <p>
 * This is a basic remember-me implementation which is suitable for many
 * applications. However, we recommend a database-based implementation if you
 * require a more secure remember-me approach (see
 * {@link PersistentTokenBasedRememberMeServices}).
 * <p>
 * By default the tokens will be valid for 14 days from the last successful
 * authentication attempt. This can be changed using
 * {@link #setTokenValiditySeconds(int)}. If this value is less than zero, the
 * <tt>expiryTime</tt> will remain at 14 days, but the negative value will be
 * used for the <tt>maxAge</tt> property of the cookie, meaning that it will not
 * be stored when the browser is closed.
 * 
 * 
 * @author Ben Alex
 * @version $Id: TokenBasedRememberMeServices.java 3558 2009-04-15 07:39:21Z
 *          ltaylor $
 */
public class TokenBasedRememberMeServices extends AbstractRememberMeServices {

	protected UserDetails processAutoLoginCookie(String[] cookieTokens,
			HttpServletRequest request, HttpServletResponse response)
			throws DataAccessException, UsernameNotFoundException,
			InvalidCookieException {

		if (cookieTokens.length != 4) {
			throw new InvalidCookieException("Cookie token did not contain "
					+ 4 + " tokens, but contained '"
					+ Arrays.asList(cookieTokens) + "'");
		}

		long tokenExpiryTime;
		Long userId;
		try {
			tokenExpiryTime = new Long(cookieTokens[1]).longValue();
		} catch (NumberFormatException nfe) {
			throw new InvalidCookieException(
					"Cookie token[1] did not contain a valid number (contained '"
							+ cookieTokens[1] + "')");
		}

		if (isTokenExpired(tokenExpiryTime)) {
			throw new InvalidCookieException(
					"Cookie token[1] has expired (expired on '"
							+ new Date(tokenExpiryTime)
							+ "'; current time is '" + new Date() + "')");
		}
		try {
			userId = new Long(cookieTokens[3]);
		} catch (NumberFormatException nfe) {
			throw new InvalidCookieException(
					"Cookie token[3] did not contain a valid number (contained '"
							+ cookieTokens[3] + "')");
		}
		// Check the user exists.
		// Defer lookup until after expiry time checked, to possibly avoid
		// expensive database call.

		UserDetails user = getUserDetailsService().loadUser(userId,
				cookieTokens[0]);

		// Check signature of token matches remaining details.
		// Must do this after user lookup, as we need the DAO-derived password.
		// If efficiency was a major issue, just add in a UserCache
		// implementation,
		// but recall that this method is usually only called once per
		// HttpSession - if the token is valid,
		// it will cause SecurityContextHolder population, whilst if invalid,
		// will cause the cookie to be cancelled.
		String expectedTokenSignature = makeTokenSignature(tokenExpiryTime,
				user.getUsername(), user.getPassword(), user.getId());

		if (!expectedTokenSignature.equals(cookieTokens[2])) {
			throw new InvalidCookieException(
					"Cookie token[2] contained signature '" + cookieTokens[2]
							+ "' but expected '" + expectedTokenSignature + "'");
		}
		return user;
	}

	/**
	 * Calculates the digital signature to be put in the cookie. Default value
	 * is MD5 ("username:tokenExpiryTime:password:key")
	 */
	protected String makeTokenSignature(long tokenExpiryTime, String username,
			String password, Long id) {
		return DigestUtils.md5Hex(username + ":" + tokenExpiryTime + ":"
				+ password + ":" + getKey() + ":" + id);
	}

	protected boolean isTokenExpired(long tokenExpiryTime) {
		return tokenExpiryTime < System.currentTimeMillis();
	}

	public boolean onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response, UserDetails user) {

		String username = user.getUsername();
		String password = user.getPassword();

		// If unable to find a username and password, just abort as
		// TokenBasedRememberMeServices is
		// unable to construct a valid token in this case.
		if (!StringUtils.hasLength(username)
				|| !StringUtils.hasLength(password)) {
			return false;
		}

		int tokenLifetime = calculateLoginLifetime(request, user);
		long expiryTime = System.currentTimeMillis();
		// SEC-949
		expiryTime += 1000L * (tokenLifetime < 0 ? TWO_WEEKS_S : tokenLifetime);

		String signatureValue = makeTokenSignature(expiryTime, username,
				password, user.getId());

		setCookie(new String[] { username, Long.toString(expiryTime),
				signatureValue, user.getId().toString() }, tokenLifetime,
				request, response);

		if (logger.isDebugEnabled()) {
			logger.debug("Added remember-me cookie for user '" + username
					+ "', expiry: '" + new Date(expiryTime) + "'");
		}
		return true;
	}

	/**
	 * Calculates the validity period in seconds for a newly generated
	 * remember-me login. After this period (from the current time) the
	 * remember-me login will be considered expired. This method allows
	 * customization based on request parameters supplied with the login or
	 * information in the <tt>Authentication</tt> object. The default value is
	 * just the token validity period property, <tt>tokenValiditySeconds</tt>.
	 * <p>
	 * The returned value will be used to work out the expiry time of the token
	 * and will also be used to set the <tt>maxAge</tt> property of the cookie.
	 * 
	 * See SEC-485.
	 * 
	 * @param request
	 *            the request passed to onLoginSuccess
	 * @param user
	 *            the successful authentication object.
	 * @return the lifetime in seconds.
	 */
	protected int calculateLoginLifetime(HttpServletRequest request,
			UserDetails user) {
		return getTokenValiditySeconds();
	}
}
