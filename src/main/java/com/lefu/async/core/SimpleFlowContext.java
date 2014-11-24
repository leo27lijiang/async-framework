package com.lefu.async.core;

import java.util.Hashtable;
import java.util.Map;

import com.lefu.async.FlowContext;

/**
 * 简单实现
 * @author jiang.li
 *
 */
public class SimpleFlowContext implements FlowContext {
	private final Map<Object, Object> map = new Hashtable<Object, Object>();
	
	public SimpleFlowContext() {
		
	}
	
	@Override
	public void putAttr(Object key, Object value) {
		this.map.put(key, value);
	}

	@Override
	public Object getAttr(Object key) {
		return this.map.get(key);
	}

}
