package com.example.xuqunxing.test.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.adapter.MainFragmentAdapter;
import com.example.xuqunxing.test.baseLayout.fragment.BasicLoadstateHttpFragment;
import com.example.xuqunxing.test.bean.MainFragmentBean;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.presenter.MainFragmentPresenter;
import com.example.xuqunxing.test.ui.MainActivity;
import com.example.xuqunxing.test.ui.NewsDetailActivity;
import com.example.xuqunxing.test.ui.interfaces.IMainFragment;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.view.MainFragmentHeadView;

/**主页fragment
 * Created by xuqunxing on 2016/3/31.
 */
public class MainFragment extends BasicLoadstateHttpFragment implements IMainFragment {

    private ListView listView;
    private MainFragmentPresenter mainFragmentPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainFragmentHeadView headView;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) mContext).setToolbarTitle("今日热闻");
        View view=View.inflate(mContext, R.layout.fragment_main,null);
        listView = (ListView) view.findViewById(R.id.lv_news);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefrseshLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //replaceFragment();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        headView = new MainFragmentHeadView(mContext);
        listView.addHeaderView(headView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView != null && listView.getChildCount() > 0) {
                    //让下拉刷新的操作在ListView被滑动到最顶部的时候才可以下拉刷新
                    boolean enable = (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop() == 0);
                    setSwipeRefreshEnable(enable);
                }
            }
        });
        return view;
    }

    @Override
    public void initDataAfterView() {
        loadData();
    }

    @Override
    public void initListener() {

    }


    @Override
    public void loadData() {
        mainFragmentPresenter=new MainFragmentPresenter(this);
        mainFragmentPresenter.LoadData(Constant.LATESTNEWS);
    }

    @Override
    public void loadData(int page) {
        mainFragmentPresenter=new MainFragmentPresenter(this);
        mainFragmentPresenter.LoadData(Constant.LATESTNEWS);
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
            onLoadSuccess();
            final MainFragmentBean mainFragmentBean=(MainFragmentBean)obj;
            MainFragmentAdapter adapter=new MainFragmentAdapter(mContext,mainFragmentBean.stories);
            listView.setAdapter(adapter);
            headView.setData(mainFragmentBean.top_stories);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int[] startingLocation = new int[2];
                    view.getLocationOnScreen(startingLocation);
                    startingLocation[0] += view.getWidth() / 2;
                    StoriesBean entity = mainFragmentBean.stories.get(position-1>=0?position-1:0);
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(Constant.START_LOCATION, startingLocation);
                    intent.putExtra("entity", entity);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                    tv_title.setTextColor(getResources().getColor(R.color.clicked_tv_textcolor));
                    startActivity(intent);
                    mContext.overridePendingTransition(0, 0);
                }
            });
        }catch (Exception e){

        }
    }

    @Override
    public void showError() {
        onLoadEmpty();
    }

    /**
     * 设置是否可以下拉刷新
     * @param enable
     */
    public void setSwipeRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }
}
