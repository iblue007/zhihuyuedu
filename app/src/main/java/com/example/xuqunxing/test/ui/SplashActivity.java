package com.example.xuqunxing.test.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.bean.SplashBean;
import com.example.xuqunxing.test.presenter.SplashPresenter;
import com.example.xuqunxing.test.ui.interfaces.Isplash;
import com.example.xuqunxing.test.util.BitmapUtil;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.util.Util;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

/**欢迎页面
 * Created by xuqunxing on 2016/3/29.
 */
public class SplashActivity extends Activity implements Isplash{

    private ImageView start;
    private SplashPresenter splashPresenter;
    private File imgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        start = (ImageView) findViewById(R.id.iv_start);
        splashPresenter=new SplashPresenter(this);
        initImage();
    }

    private void loadData() {
        if(Util.isNetworkAvailable(SplashActivity.this)){
            splashPresenter.LoadData(Constant.START);
        }else{
            toNewActivity();
        }
    }

    @Override
    public void onSuccessView(Object obj) {
        try{
            final SplashBean splashBean=(SplashBean)obj;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapUtil.returnBitmap(splashBean.img);
                    try{
                        BitmapUtil.saveImage(imgFile, BitmapUtil.Bitmap2Bytes(bitmap));
                    }catch (Exception  e){

                    }
                }
            }).start();
        }catch (Exception e){

        }
//        Glide.with(this).load(splashBean.img).fitCenter().into(start);
    }

    private void initImage() {
        File dir = getFilesDir();
        imgFile = new File(dir, "start.jpg");
        if (imgFile.exists()) {
            start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            start.setImageResource(R.mipmap.start);
        }

        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              //访问网络获取网络图片
              loadData();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        start.startAnimation(scaleAnim);
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
    public void showError() {
        Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toNewActivity() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
