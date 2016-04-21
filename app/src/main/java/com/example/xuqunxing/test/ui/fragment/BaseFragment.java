package com.example.xuqunxing.test.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuqunxing on 2016/3/31.
 */
public abstract class BaseFragment extends Fragment{

    protected Activity mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext=getActivity();
        return initView(inflater,container,savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected  void initData(){

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext=null;
    }

    public abstract  View initView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceStat);
}
