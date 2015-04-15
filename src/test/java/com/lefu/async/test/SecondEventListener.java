package com.lefu.async.test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.lefu.async.EventListener;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;

public class SecondEventListener implements EventListener {
	private AtomicInteger counter = new AtomicInteger(0);
	
	@Override
	public void onEvent(QueueEvent e, List<PublishPair> futureList) throws Exception {
		int result = new Random().nextInt(10000);
		if (result > 9990) {
			throw new RuntimeException("Random count " + result);
		}
		counter.incrementAndGet();
	}

	@Override
	public void destroy() {
		System.out.println("Finally count is " + counter.get());
	}

}
