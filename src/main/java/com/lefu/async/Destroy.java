package com.lefu.async;

/**
 * 标识资源需要清理
 * @author jiang.li
 *
 */
public interface Destroy {
	/**
	 * 销毁回收资源
	 */
	public void destroy();
	
}
