package com.example.xuqunxing.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.baseLayout.activity.BasicLoadstateHttpActivity;
import com.example.xuqunxing.test.bean.NewsDetailBean;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.manager.UMManager;
import com.example.xuqunxing.test.presenter.NewsDetailActivityPresenter;
import com.example.xuqunxing.test.ui.interfaces.INewsDetailActivity;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.view.RevealBackgroundView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**新闻详情
 * Created by xuqunxing on 2016/3/31.
 */
public class NewsDetailActivity extends BasicLoadstateHttpActivity implements RevealBackgroundView.OnStateChangeListener,INewsDetailActivity {

    private StoriesBean entity;
    private WebView mWebView;
    private ImageView iv;
    private AppBarLayout mAppBarLayout;
    private RevealBackgroundView vRevealBackground;
    private NewsDetailActivityPresenter newsDetailActivityPresenter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_news_details);
        newsDetailActivityPresenter=new NewsDetailActivityPresenter(this);
        entity = (StoriesBean) getIntent().getSerializableExtra("entity");

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        vRevealBackground = (RevealBackgroundView) findViewById(R.id.revealBackgroundView);
        iv = (ImageView) findViewById(R.id.iv);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);

        setupRevealBackground(savedInstanceState);
        setStatusBarColor(getResources().getColor(R.color.light_toolbar));

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataAfterView() {
        HashMap<String,String> map=new HashMap<>();
        map.put("type", "newdetailactivity");
        map.put("entityid", entity.id + "");
        UMManager.getInstance().event(Constant.new_detail_activity, map);
        loadData();
    }

    @Override
    public void loadData() {
        newsDetailActivityPresenter.setDataId(entity.id);
        newsDetailActivityPresenter.LoadData(Constant.CONTENT + entity.id);
    }

    @Override
    public View getContentView() {
        return coordinatorLayout;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        finish();
//        overridePendingTransition(0, R.anim.slide_out_to_left_from_right);
        overridePendingTransition(0, R.anim.slide_out_to_left);
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        mAppBarLayout.setVisibility(View.GONE);
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(Constant.START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();

                }
            });
            CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            mCollapsingToolbarLayout.setTitle(entity.title);
            //扩张时候的title颜色
//        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.dark_toolbar));
//        收缩后在Toolbar上显示时的title的颜色：
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.dark_toolbar));
            //ToolBar被折叠到顶部固定时候的背景
            mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.light_toolbar));
            //状态栏的背景,不过这个只能在Android5.0以上系统有效果
            mCollapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.light_toolbar));

            mAppBarLayout.setVisibility(View.VISIBLE);
            setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onSuccessView(Object obj) {
        onLoadSuccess();
        NewsDetailBean newsDetailBean=(NewsDetailBean)obj;
        Glide.with(mContext).load(newsDetailBean.getImage()).into(iv);
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + newsDetailBean.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public void onFailView() {
        onLoadEmpty();
    }


}
