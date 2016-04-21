package com.example.xuqunxing.test.baseLayout.view.inf;

/**懒加载接口
 * Created by xuqunxing on 2016/4/20.
 */
public interface ILazyLoad {

    /**
     * 加载数据
     *
     * @return
     */
    public boolean firstdata();

    /**
     * loadData
     * @Description: 加载数据
     * @param
     * @return void
     * @throws
     */
    public void loadData();
    /**
     * loadData
     * @Description: 加载数据
     * @param
     * @return void
     * @throws
     */
    public void loadData(int page);

}
