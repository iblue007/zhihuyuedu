package com.example.xuqunxing.test.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.adapter.MainFragmentAdapter;
import com.example.xuqunxing.test.baseLayout.fragment.BasicLoadstateHttpFragment;
import com.example.xuqunxing.test.baseLayout.view.loadstate.LoadstatueViewFactory;
import com.example.xuqunxing.test.bean.MainFragmentBean;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.loadview.ReDefaultSwipeRefreshLayout;
import com.example.xuqunxing.test.loadview.RefreshListView;
import com.example.xuqunxing.test.presenter.MainFragmentPresenter;
import com.example.xuqunxing.test.ui.MainActivity;
import com.example.xuqunxing.test.ui.NewsDetailActivity;
import com.example.xuqunxing.test.ui.interfaces.IMainFragment;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.util.ToastUtils;
import com.example.xuqunxing.test.util.Util;
import com.example.xuqunxing.test.view.MainFragmentHeadView;

/**主页fragment
 * Created by xuqunxing on 2016/3/31.
 */
public class MainFragment extends BasicLoadstateHttpFragment implements IMainFragment {

            private ListView listView;
            private MainFragmentPresenter mainFragmentPresenter;
            private ReDefaultSwipeRefreshLayout swipeRefreshLayout;
            private MainFragmentHeadView headView;
            private MainFragmentAdapter adapter;

            @Override
            public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view=View.inflate(mContext, R.layout.fragment_main, null);
                try{
                    ((MainActivity) mContext).setToolbarTitle("今日热闻");
                    swipeRefreshLayout = (ReDefaultSwipeRefreshLayout) view.findViewById(R.id.comm_swipe_refresh_ly);
                    swipeRefreshLayout.init(view);
                    listView=swipeRefreshLayout.getmListView();
                    headView = new MainFragmentHeadView(mContext);
                    listView.addHeaderView(headView);
                }catch (Exception e){
            Log.i("Exception","Exception");
       }
        return view;
    }

    @Override
    public void initDataAfterView() {
        firstdata();
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setUpAndDownRefresh(new RefreshListView.IListViewCallBack() {
            @Override
            public void onUpLoad() {
                loadData(2);
            }
        }, new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void loadData() {
        if(Util.isNetworkAvailable(getContext())){
            mainFragmentPresenter=new MainFragmentPresenter(this);
            mainFragmentPresenter.LoadData(Constant.LATESTNEWS);
        }else{
            if(mainFragmentPresenter==null){
                mainFragmentPresenter=new MainFragmentPresenter(this);
            }
            mainFragmentPresenter.loadLocalData();
           // onLoadEmpty();
            //ToastUtils.showToast(getContext(),R.string.net_wrong);
        }

    }

    @Override
    public void loadData(int page) {
        if(Util.isNetworkAvailable(getContext())){
            mainFragmentPresenter.LoadData(page,Constant.LATESTNEWS);
        }else{
            ToastUtils.showToast(getContext(), R.string.net_wrong);
            swipeRefreshLayout.onLoadFailed();
            //    LoadstatueViewFactory.loadIsFailed(adapter, swipeRefreshLayout, this);
        }

    }

    @Override
    public View getContentView() {
        return swipeRefreshLayout;
    }

    @Override
    public void invalidateViews() {

    }

    @Override
    public void onSuccessView(Object obj) {
        try{
           // onLoadSuccess();
            final MainFragmentBean mainFragmentBean=(MainFragmentBean)obj;
            adapter = new MainFragmentAdapter(mContext,mainFragmentBean.stories);
            listView.setAdapter(adapter);
            headView.setData(mainFragmentBean.top_stories);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int[] startingLocation = new int[2];
                    view.getLocationOnScreen(startingLocation);
                    startingLocation[0] += view.getWidth() / 2;
                    StoriesBean entity = mainFragmentBean.stories.get(position - 1 >= 0 ? position - 1 : 0);
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(Constant.START_LOCATION, startingLocation);
                    intent.putExtra("entity", entity);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                    tv_title.setTextColor(getResources().getColor(R.color.clicked_tv_textcolor));
                    startActivity(intent);
                    mContext.overridePendingTransition(0, 0);
                }
            });
            LoadstatueViewFactory.loadSuccess(adapter, swipeRefreshLayout, this);
        }catch (Exception e){

        }
    }

    @Override
    public void showError() {
       // onLoadEmpty();
        LoadstatueViewFactory.loadIsFailed(adapter, swipeRefreshLayout, this);
    }

    @Override
    public void onLoadMoreSueecee(Object obj) {
        try{
            ToastUtils.showToast(mContext, "模拟加载下一页");
           // onLoadSuccess();
            final MainFragmentBean mainFragmentBean=(MainFragmentBean)obj;
            adapter.addNotifyDataSetChanged(mainFragmentBean.stories);
            LoadstatueViewFactory.loadSuccess(adapter, swipeRefreshLayout, this);
        }catch (Exception e){

        }
    }

    /**
     * 设置是否可以下拉刷新
     * @param enable
     */
//    public void setSwipeRefreshEnable(boolean enable) {
//        swipeRefreshLayout.setEnabled(enable);
//    }
}
