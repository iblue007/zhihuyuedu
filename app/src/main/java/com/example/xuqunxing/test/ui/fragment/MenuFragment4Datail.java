package com.example.xuqunxing.test.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.adapter.MainFragmentAdapter;
import com.example.xuqunxing.test.bean.LeftMenuDatailBean;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.presenter.LeftMenuDatailPresenter;
import com.example.xuqunxing.test.ui.LeftMenuDetailActivity;
import com.example.xuqunxing.test.ui.MainActivity;
import com.example.xuqunxing.test.ui.interfaces.IMenuDetailFragment;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.util.Util;

/**侧拉框的详情
 * Created by xuqunxing on 2016/4/7.
 */
public class MenuFragment4Datail extends BaseFragment implements IMenuDetailFragment{

    private ListView listView;
    private ImageView iv_title;
    private TextView tv_title;
    private int urlId;
    private LeftMenuDatailPresenter leftMenuDatailPresenter;
    private MainFragmentAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MenuFragment4Datail(int id, String name) {
        urlId=id;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStat) {
        View view= View.inflate(mContext, R.layout.fragment_menu_detail_menu, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefrseshLayout);
        listView = (ListView) view.findViewById(R.id.lv_news);
        View header = LayoutInflater.from(mContext).inflate(R.layout.fragment_menu_detail_header, listView, false);
        iv_title = (ImageView) header.findViewById(R.id.iv_title);
        tv_title = (TextView) header.findViewById(R.id.tv_title);
        listView.addHeaderView(header);
        leftMenuDatailPresenter=new LeftMenuDatailPresenter(this);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        leftMenuDatailPresenter.setUrlId(urlId);
        if(Util.isNetworkAvailable(mContext)){
            leftMenuDatailPresenter.LoadData(Constant.THEMENEWS+urlId);
        }else{
            leftMenuDatailPresenter.loadLocalData();
        }
    }

    @Override
    public void onSuccessView(Object obj) {
        final LeftMenuDatailBean leftMenuDatailBean=(LeftMenuDatailBean)obj;
        ((MainActivity) mContext).setToolbarTitle(leftMenuDatailBean.name);
        tv_title.setText(leftMenuDatailBean.description);
        Glide.with(mContext).load(leftMenuDatailBean.image).crossFade().into(iv_title);
        mAdapter = new MainFragmentAdapter(mContext, leftMenuDatailBean.stories);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int[] startingLocation = new int[2];
                view.getLocationOnScreen(startingLocation);
                startingLocation[0] += view.getWidth() / 2;
                StoriesBean entity = leftMenuDatailBean.stories.get(position);
                Intent intent = new Intent(mContext, LeftMenuDetailActivity.class);
                intent.putExtra(Constant.START_LOCATION, startingLocation);
                intent.putExtra("entity", entity);

                TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_title.setTextColor(getResources().getColor(R.color.clicked_tv_textcolor));

                startActivity(intent);
                mContext.overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void showError() {

    }

    @Override
    public void onLoadMoreSueecee(Object obj) {

    }
}
