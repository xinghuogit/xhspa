/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：ModelFactory.java
 * 内容摘要：ModelFactory.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-15 下午5:05:20
 * 修改记录：
 * 修改日期：2016-4-15 下午5:05:20
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.model;

import org.json.JSONObject;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：ModelFactory.java
 * @contents 内容摘要：实体工厂类
 */
public class ModelFactory {
	private static ModelFactory singleton = new ModelFactory();

	private ModelFactory() {
	}

	public static ModelFactory getInstance() {
		return singleton;
	}

	/**
	 * 用户信息
	 * 
	 * @param json
	 * @return
	 */
	protected User newUser(JSONObject json) {
		User item = new User();
		item.parseJSON(json);
		return item;
	}

	/**
	 * 商品信息
	 * 
	 * @param json
	 * @return
	 */
	protected Product newProduct(JSONObject json) {
		Product item = new Product();
		item.parseJSON(json);
		return item;
	}
}
