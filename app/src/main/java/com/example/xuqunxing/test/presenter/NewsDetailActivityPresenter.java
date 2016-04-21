package com.example.xuqunxing.test.presenter;

import com.example.xuqunxing.test.bean.NewsDetailBean;
import com.example.xuqunxing.test.biz.LoadDataBiz;
import com.example.xuqunxing.test.interfacess.IUIDataListener;
import com.example.xuqunxing.test.ui.interfaces.INewsDetailActivity;
import com.example.xuqunxing.test.util.Util;

/**新闻详情presenter
 * Created by xuqunxing on 2016/3/31.
 */
public class NewsDetailActivityPresenter extends BasePresenter<NewsDetailBean>{
    private INewsDetailActivity iNewsDetailActivity;
    private LoadDataBiz loadDataBiz;
    private long id;
    public NewsDetailActivityPresenter(INewsDetailActivity iNewsDetailActivity1){
        iNewsDetailActivity=iNewsDetailActivity1;
        loadDataBiz=new LoadDataBiz();
    }
    @Override
    public void LoadData(String url) {
       try{
           loadDataBiz.loadDataByGet(url, new IUIDataListener() {
               @Override
               public void uiDataSuccess(Object obj) {
                   try{
                       String json = new String(((String) obj).getBytes("utf-8"), "utf-8");
                       //缓存数据
                       if(json!=null){
                           Util.save2Local(json, Util.getSimpleClassName(getClass().getName()), id + "");
                       }
                       NewsDetailBean newsDetailBean = parseNetworkResponse4Str(json);
                       iNewsDetailActivity.onSuccessView(newsDetailBean);
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }

               @Override
               public void uiDataError() {
                   iNewsDetailActivity.onFailView();
               }
           });
       }catch (Exception e){
           iNewsDetailActivity.onFailView();
       }
    }

    public void setDataId(long id1) {
        id=id1;
    }
}
