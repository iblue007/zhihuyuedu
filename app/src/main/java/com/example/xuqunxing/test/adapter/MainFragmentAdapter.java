package com.example.xuqunxing.test.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.bean.StoriesBean;
import com.example.xuqunxing.test.util.Constant;

import java.util.List;

/**mainFragment适配器
 * Created by xuqunxing on 2016/3/31.
 */
public class MainFragmentAdapter extends BasicAdapter<StoriesBean>{


    public MainFragmentAdapter(Context context) {
        super(context);
    }

    public MainFragmentAdapter(Context context, List<StoriesBean> data1) {
        super(context, data1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_main_item, parent, false);
            viewHolder.tv_topic = (TextView) convertView.findViewById(R.id.tv_topic);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.iv_title = (ImageView) convertView.findViewById(R.id.iv_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setTextColor(mContext.getResources().getColor(android.R.color.black));
        ((LinearLayout) viewHolder.iv_title.getParent().getParent().getParent()).setBackgroundColor(mContext.getResources().getColor(R.color.light_news_item));
        viewHolder.tv_topic.setTextColor(mContext.getResources().getColor(R.color.light_news_topic));
        StoriesBean entity = mData.get(position);
        if (entity.type == Constant.TOPIC) {
            ((FrameLayout) viewHolder.tv_topic.getParent()).setBackgroundColor(Color.TRANSPARENT);
            viewHolder.tv_title.setVisibility(View.GONE);
            viewHolder.iv_title.setVisibility(View.GONE);
            viewHolder.tv_topic.setVisibility(View.VISIBLE);
            viewHolder.tv_topic.setText(entity.title);
        } else {
            ((FrameLayout) viewHolder.tv_topic.getParent()).setBackgroundResource(R.drawable.item_background_selector_light);
            viewHolder.tv_topic.setVisibility(View.GONE);
            viewHolder.tv_title.setVisibility(View.VISIBLE);
            viewHolder.iv_title.setVisibility(View.VISIBLE);
            viewHolder.tv_title.setText(entity.title);
            if(entity.images!=null&&!"".equals(entity.images.get(0))){
                Glide.with(mContext).load(entity.images.get(0)).crossFade().into(viewHolder.iv_title);
            }
        }
        return convertView;
    }


    public static class ViewHolder {
        TextView tv_topic;
        TextView tv_title;
        ImageView iv_title;
    }
}
