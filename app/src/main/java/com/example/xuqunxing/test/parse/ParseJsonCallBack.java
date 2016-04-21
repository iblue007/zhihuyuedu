package com.example.xuqunxing.test.parse;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**主页fragment数据解析
 * Created by xuqunxing on 2016/3/30.
 */
public abstract class ParseJsonCallBack<T> extends BaseCallback<T>{

    protected Class<T> clazz;
    public ParseJsonCallBack(){
        Class clazz = getClass();
        while (clazz != Object.class) {
            Type t = clazz.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    this.clazz = (Class<T>) args[0];
                    break;
                }
            }
            clazz = clazz.getSuperclass();
        }

    }

    @Override
    public T parseNetworkResponse4Str(String string) throws IOException {
        T t = new Gson().fromJson(string, clazz);
        return t;
    }
    public abstract void LoadData(String url);

}
