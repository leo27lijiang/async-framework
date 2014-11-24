package com.lefu.async.disruptor;

import com.lefu.async.EventData;
import com.lefu.async.QueueEvent;
import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * 默认的赋值实现
 * @author jiang.li
 *
 */
public class DefaultEventDataTranslator implements EventTranslatorOneArg<QueueEvent, EventData> {

	@Override
	public void translateTo(QueueEvent event, long sequence, EventData data) {
		event.setEventData(data);
	}

}
