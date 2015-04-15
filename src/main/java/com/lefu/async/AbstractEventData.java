package com.lefu.async;

/**
 * 抽象事件数据，关联流程Flow
 * @author jiang.li
 *
 */
public abstract class AbstractEventData implements EventData {
	private Flow flow;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	
}
