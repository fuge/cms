package foo.common.fck;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Static helper methods for strings.
 * 
 * @version $Id: Utils.java,v 1.1 2011/12/06 01:36:06 administrator Exp $
 */
public class Utils {

	/** The empty string {@code ""}. */
	public static final String EMPTY_STRING = "";

	/**
	 * Constructs a set of lower-cased strings from a delimiter-separated
	 * string.
	 * 
	 * @param stringList
	 *            strings separated with a delimiter
	 * @param delimiter
	 *            separating delimiter
	 * @return a lower-cased set, empty set if stringList is empty
	 * @throws IllegalArgumentException
	 *             if <code>delimiter</code> is empty
	 */
	public static Set<String> getSet(final String stringList,
			final String delimiter) {
		if (isEmpty(delimiter))
			throw new IllegalArgumentException(
					"Argument 'delimiter' shouldn't be empty!");
		if (isEmpty(stringList))
			return new HashSet<String>();

		Set<String> set = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(stringList, delimiter);
		while (st.hasMoreTokens()) {
			String tmp = st.nextToken();
			if (isNotEmpty(tmp)) // simple empty filter
				set.add(tmp.toLowerCase());
		}
		return set;
	}

	/**
	 * Constructs a set of lower-cased strings from a <code>|</code> (pipe)
	 * delimited string.
	 * 
	 * @see #getSet(String, String)
	 * @param stringList
	 *            strings separated with a delimiter
	 * @return a lower-cased set, empty set if stringList is empty
	 * @throws IllegalArgumentException
	 *             if <code>delimiter</code> is empty
	 */
	public static Set<String> getSet(final String stringList) {
		return getSet(stringList, "|");
	}

	/**
	 * Checks if a string is empty ("") or null.
	 * 
	 * @param str
	 *            string to check, may be null
	 * @return <code>true</code> if the string is <code>null</code> or empty,
	 *         else <code>false</code>
	 */
	public static boolean isEmpty(final String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Checks if a string is not empty ("") and not null.
	 * 
	 * @param str
	 *            string to check, may be null
	 * @return <code>true</code> if the string is not empty and not
	 *         <code>null</code>, else <code>false</code>
	 */
	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	/**
	 * Checks if a string is whitespace, empty ("") or null. Whitespace is
	 * checked by {@link Character#isWhitespace(char)}.
	 * 
	 * @param str
	 *            string to check, may be null
	 * @return <code>true</code> if the string is <code>null</code>, empty or
	 *         whitespace
	 */
	public static boolean isBlank(final String str) {

		if (isEmpty(str))
			return true;

		for (char c : str.toCharArray()) {
			if (!Character.isWhitespace(c))
				return false;
		}

		return true;
	}

	/**
	 * Checks if a string is not empty (""), not null and not whitespace.
	 * 
	 * @param str
	 *            string to check, may be null
	 * @return <code>true</code> if the string is not <code>null</code>, not
	 *         empty and not whitespace.
	 */
	public static boolean isNotBlank(final String str) {
		return !isBlank(str);
	}
}