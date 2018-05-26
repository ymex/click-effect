package cn.ymex.effect;

import android.view.View;

import cn.ymex.effect.view.ViewSurface;

/**
 * Created by ymexc on 2018/5/22.
 */
public interface Effect {
    void onStatePressed(View view, ViewSurface surface, boolean pressed);
}
