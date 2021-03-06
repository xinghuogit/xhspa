/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：StringPartUtil.java
 * 内容摘要：StringPartUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-12 下午4:46:37
 * 修改记录：
 * 修改日期：2016-4-12 下午4:46:37
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import com.mob.tools.network.MultiPart;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：StringPartUtil.java
 * @contents 内容摘要：
 */
public class StringPartUtil extends PartUtil {
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public StringPartUtil(String key, String value) {
		setKey(key);
		setValue(value);
	}
}
