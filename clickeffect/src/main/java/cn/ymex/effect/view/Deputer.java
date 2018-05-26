package cn.ymex.effect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by ymexc on 2018/5/26.
 * About:代理
 */
public class Deputer {
    private List<Effect> effects;
    private ViewSurface surface;
    //private View proxyView;

    private Deputer() {
        super();
        //this.proxyView = view;
        this.effects = new ArrayList<>();
        this.surface = new ViewSurface();
    }

    public static Deputer instance() {
        return new Deputer();
    }

    public void dealAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EffectView);
        surface.defSelector = typedArray.getBoolean(R.styleable.EffectView_effect_xml_selector, false);
        surface.roundRadius = typedArray.getDimensionPixelSize(R.styleable.EffectView_effect_round_radius, 0);
        surface.topLeftRadius = typedArray.getDimensionPixelSize(R.styleable.EffectView_effect_top_left_radius, 0);
        surface.topRightRadius = typedArray.getDimensionPixelSize(R.styleable.EffectView_effect_top_right_radius, 0);
        surface.bottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.EffectView_effect_bottom_left_radius, 0);
        surface.bottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.EffectView_effect_bottom_right_radius, 0);
        surface.pressedBg = typedArray.getDrawable(R.styleable.EffectView_effect_pressed_background);
        surface.pressedTextColor = typedArray.getColor(R.styleable.EffectView_effect_pressed_text_color, 0);
        surface.strokeColor = typedArray.getColor(R.styleable.EffectView_effect_stroke_color, 0);
        surface.pressedStrokeColor = typedArray.getColor(R.styleable.EffectView_effect_pressed_stroke_color, 0);
        surface.strokeWidth = typedArray.getDimensionPixelSize(R.styleable.EffectView_effect_stroke_width, 0);
        surface.pressedImage = typedArray.getDrawable(R.styleable.EffectView_effect_pressed_image);
        if (surface.strokeWidth > 0) {
            if (surface.strokeColor != 0) {
                surface.pressedStrokeColor = surface.pressedStrokeColor != 0 ? surface.pressedStrokeColor : surface.strokeColor;
            }
        }
        typedArray.recycle();
    }

    public void onViewFinishInflate(View proView) {
        proView.setClickable(true);
        View view = null;
        if (proView instanceof ViewGroup) {
            int childCount = ((ViewGroup) proView).getChildCount();
            if (childCount > 1) {
                throw new RuntimeException("just allow single view!");
            }
            view = ((ViewGroup) proView).getChildAt(0);
        } else {
            view = proView;
        }
        if (view == null) {
            return;
        }
        surface.bg = view.getBackground();
        setRoundRect(view);
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

    private void setRoundRect(View view) {
        if (!surface.isRequestRoundRect()) {
            return;
        }

        if (surface.bg != null && surface.bg instanceof ColorDrawable) {
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


    public ViewSurface getSurface() {
        return surface;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffect(Effect... effects) {
        if (effects.length > 0) {
            this.effects.clear();
            this.effects.addAll(Arrays.asList(effects));
        }
    }

    public void dispatchSetPressed(View view, boolean pressed) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) view).getChildAt(i);
                for (Effect effect : effects) {
                    if (effect != null) {
                        effect.onStatePressed(childView, surface, pressed);
                    }
                }
            }
        } else {
            for (Effect effect : effects) {
                if (effect != null) {
                    effect.onStatePressed(view, surface, pressed);
                }
            }
        }

    }

}
