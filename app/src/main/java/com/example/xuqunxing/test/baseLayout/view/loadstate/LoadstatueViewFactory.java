package com.example.xuqunxing.test.baseLayout.view.loadstate;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.xuqunxing.test.R;
import com.example.xuqunxing.test.adapter.BasicAdapter;
import com.example.xuqunxing.test.baseLayout.fragment.inf.ILoadFragmengState;
import com.example.xuqunxing.test.loadview.inf.ILoadResult;

/**
 * 视图工厂 <br>
 */
public class LoadstatueViewFactory {

    /**
     * 页面加载结果处理(不下拉刷新)
     */
    public static void loadSuccess(Adapter adapter,
                                   //PageInfo info,
                                   ILoadResult iLoadViewState, ILoadFragmengState iLoadState) {
        if (adapter == null || adapter.getCount() == 0) {
            iLoadState.onLoadEmpty();
            return;
        }
        iLoadState.onLoadSuccess();
        iLoadViewState.onLoadSuccess();

//        if (info.getIsLastPage() == 1) {
//            iLoadViewState.onLoadComplete();
//        }
    }

    /**
     * 页面加载结果处理(不下拉刷新)
     */
    public static void loadIsFailed(BasicAdapter adapter,
                                    //PageInfo info,
                                    ILoadResult iLoadViewState, ILoadFragmengState iLoadState) {
        if (adapter == null || adapter.getCount() == 0) {
            iLoadState.onLoadFailed();
            return;
        }
        iLoadState.onLoadSuccess();
        iLoadViewState.onLoadFailed();
//        if (info.getIsLastPage() == 1) {
//            iLoadViewState.onLoadComplete();
//        }
    }

    /**
     * 添加视图
     *
     * @param view
     */
    private static void addView(View view, View contentView) {
        View parentView = (View) contentView.getParent();
        if (parentView != null) {

            if (parentView instanceof RelativeLayout) {
                RelativeLayout parent = (RelativeLayout) parentView;
                parent.addView(view);
                RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(view.getLayoutParams());
                l.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                l.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                l.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                view.setLayoutParams(l);
            } else if (parentView instanceof LinearLayout) {
                LinearLayout parent = (LinearLayout) parentView;
                parent.addView(view);
                LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(view.getLayoutParams());
                l.width = LinearLayout.LayoutParams.MATCH_PARENT;
                l.height = LinearLayout.LayoutParams.MATCH_PARENT;
                l.gravity = Gravity.CENTER;
                view.setLayoutParams(l);
            } else {
                throw new IllegalArgumentException("ParentView must be a RelativeLayout or LinearLayout!!!");
            }
        }
    }


    /**
     * 获取空视图
     *
     * @param context
     * @return
     */
    public static View getEmptyViewRw(Context context, View contentView, View.OnClickListener ocl) {
        View view = LayoutInflater.from(context).inflate(R.layout.rw_load_failed_ly, null);
//		ImageView iv = (ImageView) view.findViewById(R.id.load_error_view_iv);
//		TextView textView = (TextView) view.findViewById(R.id.load_error_click_tv);
//		TextView textView2 = (TextView) view.findViewById(R.id.load_error_view_tv);
//		textView.setOnClickListener(ocl);
//		textView.setText(R.string.common_load_empty_click_update);
//		textView2.setText(R.string.common_load_empty);
//		iv.setImageResource(R.drawable.bg_no_attention);
        view.setOnClickListener(ocl);
        view.setVisibility(View.GONE);
        addView(view, contentView);
        return view;
    }

//    /**
//     * 获取加载失败视图
//     *
//     * @param context
//     * @return
//     */
//    public static View getLoadingFailedRw(Context context, View contentView, View.OnClickListener ocl) {
//        View view = getView(context);
//        TextView textView = (TextView) view.findViewById(R.id.load_error_click_tv);
//        textView.setOnClickListener(ocl);
//        view.setVisibility(View.GONE);
//        addView(view, contentView);
//        return view;
//    }


    /**
     * 获取加载视图
     *
     * @param context
     * @return
     */
    public static View getLoadingViewRw(Context context, View contentView) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewfactory_info_loading_view, null);
        view.setBackgroundColor(android.graphics.Color.parseColor("#384b5e"));
        view.setVisibility(View.GONE);
        addView(view, contentView);
        return view;
    }
}
