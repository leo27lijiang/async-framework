package com.lefu.async;

/**
 * 队列查找失败
 * @author jiang.li
 *
 */
public class QueueNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8600601606541979829L;
	
	public QueueNotFoundException() {
		super();
	}
	
	public QueueNotFoundException(String message) {
		super(message);
	}

}
