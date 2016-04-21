package com.example.xuqunxing.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/*
 * StrokeTextView的目标是给文字描边
 * 实现方法是两个TextView叠加,只有描边的TextView为底,实体TextView叠加在上面
 * 看上去文字就有个不同颜色的边框了
 */
public class StrokeTextView extends TextView {

    private boolean m_bDrawSideLine = true; // 默认不采用描边
    private Paint m_TextPaint;

    public StrokeTextView(Context context) {
        super(context);
        m_TextPaint = getPaint();
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_TextPaint = getPaint();
    }

    public StrokeTextView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
        m_TextPaint = getPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (m_bDrawSideLine) {
            // 描外层
            //super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(Color.rgb(0, 0, 0));
            m_TextPaint.setStrokeWidth(2);  // 描边宽度
            m_TextPaint.setStyle(Paint.Style.STROKE); //描边种类
            m_TextPaint.setFakeBoldText(false); // 外层text采用粗体
            m_TextPaint.setShadowLayer(2, 0, 4, 0xff000000); //字体的阴影效果，可以忽略
            super.onDraw(canvas);


            // 描内层，恢复原先的画笔

            //super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(Color.rgb(255, 255, 255));
            m_TextPaint.setStrokeWidth(0);
            m_TextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            m_TextPaint.setFakeBoldText(false);
            m_TextPaint.setShadowLayer(0, 0, 0, 0);
        }
        super.onDraw(canvas);
    }

    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        m_TextPaint.setColor(color);
    }
}

