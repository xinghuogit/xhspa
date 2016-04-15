/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：Product.java
 * 内容摘要：Product.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-15 下午5:03:23
 * 修改记录：
 * 修改日期：2016-4-15 下午5:03:23
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.model;

import java.io.Serializable;
import org.json.JSONObject;

import com.xh.shopping.util.StringUtil;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：Product.java
 * @contents 内容摘要：商品信息 bean
 */
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String descr;
	private String normalPrice;
	private String memberPrice;
	private String pdate;
	private String categoryId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(String memberPrice) {
		this.memberPrice = memberPrice;
	}

	public String getPdate() {
		return pdate;
	}

	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void parseJSON(JSONObject json) {
		if (json == null) {
			return;
		}
		setId(json.optString("id"));
		setName(json.optString("name"));
		setDescr(StringUtil.isEmpty(json.optString("descr")) ? "暂无描述" : json
				.optString("descr"));
		setNormalPrice(json.optString("normalPrice"));
		setMemberPrice(json.optString("memberPrice"));
		setPdate(json.optString("pdate"));
		setCategoryId(json.optString("categoryId"));
	}
}
