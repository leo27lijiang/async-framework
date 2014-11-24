package com.lefu.async.disruptor;

import com.lefu.async.EventData;
import com.lefu.async.QueueEvent;

/**
 * 默认的事件
 * @author jiang.li
 *
 */
public class DefaultEvent implements QueueEvent {
	private EventData data;
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public EventData getEventData() {
		return this.data;
	}

	@Override
	public void setEventData(EventData data) {
		this.data = data;
	}

}
