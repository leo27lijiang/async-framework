package com.lefu.async;

/**
 * 提供线程安全的流程上下文
 * @author jiang.li
 *
 */
public interface FlowContext extends Destroy {
	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void putAttr(Object key, Object value);
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getAttr(Object key);
}
