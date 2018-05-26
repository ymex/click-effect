package cn.ymex.effect;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ymex.effect.view.ViewSurface;

/**
 * Created by ymex on 2018/5/22.
 * About: selector
 */
public class SelectorEffect implements Effect {
    @Override
    public void onStatePressed(View view, ViewSurface surface, boolean pressed) {
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

        if (surface.pressedImage != null && view instanceof ImageView && surface.image != null) {
            ((ImageView) view).setImageDrawable(pressed ? surface.pressedImage : surface.image);
        }
    }
}
