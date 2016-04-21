package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.bean.NewsDetailBean;
import com.example.xuqunxing.test.ui.interfaces.ILeftMenuDetailActivity;
import com.example.xuqunxing.test.util.Util;

import java.io.IOException;

/**
 * Created by xuqunxing on 2016/4/7.
 */
public class LeftMenuDetailActivityPresenter extends BasePresenter<NewsDetailBean>{

    protected ILeftMenuDetailActivity iLeftMenuDetailActivity;
//    protected LoadDataBiz loadDataBiz;
    public LeftMenuDetailActivityPresenter(ILeftMenuDetailActivity iLeftMenuDetailActivity1){
        iLeftMenuDetailActivity=iLeftMenuDetailActivity1;
//        loadDataBiz=new LoadDataBiz();
    }
//    @Override
//    public void LoadData(String url) {
//        loadDataBiz.loadDataByGet(url, new IUIDataListener() {
//            @Override
//            public void uiDataSuccess(Object obj) {
//                try {
//                    String json = new String(((String) obj).getBytes("utf-8"), "utf-8");
//                    //缓存数据
//                    if(json!=null){
//                        Util.save2Local(json, Util.getSimpleClassName(getClass().getName()), "LoadData");
//                    }else{
//                        // Util.save2Local(json,Util.getSimpleClassName(getClass().getName())+"/"+UserInfoManager.getInstance().getUserId(),mTeamId+"_"+1);
//                    }
//
//                    NewsDetailBean NewsDetailBean = parseNetworkResponse4Str(json);
//                    iLeftMenuDetailActivity.onSuccessView(NewsDetailBean);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void uiDataError() {
//                iLeftMenuDetailActivity.onFailView();
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

            NewsDetailBean NewsDetailBean = parseNetworkResponse4Str(json);
            iLeftMenuDetailActivity.onSuccessView(NewsDetailBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError() {
        iLeftMenuDetailActivity.onFailView();
    }
}
