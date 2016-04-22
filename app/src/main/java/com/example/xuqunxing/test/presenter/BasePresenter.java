package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.biz.LoadDataBiz;
import com.example.xuqunxing.test.interfacess.IUIDataListener;
import com.example.xuqunxing.test.parse.ParseJsonCallBack;

/**
 * Created by xuqunxing on 2016/3/29.
 */
public abstract class BasePresenter<T> extends ParseJsonCallBack<T>  {
    protected LoadDataBiz loadDataBiz=new LoadDataBiz();

    @Override
    public void LoadData(String url) {
        loadDataBiz.loadDataByGet(url, new IUIDataListener() {
            @Override
            public void uiDataSuccess(Object obj) {
                onSuccessView(obj);
            }

            @Override
            public void uiDataError() {
                showError();
            }
        });
    }

    @Override
    public void LoadData(int page, String url) {
        loadDataBiz.loadDataByGet(url, new IUIDataListener() {
            @Override
            public void uiDataSuccess(Object obj) {
                onLoadMoreSueecee(obj);
            }

            @Override
            public void uiDataError() {
                showError();
            }
        });
    }
}
