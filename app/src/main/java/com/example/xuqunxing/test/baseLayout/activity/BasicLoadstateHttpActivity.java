package com.example.xuqunxing.test.baseLayout.activity;

import android.view.View;

import com.example.xuqunxing.test.baseLayout.activity.inf.ILazyLoad;
import com.example.xuqunxing.test.baseLayout.view.loadstate.LoadstatueViewFactory;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class BasicLoadstateHttpActivity extends LoadstateActivity implements ILazyLoad{

    @Override
    public boolean firstdata() {
        onLoadStart();
        loadData();
        return true;
    }

    @Override
    public View getLoadingView() {
        if (mContentView != null) {
            return LoadstatueViewFactory.getLoadingViewRw(this, mContentView);
        }
        return null;
    }

    @Override
    public View getLoadFailedView() {
        if (mContentView != null) {
            return LoadstatueViewFactory.getEmptyViewRw(this, mContentView, new View.OnClickListener() {
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
            return LoadstatueViewFactory.getEmptyViewRw(this, mContentView, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstdata();
                }
            });
        }
        return null;
    }
}
