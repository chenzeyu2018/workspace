package cn.tedu.store.service.exception;

public class RequestException extends RuntimeException{

	private static final long serialVersionUID = 1484637976176258685L;

	public RequestException() {
		super();
	}

	public RequestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public RequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RequestException(String arg0) {
		super(arg0);
	}

	public RequestException(Throwable arg0) {
		super(arg0);
	}

}
