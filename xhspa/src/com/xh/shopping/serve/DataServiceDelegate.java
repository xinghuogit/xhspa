/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：DataServiceDelegate.java
 * 内容摘要：DataServiceDelegate.java
 * 当前版本：1
 * 作        者：李加蒙
 * 完成日期：2016-3-31 下午1:38:23
 * 修改记录：
 * 修改日期：2016-3-31 下午1:38:23
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：DataServiceDelegate.java
 * @contents 内容摘要：数据Service委派接口
 * @models Delegate
 */
public interface DataServiceDelegate {
	/**
	 * Service成功后调用的接口方法
	 * 
	 * @param service
	 *            当前Service
	 * @param object
	 *            返回Data
	 */
	void onServiceSuccess(DataService service, Object object);

	/**
	 * Service失败后调用的接口方法
	 * 
	 * @param service
	 *            当前Service
	 * @param ret
	 *            异常原因，如无则传空
	 */
	void onServiceFailure(DataService service, String ret);
}
