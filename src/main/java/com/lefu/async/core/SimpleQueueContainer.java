package com.lefu.async.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventData;
import com.lefu.async.Flow;
import com.lefu.async.QueueContainer;
import com.lefu.async.QueueNotFoundException;
import com.lefu.async.disruptor.DisruptorQueue;

/**
 * 队列容器基础实现
 * @author jiang.li
 *
 */
public class SimpleQueueContainer implements QueueContainer {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Map<String, DisruptorQueue> map = new Hashtable<String, DisruptorQueue>();
	private AtomicBoolean isStarted = new AtomicBoolean(false);
	
	public SimpleQueueContainer() {
		
	}
	
	@Override
	public void start() {
		if (isStarted.get()) {
			return;
		}
		Iterator<DisruptorQueue> iter = iterator();
		while (iter.hasNext()) {
			DisruptorQueue queue = iter.next();
			queue.start();
		}
		log.info("Queue container start finished.");
		isStarted.set(true);
	}
	
	@Override
	public void shutdown() {
		if (!isStarted.get()) {
			return;
		}
		Iterator<DisruptorQueue> iter = iterator();
		while (iter.hasNext()) {
			DisruptorQueue queue = iter.next();
			queue.shutdown();
		}
		log.info("Queue container shutdown finished.");
		isStarted.set(false);
	}
	
	@Override
	public Flow startFlow() {
		return new SimpleFlow(this);
	}
	
	@Override
	public void putQueue(DisruptorQueue queue) {
		if (queue == null) {
			throw new NullPointerException();
		}
		queue.setQueueContainer(this);
		this.map.put(queue.getName(), queue);
	}
	
	@Override
	public void setQueues(List<DisruptorQueue> queues) {
		if (queues == null) {
			return;
		}
		for (DisruptorQueue q : queues) {
			putQueue(q);
		}
	}

	@Override
	public void publish(String queueName, EventData data)
			throws QueueNotFoundException {
		if (!this.map.containsKey(queueName)) {
			throw new QueueNotFoundException("Queue " + queueName + " not found");
		}
		DisruptorQueue queue = this.map.get(queueName);
		queue.publish(data);
	}
	
	@Override
	public boolean hasAvailableCapacity(String queueName) {
		if (!this.map.containsKey(queueName)) {
			throw new QueueNotFoundException("Queue " + queueName + " not found");
		}
		DisruptorQueue queue = this.map.get(queueName);
		return queue.hasAvailableCapacity();
	}

	@Override
	public Iterator<DisruptorQueue> iterator() {
		return this.map.values().iterator();
	}

}
