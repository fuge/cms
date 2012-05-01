package foo.common.security.rememberme;

/**
 * @author Luke Taylor
 * @version $Id: CookieTheftException.java,v 1.1 2011/12/06 01:36:07 administrator Exp $
 */
@SuppressWarnings("serial")
public class CookieTheftException extends RememberMeAuthenticationException {
	public CookieTheftException(String message) {
		super(message);
	}
}
