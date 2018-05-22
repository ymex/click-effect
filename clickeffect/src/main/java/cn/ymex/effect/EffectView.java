package cn.ymex.effect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import cn.ymex.clickeffect.R;

/**
 * Created by ymexc on 2018/5/22.
 * About:EffectView
 */
public class EffectView extends FrameLayout {


    private Effect effect;
    private int roundRadius;//圆角半径
    private Drawable pressedBg;
    private int pressedTextColor;

    public EffectView(Context context) {
        this(context, null);
    }

    public EffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public EffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EffectView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setClickable(true);
        this.effect = new AlphaEffect();
    }

    private void dealAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickEffect);
        roundRadius = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_round_radius, 0);
        pressedBg = typedArray.getDrawable(R.styleable.ClickEffect_effect_pressed_background);
        pressedTextColor = typedArray.getColor(R.styleable.ClickEffect_effect_pressed_text_color, -1);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new RuntimeException("just allow single view!");
        }
        getChildAt(0).setEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getChildAt(0).setBackground(createDrawable(0,0));
        }else {
            getChildAt(0).setBackgroundDrawable(createDrawable(0,0));
        }
    }

    /**

     * @return Drawable
     */
    private ShapeDrawable createDrawable(int color, int radius ) {
        ShapeDrawable drawable= new ShapeDrawable();
        float[] outerR = new float[] { 8, 8, 8, 8, 8, 8, 8, 8 };// 外部矩形弧度
        RoundRectShape rectShape = new RoundRectShape(outerR, null, null);
        drawable.getPaint().setColor(Color.YELLOW);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.setShape(rectShape);
        return drawable;
    }


    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (effect != null) {
                effect.onStatePressed(childView, pressed);
            }
        }
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
