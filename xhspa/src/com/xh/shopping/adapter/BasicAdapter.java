/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：BaseAdapter.java
 * 内容摘要：BaseAdapter.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午10:24:38
 * 修改记录：
 * 修改日期：2016-4-13 上午10:24:38
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：BaseAdapter.java
 * @contents 内容摘要：
 */
public abstract class BasicAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Object> datas = new ArrayList<Object>();

	public BasicAdapter() {
	}

	public BasicAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	protected LayoutInflater getInflater() {
		return inflater;
	}

	@Override
	public int getCount() {
		if (datas != null) {
			return datas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (datas != null) {
			datas.get(position);
		}
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 设置数据
	 * 
	 * @param data
	 */
	@SuppressWarnings("rawtypes")
	public void setData(Object data) {
		if (data == null) {
			return;
		}
		if (datas != data) {
			datas.clear();
			if (data != null) {
				if (data instanceof List<?>) {
					for (Object object : (List) data)
						datas.add(object);
				}
			}
		}
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected List getData() {
		return datas;
	}

	/**
	 * 寻找id
	 * @param view
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T findView(View view, int id) {
		return (T) view.findViewById(id);
	}
}
