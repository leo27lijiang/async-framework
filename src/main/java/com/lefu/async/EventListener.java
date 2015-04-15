package com.lefu.async;

import java.util.List;

/**
 * 事件监听器
 * @author jiang.li
 *
 */
public interface EventListener extends Destroy {
	/**
	 * 事件处理
	 * @param e
	 * @param futureList 待分发事件列表，默认会按照列表的添加顺序分发事件到对应的队列中去
	 * @throws Exception
	 */
	public void onEvent(QueueEvent e, List<PublishPair> futureList) throws Exception;
	
}
