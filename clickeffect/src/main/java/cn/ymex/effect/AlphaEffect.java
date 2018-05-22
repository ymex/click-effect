package cn.ymex.effect;

import android.view.View;

/**
 * Created by ymexc on 2018/5/22.
 * About:透明效果
 */
public class AlphaEffect implements Effect {

    private static final float DEFAULT_EFFECT_ALPHA = .6f;
    private float alpha = DEFAULT_EFFECT_ALPHA;


    @Override
    public void onStatePressed(View view, boolean pressed) {
        view.setAlpha(pressed ? alpha : 1.0f);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}