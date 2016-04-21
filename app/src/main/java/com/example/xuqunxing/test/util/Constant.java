package com.example.xuqunxing.test.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by wwjun.wang on 2015/8/11.
 */
public class Constant {
    public static final String BASEURL = "http://news-at.zhihu.com/api/4/";
    public static final String START = BASEURL+"start-image/1080*1776";
    //侧拉页数据
    public static final String THEMES =  BASEURL+"themes";
    //获得主页main的数据
    public static final String LATESTNEWS =  BASEURL+"news/latest";
    public static final String BEFORE =  BASEURL+"news/before/";
    public static final String THEMENEWS =  BASEURL+"theme/";
    //消息详情
    public static final String CONTENT =  BASEURL+"news/";
    public static final int TOPIC = 131;
    public static final String START_LOCATION =  BASEURL+"start_location";
    public static final String CACHE =  BASEURL+"cache";
    public static final int LATEST_COLUMN = Integer.MAX_VALUE;
    public static final int BASE_COLUMN = 100000000;


    /**
     * 主页fragment
     */
    public static final String INDEX_MAIN_FRAGMENT="index_main_fragment";
    /**
     * 侧拉列表tv
     */
    public static final String TV_MAIN="tv_main";
    /**
     * 详情页
     */
    public static final String new_detail_activity="NEW_DETAIL_ACTIVITY";
    /**
     * 存储卡文件位置
     */
    public final static String SDCRAD_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + File.separatorChar
            + "zhihu" + File.separatorChar;

    public static final String ALIAS_TYPE ="zhihuyuedu" ;
    public static final String UMengPost =" http://msg.umeng.com/api/send" ;
}
