package com.example.xuqunxing.test.bean;

import java.util.List;

/**侧拉数据详情页
 * Created by xuqunxing on 2016/4/7.
 */
public class LeftMenuDatailBean {


    public String background;
    public int color;
    public String description;
    public String image;
    public String image_source;
    public String name;

    public List<editors> editors;
    public List<StoriesBean> stories;

    public class editors{
        public String avatar;
        public String name;
        public int id;
    }

}
