package com.lefu.async.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventListener;
import com.lefu.async.QueueEvent;

public class TipEventListener implements EventListener {
	public static final String QUEUE_NAME = "TipQueue";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onEvent(QueueEvent e) throws Exception {
		TipEventData data = (TipEventData) e.getEventData();
		Thread.sleep(15);
		log.info("Get data message {}", data.getMessage());
		if (data.getMessage().startsWith("10")) {
			throw new RuntimeException();
		}
		HelloEventData second = new HelloEventData(data.getFlow());
		second.setMessage("-->" + data.getMessage());
		data.getFlow().getQueueContainer().publish(HelloEventListener.QUEUE_NAME, second);
	}

}
