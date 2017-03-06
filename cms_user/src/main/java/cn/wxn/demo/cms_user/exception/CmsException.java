package cn.wxn.demo.cms_user.exception;

public class CmsException extends Exception {
 
	private static final long serialVersionUID = -8441052019052389781L;

	public CmsException() {
		super();
	}

	public CmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public CmsException(String message) {
		super(message);
	}

	public CmsException(Throwable cause) {
		super(cause);
	}

}
