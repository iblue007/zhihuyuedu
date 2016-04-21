
package com.example.xuqunxing.test.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BasicAdapter
 * @Description: 基础适配器
 * @param <T>
 */
public abstract class BasicAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData = new ArrayList<T>();

    public BasicAdapter(Context context) {
        this.mContext = context;
    }

    public BasicAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    /**
     * 添加数据
     * 
     * @param list
     */
    @Deprecated
    public void addData(List<T> list) {
        if (list != null && list.size() > 0) {
            for (T t : list) {
                if (t != null) {
                    mData.add(t);
                }
            }
        }
    }

    /**
     * 添加数据
     * 
     * @param list
     */
    public void appendData(List<T> list) {
        if (list != null && list.size() > 0) {
            for (T t : list) {
                if (t != null) {
                    mData.add(t);
                }
            }
        }
    }

    /**
     * 添加数据
     * 
     * @param list
     */
    public void prependData(List<T> list) {
        if (list != null && list.size() > 0) {
            for (int i = list.size() - 1; i >= 0; i--) {
                T t = list.get(i);
                if (t != null) {
                    mData.add(0, t);
                }
            }
        }
    }

    /**
     * 替换数据
     * 
     * @param list
     */
    public void setData(List<T> list) {
        if (list != null && list.size() > 0) {
            mData = list;
        }
    }

    /**
     * 清空数据
     */
    public void clear() {
        mData.clear();
    }

    public int getCount() {
        if(mData==null){return 0;}
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return mData;
    }


    /**
    
      * notifyDataSetChanged()
    
      * @Title: notifyDataSetChanged
    
      * @Description: 更新数据
    
      * @param @param data  数据源
    
      * @return void    返回类型
    
      * @throws
    
      */
    public void notifyDataSetChanged(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.mData = data;
        this.notifyDataSetChanged();
    }
    public void addNotifyDataSetChanged(List<T> data) {
        if (data == null) {
            return;
        }
        if(mData==null){
            mData = new ArrayList<T>();
        }
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }
}
