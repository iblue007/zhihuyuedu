package com.example.xuqunxing.test.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.baseLayout.activity.BasicLoadstateHttpActivity;
import com.example.xuqunxing.test.bean.NewsDetailBean;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.presenter.LeftMenuDetailActivityPresenter;
import com.example.xuqunxing.test.ui.interfaces.ILeftMenuDetailActivity;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.util.Util;
import com.example.xuqunxing.test.view.RevealBackgroundView;

/**侧拉详情activity
 * Created by xuqunxing on 2016/4/7.
 */
public class LeftMenuDetailActivity extends BasicLoadstateHttpActivity implements RevealBackgroundView.OnStateChangeListener,ILeftMenuDetailActivity {
    private WebView mWebView;
    private StoriesBean entity;
    private RevealBackgroundView vRevealBackground;
    private CoordinatorLayout coordinatorLayout;
    private boolean isLight;
    private LeftMenuDetailActivityPresenter leftMenuDetailActivityPresenter;



    private void setupRevealBackground(Bundle savedInstanceState) {
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
            coordinatorLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_left);
    }

    @Override
    public void onSuccessView(Object obj) {
        onLoadSuccess();
        NewsDetailBean newsDetailBean=(NewsDetailBean)obj;
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + newsDetailBean.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        mWebView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    @Override
    public void onFailView() {
        onLoadEmpty();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_leftmenu_detail);
        leftMenuDetailActivityPresenter=new LeftMenuDetailActivityPresenter(this);
        isLight = getIntent().getBooleanExtra("isLight", true);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        coordinatorLayout.setVisibility(View.INVISIBLE);
        vRevealBackground = (RevealBackgroundView) findViewById(R.id.revealBackgroundView);
        entity = (StoriesBean) getIntent().getSerializableExtra("entity");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("享受阅读的乐趣");
        toolbar.setBackgroundColor(getResources().getColor(isLight ? R.color.light_toolbar : R.color.dark_toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataAfterView() {
        loadData();
    }

    @Override
    public void loadData() {
        if(Util.isNetworkAvailable(mContext)){
            leftMenuDetailActivityPresenter.LoadData(Constant.CONTENT + entity.id);
        }else{
            leftMenuDetailActivityPresenter.loadLocalData();
        }

    }

    @Override
    public View getContentView() {
        return coordinatorLayout;
    }
}
