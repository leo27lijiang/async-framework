package com.lefu.async.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.lefu.async.EventStatus;
import com.lefu.async.Flow;
import com.lefu.async.FlowContext;
import com.lefu.async.QueueContainer;
import com.lefu.async.QueueNotFoundException;
import com.lefu.async.disruptor.DisruptorQueue;

/**
 * 简单流程实现
 * @author jiang.li
 *
 */
public class SimpleFlow implements Flow {
	private static final AtomicLong COUNTER = new AtomicLong();
	private final long flowId = COUNTER.getAndIncrement();
	private final FlowContext flowContext = new SimpleFlowContext();
	private final QueueContainer queueContainer;
	private final Map<String, EventStatus> queueEventStatus = new Hashtable<String, EventStatus>();
	
	public SimpleFlow(QueueContainer queueContainer) {
		this.queueContainer = queueContainer;
		Iterator<DisruptorQueue> iter = this.queueContainer.iterator();
		while (iter.hasNext()) {
			DisruptorQueue queue = iter.next();
			this.queueEventStatus.put(queue.getName(), EventStatus.UNKOWN);
		}
	}
	
	@Override
	public long getId() {
		return this.flowId;
	}

	@Override
	public FlowContext getContext() {
		return this.flowContext;
	}

	@Override
	public QueueContainer getQueueContainer() {
		return this.queueContainer;
	}

	@Override
	public EventStatus getQueueEventStatus(String queueName)
			throws QueueNotFoundException {
		if (!this.queueEventStatus.containsKey(queueName)) {
			throw new QueueNotFoundException("Queue not found for name " + queueName);
		}
		return this.queueEventStatus.get(queueName);
	}

	@Override
	public void putQueueEventStatus(String queueName, EventStatus status)
			throws QueueNotFoundException {
		if (status.equals(EventStatus.UNKOWN)) throw new RuntimeException("Can not set status to UNKOWN, it is a special state"); 
		if(this.queueEventStatus.containsKey(queueName)) this.queueEventStatus.put(queueName, status);
	}

	@Override
	public void destroy() {
		this.queueEventStatus.clear();
		this.flowContext.destroy();
	}

}
