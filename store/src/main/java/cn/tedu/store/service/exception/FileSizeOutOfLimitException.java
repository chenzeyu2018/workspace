package cn.tedu.store.service.exception;

public class FileSizeOutOfLimitException extends FileUploadException{

	private static final long serialVersionUID = -3280334688436410945L;

	public FileSizeOutOfLimitException() {
		super();
	}

	public FileSizeOutOfLimitException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public FileSizeOutOfLimitException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileSizeOutOfLimitException(String arg0) {
		super(arg0);
	}

	public FileSizeOutOfLimitException(Throwable arg0) {
		super(arg0);
	}

}
