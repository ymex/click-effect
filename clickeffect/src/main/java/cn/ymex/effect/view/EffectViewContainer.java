package cn.ymex.effect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.ymex.clickeffect.R;
import cn.ymex.effect.AlphaEffect;
import cn.ymex.effect.Effect;
import cn.ymex.effect.SelectorEffect;

/**
 * Created by ymexc on 2018/5/22.
 * About:EffectView
 */
public class EffectViewContainer extends FrameLayout {


    private List<Effect> effects;
    private ViewSurface surface;


    public EffectViewContainer(Context context) {
        this(context, null);
    }

    public EffectViewContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public EffectViewContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EffectViewContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setClickable(true);
        this.effects = new ArrayList<>();
        this.surface = new ViewSurface();
        dealAttrs(context, attrs);
    }

    private void dealAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickEffect);
        surface.defSelector = typedArray.getBoolean(R.styleable.ClickEffect_effect_xml_selector, false);
        surface.roundRadius = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_round_radius, 0);
        surface.topLeftRadius = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_top_left_radius, 0);
        surface.topRightRadius = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_top_right_radius, 0);
        surface.bottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_bottom_left_radius, 0);
        surface.bottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_bottom_right_radius, 0);
        surface.pressedBg = typedArray.getDrawable(R.styleable.ClickEffect_effect_pressed_background);
        surface.pressedTextColor = typedArray.getColor(R.styleable.ClickEffect_effect_pressed_text_color, 0);
        surface.strokeColor = typedArray.getColor(R.styleable.ClickEffect_effect_stroke_color, 0);
        surface.pressedStrokeColor = typedArray.getColor(R.styleable.ClickEffect_effect_pressed_stroke_color, 0);
        surface.strokeWidth = typedArray.getDimensionPixelSize(R.styleable.ClickEffect_effect_stroke_width, 0);
        surface.pressedImage = typedArray.getDrawable(R.styleable.ClickEffect_effect_pressed_image);
        if (surface.strokeWidth > 0) {
            if (surface.strokeColor != 0) {
                surface.pressedStrokeColor = surface.pressedStrokeColor != 0 ? surface.pressedStrokeColor : surface.strokeColor;
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new RuntimeException("just allow single view!");
        }

        View view = getChildAt(0);
        //view.setEnabled(false);
        surface.bg = view.getBackground();
        setRoundRect();
        if (view instanceof TextView) {
            surface.textColor = ((TextView) view).getCurrentTextColor();
        }
        if (view instanceof ImageView) {
            surface.image = ((ImageView) view).getDrawable();
        }
        this.effects.clear();
        if (surface.defSelector) {
            this.effects.add(new SelectorEffect());
        } else if (surface.pressedBg != null || surface.pressedTextColor != 0 || surface.pressedImage != null) {
            this.effects.add(new SelectorEffect());
        } else {
            this.effects.add(new AlphaEffect());
        }

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


    private void setRoundRect() {
        if (!surface.isRequestRoundRect()) {
            return;
        }

        if (surface.bg != null && surface.bg instanceof ColorDrawable) {
            View view = getChildAt(0);
            int bgColor = ((ColorDrawable) surface.bg).getColor();
            surface.bg = createRoundRectDrawable(bgColor, surface.strokeWidth, surface.strokeColor);
            if (!surface.defSelector) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(surface.bg);
                } else {
                    view.setBackgroundDrawable(surface.bg);
                }
            }

        }
        if (surface.pressedBg != null && surface.pressedBg instanceof ColorDrawable) {
            surface.pressedBg = createRoundRectDrawable(((ColorDrawable) surface.pressedBg).getColor(), surface.strokeWidth, surface.pressedStrokeColor);
        }
    }


    /**
     * int color : background color
     * return Drawable
     */
    private Drawable createRoundRectDrawable(int bgcolor, int strokeWidth, int strokeColor) {

        float[] outerR = new float[]{
                surface.topLeftRadius, surface.topLeftRadius,
                surface.topRightRadius, surface.topRightRadius,
                surface.bottomRightRadius, surface.bottomRightRadius,
                surface.bottomLeftRadius, surface.bottomLeftRadius
        };
        if (surface.roundRadius > 0) {
            outerR = new float[]{
                    surface.roundRadius, surface.roundRadius,
                    surface.roundRadius, surface.roundRadius,
                    surface.roundRadius, surface.roundRadius,
                    surface.roundRadius, surface.roundRadius
            };
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(bgcolor);
        gradientDrawable.setCornerRadii(outerR);
        if (strokeWidth > 0 && strokeColor != 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor);
        }
        return gradientDrawable;
    }


    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            for (Effect effect : effects)
                if (effect != null) {
                    effect.onStatePressed(childView, surface, pressed);
                }
        }
    }

    public void setEffect(Effect... effects) {
        if (effects.length > 0) {
            this.effects.clear();
            this.effects.addAll(Arrays.asList(effects));
        }
    }

    public static class ViewSurface {
        public boolean defSelector;
        public Drawable bg;//背景
        public int textColor;//文字颜色
        public Drawable pressedBg;
        public int pressedTextColor;
        public int strokeWidth;
        public int strokeColor;
        public int pressedStrokeColor;

        public Drawable image;
        public Drawable pressedImage;

        public int roundRadius;//圆角半径
        public int topLeftRadius;
        public int topRightRadius;
        public int bottomLeftRadius;
        public int bottomRightRadius;

        public boolean isRequestRoundRect() {
            if (roundRadius > 0 || topRightRadius > 0 || topLeftRadius > 0
                    || bottomLeftRadius > 0 || bottomRightRadius > 0) {
                return true;
            }
            return false;
        }
    }
}
