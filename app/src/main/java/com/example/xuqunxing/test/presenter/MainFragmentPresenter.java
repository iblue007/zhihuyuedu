package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.bean.MainFragmentBean;
import com.example.xuqunxing.test.ui.interfaces.IMainFragment;
import com.example.xuqunxing.test.util.Util;

import java.io.IOException;

/**主页Fragment-presenter
 * Created by xuqunxing on 2016/3/31.
 */
public class MainFragmentPresenter extends BasePresenter<MainFragmentBean>{
    IMainFragment iMainFragment;
//    LoadDataBiz loadDataBiz;
    public MainFragmentPresenter(IMainFragment iMainFragment1){
        iMainFragment=iMainFragment1;
//        loadDataBiz=new LoadDataBiz();
    }

//    @Override
//    public void LoadData(String url) {
//        loadDataBiz.loadDataByGet(url, new IUIDataListener() {
//            @Override
//            public void uiDataSuccess(Object obj) {
////                String json=(String) obj;
//            try {
//                String json = new String(((String) obj).getBytes("utf-8"), "utf-8");
//                    //缓存数据
//                    if(json!=null){
//                        Util.save2Local(json, Util.getSimpleClassName(getClass().getName()),"LoadData");
//                    }else{
//                       // Util.save2Local(json,Util.getSimpleClassName(getClass().getName())+"/"+UserInfoManager.getInstance().getUserId(),mTeamId+"_"+1);
//                    }
//
//                    MainFragmentBean mainFragmentBean = parseNetworkResponse4Str(json);
//                    iMainFragment.onSuccessView(mainFragmentBean);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void uiDataError() {
//                iMainFragment.showError();
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

            MainFragmentBean MainFragmentBean = parseNetworkResponse4Str(json);
            iMainFragment.onSuccessView(MainFragmentBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError() {
        iMainFragment.showError();
    }
}
