package com.example.xuqunxing.test;

import android.app.Application;

/**
 * Created by xuqunxing on 2016/3/29.
 */
public class BaseApplication extends Application{
    protected static BaseApplication baseApplication = null;

    public static BaseApplication getInstance() {
        if (baseApplication == null) {
            baseApplication = new BaseApplication();
        }
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }



}
