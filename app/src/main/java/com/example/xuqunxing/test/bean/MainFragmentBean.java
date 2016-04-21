package com.example.xuqunxing.test.bean;

import java.util.List;

/**主页fragment实体类
 * Created by xuqunxing on 2016/3/31.
 */
public class MainFragmentBean {

    public String date;
    public List<StoriesBean> stories;
    public List<top_stories> top_stories;

//    public class stories implements Serializable {
//
//        public stories() {
//        }
//        public String ga_prefix;
//        public long id;
//        public String title;
//        public long type;
//        public List<String> image;
//
//        public void setGa_prefix(String ga_prefix) {
//            this.ga_prefix = ga_prefix;
//        }
//
//        public void setId(long id) {
//            this.id = id;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public void setType(long type) {
//            this.type = type;
//        }
//
//        public void setImages(List<String> image) {
//            this.image = image;
//        }
//    }

    public class top_stories{
        public String ga_prefix;
        public long id;
        public String title;
        public long type;
        public String image;
    }

}
