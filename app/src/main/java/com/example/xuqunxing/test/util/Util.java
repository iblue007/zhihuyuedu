package com.example.xuqunxing.test.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

	private static BufferedWriter bw;



	/**
	 * 检测当的网络（WLAN、3G/2G）状态
	 * @param context Context
	 * @return true 表示网络可用
	 */
	public static boolean isNetworkAvailable(Context context) {

		boolean flag = false;

		ConnectivityManager cwjManager = (ConnectivityManager) context    // 获取实例

				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cwjManager.getActiveNetworkInfo() != null) {

			flag = cwjManager.getActiveNetworkInfo().isAvailable();    // 检测是否联网

		}
		return flag;
	}

	/**
	 * Convert Dp to Pixel
	 * 将dp转换为pixel
	 */
	public static int dpToPx(float dp, Resources resources){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}

	/**
	 * @param value
	 * @return 将dip或者dp转为float
	 */
	public static float dipOrDpToFloat(String value) {
		if (value.indexOf("dp") != -1) {
			value = value.replace("dp", "");
		}
		else {
			value = value.replace("dip", "");
		}
		return Float.parseFloat(value);
	}

	/**
	 * 剪切图片
	 *
	 * @param context
	 * @param inUri
	 * @param outputUri
	 * @param resultCode
	 */
	public static void performCrop(Context context, Uri inUri, Uri outputUri,
								   int resultCode) {
		try {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(inUri, "image/*");
			intent.putExtra("scale", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("scaleUpIfNeeded", true);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
			// intent.putExtra("outputFormat",
			// Bitmap.CompressFormat.JPEG.toString());
			Activity activity = (Activity) context;
			activity.startActivityForResult(intent, resultCode);
		} catch (ActivityNotFoundException anfe) {
		}
	}

	/**
	 * 将图片转为二进制流
	 *
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmap2ByteArray(Bitmap bitmap) {
		try {
			//由于Intent传递bitmap不能超过40k,此处使用二进制数组传递
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
			byte[] bitmapByte = baos.toByteArray();
			baos.close();
			return bitmapByte;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 比较两个时间的大小
	 * @param DATE1
	 * @param DATE2
	 * DATE1>DATE2  返回1
	 * DATE1=DATE2  返回0
	 * DATE1<DATE2  返回-1
	 * @return
	 */
	public static int compare_date(String DATE1, String DATE2) {

        if(DATE1.contains("月")){
			DATE1=DATE1.replace("月","-");
		}
		if(DATE1.contains("日")){
			DATE1=DATE1.replace("日","");
		}
		if(DATE2.contains("月")){
			DATE2=DATE1.replace("月","-");
		}
		if(DATE2.contains("日")){
			DATE2=DATE1.replace("日","");
		}
		DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				//System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 *字符串的日期格式的计算(计算两个时间之间的天数)
	 */
	public static String daysBetween(String smdate,String bdate){
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days=(time2-time1)/(1000*3600*24);

			return String.valueOf(between_days);
		} catch (Exception e) {
			return -1+"";
		}
	}

	public static String formatDateTime(String time,boolean isOnShowWeekDay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (time == null || "".equals(time)) {
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Calendar current = Calendar.getInstance();

		Calendar today = Calendar.getInstance();    //今天

		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		//today.set(Calendar.SECOND, 0);

		Calendar yesterday = Calendar.getInstance();    //昨天

		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
		yesterday.set(Calendar.HOUR_OF_DAY, 0);
		yesterday.set(Calendar.MINUTE, 0);
		//yesterday.set(Calendar.SECOND, 0);

		current.setTime(date);

		if (current.after(today)) {
			if(isOnShowWeekDay){
//			   return "今天 "+time.split(" ")[1];
			   return "今天 ";
			}else{
			   return time.split(" ")[1];
			}
		} else if (current.before(today) && current.after(yesterday)) {
			if(isOnShowWeekDay){
				return "昨天 ";
			}else{
			   return "昨天 " + time.split(" ")[1];
			}
		} else if (isThisWeek(time)) {

			if(isOnShowWeekDay){
				SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
				return format2.format(date);
			}else{
				String oldTime = getWeekDay(time);
				return oldTime + " " + time.split(" ")[1];
			}
		} else {
			if(isOnShowWeekDay){
				SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
				return format2.format(date);
			}else{
				int index = time.indexOf("-") + 1;
				return time.substring(index, time.length());
			}
		}
	}

	private static boolean isThisWeek(String oldTime) {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = format.format(date);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date oldDate = df.parse(oldTime);
			Date currentDate = df.parse(currentTime);
			long diff = currentDate.getTime() - oldDate.getTime();
			long days = diff / (1000 * 60 * 60 * 24);
			if (days > 7) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static String getWeekDay(String DateStr) {
		SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");//formatYMD表示的是yyyy-MM-dd格式
		SimpleDateFormat formatD = new SimpleDateFormat("E");//"E"表示"day in week"
		Date d = null;
		String weekDay = "";
		try {
			d = formatYMD.parse(DateStr);//将String 转换为符合格式的日期
			weekDay = formatD.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("日期:"+DateStr+" ： "+weekDay);
		return weekDay;
	}
	public static String getWeekDay2(String DateStr) {
		SimpleDateFormat formatYMD = new SimpleDateFormat("MM月dd日");//formatYMD表示的是yyyy-MM-dd格式
		SimpleDateFormat formatD = new SimpleDateFormat("E");//"E"表示"day in week"
		Date d = null;
		String weekDay = "";
		try {
			d = formatYMD.parse(DateStr);//将String 转换为符合格式的日期
			weekDay = formatD.format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println("日期:"+DateStr+" ： "+weekDay);
		return weekDay;
	}

	/**
	 * 获得相对于控件的高度
	 */

	static int totalHeigh=0;
	public static int getViewHeight(final Context context, final View view, final int phoneHeigh){
		view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int viewLLHeigh=view.getHeight();
				totalHeigh=viewLLHeigh;
				view.getViewTreeObserver().removeGlobalOnLayoutListener(null);
				int hei=phoneHeigh-viewLLHeigh;
				totalHeigh=hei;
				if(hei==0){
					ToastUtils.showToast(context,"没有虚拟按键");
				}else{

				}
			}
		});
		return totalHeigh;
	}
	/**
	 * 获得状态栏的高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			//loge("get status bar height fail");
			e1.printStackTrace();
		}
		return sbar;
	}

	public static int isDoSystemBarTintManager=1000;
	/**
	 * 根据条件拦截是否启动沉浸式(有虚拟键盘的机型，默认不启动沉浸式)
	 */
	public static boolean isDoSystemBarTintManager(){
		String model= Build.MODEL;
		if(model.equals("M351")||model.equals("H60-L02")||model.equals("HUAWEI Pwen-L07")){
			return false;
		}
		if(isDoSystemBarTintManager==1000){
			return true;
		}else if(isDoSystemBarTintManager==-1){
			return false;
		}else if(isDoSystemBarTintManager==0){
			return true;
		}
		return true;
	}
	/**
	 * 判断版本是否大于4.4
	 */
	public static boolean isKitKat(){
        if(!isDoSystemBarTintManager()){
			return false;
		}
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}
	/**
	 * 设置View的状态栏高度
	 */
	public static void setViewMargin(Context context, LinearLayout linearLayout, RelativeLayout relativeLayout) {
		if(!isDoSystemBarTintManager()){
			return ;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (linearLayout != null) {
//				ViewGroup parent = (ViewGroup) linearLayout.getParent();
//				if(parent!=null){
//					parent.removeAllViews();
//				}
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(linearLayout.getLayoutParams());
				lp.setMargins(0, getStatusHeight(context), 0, 0);
				linearLayout.setLayoutParams(lp);
			} else if (relativeLayout != null) {
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(relativeLayout.getLayoutParams());
				lp.setMargins(0, getStatusHeight(context), 0, 0);
				relativeLayout.setLayoutParams(lp);
			}
		}
	}
	public static void setViewMarginBottom(Context context, LinearLayout linearLayout, RelativeLayout relativeLayout) {
		if(!isDoSystemBarTintManager()){
			return ;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (linearLayout != null) {
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(linearLayout.getLayoutParams());
				lp.setMargins(0, 0, 0, getStatusHeight(context));
				linearLayout.setLayoutParams(lp);
			} else if (relativeLayout != null) {
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(relativeLayout.getLayoutParams());
				lp.setMargins(0,0, 0, getStatusHeight(context));
				relativeLayout.setLayoutParams(lp);
			}
		}
	}

	/**
	 * 设置View的状态栏高度(padding)
	 */
	public static void setViewPadding(Context context, View view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view.setPadding(0, getStatusHeight(context), 0, 0);
		}
	}


	/**
	 * 设置View的状态栏高度(主要是适应赛事结局列表corpsinfoUserActivity)
	 */
	public static void setScrollViewMargin(Context context, ScrollView view) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (view != null) {
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getLayoutParams());
				lp.setMargins(0, getStatusHeight(context), 0, 0);
				view.setLayoutParams(lp);
			}
		}
	}




	public static String getSimpleClassName(String name) {
		String simpleName = null;
		if (!"".equals(name)) {
			if (!name.contains("$")) {
				simpleName = name.substring(name.lastIndexOf(".") + 1, name.length());
			} else {
				simpleName = name.substring(name.lastIndexOf(".") + 1, name.length() - 2);
			}
		}
		return simpleName;
	}





	/**
	 * 保存String 到本地(请确认你所保存的数据不是空数据)
	 */
	public static void save2Local(final String data, final String packName, final String index) {
		Log.i("xuqunxing","save2Local");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File file=null;
					String newClassName=null;
					if(packName.contains("/")){//判断传进来的是否有两层的文件夹
						String[] strings = packName.split("/");
						String frontFolder=strings[0];
						String backFolder=strings[1];
						newClassName=frontFolder+"_"+backFolder;
					}
					File cacheDir = FileUtils.getCacheDir(packName);//创建文件夹保存文件
					if(newClassName!=null){
						file = new File(cacheDir, newClassName + "_" + index);
					}else{
						file = new File(cacheDir, packName + "_" + index);
					}

					if(file.exists()){
						file.delete();
					}
					FileWriter fw = new FileWriter(file);
					bw = new BufferedWriter(fw);
					bw.write(data);
					bw.flush();
 					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	/**
	 * 清空缓存
	 */
	public static void clearLocal(String packName, int index) {

		try {
			File cacheDir = FileUtils.getCacheDir(packName);//创建文件夹保存文件
			File[] files = cacheDir.listFiles();
			for(File f:files) {
				if (f.getName().equals("CompetitionJoinChildListView_0_1")) {
					Log.i("xuqunxing","f.delete");
					f.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取本地缓存
	 */
	public static String readDataFromLocal(String className, String index) {
		File file=null;
		String newClassName=null;
		if(className.contains("/")){//判断传进来的是否有两层的文件夹
			String[] strings = className.split("/");
			String frontFolder=strings[0];
			String backFolder=strings[1];
			newClassName=frontFolder+"_"+backFolder;
		}
		try {
			File dir = FileUtils.getCacheDir(className);// 获取缓存所在的文件夹
			if(newClassName!=null){
				file = new File(dir, newClassName + "_" + index);
			}else{
				file = new File(dir, className + "_" + index);
			}
			if(index.contains("_")){
//				int currentPage=Integer.parseInt(index.substring(index.length()-1,index.length()));//如果页数大于10的话要换一个方法
				int currentPage=Integer.parseInt(index.substring(index.lastIndexOf("_")+1));
				if(!file.exists()&&currentPage>1){//&&index>1
					return "notEnough";
				}
			}else{
				if(!file.exists()){
					return "notEnough";
				}
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String str = null;
			StringWriter sw = new StringWriter();
			while ((str = br.readLine()) != null) {
				sw.write(str);
			}
			br.close();
			return sw.toString();
		} catch (Exception e) {
			Log.i("xuqunxing","Util-保存本地缓存错误");
			e.printStackTrace();
			return null;
		}
	}


	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	//删除文件夹
	//param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

