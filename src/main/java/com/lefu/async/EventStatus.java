package com.lefu.async;

/**
 * 
 * @author jiang.li
 *
 */
public enum EventStatus {
	/**
	 * 对应流程的队列事件开始
	 */
	STARTED,
	/**
	 * 完成状态
	 */
	FINISHED,
	/**
	 * 异常
	 */
	EXCEPTION,
	/**
	 * 未定义
	 */
	UNKOWN;
}
