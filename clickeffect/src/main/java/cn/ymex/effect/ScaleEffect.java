package cn.ymex.effect;

import android.view.View;

/**
 * Created by ymexc on 2018/5/22.
 * About:缩放效果
 */
public class ScaleEffect implements Effect {

    private static final float DEFAULT_EFFECT_SCALE = .98f;
    private float scaleX = DEFAULT_EFFECT_SCALE;
    private float scaleY = DEFAULT_EFFECT_SCALE;

    @Override
    public void onStatePressed(View view, boolean pressed) {
        view.setScaleX(pressed ? scaleX : 1.0f);
        view.setScaleY(pressed ? scaleY : 1.0f);
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }
}
