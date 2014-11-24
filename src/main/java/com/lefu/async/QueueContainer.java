package com.lefu.async;

import java.util.Iterator;

import com.lefu.async.disruptor.DisruptorQueue;

/**
 * 队列容器
 * @author jiang.li
 *
 */
public interface QueueContainer {
	/**
	 * 开始一个新的流程
	 * @return
	 */
	public Flow startFlow();
	/**
	 * 
	 * @param queue
	 */
	public void putQueue(DisruptorQueue queue);
	/**
	 * 发布数据至指定队列
	 * @param queueName {@link DisruptorQueue#getName()}
	 * @param data
	 * @throws QueueNotFoundException
	 */
	public void publish(String queueName, EventData data) throws QueueNotFoundException;
	/**
	 * 检查指定队列是否有足够的空间
	 * @param queueName
	 * @return
	 * @throws QueueNotFoundException
	 */
	public boolean hasAvailableCapacity(String queueName) throws QueueNotFoundException;
	/**
	 * 获取队列迭代器
	 * @return
	 */
	public Iterator<DisruptorQueue> iterator();
	/**
	 * 启动容器
	 */
	public void start();
	/**
	 * 关闭容器
	 */
	public void shutdown();
}
