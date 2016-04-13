/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：GridImagesAdapter.java
 * 内容摘要：GridImagesAdapter.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午10:22:11
 * 修改记录：
 * 修改日期：2016-4-13 上午10:22:11
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xh.shopping.R;
import com.xh.shopping.util.BitmapUtil;
import com.xh.shopping.util.ImageUtil;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：GridImagesAdapter.java
 * @contents 内容摘要：
 */
public class GridImagesAdapter extends BasicAdapter {

	public GridImagesAdapter(Context context) {
		super(context);
	}

	@Override
	public int getCount() {
		if (getData().size() == BitmapUtil.num) {
			return getData().size();
		}
		return getData().size() + 1;
	}

	@Override
	public Object getItem(int position) {
		if (getData().size() == BitmapUtil.num) {
			return position;
		}
		return getData().get(position);
	}

	@Override
	public long getItemId(int position) {
		if (getData().size() == BitmapUtil.num) {
			return position;
		}
		return getData().size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = getInflater().inflate(R.layout.grid_item_image,
					parent, false);
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == BitmapUtil.tempSelectBitmap.size()) {
			holder.image.setImageResource(R.drawable.icon_addpic_focused);
			if (position == BitmapUtil.num) {
				holder.image.setVisibility(View.GONE);
			}
		} else {
			String item = (String) getData().get(position);
			holder.image.setImageBitmap(ImageUtil.getBitmap1(item));
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}
}
