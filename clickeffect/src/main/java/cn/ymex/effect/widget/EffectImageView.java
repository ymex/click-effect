package cn.ymex.effect.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import cn.ymex.effect.view.Deputer;

/**
 * Created by ymexc on 2018/5/26.
 * About:AppCompatImageView
 */
public class EffectImageView extends AppCompatImageView{
    Deputer deputer;
    public EffectImageView(Context context) {
        this(context,null);
    }

    public EffectImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EffectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        deputer = Deputer.instance();
        deputer.dealAttrs(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        deputer.onViewFinishInflate(this);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        deputer.dispatchSetPressed(this,pressed);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        deputer.onFocusChanged(this, gainFocus, direction, previouslyFocusedRect);
    }

    public Deputer getDeputer() {
        return deputer;
    }
}
