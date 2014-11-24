package com.lefu.async.disruptor;

import com.lefu.async.QueueEvent;
import com.lmax.disruptor.EventFactory;

public class DefaultEventFactory implements EventFactory<QueueEvent> {

	@Override
	public QueueEvent newInstance() {
		return new DefaultEvent();
	}

}
