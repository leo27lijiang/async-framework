package com.lefu.async.test;

import com.lefu.async.EventData;
import com.lefu.async.Flow;

public class HelloEventData implements EventData {
	private Flow flow;
	private String message;
	
	public HelloEventData() {
		
	}
	
	public HelloEventData(Flow flow) {
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
