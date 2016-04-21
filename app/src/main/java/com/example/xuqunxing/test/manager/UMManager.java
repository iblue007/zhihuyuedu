package com.example.xuqunxing.test.manager;

import android.content.Context;

import com.example.xuqunxing.test.BaseApplication;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * 友盟统计
 */
public class UMManager {
    private static UMManager manager;

    private UMManager() {
        initUMAnalytics();
    }

    /**
     * initUMAnalytics
     *
     * @param
     * @return void
     * @throws
     * @Description: 统计信息初始化
     */
    private static void initUMAnalytics() {
        // 没有直接统计activity

        MobclickAgent.openActivityDurationTrack(true);
        MobclickAgent.updateOnlineConfig(BaseApplication.getInstance());
    }

    public static UMManager getInstance() {
        if (manager == null) {
            manager = new UMManager();
        }
        return manager;
    }

    public void event(String key) {
        MobclickAgent.onEvent(BaseApplication.getInstance(),key);
    }

    /**
     * 考虑事件在不同属性上的取值，可以调用如下方法：
     * @param key
     * @param map
     */
    public void event(String key,HashMap<String ,String> map) {
        MobclickAgent.onEvent(BaseApplication.getInstance(),key,map);
    }

    public void onResume(Context context){
        MobclickAgent.onResume(context);

    }
    public void onPause(Context context){
        MobclickAgent.onPause(context);

    }
}
