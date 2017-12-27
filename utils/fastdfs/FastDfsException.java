package com.core.fastdfs;

/**
 * FastDfs异常
 * @author huangweiqi
 * 2015-2-5
 */
public class FastDfsException extends Exception {

	private static final long serialVersionUID = -7129831731090458675L;

	public FastDfsException() {
		super();
	}

	public FastDfsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FastDfsException(String message, Throwable cause) {
		super(message, cause);
	}

	public FastDfsException(String message) {
		super(message);
	}

	public FastDfsException(Throwable cause) {
		super(cause);
	}

	
	
}
