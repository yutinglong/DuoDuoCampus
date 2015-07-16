package com.duoduo.duoduocampus.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

public class SDCardUtils {
	static {
		if (hasSDCard()) {
			File file = new File(Constants.SDCARD_DIR);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(Constants.PHOTO_DIR);
			if (!file.exists()) {
				file.mkdirs();
			}

		}
	}

	public static boolean hasSDCard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static Bitmap featBitmap(String path, int width) {
		Bitmap bitmap = null;
		try {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int bitmap_w = options.outWidth;
			while (bitmap_w / (float) width > 2) {
				options.inSampleSize <<= 1;
				bitmap_w >>= 1;
			}
			return BitmapFactory.decodeFile(path, options);
		} catch (Exception e) {
		}
		return bitmap;
	}

	/**
	 * 获取合适尺寸的图片 图片的长或高中较大的值要 < suitableSize*factor
	 * 
	 * @param path
	 * @param suitableSize
	 * @return
	 */
	public static Bitmap featBitmapToSuitable(String path, int suitableSize,
			float factor) {
		Bitmap bitmap = null;
		try {
			Options options = new Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			options.inJustDecodeBounds = false;
			options.inSampleSize = 1;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			int bitmap_w = options.outWidth;
			int bitmap_h = options.outHeight;
			int max_edge = bitmap_w > bitmap_h ? bitmap_w : bitmap_h;
			while (max_edge / (float) suitableSize > factor) {
				options.inSampleSize <<= 1;
				max_edge >>= 1;
			}
			return BitmapFactory.decodeFile(path, options);
		} catch (Exception e) {
		}
		return bitmap;
	}

	/**
	 * 
	 * @param time
	 * @param currentTime
	 * @param path
	 */

	/**
	 * 删文件或者目录
	 * 
	 * @param file
	 */
	public static void deleteFile(File file, boolean delFolder) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						deleteFile(files[i], true);
					}
				}
			}
			if (delFolder) {
				file.delete();
			}
		}

	}

	/**
	 * 转换bitmap为字节数组
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bitmap) {
		if (bitmap != null) {
			final int size = bitmap.getWidth() * bitmap.getHeight() * 4;
			final ByteArrayOutputStream out = new ByteArrayOutputStream(size);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			byte[] image = out.toByteArray();
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return image;
		} else {
			return null;
		}
	}

	/**
	 * 从exif信息获取图片旋转角度
	 * 
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 图片旋转90度
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotate(Bitmap bitmap, int rotate) {
		try {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Matrix m = new Matrix();
			m.setRotate(rotate, w / 2f, h / 2f);
			Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, m,
					false);
			return rotateBitmap;
		} catch (OutOfMemoryError ex) {
			System.gc();
		}
		return bitmap;
	}

	/**
	 * 对图片进行压缩选择处理
	 * 
	 * @param picPath
	 * @return
	 */
	public static Bitmap compressRotateBitmap(String picPath) {
		Bitmap bitmap = null;
		int degree = readPictureDegree(picPath);
		if (degree == 90) {
			bitmap = SDCardUtils.featBitmapToSuitable(picPath, 500, 1.8f);
			bitmap = rotate(bitmap, 90);
		} else {
			bitmap = SDCardUtils.featBitmapToSuitable(picPath, 500, 1.8f);
		}
		return bitmap;
	}

	public static String savePic(byte[] data, String path) {
		File file = new File(path);
		if (file.exists()) {
			file.mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return path;
	}

	public static String saveBitmap(String path, Bitmap bitmap, boolean recycle) {
		return saveBitmap(path, bitmap, Bitmap.CompressFormat.JPEG, recycle);
	}

	public static String saveBitmap(String path, Bitmap bitmap,
			Bitmap.CompressFormat format, boolean recycle) {
		File f = new File(path);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(format, 100, fOut);
			fOut.flush();
			if (recycle) {
				bitmap.recycle();
			}
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fOut != null) {
				try {
					fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String savePic(byte[] data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss",
				Locale.CHINESE);
		String picName = sdf.format(new Date()) + ".png";
		String path = Constants.PHOTO_DIR + picName;
		return savePic(data, path);
	}

	/**
	 * 保存图片到图库
	 * 
	 * @param context
	 * @param bitmap
	 */
	public static void saveToGallery(Context context, Bitmap bitmap) {
		if (context == null || bitmap == null) {
			return;
		}
		ContentResolver cr = context.getContentResolver();
		String path = MediaStore.Images.Media.insertImage(cr, bitmap,
				System.currentTimeMillis() + ".jpg", "");
		if (!TextUtils.isEmpty(path)) {
			path = getImagePath(context, Uri.parse(path));
			context.sendBroadcast(new Intent(
					Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
							+ path)));
		}

	}

	/**
	 * 保存图片到图库
	 * 
	 * @param context
	 * @param bitmap
	 */
	public static void saveToGallery(Context context, String path) {
		if (context == null || TextUtils.isEmpty(path)) {
			return;
		}
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.parse("file://" + path)));
	}

	/**
	 * 获取图片本地路径
	 * 
	 * @param uri
	 * @return
	 */
	public static String getImagePath(Context context, Uri uri) {
		String path = null;
		if (uri == null) {
			return path;
		}
		if ("content".equals(uri.getScheme())) {
			Cursor cursor = context.getContentResolver().query(uri, null, null,
					null, null);
			cursor.moveToFirst();
			String document_id = cursor.getString(0);
			document_id = document_id
					.substring(document_id.lastIndexOf(":") + 1);
			cursor.close();

			cursor = context
					.getContentResolver()
					.query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
							null, MediaStore.Images.Media._ID + " = ? ",
							new String[] { document_id }, null);
			cursor.moveToFirst();
			path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			cursor.close();
		} else if ("file".equals(uri.getScheme())) {
			path = uri.getPath();
		}
		return path;
	}

}
