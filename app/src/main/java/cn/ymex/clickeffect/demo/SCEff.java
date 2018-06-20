package cn.ymex.clickeffect.demo;

import android.view.View;

import cn.ymex.effect.Effect;
import cn.ymex.effect.ScaleEffect;
import cn.ymex.effect.SelectorEffect;
import cn.ymex.effect.view.ViewSurface;

/**
 * Created by ymexc on 2018/6/20.
 * About:自定义效果
 */
public class SCEff extends SelectorEffect {
    Effect scaleEffect = new ScaleEffect();

    @Override
    public void onStateChange(View view, ViewSurface surface, boolean pressed) {
        super.onStateChange(view, surface, pressed);
        scaleEffect.onStateChange(view, surface, pressed);
    }
}
