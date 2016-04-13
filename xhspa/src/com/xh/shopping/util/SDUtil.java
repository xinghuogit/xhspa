package com.xh.shopping.util;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SDUtil {
	Activity activity;

	public SDUtil(Activity activity) {
		this.activity = activity;
	}

	public void outDeveil() {
		System.out.println("全部SD" + getSDTotalSize() + "\n可用SD"
				+ getSDAvailableSize() + "\n全部ROM" + getRomTotalSize()
				+ "\n可用ROM" + getRomAvailableSize()

		);
	}

	public int getSDKINT() {
		return android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 获得SD卡总大小
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getSDTotalSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long totalBlocks;
		if (getSDKINT() >= 18) {
			blockSize = stat.getBlockSizeLong();
			totalBlocks = stat.getBlockCountLong();
		} else {
			blockSize = stat.getBlockSize();
			totalBlocks = stat.getBlockCount();
		}
		return Formatter.formatFileSize(activity, blockSize * totalBlocks);
	}

	/**
	 * 获得sd卡剩余容量，即可用大小
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getSDAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long availableBlocks;
		if (getSDKINT() >= 18) {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getBlockCount();
		}
		return Formatter.formatFileSize(activity, blockSize * availableBlocks);
	}

	/**
	 * 获得机身内存总大小
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getRomTotalSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long totalBlocks;
		if (getSDKINT() >= 18) {
			blockSize = stat.getBlockSizeLong();
			totalBlocks = stat.getBlockCountLong();
		} else {
			blockSize = stat.getBlockSize();
			totalBlocks = stat.getBlockCount();
		}
		return Formatter.formatFileSize(activity, blockSize * totalBlocks);
	}

	/**
	 * 获得机身可用内存
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getRomAvailableSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long availableBlocks;
		if (getSDKINT() >= 18) {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getAvailableBlocks();
		}
		return Formatter.formatFileSize(activity, blockSize * availableBlocks);
	}

}
