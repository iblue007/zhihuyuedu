package com.example.xuqunxing.test.biz;

import com.example.xuqunxing.test.interfacess.ILoadDataListener;
import com.example.xuqunxing.test.interfacess.IUIDataListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**获取数据biz
 * Created by xuqunxing on 2016/3/29.
 */
public class LoadDataBiz implements ILoadDataListener{


    @Override
    public void loadDataByGet(String url, final IUIDataListener listener) {
        OkHttpUtils
                .get()
                .url(url)
//                .addParams("username", "hyman")
//                .addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onError(Call call, Exception e) {
                        listener.uiDataError();
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        listener.uiDataSuccess(response);
                    }
                });
    }


    @Override
    public void loadDataByPost(String url, IUIDataListener listener) {

    }

}
