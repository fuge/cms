package foo.core;

public class CoreException extends Exception {
	private static final long serialVersionUID = -4203751630648516290L;

	public CoreException() {
		super();
	}

	public CoreException(String message) {
		super(message);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public CoreException(Throwable cause) {
		super(cause);
	}
}
