package com.example.xuqunxing.test.loadview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.loadview.inf.ILoadResult;

/**自定义swipeRefreshLayout
 * Created by xuqunxing on 2016/4/21.
 */
public class ReDefaultSwipeRefreshLayout extends SwipeRefreshLayout implements ILoadResult{

    protected RefreshListView mListView;
    private BaseAdapter mAdapter;
    /**
     * 返回一个合适的阀值 过了就给滑动
     */
    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;

    public ReDefaultSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public ReDefaultSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void init(View view) {
        setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView = (RefreshListView) view.findViewById(R.id.comm_refresh_lv);
        FootView footView = new FootView(getContext());
        mListView.addBaseFootView(footView);
    }

    public ListView getmListView() {
        return mListView;
    }

    /**
     * 设置头部
     */
    public void addHeaderView(View headView) {
        mListView.addHeaderView(headView);

    }

    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }

    /**
     * 上下拉刷新
     *
     * @param callBack
     * @param onRefreshListener
     */
    public void setUpAndDownRefresh(RefreshListView.IListViewCallBack callBack, OnRefreshListener onRefreshListener) {
        if (callBack != null) {
            mListView.setIListViewCallBack(callBack);
        }
        if (onRefreshListener != null) {
            setOnRefreshListener(onRefreshListener);
        }
    }

    public void smoothScrollToIndex(int i) {
        mListView.smoothScrollToPosition(i);

    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
        if (clickListener != null)
            mListView.setOnItemClickListener(clickListener);
    }

    @Override
    public void onLoadFailed() {
        setRefreshing(false);
        mListView.onLoadFailed();
    }

    @Override
    public void onLoadSuccess() {
        setRefreshing(false);
        mListView.onLoadSuccess();
    }

    /**
     * 加载结束
     */
    @Override
    public void onLoadComplete() {
        setRefreshing(false);
        mListView.onLoadComplete();
    }

    @Override
    public void onLoadEmpty() {
        setRefreshing(false);
        mListView.onLoadEmpty();
    }

    @Override
    public void onLoadStart() {
        setRefreshing(false);
        mListView.onLoadStart();

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(event);
    }

}
