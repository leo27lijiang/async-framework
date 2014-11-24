package com.lefu.async.test;

import com.lefu.async.EventData;
import com.lefu.async.Flow;

public class TipEventData implements EventData {
	private Flow flow;
	private String message;
	
	public TipEventData(Flow flow) {
		this.flow = flow;
	}
	
	@Override
	public Flow getFlow() {
		return this.flow;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
