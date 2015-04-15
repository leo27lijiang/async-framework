package com.lefu.async.util;

public class SimpleUtil {
	/**
	 * 为流程中的锁对象生成一个Key值
	 * @param queueName
	 * @return
	 */
	public static String generateKeyByQueueName(String queueName) {
		StringBuffer sb = new StringBuffer();
		sb.append("__");
		sb.append(queueName);
		sb.append("__");
		return sb.toString();
	}
	
}
