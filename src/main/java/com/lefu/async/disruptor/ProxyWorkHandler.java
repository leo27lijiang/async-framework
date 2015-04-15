package com.lefu.async.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventListener;
import com.lefu.async.EventStatus;
import com.lefu.async.Flow;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;
import com.lefu.async.QueueNotFoundException;
import com.lefu.async.util.SimpleUtil;
import com.lmax.disruptor.WorkHandler;

/**
 * 代理工作句柄实现
 * @author jiang.li
 *
 */
public class ProxyWorkHandler implements WorkHandler<QueueEvent> {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final EventListener eventListener;
	private final String queueName;
	private final String exceptionQueue;
	private boolean logUseTime = false;
	private List<String> dependents;
	
	public ProxyWorkHandler(EventListener listener, String queueName, String exceptionQueue) {
		this.eventListener = listener;
		this.queueName = queueName;
		this.exceptionQueue = exceptionQueue;
	}
	
	@Override
	public void onEvent(QueueEvent event) throws Exception {
		Flow flow = event.getEventData().getFlow();
		if (this.dependents != null) {
			ReentrantLock lock = (ReentrantLock)flow.getContext().getAttr(SimpleUtil.generateKeyByQueueName(queueName));
			lock.lock();
			try {
				EventStatus local = flow.getQueueEventStatus(queueName);
				if (!local.equals(EventStatus.UNKOWN)) {
					if (log.isDebugEnabled()) {
						log.debug("Queue {} event already started", queueName);
					}
					return;
				}
				boolean finished = true;
				for (String n : dependents) {
					EventStatus status = flow.getQueueEventStatus(n);
					if (!status.equals(EventStatus.FINISHED)) {
						finished = false;
						break;
					}
				}
				if (!finished) {
					if (log.isDebugEnabled()) {
						log.debug("Dependents for {} not finished", this.queueName);
					}
					return;
				}
				flow.putQueueEventStatus(queueName, EventStatus.STARTED);
				if (log.isDebugEnabled()) {
					log.debug("{} first start queue event", queueName);
				}
			} finally {
				lock.unlock();
			}
		}
		try {
			if (this.dependents == null) {
				flow.putQueueEventStatus(queueName, EventStatus.STARTED);
			}
			long start = System.currentTimeMillis();
			List<PublishPair> futureList = new ArrayList<PublishPair>();
			eventListener.onEvent(event, futureList);
			long end = System.currentTimeMillis();
			flow.putQueueEventStatus(queueName, EventStatus.FINISHED);
			if (!futureList.isEmpty()) {
				try {
					for (PublishPair p : futureList) {
						if (p.getQueueName().equals(queueName)) {
							throw new QueueNotFoundException("Can not publish event to itself");
						}
						flow.getQueueContainer().publish(p.getQueueName(), p.getData());
					}
				} catch (QueueNotFoundException qnt) {
					qnt.printStackTrace();
				}
				futureList.clear();//Clean
			}
			if (logUseTime) {
				log.info(String.format("Event from queue %1$s use time %2$d ms", queueName, (end - start)));
			}
		} catch (Throwable e) {
			log.error(String.format("Catch exception from [%1$s]", queueName), e);
			try {
				if (flow != null) {
					flow.putQueueEventStatus(queueName, EventStatus.EXCEPTION);
				}
				if (this.exceptionQueue != null && !(queueName.equals(exceptionQueue))) {
					flow.getQueueContainer().publish(exceptionQueue, event.getEventData());
				}
			} catch (QueueNotFoundException q) {
				q.printStackTrace();
			}
		} finally {
			event.setEventData(null);//Clean event data reference
		}
	}

	public void setLogUseTime(boolean logUseTime) {
		this.logUseTime = logUseTime;
	}

	public void setDependents(List<String> dependents) {
		this.dependents = dependents;
	}

}
