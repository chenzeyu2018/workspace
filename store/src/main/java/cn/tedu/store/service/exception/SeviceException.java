package cn.tedu.store.service.exception;

/**
 * 父类异常
 * 
 * @author Tiberius
 *
 */
public class SeviceException extends RuntimeException {

	private static final long serialVersionUID = 3985251940488175731L;

	public SeviceException() {
		super();
	}

	public SeviceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SeviceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeviceException(String message) {
		super(message);
	}

	public SeviceException(Throwable cause) {
		super(cause);
	}

}
