package com.lefu.async.test;

import java.util.List;
import java.util.Random;

import com.lefu.async.EventListener;
import com.lefu.async.Flow;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;

public class FisrtEventListener implements EventListener {

	@Override
	public void destroy() {

	}

	@Override
	public void onEvent(QueueEvent e, List<PublishPair> futureList) throws Exception {
		int result = new Random().nextInt(100000);
		if (result > 99950) {
			throw new RuntimeException("Random count " + result);
		}
		FirstEventData data = (FirstEventData) e.getEventData();
		Flow flow = data.getFlow();
		TipEventData tipData = new TipEventData(flow);
		tipData.setMessage(data.getMessage());
		futureList.add(new PublishPair(QueueTest.TIP, tipData));
		HelloEventData helloData = new HelloEventData(flow);
		helloData.setMessage(data.getMessage());
		futureList.add(new PublishPair(QueueTest.HELLO, helloData));
	}

}
