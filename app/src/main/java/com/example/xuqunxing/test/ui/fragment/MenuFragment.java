package com.example.xuqunxing.test.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.baseLayout.fragment.BasicLoadstateHttpFragment;
import com.example.xuqunxing.test.bean.LeftMenuBean;
import com.example.xuqunxing.test.manager.UMManager;
import com.example.xuqunxing.test.presenter.LeftMenuPresenter;
import com.example.xuqunxing.test.ui.MainActivity;
import com.example.xuqunxing.test.ui.interfaces.IMenuFragment;
import com.example.xuqunxing.test.util.Constant;
import com.example.xuqunxing.test.util.Util;

import java.util.ArrayList;
import java.util.List;

/**侧拉页面fragment
 * Created by xuqunxing on 2016/3/30.
 */
public class MenuFragment extends BasicLoadstateHttpFragment implements View.OnClickListener,IMenuFragment {

    private ListView lv_item;
    private TextView tv_download, tv_main;
    private List<LeftMenuBean.other> items;
    private Handler handler = new Handler();
    private LeftMenuPresenter leftMenuPresenter;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        tv_download = (TextView) view.findViewById(R.id.tv_download);
        tv_download.setOnClickListener(this);
        tv_main = (TextView) view.findViewById(R.id.tv_main);
        tv_main.setOnClickListener(this);
        lv_item = (ListView) view.findViewById(R.id.lv_item);
        leftMenuPresenter = new LeftMenuPresenter(this);
        items = new ArrayList<LeftMenuBean.other>();
        return view;
    }

    @Override
    public void initDataAfterView() {
        loadData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        if(Util.isNetworkAvailable(getContext())){
            leftMenuPresenter.LoadData(Constant.THEMES);
        }else{
//            onLoadEmpty();
            if(leftMenuPresenter!=null){
                leftMenuPresenter.LoadLoaclData();
            }
        }

    }

    @Override
    public void loadData(int page) {
        if(Util.isNetworkAvailable(getContext())){
            leftMenuPresenter.LoadData(Constant.THEMES);
        }else{
            onLoadEmpty();
        }
    }

    @Override
    public View getContentView() {
        return lv_item;
    }

    @Override
    public void invalidateViews() {

    }

    @Override
    public void onSuccessView(Object obj) {
        onLoadSuccess();
        LeftMenuBean leftMenuBean=(LeftMenuBean)obj;
        items=leftMenuBean.others;
        NewsTypeAdapter adapter=new NewsTypeAdapter(items);
        lv_item.setAdapter(adapter);
        lv_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).
                        replace(R.id.fl_content, new MenuFragment4Datail(items.get(position).id, items.get(position).name), "newsfragment").commit();

                ((MainActivity) getContext()).setCurId(items.get(position).id + "");
                ((MainActivity) getContext()).closeMenu();
            }
        });
    }

    @Override
    public void showError() {
        onLoadEmpty();
    }

    public class NewsTypeAdapter extends BaseAdapter {
        List<LeftMenuBean.other> itemList;
        public NewsTypeAdapter(List<LeftMenuBean.other> items) {
            itemList=items;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.fragment_menu_item, parent, false);
            }
            TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            tv_item.setText(itemList.get(position).name);
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==tv_main.getId()){
            UMManager.getInstance().event(Constant.TV_MAIN);
            ((MainActivity)mContext).replaceFragment();
            ((MainActivity) mContext).closeMenu();
            ((MainActivity) getContext()).setCurId(Constant.INDEX_MAIN_FRAGMENT);
        }
    }
}