/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：TestService.java
 * 内容摘要：TestService.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-12 下午2:00:32
 * 修改记录：
 * 修改日期：2016-4-12 下午2:00:32
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve.extend;

import java.util.List;

import org.json.JSONObject;

import com.xh.shopping.serve.DataServiceImplJSON;
import com.xh.shopping.util.PartUtil;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：TestService.java
 * @contents 内容摘要：
 */
public class TestUploadService extends DataServiceImplJSON {

	List<PartUtil> list;

	public List<PartUtil> getList() {
		return list;
	}

	public void setList(List<PartUtil> list) {
		this.list = list;
	}

	@Override
	protected String getFullURL() {
		return "http://192.168.31.133/xfzxapi/api/Bychance/Input.json";
		// return constructFullURL("upload.json");
	}

	@Override
	protected Object getParams() {
		return getList();
	}

	@Override
	protected Object buildObjectModel(JSONObject jsonObject) {
		return jsonObject;
	}
}
