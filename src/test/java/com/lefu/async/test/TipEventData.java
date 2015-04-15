package com.lefu.async.test;

import com.lefu.async.AbstractEventData;
import com.lefu.async.Flow;

public class TipEventData extends AbstractEventData {
	private String message;
	
	public TipEventData(Flow flow) {
		setFlow(flow);
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
