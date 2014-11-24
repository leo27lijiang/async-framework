package com.lefu.async;

/**
 * 队列事件的基础定义
 * @author jiang.li
 *
 */
public interface QueueEvent {
	/**
	 * 获取事件名称
	 * @return
	 */
	public String getName();
	/**
	 * 获取事件中传递的对象
	 * @return
	 */
	public EventData getEventData();
	/**
	 * 设置将要传递的事件数据
	 * <pre>
	 * {@link QueueEvent} 在队列初始化时每个队列位置只会实例化一个，所以这里设置的 {@link EventData} 是会自动覆盖上一次的数据！
	 * </pre>
	 * @param data
	 */
	public void setEventData(EventData data);
}
