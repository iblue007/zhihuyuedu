package com.example.xuqunxing.test.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.bean.MainFragmentBean;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.ui.NewsDetailActivity;
import com.example.xuqunxing.test.util.Constant;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    private List<MainFragmentBean.top_stories> images;
    private Context mContext;

    public ImagePagerAdapter(List<MainFragmentBean.top_stories> images, Context context) {
        this.images = images;
        this.mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {

        return images.size()==1?1:Integer.MAX_VALUE;
        //return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = LayoutInflater.from(mContext).inflate(R.layout.item_main_fragment_heand_pager_image, view, false);
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    MainFragmentBean.top_stories top_stories = images.get(position%5);
                    int[] startingLocation = new int[2];
                    view.getLocationOnScreen(startingLocation);
                    startingLocation[0] += view.getWidth() / 2;
                    StoriesBean entity =new StoriesBean();
                    entity.setId(top_stories.id);
                    entity.setTitle(top_stories.title);
                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                    intent.putExtra(Constant.START_LOCATION, startingLocation);
                    intent.putExtra("entity", entity);
                    mContext.startActivity(intent);
                    ((Activity)mContext).overridePendingTransition(0, 0);
                }catch (Exception e){

                }
            }
        });
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
        if(images.size()>0){
            Glide.with(mContext).load(images.get(position % images.size()).image).crossFade().into(imageView);
//            ImageLoader.getInstance().displayImage(images.get(position%images.size()).getImgUrl(), imageView,
//                    ImageLoaderManager.getDisplayImageOptions(R.drawable.bg_normal_pic), new
//                            SimpleImageLoadingListener() {
//                                @Override
//                                public void onLoadingStarted(String imageUri, View view) {
//                                    spinner.setVisibility(View.VISIBLE);
//                                }
//
//                                @Override
//                                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                                    String message = null;
//                                    switch (failReason.getType()) {     // 获取图片失败类型
//                                        case IO_ERROR:              // 文件I/O错误
//                                            message = "Input/Output error";
//                                            break;
//                                        case DECODING_ERROR:        // 解码错误
//                                            message = "Image can't be decoded";
//                                            break;
//                                        case NETWORK_DENIED:        // 网络延迟
//                                            message = "Downloads are denied";
//                                            break;
//                                        case OUT_OF_MEMORY:         // 内存不足
//                                            message = "Out Of Memory error";
//                                            break;
//                                        case UNKNOWN:               // 原因不明
//                                            message = "Unknown error";
//                                            break;
//                                    }
////                                ToastUtil.showToast(mContext, message);
//                                    spinner.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                                    spinner.setVisibility(View.GONE);       // 不显示圆形进度条
//                                }
//                            });
        }

        view.addView(imageLayout, 0);     // 将图片增加到ViewPager
        return imageLayout;
        // return images.get(position%images.size());
    }

//    public static boolean checkURL(String urls){
//        boolean value=false;
////        try {
////            HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
////            int code=conn.getResponseCode();
////            System.out.println(">>>>>>>>>>>>>>>> "+code+" <<<<<<<<<<<<<<<<<<");
////            if(code!=200){
////                value=false;
////            }else{
////                value=true;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        URL url;
//        try {
//            url = new URL("http://www.baidu.com");
//            InputStream in = url.openStream();
//            System.out.println("连接可用");
//            value=true;
//        } catch (Exception e1) {
//            System.out.println("连接打不开!");
//            url = null;
//            value=false;
//        }
//        return value;
//    }

    private boolean checkString(String s) {


        return s.matches("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}