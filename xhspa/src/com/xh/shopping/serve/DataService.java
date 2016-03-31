/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：DataService.java
 * 内容摘要：DataService.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-19 下午6:08:40
 * 修改记录：
 * 修改日期：2015-12-19 下午6:08:40
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：DataService.java
 * @contents 内容摘要：数据Service接口
 */
public interface DataService {
	/**
	 * 继承DataServiceDelegate，设置当前Activity
	 * 
	 * @param delegate
	 *            当前Activity
	 */
	public void setDataService(DataServiceDelegate delegate);

	/**
	 * 开启Service
	 */
	public void start();

	/**
	 * 取消Service
	 */
	public void cancll();
}
