package com.lefu.async.test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.lefu.async.EventListener;
import com.lefu.async.PublishPair;
import com.lefu.async.QueueEvent;

public class ExceptionEventListener implements EventListener {
	private AtomicInteger counter = new AtomicInteger(0);
	
	@Override
	public void destroy() {
		System.out.println("Finally exception count is " + counter.get());
	}

	@Override
	public void onEvent(QueueEvent e, List<PublishPair> futureList)
			throws Exception {
		counter.incrementAndGet();
	}

}
