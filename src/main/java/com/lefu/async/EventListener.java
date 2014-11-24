package com.lefu.async;

/**
 * 事件监听器
 * @author jiang.li
 *
 */
public interface EventListener {
	/**
	 * 事件处理
	 * @param e
	 * @throws Exception
	 */
	public void onEvent(QueueEvent e) throws Exception;
}
