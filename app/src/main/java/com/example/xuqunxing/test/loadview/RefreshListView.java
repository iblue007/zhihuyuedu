package com.example.xuqunxing.test.loadview;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.xuqunxing.test.loadview.inf.ILoadResult;

/**
 * Created by xuqunxing on 2016/4/21.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener,
        ILoadResult {
    private static final int  MAX_Y_OVERSCROLL_DISTANCE = 100;
    private int mMaxYOverscrollDistance;
    private int mPageSize = 10;
    /**
     * 底部布局
     */
    private BaseFootView mFootView;
    /**
     * 每页的第5项就加载下一页
     */
    private   int BEFOR_LOAD_NUM = 5;

    /**
     * 正在加载中
     */
    private boolean isLoading;

    /**
     * 是否已经加载完结
     */
    private boolean isComplete;
    /**
     * 页面回调
     */
    private IListViewCallBack mCallBack;

    private int mFirstVisibleItem;
    /**
     * 已经显示的项
     */
    private int mVisibledCount;
    /**
     * 总的项
     */
    private int mTotalItemCount;

    /**
     * 滚动状态
     */
    private int iScrollState;

    /**
     * 回调接口
     *
     * @author linbinghuang
     *
     */
    public interface IListViewCallBack {

        /**
         * 上拉刷新
         */
        public void onUpLoad();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshListView(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        initBounceListView();
        setOnScrollListener(this);

    }

    /**
     * 设置回调
     *
     * @param callBack
     */
    public void setIListViewCallBack(IListViewCallBack callBack) {
        mCallBack = callBack;
        mFootView.setmCallBack(mCallBack);
    }

    @Override
    public void addFooterView(View v, Object data, boolean isSelectable) {
        if (v instanceof BaseFootView && mFootView==null) {
            mFootView = (BaseFootView) v;
            mFootView.setmCallBack(mCallBack);
        }
        super.addFooterView(v, data, isSelectable);
    }

//	@Override
//	public void addFooterView(View v) {
//		if (v instanceof BaseFootView && mFootView==null) {
//			mFootView = (BaseFootView) v;
//			mFootView.setmCallBack(mCallBack);
//		}
//		super.addFooterView(v, null, false);
//	}

    @Override
    public boolean removeFooterView(View v) {
        if (mFootView == v) {
            mFootView = null;
        }
        return super.removeFooterView(v);
    }
    /**
     * list底部加载
     */
    public void addBaseFootView(View v){
        if (v instanceof BaseFootView && mFootView==null) {
            mFootView = (BaseFootView) v;
            mFootView.setmCallBack(mCallBack);
            addFooterView(v, null, false);
        }

    }
    /**
     * 移除加载的footView
     * @return
     */
    public boolean removeFooterView() {
        if (mFootView != null) {
            BaseFootView view = mFootView;
            mFootView = null;
            return removeFooterView(view);
        }
        return false;
    }
    /**
     * 替换footView
     * @return
     */
    public void replaceFooterView(View view) {
        if (mFootView != null) {
            removeFooterView(mFootView);
        }
        addBaseFootView(view);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        iScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem  = firstVisibleItem;
        mVisibledCount = firstVisibleItem + visibleItemCount;
        mTotalItemCount = totalItemCount;
        scollLoadData();
    }

    @Override
    public void onLoadFailed() {
        isLoading = false;
        if (mFootView != null) {
            mFootView.onLoadFailed();
        }
    }

    @Override
    public void onLoadSuccess() {
        isLoading = false;
        isComplete = false;
        if (mFootView != null) {
            mFootView.onLoadSuccess();
        }
    }

    @Override
    public void onLoadComplete() {
        isLoading = false;
        if (mFootView != null) {
            mFootView.onLoadComplete();
        }
        isComplete = true;
    }

    @Override
    public void onLoadEmpty() {
        isLoading = false;
        isComplete = false;
        if (mFootView != null) {
            mFootView.onLoadEmpty();
        }
    }

    @Override
    public void onLoadStart() {
        isLoading = false;
        isComplete = false;
        if (mFootView != null) {
            mFootView.onLoadStart();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

//    /**
//     * 网络改变广播
//     */
//    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context arg0, Intent arg1) {
//            String action = arg1.getAction();
//            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                ConnectivityManager connectivityManager = (ConnectivityManager) getContext()
//                        .getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//                if (info != null && info.isAvailable()) {
//                    loadData();
//                } else {
//
//                }
//            }
//        }
//    };

    private void scollLoadData() {
        if (iScrollState == SCROLL_STATE_IDLE) {
            return;
        }
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData() {

        if (mTotalItemCount >= mPageSize) {
            boolean isLoad = mTotalItemCount - mVisibledCount < BEFOR_LOAD_NUM ? true
                    : false;
            if (isLoad && !isLoading) {

                if (mCallBack != null && !isLoading && !isComplete) {
                    isLoading = true;
                    mCallBack.onUpLoad();
                }
            }
        }
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }

    public BaseFootView getmFootView() {
        return mFootView;
    }

    private void initBounceListView(){

        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){
        if(!isComplete){
            return false;
        }
        if(mFirstVisibleItem==0){
            return false;
        }
//		Log.i("FFF", "mVisibledCount"+mVisibledCount+"......mFirstVisibleItem="+mFirstVisibleItem);
//		Log.i("FFF", "deltaX="+deltaX+"...deltaY="+deltaY+"...scrollX="+scrollX+"scrollY="+scrollY+"...scrollRangeX="+scrollRangeX+"...scrollRangeY="+scrollRangeY+"...maxOverScrollX="+maxOverScrollX+"...maxOverScrollY="+maxOverScrollY+"...isTouchEvent="+isTouchEvent);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }


    public void setbeforLoadNum(int BEFOR_LOAD_NUM) {
        this.BEFOR_LOAD_NUM = BEFOR_LOAD_NUM;
    }
}
