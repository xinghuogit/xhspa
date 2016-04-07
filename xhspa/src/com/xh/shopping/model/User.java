/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：User.java
 * 内容摘要：User.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-20 下午5:14:05
 * 修改记录：
 * 修改日期：2015-12-20 下午5:14:05
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.util.StringUtil;

/**
 * @filename 文件名称：User.java
 * @contents 内容摘要：用户资料数据
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userid;
	private String username;
	private String password;
	private String phone;
	private String nickname;
	private String addr;
	private String radte;
	private String cpdate;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRadte() {
		return radte;
	}

	public void setRadte(String radte) {
		this.radte = radte;
	}

	public String getCpdate() {
		return cpdate;
	}

	public void setCpdate(String cpdate) {
		this.cpdate = cpdate;
	}

	public void parseJSON(JSONObject json) {
		try {
			JSONObject body = json.getJSONObject("body");
			setUserid(body.optString("userid"));
			setUsername(body.optString("username"));
			setPassword(body.optString("password"));

			if (StringUtil.isEmpty(getUsername())) {
				System.out.println("返回数据异常，账号为空");
				return;
			}
			if (StringUtil.isEmpty(getPassword())) {
				System.out.println("返回数据异常，密码为空");
				return;
			}

			setPhone(body.optString("phone"));
			setNickname(body.optString("nickname"));
			setAddr(body.optString("addr"));
			setRadte(body.optString("radte"));
			setCpdate(body.optString("cpdate"));

			SettingHelper.getInstance().setUserInfo(this);
		} catch (JSONException e) {
			SettingHelper.getInstance().setUserInfo(null);
			e.printStackTrace();
		}

	}

}
