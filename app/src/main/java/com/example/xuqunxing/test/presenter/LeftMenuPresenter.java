package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.bean.LeftMenuBean;
import com.example.xuqunxing.test.ui.interfaces.IMenuFragment;
import com.example.xuqunxing.test.util.Util;

import java.io.IOException;

/**侧滑菜单presenter
 * Created by xuqunxing on 2016/3/30.
 */
public class LeftMenuPresenter extends BasePresenter<LeftMenuBean> {
    private IMenuFragment iMenuFragment;
//    private LoadDataBiz loadDataBiz;
    public LeftMenuPresenter(IMenuFragment iMenuFragment1){
        iMenuFragment=iMenuFragment1;
//        loadDataBiz=new LoadDataBiz();
    }

//    @Override
//    public void LoadData(String url) {
//        loadDataBiz.loadDataByGet(url, new IUIDataListener() {
//            @Override
//            public void uiDataSuccess(Object obj) {
//
//                try {
//                    LeftMenuBean leftMenuBean = parseNetworkResponse4Str((String) obj);
//                    iMenuFragment.onSuccessView(leftMenuBean);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void uiDataError() {
//                iMenuFragment.showError();
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

            LeftMenuBean LeftMenuBean = parseNetworkResponse4Str(json);
            iMenuFragment.onSuccessView(LeftMenuBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError() {
        iMenuFragment.showError();
    }
}
