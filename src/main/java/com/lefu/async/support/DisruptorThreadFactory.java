package com.lefu.async.support;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认的线程工程
 * @author jiang.li
 *
 */
public class DisruptorThreadFactory implements ThreadFactory {
	/**
	 * 线程前缀
	 */
	public static String ThreadPreffix = "Disruptor-";
	/**
	 * 全局计数器
	 */
	private static final AtomicInteger count = new AtomicInteger();
	
	private String preffix = ThreadPreffix;
	
	public DisruptorThreadFactory() {
		
	}
	
	public DisruptorThreadFactory(String preffix) {
		this.preffix = preffix;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(this.preffix + String.valueOf(count.getAndIncrement()));
		return t;
	}

}
