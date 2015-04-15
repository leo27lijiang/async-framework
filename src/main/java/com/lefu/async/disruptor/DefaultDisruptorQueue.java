package com.lefu.async.disruptor;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventData;
import com.lefu.async.EventListener;
import com.lefu.async.QueueContainer;
import com.lefu.async.QueueEvent;
import com.lefu.async.support.ThreadPoolFactory;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 基础队列实现
 * @author jiang.li
 *
 */
public class DefaultDisruptorQueue implements DisruptorQueue {
	/**
	 * 默认检查剩余空间大小，应用可以修改这个阀值
	 */
	public static int DEFAULT_CHECK_CAPACITY = 10;
	private static final String DEFAULT_NAME = "UNKOWN";
	private static final EventTranslatorOneArg<QueueEvent, EventData> TRANSLATOR = new DefaultEventDataTranslator();
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Disruptor<QueueEvent> disruptor;
	private RingBuffer<QueueEvent> ringBuffer;
	private EventTranslatorOneArg<QueueEvent, EventData> translator = TRANSLATOR;
	private AtomicBoolean isStarted = new AtomicBoolean(false);
	private String name = DEFAULT_NAME;
	private int threads = -1;
	private EventListener eventListener;
	private boolean logUseTime = false;
	private QueueContainer queueContainer;
	private List<String> dependents;
	private String exceptionQueue;
	
	static {
		try {
			DEFAULT_CHECK_CAPACITY = Integer.parseInt(System.getProperty("com.lefu.async.defaultCheckSize", "10"));
		} catch (NumberFormatException e) {
		}
	}
	
	/**
	 * 使用默认的配置生成指定大小的队列
	 * @param size
	 */
	public DefaultDisruptorQueue(int size) {
		this(ThreadPoolFactory.EXECUTOR, size);
	}
	
	/**
	 * 使用指定的线程池进行构建
	 * @param executor
	 * @param size
	 */
	public DefaultDisruptorQueue(final Executor executor, int size) {
		this(new DefaultEventFactory(), executor, size);
	}
	
	/**
	 * 使用指定的事件工厂和线程池进行构建
	 * @param eventFactory
	 * @param executor
	 * @param size
	 */
	public DefaultDisruptorQueue(final EventFactory<QueueEvent> eventFactory, final Executor executor, int size) {
		this.disruptor = new Disruptor<QueueEvent>(eventFactory, size, executor);
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void start() {
		if (isStarted.get()) {
			return;
		}
		if (name == null) {
			throw new NullPointerException("Queue name can not be null");
		}
		if (DEFAULT_NAME.equals(name)) {
			throw new RuntimeException("You are named queue with default name, you should give it a new unique name");
		}
		if (this.threads <= 0) {
			throw new RuntimeException("Threads must more than 0");
		}
		if (this.eventListener == null) {
			throw new RuntimeException("EventListener must be set");
		}
		if (this.queueContainer == null) {
			throw new RuntimeException("QueueContainer must not be null");
		}
		if (this.dependents != null) {
			for (String n : dependents) {
				if (n.equals(name)) {
					throw new RuntimeException("Queue can not denpend itself");
				}
			}
		}
		this.disruptor.handleExceptionsWith(new DefaultExceptionHandler());
		ProxyWorkHandler[] handlers = new ProxyWorkHandler[threads];
		for (int i = 0; i < threads; i++) {
			ProxyWorkHandler handler = new ProxyWorkHandler(this.eventListener, getName(), exceptionQueue);
			handler.setLogUseTime(logUseTime);
			handler.setDependents(dependents);
			handlers[i] = handler;
		}
		this.disruptor.handleEventsWithWorkerPool(handlers);
		log.info("Handler listeners with {} thread in queue {}", threads, getName());
		this.ringBuffer = this.disruptor.start();
		isStarted.set(true);
		log.info("Queue {} started", getName());
	}
	
	@Override
	public void shutdown() {
		if (!isStarted.get()) {
			return;
		}
		this.disruptor.shutdown();
		isStarted.set(false);
		if (this.eventListener != null) {
			this.eventListener.destroy();
		}
		log.info("Queue {} shutdown", getName());
	}

	@Override
	public void publish(EventData data) {
		this.ringBuffer.publishEvent(translator, data);
	}
	
	@Override
	public boolean hasAvailableCapacity() {
		return this.ringBuffer.hasAvailableCapacity(DEFAULT_CHECK_CAPACITY);
	}
	
	@Override
	public boolean hasDependents() {
		return this.dependents != null;
	}
	
	@Override
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	@Override
	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}
	
	@Override
	public void setQueueContainer(QueueContainer queueContainer) {
		this.queueContainer = queueContainer;
	}
	
	public void setTranslator(EventTranslatorOneArg<QueueEvent, EventData> translator) {
		this.translator = translator;
	}

	@Override
	public void setDependents(List<String> dependents) {
		this.dependents = dependents;
	}
	
	@Override
	public void setExceptionQuene(String queueName) {
		this.exceptionQueue = queueName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLogUseTime(boolean logUseTime) {
		this.logUseTime = logUseTime;
	}

}
