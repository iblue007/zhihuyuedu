package com.example.xuqunxing.test.bean;

import java.io.Serializable;
import java.util.List;

/**mainfragment-stories实体类
 * Created by xuqunxing on 2016/4/7.
 */
public class StoriesBean implements Serializable {

    public StoriesBean() {
    }
    public String ga_prefix;
    public long id;
    public String title;
    public long type;
    public List<String> images;

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}