package com.example.xuqunxing.test.baseLayout.activity.inf;

import android.os.Bundle;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public interface IInitView {

    /**

     * initDataBeforView()

     * @Title: initDataBeforView

     * @Description: 页面初始化前获得数据

     * @param

     * @return void    返回类型

     * @throws

     */

    public void initDataBeforView();
    /**

     * initView()

     * @Title: initView

     * @Description: 初始化布局

     * @param

     * @return void    返回类型

     * @throws

     */
    public  void initView(Bundle savedInstanceState);
    /**

     * initData()

     * @Title: initData

     * @Description: 初始化数据

     * @param

     * @return void    返回类型

     * @throws

     */
//    public  void initData();

    /**
     * initListener
     * @Description: 初始化事件
     * @param
     * @return void
     * @throws
     */
    public void initListener();

    public void initDataAfterView();
}
