package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.bean.SplashBean;
import com.example.xuqunxing.test.ui.interfaces.Isplash;
import com.example.xuqunxing.test.util.Util;

import java.io.IOException;

/**欢迎页面presenter
 * Created by xuqunxing on 2016/3/29.
 */
public class SplashPresenter extends BasePresenter<SplashBean>{
    private Isplash isplash;
//    private LoadDataBiz loadDataBiz;
    public SplashPresenter(Isplash isplash1){
        isplash=isplash1;
//        loadDataBiz=new LoadDataBiz();
    }

//    @Override
//    public void LoadData(String url) {
//        loadDataBiz.loadDataByGet(url, new IUIDataListener() {
//            @Override
//            public void uiDataSuccess(Object obj) {
//
//                try {
//                    SplashBean splashBean = parseNetworkResponse4Str((String) obj);
//                    isplash.onSuccessView(splashBean);
//                    isplash.toNewActivity();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void uiDataError() {
//                isplash.showError();
//                isplash.toNewActivity();
//            }
//        });
//    }


    @Override
    public void onSuccessView(Object obj) {
        try {
            String json = new String(((String) obj).getBytes("utf-8"), "utf-8");
            //缓存数据
            if(json!=null){
                Util.save2Local(json, Util.getSimpleClassName(getClass().getName()), "LoadData");
            }else{
                // Util.save2Local(json,Util.getSimpleClassName(getClass().getName())+"/"+UserInfoManager.getInstance().getUserId(),mTeamId+"_"+1);
            }

            SplashBean SplashBean = parseNetworkResponse4Str(json);
            isplash.onSuccessView(SplashBean);
            isplash.toNewActivity();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError() {
        isplash.showError();
        isplash.toNewActivity();
    }
}
