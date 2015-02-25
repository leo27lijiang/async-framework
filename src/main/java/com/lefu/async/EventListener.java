package com.lefu.async;

import java.util.List;

/**
 * 事件监听器
 * @author jiang.li
 *
 */
public interface EventListener {
	/**
	 * 事件处理
	 * @param e
	 * @return 返回的待分发事件列表，默认会按照列表的顺序分发事件到对应的队列中去，如果为null则不进行分发
	 * @throws Exception
	 */
	public List<PublishPair> onEvent(QueueEvent e) throws Exception;
}
