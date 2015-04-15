package com.lefu.async.test;

import com.lefu.async.AbstractEventData;
import com.lefu.async.Flow;

public class FirstEventData extends AbstractEventData {
	private String message;
	
	public FirstEventData() {
		
	}
	
	public FirstEventData(Flow flow) {
		setFlow(flow);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}