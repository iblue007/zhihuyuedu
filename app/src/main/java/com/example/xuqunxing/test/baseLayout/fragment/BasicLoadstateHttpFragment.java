package com.example.xuqunxing.test.baseLayout.fragment;

import android.view.View;

import com.example.xuqunxing.test.baseLayout.view.loadstate.LoadstatueViewFactory;

/**网络加载fragment基类
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class BasicLoadstateHttpFragment extends LoadStateHttpFragment{

    @Override
    public View getLoadingView() {
        if (mContentView != null) {
            return LoadstatueViewFactory.getLoadingViewRw(getContext(), mContentView);
        }
        return null;
    }

    @Override
    public View getLoadFailedView() {
        if (mContentView != null) {
            return LoadstatueViewFactory.getEmptyViewRw(getContext(), mContentView, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstdata();
                }
            });
        }
        return null;
    }

    @Override
    public View getEmptyView() {
        if (mContentView != null) {
            return LoadstatueViewFactory.getEmptyViewRw(getContext(), mContentView, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstdata();
                }
            });
        }
        return null;
    }
}
