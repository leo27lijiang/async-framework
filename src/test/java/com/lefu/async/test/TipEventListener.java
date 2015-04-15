package com.lefu.async.test;

import java.util.List;
import java.util.Random;

import com.lefu.async.EventListener;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;

public class TipEventListener implements EventListener {
	
	@Override
	public void onEvent(QueueEvent e, List<PublishPair> futureList) throws Exception {
		TipEventData data = (TipEventData) e.getEventData();
		SecondEventData second = new SecondEventData(data.getFlow());
		second.setMessage(data.getMessage());
		futureList.add(new PublishPair(QueueTest.SECOND, second));
		Random random = new Random();
		Thread.sleep(random.nextInt(5));
	}

	@Override
	public void destroy() {
		
	}

}
