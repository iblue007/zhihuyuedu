package com.example.xuqunxing.test.baseLayout.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xuqunxing.test.baseLayout.fragment.inf.IFragmentView;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class BaseLoadFragment extends Fragment implements IFragmentView{

    protected Activity mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        initDataBeforView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=initView(inflater,container,savedInstanceState);
        initDataAfterView();
        initListener();
        initActionbar();
        return view;
    }

    @Override
    public void initActionbar() {
    }
    @Override
    public void initDataBeforView() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext=null;
    }
}
