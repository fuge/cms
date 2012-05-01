package foo.common.fck;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Represents the <a href="http://docs.fckeditor.net/FCKeditor_2.x/Developers_Guide/Server_Side_Integration#FileUpload_.28HTML.29"
 * target="_blank">HTML/JS response</a> for the File Browser <code>POST</code>
 * requests.
 * <p>
 * This class utilizes varags to reflect the JavaScript callback function in an
 * optimal way. However, varargs can be tricky. You can always omit passed
 * parameters from right to left but any violation may result in an exception or
 * a failed callback.
 * </p>
 * <p>
 * For example: <code>window.parent.OnUploadCompleted(101,'some/url/file.img','file.img','no error')</code> can be mapped with
 * 
 * <code>UploadResponse ur = new UploadResponse(SC_SOME_ERROR,"/some/url/file.img","file.img","no error")</code>
 * .
 * </p>
 * <p>
 * But <code>window.parent.OnUploadCompleted(101,'some/url/file.img','no error')</code> is an illegal callback and
 * will fail.
 * </p>
 * 
 * @version $Id: UploadResponse.java,v 1.1 2011/12/06 01:36:06 administrator Exp $
 */
public class UploadResponse {
	/** JavaScript callback parameters */
	protected Object[] parameters;

	/** Error number OK */
	public static final int EN_OK = 0;

	/** Error number CUSTOM ERROR */
	public static final int EN_CUSTOM_ERROR = 1;

	/** Error number CUSTOM WARNING */
	public static final int EN_CUSTOM_WARNING = 101;

	/** Error number FILE RENAMED WARNING */
	public static final int EN_FILE_RENAMED_WARNING = 201;

	/** Error number INVALID FILE TYPE */
	public static final int EN_INVALID_FILE_TYPE_ERROR = 202;

	/** Error number SECURITY ERROR */
	public static final int EN_SECURITY_ERROR = 203;

	/**
	 * Constructs a response with a varying amount of arguments.
	 * <p>
	 * Use the predefined error numbers or upload responses, if possible. If you
	 * need to set error number and message only, use this constructor with the
	 * first argument only and call
	 * {@link UploadResponse#setCustomMessage(String)}.
	 * </p>
	 * 
	 * @param arguments
	 *            possible argument order:
	 *            <code>int errorNumber, String fileUrl, String filename, String customMessage</code>
	 *            . Omit from right to left.
	 * @throws IllegalArgumentException
	 *             if amount of arguments is less than 1 and above 4
	 * @throws IllegalArgumentException
	 *             if the first argument is not an error number (int)
	 */
	public UploadResponse(Object... arguments) {
		if (arguments.length < 1 || arguments.length > 4)
			throw new IllegalArgumentException(
					"The amount of arguments has to be between 1 and 4");

		parameters = new Object[arguments.length];

		if (!(arguments[0] instanceof Integer))
			throw new IllegalArgumentException(
					"The first argument has to be an error number (int)");

		System.arraycopy(arguments, 0, parameters, 0, arguments.length);
	}

	/**
	 * Sets the custom message of this upload response.
	 * 
	 * Methods automatically determines how many arguments are set and puts the
	 * message at the end.
	 * 
	 * @param customMassage
	 *            the custom message of this upload response
	 */
	public void setCustomMessage(final String customMassage) {
		if (!StringUtils.isBlank(customMassage)) {
			if (parameters.length == 1) {
				Object errorNumber = parameters[0];
				parameters = new Object[4];
				parameters[0] = errorNumber;
				parameters[1] = null;
				parameters[2] = null;
			}
			parameters[3] = customMassage;
		}
	}

	/** Creates an <code>OK</code> response. */
	public static UploadResponse getOK(HttpServletRequest request,
			String fileUrl) {
		return new UploadResponse(EN_OK, fileUrl);
	}

	/** Creates a <code>FILE RENAMED</code> warning. */
	public static UploadResponse getFileRenamedWarning(
			HttpServletRequest request, String fileUrl, String newFileName) {
		return new UploadResponse(EN_FILE_RENAMED_WARNING, fileUrl,
				newFileName, LocalizedMessages.getFileRenamedWarning(request,
						newFileName));
	}

	/** Creates a <code>INVALID FILE TYPE</code> error. */
	public static UploadResponse getInvalidFileTypeError(
			HttpServletRequest request) {
		return new UploadResponse(EN_INVALID_FILE_TYPE_ERROR, LocalizedMessages
				.getInvalidFileTypeSpecified(request));
	}

	/** Creates a <code>INVALID COMMAND</code> error. */
	public static UploadResponse getInvalidCommandError(
			HttpServletRequest request) {
		return new UploadResponse(EN_CUSTOM_ERROR, null, null,
				LocalizedMessages.getInvalidCommandSpecified(request));
	}

	/** Creates a <code>INVALID RESOURCE TYPE</code> error. */
	public static UploadResponse getInvalidResourceTypeError(
			HttpServletRequest request) {
		return new UploadResponse(EN_CUSTOM_ERROR, null, null,
				LocalizedMessages.getInvalidResouceTypeSpecified(request));
	}

	/** Creates a <code>INVALID CURRENT FOLDER</code> error. */
	public static UploadResponse getInvalidCurrentFolderError(
			HttpServletRequest request) {
		return new UploadResponse(EN_CUSTOM_ERROR, null, null,
				LocalizedMessages.getInvalidCurrentFolderSpecified(request));
	}

	/** Creates a <code>FILE UPLOAD DISABLED</code> error. */
	public static UploadResponse getFileUploadDisabledError(
			HttpServletRequest request) {
		return new UploadResponse(EN_SECURITY_ERROR, null, null,
				LocalizedMessages.getFileUploadDisabled(request));
	}

	/** Creates a <code>FILE UPLOAD WRITE</code> error. */
	public static UploadResponse getFileUploadWriteError(
			HttpServletRequest request) {
		return new UploadResponse(EN_CUSTOM_ERROR, null, null,
				LocalizedMessages.getFileUploadWriteError(request));
	}

	/**
	 * Creates the HTML/JS representation of this upload response.
	 * 
	 * @return HTML/JS representation of this upload response
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(400);
		sb.append("<script type=\"text/javascript\">\n");
		// Compressed version of the document.domain automatic fix script.
		// The original script can be found at
		// [fckeditor_dir]/_dev/domain_fix_template.js
		sb
				.append("(function(){var d=document.domain;while (true){try{var A=window.parent.document.domain;break;}catch(e) {};d=d.replace(/.*?(?:\\.|$)/,'');if (d.length==0) break;try{document.domain=d;}catch (e){break;}}})();\n");
		sb.append("window.parent.OnUploadCompleted(");

		for (Object parameter : parameters) {
			if (parameter instanceof Integer) {
				sb.append(parameter);
			} else {
				sb.append("'");
				if (parameter != null)
					sb.append(parameter);
				sb.append("'");
			}
			sb.append(",");
		}

		sb.deleteCharAt(sb.length() - 1);
		sb.append(");\n");
		sb.append("</script>");

		return sb.toString();
	}
}