package cn.ymex.effect.view;

import android.graphics.drawable.Drawable;

/**
 * Created by ymexc on 2018/5/26.
 * About:surface
 */
public class ViewSurface {
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
