package com.cheddd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义的MyGridView，嵌套使用时高度充满父布局
 * Created by GuoSai on 2016/7/9.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
