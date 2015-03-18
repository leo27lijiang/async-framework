package com.lefu.async.test;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.async.EventListener;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;

public class TipEventListener implements EventListener {
	public static final String QUEUE_NAME = "TipQueue";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<PublishPair> onEvent(QueueEvent e) throws Exception {
		TipEventData data = (TipEventData) e.getEventData();
//		log.info("Get data message {}", data.getMessage());
		HelloEventData second = new HelloEventData(data.getFlow());
		second.setMessage("-->" + data.getMessage());
		List<PublishPair> events = new ArrayList<PublishPair>();
		PublishPair pair = new PublishPair(HelloEventListener.QUEUE_NAME, second);
		events.add(pair);
		return events;
	}

}
