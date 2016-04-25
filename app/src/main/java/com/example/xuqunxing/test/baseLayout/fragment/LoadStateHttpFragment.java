package com.example.xuqunxing.test.baseLayout.fragment;

import com.example.xuqunxing.test.baseLayout.fragment.inf.ILazyLoad;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class LoadStateHttpFragment extends LoadStateFragment implements ILazyLoad{

    @Override
    public boolean firstdata() {
        onLoadStart();
        loadData();
        return true;
    }
}
