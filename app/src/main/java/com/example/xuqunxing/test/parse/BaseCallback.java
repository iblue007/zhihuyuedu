package com.example.xuqunxing.test.parse;

import com.example.xuqunxing.test.ui.interfaces.IMenuDetailFragment;

import java.io.IOException;

/**
 * Created by xuqunxing on 2016/3/30.
 */
public abstract class BaseCallback<T> implements IMenuDetailFragment {

//    public abstract T parseNetworkResponse(Response response) throws IOException;
    public abstract T parseNetworkResponse4Str(String response) throws IOException;
    @Override
    public void onSuccessView(Object obj) {

    }

    @Override
    public void showError() {

    }
}
