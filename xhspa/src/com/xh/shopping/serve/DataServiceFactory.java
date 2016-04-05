/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：DataServiceFactory.java
 * 内容摘要：DataServiceFactory.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-5 下午1:59:24
 * 修改记录：
 * 修改日期：2016-4-5 下午1:59:24
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：DataServiceFactory.java
 * @contents 内容摘要：DataService工厂类，管理任务线程池
 */
public class DataServiceFactory {
	/**
	 * @return DataServiceFactory的对象
	 */
	private static DataServiceFactory dataServiceFactory;
	/**
	 * 实例化一个固定的线程池
	 */
	private static ExecutorService executorService = Executors
			.newFixedThreadPool(3);

	/**
	 * 实例化DataServiceFactory的对象
	 */
	static {
		if (dataServiceFactory == null) {
			dataServiceFactory = new DataServiceFactory();
		}
	}

	/**
	 * 私有构造方法
	 */
	private DataServiceFactory() {
	}

	/**
	 * @return 返回DataServiceFactory的对象
	 */
	public static DataServiceFactory getInstance() {
		return dataServiceFactory;
	}

	/**
	 * @return 返回ExecutorService的对象 一个线程池
	 */
	public ExecutorService getExecutorService() {
		return executorService;
	}

	/**
	 * 取消全部数据服务
	 * 
	 * @shutdown 执行后不再接收新任务，如果里面有任务，就执行完
	 * @shutdownNow 执行后不再接受新任务，如果有等待任务，移出队列；有正在执行的，尝试停止之
	 */
	public synchronized void cancelAllDataService() {
		ExecutorService tmpExecutorService = executorService;
		executorService = Executors.newFixedThreadPool(3);
		tmpExecutorService.shutdownNow();
	}

	/**
	 * 停止全部服务
	 * 
	 * @shutdown 执行后不再接收新任务，如果里面有任务，就执行完
	 * @shutdownNow 执行后不再接受新任务，如果有等待任务，移出队列；有正在执行的，尝试停止之
	 * @awaitTermination 用于等待子线程结束，再继续执行下面的代码。该例中我设置一直等着子线程结束。
	 * @Thread.currentThread().interrupt(); 关闭当前线程
	 */
	public void stopAllService() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
				if (executorService.awaitTermination(5, TimeUnit.SECONDS)) {
				}
			}
		} catch (Exception e) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

}
