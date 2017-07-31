package com.cheddd.view;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by mateng on 2017/3/7.
 */

public class CircleProgressView extends View {

    //View默认最小宽度
    private static final int DEFAULT_MIN_WIDTH = 100;

    private float currentValue = 0f;

    private Paint mOuterRingPaint = new Paint();

    private int width, height;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    private int measure(int origin) {
        int result = DEFAULT_MIN_WIDTH;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        resetParams();
        initPaint();

        int centerX = Math.min(width, height) / 2;

        float doughnutWidth = Math.min(width, height) / 2 * 0.07f;

        mOuterRingPaint.setStrokeWidth(doughnutWidth);

        canvas.drawCircle(width / 2, height / 2, centerX - doughnutWidth / 2 - 8, mOuterRingPaint);

        mOuterRingPaint.setColor(Color.RED);
        RectF rectF = new RectF(8 + doughnutWidth / 2, 8 + doughnutWidth / 2, centerX * 2 - 8 - doughnutWidth / 2, centerX * 2 - 8 - doughnutWidth / 2);
        canvas.rotate(-90, height / 2, height / 2);
        canvas.drawArc(rectF, 0, currentValue, false, mOuterRingPaint);
//        mOuterRingPaint.setStyle(Paint.Style.FILL);

//        float x = (float) (width / 2 + (width / 2 - doughnutWidth / 2 - 8) * Math.cos(currentValue * Math.PI / 180));
//        float y = (float) (height / 2 + (height / 2 - doughnutWidth / 2 - 8) * Math.sin(currentValue * Math.PI / 180));
//
//        canvas.drawCircle(x, y, 16, mOuterRingPaint);
    }

    private void resetParams() {
        width = getWidth();
        height = getHeight();
        if (width > height) {
            width = height;
        } else if (height > width) {
            height = width;
        }
    }

    private void initPaint() {

        mOuterRingPaint.reset();
        mOuterRingPaint.setAntiAlias(true);            // 抗锯齿
        mOuterRingPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
        mOuterRingPaint.setStyle(Paint.Style.STROKE);
        mOuterRingPaint.setColor(0xFFDEDEDE);
    }
    public void setValue(float value) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentValue, value);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float v) {
                return 1 - (1 - v) * (1 - v) * (1 - v);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void setValue(float value, final ProgressValueListener progressValueListener) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentValue, value);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float v) {
                return 1 - (1 - v) * (1 - v) * (1 - v);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue = (float) valueAnimator.getAnimatedValue();

                progressValueListener.value(currentValue / 360);

                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void setValue(float value, Animator.AnimatorListener animationListener, final ProgressValueListener progressValueListener) {
        if (value > 349)
            value = 349;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentValue, value);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue = (float) valueAnimator.getAnimatedValue();

                progressValueListener.value(currentValue / 360);

                invalidate();
            }
        });

        valueAnimator.addListener(animationListener);
        valueAnimator.start();
    }

    public interface ProgressValueListener {
        void value(float value);
    }


}
