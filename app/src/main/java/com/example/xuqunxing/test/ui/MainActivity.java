package com.example.xuqunxing.test.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.ui.fragment.MainFragment;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class MainActivity extends BaseActivity {

    private FrameLayout flContent;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private String curfragment = Constant.INDEX_MAIN_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushAgent.getInstance(mContext).onAppStart();
        PushAgent mPushAgent = PushAgent.getInstance(mContext);
        mPushAgent.enable();
        mPushAgent.setAlias("xyp",Constant.ALIAS_TYPE);
        //开启推送并设置注册的回调处理
//        mPushAgent.enable(new IUmengRegisterCallback() {
//
//            @Override
//            public void onRegistered(final String registrationId) {
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //onRegistered方法的参数registrationId即是device_token
//                        Log.i("device_token", registrationId);
//                    }
//                });
//            }
//        });

//        String device_token = UmengRegistrar.getRegistrationId(mContext);
//        Log.i("device_token", device_token);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        flContent = (FrameLayout) findViewById(R.id.fl_content);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        
        toolbar.setBackgroundColor(getResources().getColor(R.color.light_toolbar));
        setSupportActionBar(toolbar);
        setStatusBarColor(getResources().getColor(R.color.light_toolbar));
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //在Activity的onPostCreate()中；指示，ActionBarDrawerToggle与DrawerLayout的状态同步，并将ActionBarDrawerToggle中的drawer图标，设置为ActionBar的Home-Button的icon
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                .replace(R.id.fl_content,new MainFragment()).commit();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    /**
     * 替换fragment
     */
    public void replaceFragment() {
        if(!curfragment.equals(Constant.INDEX_MAIN_FRAGMENT)){
             getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                     .replace(R.id.fl_content,new MainFragment()).commit();
        }else{

        }
    }

    public void setToolbarTitle(String str) {
        toolbar.setTitle(str);
    }

    public void closeMenu() {
        drawerLayout.closeDrawers();
    }

    private long firstTime;
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            closeMenu();
        }else{
            long secondTime=System.currentTimeMillis();
            if(secondTime-firstTime>2000){
                Snackbar sb = Snackbar.make(flContent, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor( android.R.color.holo_blue_dark));
                sb.show();
                firstTime = secondTime;
            }else{
                finish();
            }
        }
    }

    public void setCurId(String curId) {
        this.curfragment = curId;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu,menu);
        menu.getItem(0).setTitle("白天模式");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_mode){
            ToastUtils.showToast(mContext,"白天模式");
        }else if(id==R.id.action_setting){
            ToastUtils.showToast(mContext,"设置模式");
        }
        return super.onOptionsItemSelected(item);
    }
}
