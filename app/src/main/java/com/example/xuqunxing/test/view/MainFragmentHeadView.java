package com.example.xuqunxing.test.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.adapter.ImagePagerAdapter;
import com.example.xuqunxing.test.bean.MainFragmentBean;
import com.example.xuqunxing.test.ui.MainActivity;

import java.lang.reflect.Field;
import java.util.List;

/**主页头布局--轮播图布局
 * Created by xuqunxing on 2016/4/6.
 */
public class MainFragmentHeadView extends LinearLayout implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    /**
     * 请求更新显示的View。
     */
    protected static final int MSG_UPDATE_IMAGE  = 1;
    /**
     * 请求暂停轮播。
     */
    protected static final int MSG_KEEP_SILENT   = 2;
    /**
     * 请求恢复轮播。
     */
    protected static final int MSG_BREAK_SILENT  = 3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    protected static final int MSG_PAGE_CHANGED  = 4;

    private static final long MSG_DELAY = 5000;

    private ViewPager mViewPager;
    protected LinearLayout mTabLy;
    protected Context mContext;
    private ImagePagerAdapter mAdapter;

    public MainFragmentHeadView(Context context) {
        this(context, null);
    }

    public MainFragmentHeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MainFragmentHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
//        int height = ScreenUtil.getCurrentScreenWidth1(mContext) * 4 / 9;
        int height=((MainActivity)mContext).getWindowManager().getDefaultDisplay().getHeight();
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams
                .MATCH_PARENT, 400);
        setLayoutParams(params);
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_mainfragment_headview_viewapger, null);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLy = (LinearLayout) view.findViewById(R.id.ad_small_tab_ly);
        mViewPager.setOnPageChangeListener(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(view, layoutParams);
        setViewPagerScrollSpeed();
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
    }

    /**
     * 设置ViewPager的滑动速度
     * */
    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller( mViewPager.getContext( ),new AccelerateInterpolator());
            scroller.setmDuration(1000);
            mScroller.set( mViewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }
    private int positionViewPager=0;
    private int size;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Activity activity = (Activity) mContext;
            if (activity==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (mHandler.hasMessages(MSG_UPDATE_IMAGE)){
                mHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what){
                case MSG_UPDATE_IMAGE:
                    positionViewPager++;
                    mViewPager.setCurrentItem(positionViewPager);
                    //准备下次播放
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    positionViewPager = msg.arg1;
                    break;
                default:
                    break;
            }
        }

    };
//    List<MainFragmentBean.top_stories> resIds=new ArrayList<MainFragmentBean.top_stories>();
    List<MainFragmentBean.top_stories> resIds;
    public void setData(List<MainFragmentBean.top_stories> resId) {
        resIds=resId;
//        TopAdInfo topInfo=new TopAdInfo();
//        topInfo.setImgUrl("http://attach.bbs.miui.com/forum/month_1012/101203122706c89249c8f58fcc.jpg");
//        topInfo.setTitle("天天酷跑");
//        topInfo.setOpenType(2);
//        topInfo.setOpenUrl("http://bbsdown10.cnmo.com/attachments/201308/06/091441rn5ww131m0gj55r0.jpg");
//        TopAdInfo topInfo2=new TopAdInfo();
//        topInfo2.setImgUrl("http://res.anjian.com/anjianmobile/other/2016/2/27/http_imgload.jpg");
//        topInfo2.setTitle("自动抢红包");
//        topInfo2.setOpenType(2);
//        topInfo2.setOpenUrl("http://act.anjian.com/2015/SYFZ/");
//        resIds.add(topInfo);
//        resIds.add(topInfo2);
        size = resIds.size();
        //添加第六项
        mTabLy.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView tabView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_main_fragment_head_tab, null);
            tabView.setTag(i);
            tabView.setOnClickListener(this);
            mTabLy.addView(tabView);
        }
        if(resId.size()>1){
            mTabLy.setVisibility(View.VISIBLE);
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
        }else{
            mTabLy.setVisibility(View.GONE);
        }
        mAdapter = new ImagePagerAdapter(resId,mContext);
        mViewPager.setAdapter(mAdapter);
        selected(0);

//        //开始轮播效果
//        if(resId.size()>=2){//如果只有一张推荐图则不需要轮播
//            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
//        }
    }
    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        selected(position);
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        if(size>=2){
            mHandler.sendMessage(Message.obtain(mHandler, MSG_PAGE_CHANGED, position, 0));
        }
        if(size>0){
            selected(position%size);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                if(size>=2){
                    mHandler.sendEmptyMessage(MSG_KEEP_SILENT);
                }
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if(size>=2){
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHandler.removeCallbacks(null);
        return super.onTouchEvent(event);
    }

    private void selected(int position) {
        for (int i = 0; i < mTabLy.getChildCount(); i++) {
            ImageView imageView = (ImageView) mTabLy.getChildAt(i);
            if (i == position) {
                imageView.setImageResource(R.mipmap.bg_circle_blue);
            } else {
                imageView.setImageResource(R.mipmap.bg_circle_gray);
            }
        }
    }
}
