/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：ImageUtil.java
 * 内容摘要：ImageUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午10:47:53
 * 修改记录：
 * 修改日期：2016-4-13 上午10:47:53
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.xh.shopping.setting.SettingHelper;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：ImageUtil.java
 * @contents 内容摘要：
 */
public class ImageUtil {
	private static AtomicInteger bitmapAllocationCount = new AtomicInteger();
	private static AtomicInteger bitmapRecycleCount = new AtomicInteger();
	private static final int LOG_THRESHOLD = 25;

	/** 水平方向模糊度 */
	private static float hRadius = 10;
	/** 竖直方向模糊度 */
	private static float vRadius = 10;
	/** 模糊迭代度 */
	private static int iterations = 3;

	/**
	 * compress the image of source url into a smaller one and return the image
	 * path of the generated one .
	 * 
	 * @param sourceUrl
	 * @param percentage
	 *            percentage of the image after the compression.
	 * @return the image path of the generated one 生成压缩缩放比例的图片 返回图片路径
	 */
	public static String generateCompressedImage(String sourceUrl) {
		if (sourceUrl == null) {
			return null;
		}
		File file = new File(sourceUrl);
		if (file.exists()) {
			String result = generateNewFileName(sourceUrl, "_compressed");
			Bitmap compressBitmap = getBitmapCompressed(sourceUrl);
			// try {
			// System.out.println(sourceUrl + " 路径");
			// getThumbUploadPath(sourceUrl, 480, result);
			// } catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("抛出异常");
			// }
			saveBitmapToTargetPath(compressBitmap, result);// 未进行图片质量压缩
			return result;
		}
		return null;

	}

	/**
	 * 
	 * @param sourceUrl
	 * @return
	 */
	public static String generateCompressedImagea(String sourceUrl) {
		if (sourceUrl == null) {
			return null;
		}
		File file = new File(sourceUrl);
		if (file.exists()) {
			String result = generateNewFileName(sourceUrl, "__compressed");
//			Bitmap compressBitmap = getBitmapCompressed(sourceUrl);
			Bitmap bitmap = null;
			try {
				System.out.println("新的图片地址：" + sourceUrl);
				bitmap = getThumbUploadPath(sourceUrl, 480, result);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("抛出异常generateCompressedImagea");
			}
			// saveBitmapToTargetPath(compressBitmap, result);// 未进行图片质量压缩
			saveBitmapToTargetPath(bitmap, result);// 未进行图片质量压缩
			return result;
		}
		return null;

	}

