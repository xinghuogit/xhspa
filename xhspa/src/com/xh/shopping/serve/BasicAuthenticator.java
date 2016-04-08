/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：BasicAuthenticator.java
 * 内容摘要：BasicAuthenticator.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-8 下午9:13:45
 * 修改记录：
 * 修改日期：2016-4-8 下午9:13:45
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：BasicAuthenticator.java
 * @contents 内容摘要：验证
 */
public class BasicAuthenticator extends Authenticator {
	private String userName;
	private String password;

	public BasicAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Called when password authorization is needed. Subclasses should override
	 * the default implementation, which returns null.
	 * 
	 * @return The PasswordAuthentication collected from the user, or null if
	 *         none is provided.
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password.toCharArray());
	}
}
