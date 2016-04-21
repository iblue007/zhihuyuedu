package com.example.xuqunxing.test.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.xuqunxing.test.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* @Description: 文件工具类 
* @author wushan
* @date 2015年8月25日 下午3:04:41 
*
 */
public class FileUtils {
	/**
	 * 读取表情配置文件
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getEmojiFile(Context context) {
		try {
			List<String> list = new ArrayList<String>();
			InputStream in = context.getResources().getAssets().open("emoji");
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String str = null;
			while ((str = br.readLine()) != null) {
				list.add(str);
			}

			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String CACHE = "cache";
	public static final String ICON = "icon";
	public static final String ROOT = "zhihu";
	/**
	 * 获取图片的缓存的路径
	 * @return
	 */
	public static File getIconDir(String className){
		return getDir(ICON,className);

	}
	/**
	 * 获取缓存路径
	 * @return
	 */
	public static File getCacheDir(String className) {
		return getDir(CACHE,className);
	}
	public static File getDir(String cache,String className) {
		String frontFolder=null;
		String backFolder=null;
		if(className.contains("/")){
			String[] strings = className.split("/");
			frontFolder=strings[0];
			backFolder=strings[1];
		}
		StringBuilder path = new StringBuilder();
		if (isSDAvailable()) {
			path.append(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			path.append(File.separator);// '/'
			path.append(ROOT);// /mnt/sdcard/lolShe
			path.append(File.separator);
			path.append(cache);// /mnt/sdcard/lolShe/cache
			path.append(File.separator);
			if(frontFolder!=null&&backFolder!=null){
				path.append(frontFolder);
				path.append(File.separator);
				path.append(backFolder);
			}else{
				path.append(className);// /mnt/sdcard/lolShe/cache/packName
			}

		}else{
			File filesDir = BaseApplication.getInstance().getCacheDir();    //  cache  getFileDir file
			path.append(filesDir.getAbsolutePath());// /data/data/com.xxxx.lolShe/cache
			path.append(File.separator);///data/data/com.xxxx.lolShe/cache/
			path.append(cache);///data/data/com.xxxx.lolShe/cache/cache
			path.append(File.separator);
			if(frontFolder!=null&&backFolder!=null){
				path.append(frontFolder);
				path.append(File.separator);
				path.append(backFolder);
			}else{
				path.append(className);// /mnt/sdcard/lolShe/cache/packName
			}
		}
		File file = new File(path.toString());
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();// 创建文件夹
		}
		return file;
	}

	private static void deleteTempPath(File file) {
         if(file.exists()){
			 Log.i("xuqunxing","Delete:"+file.toString());
			 file.delete();
		 }
	}

	private static boolean isSDAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
