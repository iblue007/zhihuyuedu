package com.example.xuqunxing.test.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.xuqunxing.test.util.Util;

/**
 * Created by xuqunxing on 2016/4/20.
 */
public abstract class CustomView extends RelativeLayout {

    protected final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    protected final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

    protected int minWidth;
    protected int minHeight;

    protected int backgroundColor;
    protected int beforeBackground;
    protected int backgroundResId = -1;// view背景的形状资源

    // Indicate if user touched this view the last time
    public boolean isLastTouch = false;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInitDefaultValues();
        //onInitAttributes(attrs);
    }
    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onInitDefaultValues();
    }




    protected abstract void onInitDefaultValues();

    // Set atributtes of XML to View
    protected void setAttributes(AttributeSet attrs) {
        setMinimumHeight(Util.dpToPx(minHeight, getResources()));
        setMinimumWidth(Util.dpToPx(minWidth, getResources()));
        if (backgroundResId != -1 && !isInEditMode()) {
            setBackgroundResource(backgroundResId);
        }
        setBackgroundAttributes(attrs);
    }

    /**
     * 设置背景色
     * Set background Color
     */
    protected void setBackgroundAttributes(AttributeSet attrs) {
        int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML,"background",-1);
        if(bacgroundColor != -1){
            setBackgroundColor(getResources().getColor(bacgroundColor));
        }else{
            // Color by hexadecimal
            int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if(background != -1 && !isInEditMode()) {
                setBackgroundColor(background);
            }else {
                setBackgroundColor(backgroundColor);// 如果没有设置，就用这个颜色
            }
        }
    }


    /**
     * Make a dark color to press effect
     * @return
     */
    protected int makePressColor(int alpha) {
        int r = (backgroundColor >> 16) & 0xFF;
        int g = (backgroundColor >> 8) & 0xFF;
        int b = (backgroundColor >> 0) & 0xFF;
        r = (r - 30 < 0) ? 0 : r - 30;
        g = (g - 30 < 0) ? 0 : g - 30;
        b = (b - 30 < 0) ? 0 : b - 30;
        return Color.argb(alpha, r, g, b);
    }

}
