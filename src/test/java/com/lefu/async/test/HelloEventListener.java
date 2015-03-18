package com.lefu.async.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventListener;
import com.lefu.async.EventStatus;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;

public class HelloEventListener implements EventListener {
	public static final String QUEUE_NAME = "HelloQueue";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public HelloEventListener() {
		
	}
	
	@Override
	public List<PublishPair> onEvent(QueueEvent e) throws Exception {
		HelloEventData data = (HelloEventData)e.getEventData();
//		log.info("Get data message {}", data.getMessage());
		EventStatus status = data.getFlow().getQueueEventStatus(TipEventListener.QUEUE_NAME);
		if (status.compareTo(EventStatus.FINISHED) != 0 ){
			log.warn("Status from TipEventListener is {}", status);
		}
		return null;
	}

}
