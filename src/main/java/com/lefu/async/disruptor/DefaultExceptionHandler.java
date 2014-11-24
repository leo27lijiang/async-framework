package com.lefu.async.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.ExceptionHandler;

/**
 * 默认的异常捕获句柄
 * @author jiang.li
 *
 */
public class DefaultExceptionHandler implements ExceptionHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void handleEventException(Throwable ex, long sequence, Object event) {
		log.error(event.toString(), ex);
	}

	@Override
	public void handleOnStartException(Throwable ex) {
		ex.printStackTrace();
	}

	@Override
	public void handleOnShutdownException(Throwable ex) {
		ex.printStackTrace();
	}

}
