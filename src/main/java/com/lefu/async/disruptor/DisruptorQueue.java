package com.lefu.async.disruptor;

import com.lefu.async.EventData;
import com.lefu.async.EventListener;
import com.lefu.async.QueueContainer;

/**
 * 队列定义
 * @author jiang.li
 *
 */
public interface DisruptorQueue {
	/**
	 * 队列名称
	 * @return
	 */
	public String getName();
	/**
	 * 当前队列是否记录状态
	 * @return
	 */
	public boolean isRecordEventStatus();
	/**
	 * 发布事件，这里发布的是事件的参数，不是事件本身，事件不会在每次发布时新建实例！
	 * @param data
	 */
	public void publish(EventData data);
	/**
	 * 注册事件监听
	 * @param eventListener
	 */
	public void setEventListener(EventListener eventListener);
	/**
	 * 关联容器
	 * @param queueContainer
	 */
	public void setQueueContainer(QueueContainer queueContainer);
	/**
	 * 设置线程数
	 * @param threads
	 */
	public void setThreads(int threads);
	/**
	 * 检查当前队列是否还有足够的空间
	 * @see {@link DisruptorQueue#DEFAULT_CHECK_CAPACITY}
	 * @return
	 */
	public boolean hasAvailableCapacity();
	/**
	 * 启动队列
	 */
	public void start();
	/**
	 * 关闭队列
	 */
	public void shutdown();
}