	/**
	 * 根据路径生成旋转90°的压缩图片
	 * 
	 * @param srcPath
	 * @return
	 */
	public static boolean generateRotatedBitmapIfNecessary(String srcPath) {

		try {
			BitmapFactory.Options sourceBitmapOptions = new BitmapFactory.Options();
			sourceBitmapOptions.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(srcPath,
					sourceBitmapOptions);
			int widthBitmap = sourceBitmapOptions.outWidth;
			int heightBitmap = sourceBitmapOptions.outHeight;

			if (widthBitmap > heightBitmap) {
				bitmap = BitmapFactory.decodeFile(srcPath);
				bitmap = getBitmapRotated(bitmap, 90);
				saveBitmapToTargetPath(bitmap, srcPath);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * compress the image of source url into a smaller one and return the image
	 * path of the generated one .
	 * 
	 * @param sourceUrl
	 * @param percentage
	 *            percentage of the image after the compression.
	 * @return the image path of the generated one 生成压缩图片 返回bitmap
	 */
	public static Bitmap generateCompressBitmap(String sourceUrl) {
		if (sourceUrl == null) {
			return null;
		}
		File file = new File(sourceUrl);
		if (file.exists()) {
			String targetPath = generateNewFileName(sourceUrl, "_compressed");
			Bitmap compressBitmap = getBitmapCompressed(sourceUrl);
			saveBitmapToTargetPath(compressBitmap, targetPath);
			return compressBitmap;
		}
		return null;

	}

	/**
	 * compress the image of source url into a smaller one.
	 * 
	 * @param sourceUrl
	 * @return 用原来的名字后面加上新的文件名字
	 */
	public static String generateNewFileName(String sourceUrl, String addedName) {
		if (sourceUrl == null) {
			return null;
		}
		File file = new File(sourceUrl);
		if (file.exists()) {
			/**
			 * generate the new file name. for example, if sourceUrl
			 * ="/sdcard/1/1.jpg",then newUrl="/sdcard/1/1_100kb.jpg"
			 */
			String parent = file.getParent();
			String fileName = file.getName();
			String fileNameWithoutSubfix = fileName.substring(0,
					fileName.length() - 4);
			String subfix = fileName.substring(fileName.length() - 4);
			String result = parent + "/" + fileNameWithoutSubfix + addedName
					+ subfix;
			return result;
		}
		return null;

	}

	/**
	 * 保存一张质量压缩图片
	 * 
	 * @param bitmap
	 * @param targetPath
	 * @return
	 */
	public static boolean saveBitmapToTargetPath(Bitmap bitmap,
			String targetPath) {
		FileOutputStream fileOutputStream;
		File file = null;
		try {
			file = new File(targetPath);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
			byte[] bytes = stream.toByteArray();
			System.out.println("bytes.length" + bytes.length);
			fileOutputStream.write(bytes);
			fileOutputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	public static Bitmap getThumbUploadPath(String oldPath, int bitmapMaxWidth,
			String result) throws Exception {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(oldPath, options);
		// int height = options.outHeight;
		// int width = options.outWidth;
		// int reqHeight = 0;
		// int reqWidth = bitmapMaxWidth;
		// reqHeight = (reqWidth * height) / width;
		// 在内存中创建bitmap对象，这个对象按照缩放大小创建的
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		// System.out.println("calculateInSampleSize(options, 480, 800);==="
		// + calculateInSampleSize(options, 480, 800));
		options.inJustDecodeBounds = false;
		Bitmap bitmap = BitmapFactory.decodeFile(oldPath, options);
		// Log.e("asdasdas", "reqWidth->"+reqWidth+"---reqHeight->"+reqHeight);
		Bitmap bbb = compressImage(
				Bitmap.createScaledBitmap(bitmap, 480, 800, false), result);
		return bbb;
	}

	// TODO
	/**
	 * 质量压缩 小于500k
	 * 
	 * @param image
	 * @param result
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image, String result) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 500) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
			options -= 10;// 每次都减少10
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		saveBitmapToTargetPath(bitmap, result);
		return bitmap;
	}

	/**
	 * The algorithm of get the simple size: 1, get the image width and height.
	 * 2, get the device screen width and height. 3, if image width is larger
	 * than image height, then we use the value of (image width/ screen width)
	 * as the inSampleSize. 4, or else use (image height/ screen height).
	 * 
	 * @param sourceBitmapOption
	 * @return 获得 宽高 后的比例
	 */

	private static int getSimpleSize(BitmapFactory.Options sourceBitmapOption) {
		int widthBitmap = sourceBitmapOption.outWidth;
		int heightBitmap = sourceBitmapOption.outHeight;

		int[] widthAndHeight = DensityUtil
				.getScreenWidthAndHeight(SettingHelper.getInstance()
						.getCurrentActivity());
		int widthScreen = widthAndHeight[0];
		int heightScreen = widthAndHeight[1];

		int sampleSize = 1;
		if (widthBitmap > heightBitmap && widthBitmap > widthScreen) {
			sampleSize = (int) (widthBitmap / widthScreen);
		} else if (widthBitmap < heightBitmap && heightBitmap > heightScreen) {
			sampleSize = (int) (heightBitmap / heightScreen);
		}
		if (sampleSize <= 0) {
			sampleSize = 1;
		}
		return sampleSize;
	}

	/**
	 * 
	 * @param bitmap
	 * @param targetPath
	 * @param percent
	 *            压缩质量
	 * @return 保存一张给定压缩质量的图片
	 */
	public static boolean saveBitmapToTargetPath(Bitmap bitmap,
			String targetPath, int percent) {
		FileOutputStream fileOutputStream;
		File file = null;
		try {
			file = new File(targetPath);
			if (file.exists()) {
				file.delete();
				file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, percent, stream);
			byte[] bytes = stream.toByteArray();
			fileOutputStream.write(bytes);
			fileOutputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * FIXME is the the best way to rotate a bitmap?
	 * 
	 * @param sourceBitmap
	 * @param angle
	 * @return 返回旋转后的Bitamp
	 */
	public static Bitmap getBitmapRotated(Bitmap sourceBitmap, int angle) {
		if (sourceBitmap == null) {
			return null;
		}
		Bitmap result = null;
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.postRotate(angle);
		result = Bitmap
				.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(),
						sourceBitmap.getHeight(), matrix, true);
		return result;
	}

	/**
	 * get the compressed bitmap by image path.
	 * 
	 * pay attention: by calling this method, you just get a compress image
	 * without quality compression. if you want to get a smaller bitmap, you
	 * could call getImageCompressedWithQualityPecentage.
	 * 
	 * @param srcPath
	 * @return
	 */

	public static Bitmap getBitmapCompressed(String srcPath) {
		BitmapFactory.Options sourceBitmapOptions = new BitmapFactory.Options();
		sourceBitmapOptions.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, sourceBitmapOptions);
		sourceBitmapOptions.inSampleSize = getSimpleSize(sourceBitmapOptions);
		sourceBitmapOptions.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(srcPath, sourceBitmapOptions);
		return bitmap;
	}

	public static Bitmap getBitmapCompressed(String srcPath, int percentage) {
		return getBitmap(getBitmapCompressed(srcPath), percentage);
	}

	/**
	 * get the compressed image by Bitmap. you could also set percentage to get
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getBitmapCompressed(Bitmap image, int percentage) {
		return getBitmap(getBitmapCompressed(image), percentage);
	}

	/**
	 * get the compressed bitmap.
	 * 
	 * pay attention: by calling this method, you just get a compress image
	 * without quality compression. if you want to get a smaller bitmap, you
	 * could call getImageCompressedWithQualityPecentage.
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getBitmapCompressed(Bitmap sourceBitmap) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

		// TODO what happens if we don't compress quality.
		// if (baos.toByteArray().length / 1024 > 1024) {
		// baos.reset();
		// sourceBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
		// }

		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		// Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
		// newOpts);
		newOpts.inJustDecodeBounds = false;
		newOpts.inSampleSize = getSimpleSize(newOpts);
		return BitmapFactory.decodeStream(inputStream, null, newOpts);
	}

	/**
	 * get the image with target percent of quality .
	 * 
	 * @param source
	 * @param percentage
	 *            percentage of the image you want to get.
	 * @return
	 */
	public static Bitmap getBitmap(Bitmap source, int percentage) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		source.compress(Bitmap.CompressFormat.JPEG, percentage, baos);
		ByteArrayInputStream input = new ByteArrayInputStream(
				baos.toByteArray());
		Bitmap result = BitmapFactory.decodeStream(input, null, null);
		return result;
	}

	/**
	 * get the image by src path directly.
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getBitmap(String srcPath) {
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, null);
		return bitmap;
	}

	/**
	 * get the image by src path directly.
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getBitmap1(String srcPath) {
		FileInputStream fs = null;
		BufferedInputStream bs = null;
		try {
			makeRootDirectory(FileUtil.getBasePath().getAbsolutePath());
			fs = new FileInputStream(srcPath);
			bs = new BufferedInputStream(fs);
			Bitmap bitmap = BitmapFactory.decodeStream(bs);
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fs != null) {
					fs.close();
				}
				if (bs != null) {
					bs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				System.out.println("创建" + filePath);
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * the default buffer size.
	 */
	private static final int BUF_SIZE = 8192;

	/**
	 * get the byte stream for image for file which contains image binary data.
	 * 
	 * @param cacheKey
	 *            the hashcode of image url.
	 * @param cacheExpirationInMillis
	 * @return the byte array of an image file. 给定图片路径 获得图片byte流
	 */
	public static byte[] getBitmapByteStreamByImagePath(String imagePath) {
		File f = new File(imagePath);
		if (f.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);

				byte[] buffer = new byte[BUF_SIZE];
				ByteArrayOutputStream baos = new ByteArrayOutputStream(BUF_SIZE);
				int amtRead = fis.read(buffer, 0, BUF_SIZE);
				while (amtRead > 0) {
					baos.write(buffer, 0, amtRead);
					amtRead = fis.read(buffer, 0, BUF_SIZE);
				}
				return baos.toByteArray();
			} catch (Exception e) {
				return null;
			} finally {
				if (fis != null) {
					try {
						fis.close();

					} catch (Exception e) {
						// don't care
					}
				}
			}
		}

		return null;
	}

	/**
	 * This method returns a rounded corner bitmap from a regular bitmap.
	 * 这个方法返回一个圆角从常规的位图位图。
	 * 
	 * @param bitmap
	 *            The original bitmap. 原来的位图。
	 * @param pixels
	 *            The number of pixels for the rounded corner.圆角的像素数。
	 * @return The new Bitmap. 获得圆角图片
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		if (bitmap == null)
			return null;
		Bitmap output = ImageUtil.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		if (output != null) {
			Canvas canvas = new Canvas(output);

			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		}

		return output;
	}

	/**
	 * This method returns a rounded corner bitmap from a regular bitmap, and
	 * also has the ability to add a colored border.
	 * 这个方法返回一个圆角位图从常规的位图,并且还能添加一个颜色的边界。
	 * 
	 * @param bitmap
	 *            The original bitmap.
	 * @param color
	 *            The color to use for the border.
	 * @param borderWidth
	 *            The border width, in pixels.
	 * @param radius
	 *            The radius of the rounded corners.
	 * @return The new Bitmap.
	 */
	public static Bitmap getRoundedBorderBitmap(Bitmap bitmap, int color,
			int borderWidth, int radius) {
		int border = borderWidth;
		Bitmap output = ImageUtil.createBitmap(bitmap.getWidth() + border * 2,
				bitmap.getHeight() + border * 2, Config.ARGB_8888);
		if (output != null) {
			Canvas canvas = new Canvas(output);

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(
					new RectF(0, 0, output.getWidth(), output.getHeight()),
					radius, radius, paint);

			Rect rect = new Rect(border, border, bitmap.getWidth() + border,
					bitmap.getHeight() + border);
			Bitmap roundedCorner = getRoundedCornerBitmap(bitmap, 8);
			canvas.drawBitmap(roundedCorner, null, rect, paint);
			recycleOldBitmap(roundedCorner);
		}
		return output;
	}

	public static Bitmap getBorderBitmap(Bitmap bitmap, int color,
			int borderRatio) {

		int border = (bitmap.getWidth() > bitmap.getHeight() ? bitmap
				.getWidth() : bitmap.getHeight()) / borderRatio;
		Bitmap output = ImageUtil.createBitmap(bitmap.getWidth() + border * 2,
				bitmap.getHeight() + border * 2, Config.ARGB_8888);
		if (output != null) {
			Canvas canvas = new Canvas(output);

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRect(0, 0, output.getWidth(), output.getHeight(), paint);

			Rect rect = new Rect(border, border, bitmap.getWidth() + border,
					bitmap.getHeight() + border);
			canvas.drawBitmap(bitmap, null, rect, paint);
		}

		return output;
	}

	/**
	 * This method will return a stacked bitmap on top of an empty border
	 * bitmap.
	 * 
	 * @param bitmap
	 *            The original bitmap.
	 * @param color
	 *            The color of the border.
	 * @param borderRatio
	 *            The border ratio.
	 * @param layers
	 *            The number of layers.
	 * @return The new bitmap.
	 */
	public static Bitmap getStackedBitmap(Bitmap bitmap, int color,
			int borderRatio, int layers) {
		// TODO border : bitmap = 1 : 16, since the view's size is 75px*75px,
		// TBD
		Bitmap source = bitmap;
		Rect rect;
		int border = (source.getWidth() > source.getHeight() ? source
				.getWidth() : source.getHeight()) / borderRatio;
		final int offset = ((layers - 1) * 3 + 2) * border;
		Bitmap output = ImageUtil.createBitmap(source.getWidth() + offset,
				source.getHeight() + offset, Config.ARGB_8888);
		if (output != null) {
			Canvas canvas = new Canvas(output);

			Bitmap[] draw = new Bitmap[layers];
			for (int i = 0; i < layers; ++i) {
				if (i == 0)
					draw[i] = getBorderBitmap(bitmap, color, borderRatio);
				else
					draw[i] = getEmptyBorderBitmap(bitmap, color, borderRatio);
			}

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);

			for (int i = layers - 1; i >= 0; --i) {
				rect = new Rect(border * 3 * i, border * 3 * (layers - i - 1),
						draw[i].getWidth() + border * 3 * i,
						draw[i].getHeight() + border * 3 * (layers - i - 1));
				canvas.drawBitmap(draw[i], null, rect, paint);
				recycleOldBitmap(draw[i]);
			}
		}

		return output;
	}

	/* Internal method used to create a white bitmap with border */
	public static Bitmap getEmptyBorderBitmap(Bitmap bitmap, int color,
			int borderRatio) {
		int border = (bitmap.getWidth() > bitmap.getHeight() ? bitmap
				.getWidth() : bitmap.getHeight()) / borderRatio;
		Bitmap output = ImageUtil.createBitmap(bitmap.getWidth() + border * 2,
				bitmap.getHeight() + border * 2, Config.ARGB_8888);
		if (output != null) {
			Canvas canvas = new Canvas(output);

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRect(0, 0, output.getWidth(), output.getHeight(), paint);

			paint = new Paint();
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.WHITE);
			canvas.drawRect(border, border, bitmap.getWidth() + border,
					bitmap.getHeight() + border, paint);
		}

		return output;
	}

