package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.bean.LeftMenuDatailBean;
import com.example.xuqunxing.test.ui.interfaces.IMenuDetailFragment;
import com.example.xuqunxing.test.util.Util;

import java.io.IOException;

/**
 * Created by xuqunxing on 2016/4/7.
 */
public class LeftMenuDatailPresenter extends BasePresenter<LeftMenuDatailBean>{


    protected  IMenuDetailFragment iMenuDetailFragment;
    public LeftMenuDatailPresenter(IMenuDetailFragment iMenuDetailFragment1){
        iMenuDetailFragment=iMenuDetailFragment1;
    }

    @Override
    public void onSuccessView(Object obj) {
        try {
            String json = new String(((String) obj).getBytes("utf-8"), "utf-8");
            //缓存数据
            if(json!=null){
                Util.save2Local(json, Util.getSimpleClassName(getClass().getName()), urlId+"LoadData");
            }else{
                // Util.save2Local(json,Util.getSimpleClassName(getClass().getName())+"/"+UserInfoManager.getInstance().getUserId(),mTeamId+"_"+1);
            }

            LeftMenuDatailBean leftMenuDatailBean = parseNetworkResponse4Str(json);
            iMenuDetailFragment.onSuccessView(leftMenuDatailBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError() {
        iMenuDetailFragment.showError();
    }

    public void loadLocalData() {
        String json = Util.readDataFromLocal(Util.getSimpleClassName(getClass().getName()), urlId+"LoadData");
        //缓存数据
        if(json!=null){
            try{
                LeftMenuDatailBean leftMenuDatailBean = parseNetworkResponse4Str(json);
                iMenuDetailFragment.onSuccessView(leftMenuDatailBean);
            }catch (Exception e){
                iMenuDetailFragment.showError();
            }
        }else{
            iMenuDetailFragment.showError();
        }
    }

    private int urlId;
    public void setUrlId(int urlId1) {
        urlId=urlId1;
    }
}
