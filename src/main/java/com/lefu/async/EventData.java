package com.lefu.async;

/**
 * 事件数据定义
 * @author jiang.li
 *
 */
public interface EventData {
	/**
	 * 获取关联的流程
	 * @return
	 */
	public Flow getFlow();
}
