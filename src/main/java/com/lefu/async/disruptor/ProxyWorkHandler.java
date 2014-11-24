package com.lefu.async.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventListener;
import com.lefu.async.EventStatus;
import com.lefu.async.Flow;
import com.lefu.async.QueueEvent;
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
	private boolean logUseTime = false;
	
	public ProxyWorkHandler(EventListener listener, String queueName) {
		this.eventListener = listener;
		this.queueName = queueName;
	}
	
	@Override
	public void onEvent(QueueEvent event) throws Exception {
		Flow flow = event.getEventData().getFlow();
		try {
			flow.putQueueEventStatus(queueName, EventStatus.STARTED);
			long start = System.currentTimeMillis();
			eventListener.onEvent(event);
			long end = System.currentTimeMillis();
			flow.putQueueEventStatus(queueName, EventStatus.FINISHED);
			if (logUseTime) {
				log.info(String.format("Event from queue %1$s use time %2$d ms", queueName, (end - start)));
			}
		} catch (Throwable e) {
			log.error(String.format("Catch exception from [%1$s], set event statuts to %2$s", queueName, EventStatus.EXCEPTION.toString()), e);
			try {
				if (flow != null) {
					flow.putQueueEventStatus(queueName, EventStatus.EXCEPTION);
				}
			} catch (Exception q) {
				q.printStackTrace();
			}
		}
	}

	public void setLogUseTime(boolean logUseTime) {
		this.logUseTime = logUseTime;
	}

}
