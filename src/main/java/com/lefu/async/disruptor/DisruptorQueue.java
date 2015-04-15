package com.lefu.async.disruptor;

import java.util.List;

import com.lefu.async.EventData;
import com.lefu.async.EventListener;
import com.lefu.async.EventStatus;
import com.lefu.async.QueueContainer;

/**
 * 队列定义
 * @author jiang.li
 *
 */
public interface DisruptorQueue {
	/**
	 * 当前的队列执行前所依赖的队列列表，为空则不依赖任何队列
	 * <pre>依赖判断的依据是所依赖的队列状态为<code>{@link EventStatus#FINISHED}</code>，其他任何的状态都会导致流程中断</pre>
	 * @param dependents
	 */
	public void setDependents(List<String> dependents);
	/**
	 * 设置当前队列处理异常后流程转向队列
	 * <pre>默认会将当前队列的<code>{@link EventData}</code>转向这里定义的异常处理队列，如果不定义异常队列，那么发生异常时流程会终止</pre>
	 * @param queueName
	 */
	public void setExceptionQuene(String queueName);
	/**
	 * 队列是否存在依赖的队列关系
	 * @return
	 */
	public boolean hasDependents();
	/**
	 * 队列名称
	 * @return
	 */
	public String getName();
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
