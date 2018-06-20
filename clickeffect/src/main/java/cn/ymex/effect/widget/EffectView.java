package cn.ymex.effect.widget;

import android.view.View;

/**
 * @author ymexc 2018/6/20
 */
public interface EffectView {
    ViewDepute getViewDepute();

   void dispatchSetEffect(View view, boolean flag);
}
