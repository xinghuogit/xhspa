package com.xh.shopping.icon;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

public class ImageController {
	private static ImageController imageController;

	private ImageController() {
	}

	public static ImageController getImageController() {
		if (imageController == null) {
			imageController = new ImageController();
		}
		return imageController;
	}
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int TOP = 2;
	public static final int BOTTOM = 3;

	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal传入的图片
	 * @return 去色后的图片
	 */
	public Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		// 获取宽高
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		// 按照一定画质新建bitmap
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		// 建画布
		Canvas c = new Canvas(bmpGrayscale);
		// 建画笔
		Paint paint = new Paint();
		// 颜色矩阵
		ColorMatrix cm = new ColorMatrix();
		// 设置灰阶
		cm.setSaturation(0);
		// 颜色矩阵颜色过滤对象,传入设置的颜色矩阵
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		// 设置画笔的颜色过滤,传入颜色矩阵颜色过滤对象
		paint.setColorFilter(f);
		// 在画布用画笔画传入的图片
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(toGrayscale(bmpOriginal), pixels);
	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		final float roundPx = pixels;// 圆角的弧度

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/** */
	/**
	 * 使圆角功能支持BitampDrawable
	 * 
	 * @param bitmapDrawable
	 * @param pixels
	 * @return
	 */
	public BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
			int pixels) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
		return bitmapDrawable;
	}

	/**
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap
	 * 
	 * @param path
	 */
	public Bitmap saveBefore(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = 2; // 图片长宽各缩小二分之一
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(path, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		System.out.println(w + "    " + h);
		// savePNG_After(bitmap,path);
		saveJPGE_After(bitmap, path);
		return bitmap;
	}

	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 *            bitmap对象
	 * @param name
	 *            path
	 */
	public void savePNG_After(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public void saveJPGE_After(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 水印
	 * 
	 * @param bitmap
	 *            图片
	 * @param bitmap
	 *            水印
	 * @return bitmap 处理后的图片
	 */
	public Bitmap createBitmapForWatermark(Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		// 位图的宽高
		int w = src.getWidth();
		int h = src.getHeight();
		// 水印的宽高
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// 创建一个新的和SRC长度宽度一样的位图
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas cv = new Canvas(newb);
		// 在 0，0坐标开始画入src
		cv.drawBitmap(src, 0, 0, null);
		// 在src的右下角画入水印（距离底部和右边距离为5）
		cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);
		// 保存
		cv.save(Canvas.ALL_SAVE_FLAG);
		// 存储
		cv.restore();
		return newb;
	}

	/**
	 * 图片合成 传入图片数组 自动合成
	 * 
	 * @param direction
	 * @param bitmap
	 * @param bitmaps
	 *            变参
	 * @return
	 */
	public Bitmap potoMix(int direction, Bitmap... bitmaps) {
		if (bitmaps.length <= 0) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		Bitmap newBitmap = bitmaps[0];
		// newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
		for (int i = 1; i < bitmaps.length; i++) {
			newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
		}
		return newBitmap;
	}
	
	/**
	 * 图片合成 传入两张图片进行合成
	 * 
	 * @param first
	 * @param second
	 * @param direction
	 * @return
	 */
	public Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,
			int direction) {
		if (first == null) {
			return null;
		}
		if (second == null) {
			return first;
		}
		int fw = first.getWidth();
		int fh = first.getHeight();

		int sw = second.getWidth();
		int sh = second.getHeight();
		Bitmap newBitmap = null;
		if (direction == LEFT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, sw, 0, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == RIGHT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, fw, 0, null);
		} else if (direction == TOP) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, sh, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == BOTTOM) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, 0, fh, null);
		}
		return newBitmap;
	}

	/**
	 * 将Bitmap转换成指定大小
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public  Bitmap createBitmapBySize(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	/**
	 * 图片压缩至指定的存储大小
	 * 
	 * @param bitMap
	 * @return
	 */
	public Bitmap imageZoom(Bitmap bitMap ,double maxSize) {
		// 图片允许最大空间 单位：KB
		
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitMap = createBitmapBySize(bitMap,
					(int) (bitMap.getWidth() / Math.sqrt(i)),
					(int) (bitMap.getHeight() / Math.sqrt(i)));
		}
		return bitMap;
	}

	/**
	 * Drawable 转 Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public Bitmap drawableToBitmapByBD(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		return bitmapDrawable.getBitmap();
	}

	/**
	 * Bitmap 转 Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public Drawable bitmapToDrawableByBD(Bitmap bitmap) {
		Drawable drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	/**
	 * byte[] 转 bitmap
	 * 
	 * @param b
	 * @return
	 */
	public Bitmap bytesToBimap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * bitmap 转 byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public byte[] bitmapToBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * Bitmap转成string
	 * 
	 * @param bitmap
	 * @return
	 */
	public String convertIconToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		bitmap.compress(CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);
	}

	/**
	 * string转成bitmap
	 * 
	 * @param st
	 */
	public Bitmap convertStringToIcon(String st) {
		// OutputStream out;
		Bitmap bitmap = null;
		try {
			// out = new FileOutputStream("/sdcard/aa.jpg");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check the SD card 检测是否有SD卡
	 * 
	 * @return
	 */
	public boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Save image to the SD card 保存图片到sd卡
	 * 
	 * @param photoBitmap
	 *            位图
	 * @param photoName
	 *            图片名
	 * @param path
	 *            存放路径
	 * 
	 */
	public void savePhotoToSDCard(Bitmap photoBitmap, String path,
			String photoName) {
		// 判断是否存在sd卡
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			// 如果dir 文件不存在,新建
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// 建图片文件路径,在文件路径下
			File photoFile = new File(path, photoName + ".png");
			// 新建文件输出流定为空
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush(); // 这里才是输出数据
						fileOutputStream.close();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 将图片存入 指定文件夹下
	 * @param bm
	 * @param picName
	 */
	public String saveBitmap(Bitmap bm, String picName) {
		try {
			
			File dir = new File(Global.AVATAR_CACHE_PATH);
			// 如果dir 文件不存在,新建
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			File f = new File(Global.AVATAR_CACHE_PATH, picName + ".png"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			return f.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	 public static final int ALL = 347120;
	    /**
	     * 
	     * 指定图片的切边，对图片进行圆角处理
	     * @param type 具体参见：{@link ImageController.ALL} , {@link ImageController.TOP} , 
	     *              {@link ImageController.LEFT} , {@link ImageController.RIGHT} , {@link ImageController.BOTTOM}
	     * @param bitmap 需要被切圆角的图片
	     * @param roundPx 要切的像素大小
	     * @return
	     *
	     */
	    public  Bitmap fillet(int type,Bitmap bitmap,int roundPx) {
	        try {
	            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
	            // 然后在画板上画出一个想要的形状的区域。
	            // 最后把源图片帖上。
	            final int width = bitmap.getWidth();
	            final int height = bitmap.getHeight();
	             
	            Bitmap paintingBoard = Bitmap.createBitmap(width,height, Config.ARGB_8888);
	            Canvas canvas = new Canvas(paintingBoard);
	            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
	             
	            final Paint paint = new Paint();
	            paint.setAntiAlias(true);
	            paint.setColor(Color.BLACK);   
	             
	            if( TOP == type ){
	                clipTop(canvas,paint,roundPx,width,height);
	            }else if( LEFT == type ){
	                 clipLeft(canvas,paint,roundPx,width,height);
	            }else if( RIGHT == type ){
	                clipRight(canvas,paint,roundPx,width,height);
	            }else if( BOTTOM == type ){
	                clipBottom(canvas,paint,roundPx,width,height);
	            }else{
	                clipAll(canvas,paint,roundPx,width,height);
	            }
	             
	            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
	            //帖子图
	            final Rect src = new Rect(0, 0, width, height);
	            final Rect dst = src;
	            canvas.drawBitmap(bitmap, src, dst, paint);   
	            return paintingBoard;
	        } catch (Exception exp) {        
	            return bitmap;
	        }
	    }
	     
	    private static void clipLeft(final Canvas canvas,final Paint paint,int offset,int width,int height){
	        final Rect block = new Rect(offset,0,width,height);
	        canvas.drawRect(block, paint);
	        final RectF rectF = new RectF(0, 0, offset * 2 , height);
	        canvas.drawRoundRect(rectF, offset, offset, paint);
	    }
	     
	    private static void clipRight(final Canvas canvas,final Paint paint,int offset,int width,int height){
	        final Rect block = new Rect(0, 0, width-offset, height);
	        canvas.drawRect(block, paint);
	        final RectF rectF = new RectF(width - offset * 2, 0, width , height);
	        canvas.drawRoundRect(rectF, offset, offset, paint);
	    }
	     
	    private static void clipTop(final Canvas canvas,final Paint paint,int offset,int width,int height){
	        final Rect block = new Rect(0, offset, width, height);
	        canvas.drawRect(block, paint);
	        final RectF rectF = new RectF(0, 0, width , offset * 2);
	        canvas.drawRoundRect(rectF, offset, offset, paint);
	    }
	     
	    private static void clipBottom(final Canvas canvas,final Paint paint,int offset,int width,int height){
	        final Rect block = new Rect(0, 0, width, height - offset);
	        canvas.drawRect(block, paint);
	        final RectF rectF = new RectF(0, height - offset * 2 , width , height);
	        canvas.drawRoundRect(rectF, offset, offset, paint);
	   
	    }
	     
	    private static void clipAll(final Canvas canvas,final Paint paint,int offset,int width,int height){
	        final RectF rectF = new RectF(0, 0, width , height);
	        canvas.drawRoundRect(rectF, offset, offset, paint);
	    }
	
	
	
	
	
	
	
	
	
	
	
	

}
