/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：HomeAdapter.java
 * 内容摘要：HomeAdapter.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-15 下午5:16:19
 * 修改记录：
 * 修改日期：2016-4-15 下午5:16:19
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.adapter;

import com.xh.shopping.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：HomeAdapter.java
 * @contents 内容摘要：
 */
public class HomeAdapter extends BasicAdapter {

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(R.layout.list_imte_product2,
					parent, false);
			viewHolder = new ViewHolder();
			viewHolder.iv1 = findView(convertView, R.id.list_item_product2_iv1);
			viewHolder.iv2 = findView(convertView, R.id.list_item_product2_iv2);
			viewHolder.name1 = findView(convertView,
					R.id.list_item_product2_name1);
			viewHolder.name2 = findView(convertView,
					R.id.list_item_product2_name2);
			viewHolder.price1 = findView(convertView,
					R.id.list_item_product2_price1);
			viewHolder.price2 = findView(convertView,
					R.id.list_item_product2_price2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	public class ViewHolder {
		public ImageView iv1, iv2;
		public TextView name1, name2;
		public TextView price1, price2;
	}

}
