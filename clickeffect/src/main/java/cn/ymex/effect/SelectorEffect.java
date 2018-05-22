package cn.ymex.effect;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import cn.ymex.effect.view.EffectViewContainer;

/**
 * Created by ymex on 2018/5/22.
 * About:
 */
public class SelectorEffect implements Effect {
    @Override
    public void onStatePressed(View view, EffectViewContainer.ViewSurface surface, boolean pressed) {
        if (surface.defSelector) {
            view.setPressed(pressed);
            return;
        }
        if (surface.pressedBg != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(pressed ? surface.pressedBg : surface.bg);
            } else {
                view.setBackgroundDrawable(pressed ? surface.pressedBg : surface.bg);
            }
        }
        if (surface.pressedTextColor != 0 && view instanceof TextView) {
            ((TextView) view).setTextColor(pressed ? surface.pressedTextColor : surface.textColor);
        }
    }
}
