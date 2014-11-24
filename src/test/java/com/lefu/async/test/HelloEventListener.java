package com.lefu.async.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventListener;
import com.lefu.async.QueueEvent;

public class HelloEventListener implements EventListener {
	public static final String QUEUE_NAME = "HelloQueue";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public HelloEventListener() {
		
	}
	
	@Override
	public void onEvent(QueueEvent e) throws Exception {
		HelloEventData data = (HelloEventData)e.getEventData();
		log.info("Get data message {}", data.getMessage());
	}

}
