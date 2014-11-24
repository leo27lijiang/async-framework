package com.lefu.async.support;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 线程池工程
 * @author jiang.li
 *
 */
public class ThreadPoolFactory {
	public static final Executor EXECUTOR = Executors.newCachedThreadPool(new DisruptorThreadFactory());
	
	/**
	 * 创建缓冲线程池
	 * @param preffix 线程工厂使用的线程前缀
	 * @return
	 */
	public static Executor newCachedThreadPool(String preffix) {
		return Executors.newCachedThreadPool(new DisruptorThreadFactory(preffix));
	}
	
}
