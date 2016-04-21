package com.example.xuqunxing.test.baseLayout.fragment.inf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public interface IFragmentView {

    public void initDataBeforView();
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public  void initDataAfterView();
    public void initListener();
    public void initActionbar();
}