	/**
	 * Returns a grayscale version of the given bitmap.
	 * 
	 * @param bitmap
	 *            The original bitmap
	 * @return The new grayscale version of the bitmap.
	 */
	public static Bitmap getGrayscaleBitmap(Bitmap bitmap) {
		Bitmap bmpGrayscale = null;
		Bitmap bmpIncreaseContrast = ImageUtil.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.RGB_565);
		if (bmpIncreaseContrast != null) {
			Canvas canvas = new Canvas(bmpIncreaseContrast);
			Paint paint = new Paint();
			ColorMatrix cm = new ColorMatrix();
			float contrast = 0.3f;
			float brightness = 0f;
			cm.set(new float[] { contrast, 0, 0, 0, brightness, 0, contrast, 0,
					0, brightness, 0, 0, contrast, 0, brightness, 0, 0, 0, 1, 0 });
			ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
			paint.setColorFilter(f);
			canvas.drawBitmap(bitmap, 0, 0, paint);

			bmpGrayscale = ImageUtil.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Bitmap.Config.RGB_565);
			if (bmpGrayscale != null) {
				canvas = new Canvas(bmpGrayscale);
				paint = new Paint();
				cm = new ColorMatrix();
				cm.setSaturation(0);
				f = new ColorMatrixColorFilter(cm);
				paint.setColorFilter(f);
				canvas.drawBitmap(bmpIncreaseContrast, 0, 0, paint);
			}
			ImageUtil.recycleOldBitmap(bmpIncreaseContrast);
		}
		return bmpGrayscale;
	}

	public static Bitmap scaleBitmapToScreenSize(Context context, byte[] data) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		return scaleBitmapToFit(data, width, height);
	}

	//
	public static Bitmap scaleBitmapToFit(byte[] data, int width, int height) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bitmap = ImageUtil.decodeByteArray(data, 0, data.length,
				bmpFactoryOptions);

		float tolerance = (float) 1.3; // Tolerance is set to be 30%
		int maxOriginal = Math.max(bmpFactoryOptions.outHeight,
				bmpFactoryOptions.outWidth);
		int minOriginal = Math.min(bmpFactoryOptions.outHeight,
				bmpFactoryOptions.outWidth);
		int maxScaled = Math.max(height, width);
		int minScaled = Math.min(height, width);
		float scaleRatio = Math.max((float) maxOriginal / (float) maxScaled,
				(float) minOriginal / (float) Math.min(height, width));

		bmpFactoryOptions = new BitmapFactory.Options();
		/*
		 * For bitmap whose width/height is less than 100px, we don't do scale
		 */
		if (maxOriginal <= 100 || scaleRatio <= 1) {
			bmpFactoryOptions.inSampleSize = 1;
		} else {
			int minScaleRatio = (int) Math.floor(scaleRatio);
			/*
			 * If max width/height after scale is less than 100px or scaled
			 * width/height over given width/length using min scale ratio is
			 * less than tolerance, we use minScaleRatio;otherwise, we use max
			 * scale ratio
			 */
			if (maxOriginal / minScaleRatio <= 100
					|| Math.max((float) maxOriginal
							/ (maxScaled * minScaleRatio), (float) minOriginal
							/ (minScaled * minScaleRatio)) < tolerance) {
				bmpFactoryOptions.inSampleSize = minScaleRatio;
			} else {
				bmpFactoryOptions.inSampleSize = (int) Math.ceil(scaleRatio);
			}
		}
		bitmap = ImageUtil.decodeByteArray(data, 0, data.length,
				bmpFactoryOptions);
		return bitmap;
	}

	public static Bitmap decodeResource(android.content.res.Resources res,
			int id) {
		Bitmap result = null;
		result = BitmapFactory.decodeResource(res, id);
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	public static Bitmap decodeByteArray(byte[] data, int offset, int length) {
		return ImageUtil.decodeByteArray(data, offset, length, null);
	}

	public static Bitmap decodeByteArray(byte[] data, int offset, int length,
			BitmapFactory.Options options) {
		Bitmap result = null;
		try {
			if (options == null) {
				result = BitmapFactory.decodeByteArray(data, offset, length);
			} else {
				result = BitmapFactory.decodeByteArray(data, offset, length,
						options);
			}
		} catch (OutOfMemoryError oome) {
			// recovery attempt - we attempt to increase the scaling factor x2
			// and redo
			if (options == null) {
				options = new BitmapFactory.Options();
			}
			if (options.inSampleSize >= 1) {
				options.inSampleSize = options.inSampleSize * 2;
			} else {
				options.inSampleSize = 2;
			}
			try {
				result = BitmapFactory.decodeByteArray(data, offset, length,
						options);
				Log.w("Memory Usage",
						"Bitmap downscaled because of out of memory error.",
						oome); // log
								// as
								// a
								// warning
								// info
								// about
								// what
								// happened
			} catch (OutOfMemoryError innerOOME) { // if failed again, just
													// return null
				Log.e("Memory Usage",
						"Out of Memory Error when allocating bitmap: "
								+ innerOOME.getLocalizedMessage());
				result = null;
			}
		}
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	public static Bitmap createBitmap(int width, int height,
			android.graphics.Bitmap.Config config) {
		Bitmap result;
		try {
			result = Bitmap.createBitmap(width, height, config);
		} catch (OutOfMemoryError oome) {
			Log.e("Memory Usage",
					"Out of Memory Error when allocating bitmap: "
							+ oome.getLocalizedMessage());
			result = null;
		}
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	public static Bitmap createBitmap(Bitmap source, int x, int y, int width,
			int height, Matrix m, boolean filter) {
		Bitmap result;
		try {
			result = Bitmap
					.createBitmap(source, x, y, width, height, m, filter);
		} catch (OutOfMemoryError oome) {
			Log.e("Memory Usage",
					"Out of Memory Error when allocating bitmap: "
							+ oome.getLocalizedMessage());
			result = null;
		}
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	public static Bitmap createScaledBitmap(Bitmap source, int destWidth,
			int destHeight, boolean filter) {
		Bitmap result = null;

		try {
			result = Bitmap.createScaledBitmap(source, destWidth, destHeight,
					filter);
		} catch (OutOfMemoryError innerOOME) { // if failed again, just return
												// null
			Log.e("Memory Usage",
					"Out of Memory Error when allocating bitmap: "
							+ innerOOME.getLocalizedMessage());
			result = null;
		}
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	public static Bitmap getMediaBitmap(ContentResolver cr, Uri url)
			throws IOException {
		Bitmap result = null;
		try {
			result = Media.getBitmap(cr, url);
		} catch (OutOfMemoryError innerOOME) { // if failed again, just return
												// null
			Log.e("Memory Usage",
					"Out of Memory Error when allocating bitmap: "
							+ innerOOME.getLocalizedMessage());
			result = null;
		}
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	private static void incrementAllocationCount() {
		int count = bitmapAllocationCount.incrementAndGet();
		if (count % LOG_THRESHOLD == LOG_THRESHOLD - 1) {
			Log.i("Image Memory Debug", bitmapAllocationCount.get()
					+ " allocations, " + bitmapRecycleCount.get()
					+ " deallocations" + ", "
					+ (bitmapAllocationCount.get() - bitmapRecycleCount.get())
					+ " difference");
		}
	}

	public static Bitmap decodeStream(InputStream is) {
		return ImageUtil.decodeStream(is, null, null);
	}

	public static Bitmap decodeStream(InputStream is, Rect outPadding,
			BitmapFactory.Options options) {
		Bitmap result = null;
		try {
			if (options == null) {
				result = BitmapFactory.decodeStream(is);
			} else {
				result = BitmapFactory.decodeStream(is, outPadding, options);
			}
		} catch (OutOfMemoryError oome) {
			// recovery attempt - we attempt to increase the scaling factor x2
			// and redo
			if (options == null) {
				options = new BitmapFactory.Options();
			}
			if (options.inSampleSize >= 1) {
				options.inSampleSize = options.inSampleSize * 2;
			} else {
				options.inSampleSize = 2;
			}
			try {
				result = BitmapFactory.decodeStream(is, outPadding, options);
				Log.w("Memory Usage",
						"Bitmap downscaled because of out of memory error.",
						oome); // log
								// as
								// a
								// warning
								// info
								// about
								// what
								// happened
			} catch (OutOfMemoryError innerOOME) { // if failed again, just
													// return null
				Log.e("Memory Usage",
						"Out of Memory Error when allocating bitmap: "
								+ innerOOME.getLocalizedMessage());
				result = null;
			}
		}
		if (result != null) {
			incrementAllocationCount();
		}
		return result;
	}

	/**
	 * Helper method to recycle old bitmaps. The bitmap passed will no longer be
	 * usable.助手方法回收旧的位图。位图通过将不再可用
	 * 
	 * @param bm
	 *            The bitmap to recycle. 浠庢柊鍒╃敤鑰佺殑Bitmap 鍒ゆ柇濡傛灉bitmap涓嶇瓑浜巒ull
	 *            骞朵笖娌℃湁鍥炴敹锛屽氨灏嗕粬閲嶆柊鍒╃敤 位图回收
	 */
	public static void recycleOldBitmap(Bitmap bm) {
		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
			int count = bitmapRecycleCount.incrementAndGet();
			if (count % LOG_THRESHOLD == LOG_THRESHOLD - 1) {
				Log.i("Image Memory Debug",
						bitmapAllocationCount.get()
								+ " allocations, "
								+ bitmapRecycleCount.get()
								+ " deallocations"
								+ ", "
								+ (bitmapAllocationCount.get() - bitmapRecycleCount
										.get()) + " difference");
			}
		}
	}

	public static Drawable getBlurBitmap(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		// int statusBarHeight = frame.top;
		View view = activity.getWindow().getDecorView();
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		view.layout(0, 0, width, height);
		view.setDrawingCacheEnabled(true);// 允许当前窗口保存缓存信息，这样
											// getDrawingCache()方法才会返回一个Bitmap
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
		return BoxBlurFilter(activity, bmp);
	}

	public static Drawable BoxBlurFilter(Context context, Bitmap bmp) {
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
		for (int i = 0; i < iterations; i++) {
			blur(inPixels, outPixels, width, height, hRadius);
			blur(outPixels, inPixels, height, width, vRadius);
		}
		blurFractional(inPixels, outPixels, width, height, hRadius);
		blurFractional(outPixels, inPixels, height, width, vRadius);
		bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
		Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
		return drawable;
	}

	public static void blur(int[] in, int[] out, int width, int height,
			float radius) {
		int widthMinus1 = width - 1;
		int r = (int) radius;
		int tableSize = 2 * r + 1;
		int divide[] = new int[256 * tableSize];

		for (int i = 0; i < 256 * tableSize; i++)
			divide[i] = i / tableSize;

		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;
			int ta = 0, tr = 0, tg = 0, tb = 0;

			for (int i = -r; i <= r; i++) {
				int rgb = in[inIndex + clamp(i, 0, width - 1)];
				ta += (rgb >> 24) & 0xff;
				tr += (rgb >> 16) & 0xff;
				tg += (rgb >> 8) & 0xff;
				tb += rgb & 0xff;
			}

			for (int x = 0; x < width; x++) {
				out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
						| (divide[tg] << 8) | divide[tb];

				int i1 = x + r + 1;
				if (i1 > widthMinus1)
					i1 = widthMinus1;
				int i2 = x - r;
				if (i2 < 0)
					i2 = 0;
				int rgb1 = in[inIndex + i1];
				int rgb2 = in[inIndex + i2];

				ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
				tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
				tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
				tb += (rgb1 & 0xff) - (rgb2 & 0xff);
				outIndex += height;
			}
			inIndex += width;
		}
	}

	public static void blurFractional(int[] in, int[] out, int width,
			int height, float radius) {
		radius -= (int) radius;
		float f = 1.0f / (1 + 2 * radius);
		int inIndex = 0;

		for (int y = 0; y < height; y++) {
			int outIndex = y;

			out[outIndex] = in[0];
			outIndex += height;
			for (int x = 1; x < width - 1; x++) {
				int i = inIndex + x;
				int rgb1 = in[i - 1];
				int rgb2 = in[i];
				int rgb3 = in[i + 1];

				int a1 = (rgb1 >> 24) & 0xff;
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >> 8) & 0xff;
				int b1 = rgb1 & 0xff;
				int a2 = (rgb2 >> 24) & 0xff;
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >> 8) & 0xff;
				int b2 = rgb2 & 0xff;
				int a3 = (rgb3 >> 24) & 0xff;
				int r3 = (rgb3 >> 16) & 0xff;
				int g3 = (rgb3 >> 8) & 0xff;
				int b3 = rgb3 & 0xff;
				a1 = a2 + (int) ((a1 + a3) * radius);
				r1 = r2 + (int) ((r1 + r3) * radius);
				g1 = g2 + (int) ((g1 + g3) * radius);
				b1 = b2 + (int) ((b1 + b3) * radius);
				a1 *= f;
				r1 *= f;
				g1 *= f;
				b1 *= f;
				out[outIndex] = (a1 << 24) | (r1 << 16) | (g1 << 8) | b1;
				outIndex += height;
			}
			out[outIndex] = in[width - 1];
			inIndex += width;
		}
	}

	public static int clamp(int x, int a, int b) {
		return (x < a) ? a : (x > b) ? b : x;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	public static Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
		if (background == null) {
			return null;
		}
		System.out.println("!null");
		int bgWidth = background.getWidth();
		int bgHeight = background.getHeight();
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();
		// create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
		Bitmap newbmp = Bitmap
				.createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
		Canvas cv = new Canvas(newbmp);
		// draw bg into
		cv.drawBitmap(background, 0, 0, null);// 在 0，0坐标开始画入bg
		// draw fg into
		cv.drawBitmap(foreground, (bgWidth - fgWidth), (bgHeight - fgHeight),
				null);// 在
						// 0，0坐标开始画入fg
						// ，可以从任意位置画入
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newbmp;
	}

	public static final int ALL = 347120;
	public static final int TOP = 547120;
	public static final int LEFT = 647120;
	public static final int RIGHT = 747120;
	public static final int BOTTOM = 847120;

	/**
	 * 
	 * 指定图片的切边，对图片进行圆角处理
	 * 
	 * @param type
	 *            具体参见：{@link BitmapFillet.ALL} , {@link BitmapFillet.TOP} ,
	 *            {@link BitmapFillet.LEFT} , {@link BitmapFillet.RIGHT} ,
	 *            {@link BitmapFillet.BOTTOM}
	 * @param bitmap
	 *            需要被切圆角的图片
	 * @param roundPx
	 *            要切的像素大小
	 * @return
	 * 
	 */
	public static Bitmap fillet(int type, Bitmap bitmap, int roundPx) {
		try {
			// 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
			// 然后在画板上画出一个想要的形状的区域。
			// 最后把源图片帖上。
			final int width = bitmap.getWidth();
			final int height = bitmap.getHeight();

			Bitmap paintingBoard = Bitmap.createBitmap(width, height,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(paintingBoard);
			canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT,
					Color.TRANSPARENT, Color.TRANSPARENT);

			final Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);

			if (TOP == type) {
				clipTop(canvas, paint, roundPx, width, height);
			} else if (LEFT == type) {
				clipLeft(canvas, paint, roundPx, width, height);
			} else if (RIGHT == type) {
				clipRight(canvas, paint, roundPx, width, height);
			} else if (BOTTOM == type) {
				clipBottom(canvas, paint, roundPx, width, height);
			} else {
				clipAll(canvas, paint, roundPx, width, height);
			}

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// 帖子图
			final Rect src = new Rect(0, 0, width, height);
			final Rect dst = src;
			canvas.drawBitmap(bitmap, src, dst, paint);
			return paintingBoard;
		} catch (Exception exp) {
			return bitmap;
		}
	}

	private static void clipLeft(final Canvas canvas, final Paint paint,
			int offset, int width, int height) {
		final Rect block = new Rect(offset, 0, width, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, 0, offset * 2, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipRight(final Canvas canvas, final Paint paint,
			int offset, int width, int height) {
		final Rect block = new Rect(0, 0, width - offset, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(width - offset * 2, 0, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipTop(final Canvas canvas, final Paint paint,
			int offset, int width, int height) {
		final Rect block = new Rect(0, offset, width, height);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, 0, width, offset * 2);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipBottom(final Canvas canvas, final Paint paint,
			int offset, int width, int height) {
		final Rect block = new Rect(0, 0, width, height - offset);
		canvas.drawRect(block, paint);
		final RectF rectF = new RectF(0, height - offset * 2, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	private static void clipAll(final Canvas canvas, final Paint paint,
			int offset, int width, int height) {
		final RectF rectF = new RectF(0, 0, width, height);
		canvas.drawRoundRect(rectF, offset, offset, paint);
	}

	/**
	 * 
	 */
	// public static Bitmap decodeUriAsBitmap(Uri uri) {
	// Bitmap bitmap = null;
	// try {
	// bitmap = BitmapFactory.decodeStream(((ContentResolver)
	// SettingHelper.getInstance()
	// .getApplicationContext()).openInputStream(uri));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// return null;
	// }
	// return bitmap;
	// }

	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // API 19
			return bitmap.getAllocationByteCount();
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {// API
			return bitmap.getByteCount();
		}
		return bitmap.getRowBytes() * bitmap.getHeight(); // earlier version
	}

	public static String AddTime(Context context, Bitmap bitmap, String path,
			String lastImageFilePath) {
		Calendar calendar = Calendar.getInstance();
		String text = calendar.get(Calendar.YEAR) + "/"
				+ StringUtil.getDouble((calendar.get(Calendar.MONDAY) + 1))
				+ "/"
				+ StringUtil.getDouble(calendar.get(Calendar.DAY_OF_MONTH))
				+ "  "
				+ StringUtil.getDouble(calendar.get(Calendar.HOUR_OF_DAY))
				+ ":" + StringUtil.getDouble(calendar.get(Calendar.MINUTE));
		System.out.println("bitmap:" + (bitmap == null));
		int width = bitmap.getWidth();
		int hight = bitmap.getHeight();
		// FileUtils.saveBitmap(bm, fileName + "wo");
		System.out.println("宽" + width + "高" + hight);
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap

		// Bitmap icon = null;
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inSampleSize = 5;
		// AssetFileDescriptor fileDescriptor =null;
		// try {
		// fileDescriptor =
		// context.getContentResolver().openAssetFileDescriptor(Uri.parse(lastImageFilePath),
		// "r");
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }finally{
		// try {
		// icon =
		// BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(),
		// null, options);
		// fileDescriptor.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		// photoPaint.setFilterBitmap(true);// 过滤一些
		photoPaint.setAntiAlias(true);

		Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(bitmap, src, dst, photoPaint);// 将photo
		// 缩放或则扩大到dst使用的填充区photoPaint

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(Math.min(width, hight) / 20);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.YELLOW);// 采用的颜色
		int s = 100;
		if (width <= hight) {
			s = (int) (hight * 0.01);
		} else {
			s = (int) (width * 0.01);
		}

		canvas.drawText(text, width - textPaint.measureText(text) - s, hight
				- textPaint.measureText(text, 0, 2) - s, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();

		// FileUtils.saveBitmap(icon, fileName);
		saveBitmapToTargetPath(icon, path);

		compressImage(icon, path);

		// bm.recycle();
		// System.gc();
		// AlbumBimpUtil.tempSelectBitmap.add(takePhoto);
		return path;
	}

}
