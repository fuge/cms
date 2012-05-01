package foo.common.security.rememberme;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import foo.common.security.userdetails.UserDetails;

public interface RememberMeService {

	/**
	 * This method will be called whenever the
	 * <code>SecurityContextHolder</code> does not contain an
	 * <code>Authentication</code> object and Spring Security wishes to provide
	 * an implementation with an opportunity to authenticate the request using
	 * remember-me capabilities. Spring Security makes no attempt whatsoever to
	 * determine whether the browser has requested remember-me services or
	 * presented a valid cookie. Such determinations are left to the
	 * implementation. If a browser has presented an unauthorised cookie for
	 * whatever reason, it should be silently ignored and invalidated using the
	 * <code>HttpServletResponse</code> object.
	 * <p>
	 * The returned <code>Authentication</code> must be acceptable to
	 * {@link org.springframework.security.authentication.AuthenticationManager}
	 * or
	 * {@link org.springframework.security.authentication.AuthenticationProvider}
	 * defined by the web application. It is recommended
	 * {@link org.springframework.security.authentication.RememberMeAuthenticationToken}
	 * be used in most cases, as it has a corresponding authentication provider.
	 * 
	 * @param request
	 *            to look for a remember-me token within
	 * @param response
	 *            to change, cancel or modify the remember-me token
	 * 
	 * @return a valid authentication object, or <code>null</code> if the
	 *         request should not be authenticated
	 */
	UserDetails autoLogin(HttpServletRequest request,
			HttpServletResponse response) throws CookieTheftException;

	/**
	 * Called whenever an interactive authentication attempt was made, but the
	 * credentials supplied by the user were missing or otherwise invalid.
	 * Implementations should invalidate any and all remember-me tokens
	 * indicated in the <code>HttpServletRequest</code>.
	 * 
	 * @param request
	 *            that contained an invalid authentication request
	 * @param response
	 *            to change, cancel or modify the remember-me token
	 */
	void loginFail(HttpServletRequest request, HttpServletResponse response);

	/**
	 * Called whenever an interactive authentication attempt is successful. An
	 * implementation may automatically set a remember-me token in the
	 * <code>HttpServletResponse</code>, although this is not recommended.
	 * Instead, implementations should typically look for a request parameter
	 * that indicates the browser has presented an explicit request for
	 * authentication to be remembered, such as the presence of a HTTP POST
	 * parameter.
	 * 
	 * @param request
	 *            that contained the valid authentication request
	 * @param response
	 *            to change, cancel or modify the remember-me token
	 * @param user
	 *            representing the successfully authenticated principal
	 */
	boolean loginSuccess(HttpServletRequest request,
			HttpServletResponse response, UserDetails user);

	void logout(HttpServletRequest request, HttpServletResponse response);
}
