# click effect


如何你受够了写 selector ，也许你可以尝试别一种写法。

```
<cn.ymex.effect.view.EffectViewContainer
        android:id="@+id/buttonEffect_lout"
        android:layout_width="248dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        app:effect_default_selector="false"
        app:effect_pressed_background="#c12d2d"
        app:effect_pressed_text_color="#ffffff"
        app:effect_top_left_radius="10dp">

        <Button
            android:id="@+id/buttonEffect"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#bbccee"
            android:text="hi-hi"
            android:textColor="#003300" />
</cn.ymex.effect.view.EffectViewContainer>
```

可配置的属性：

|属性|说明|
| --- | --- |
|effect_pressed_background|按下时背景|
|effect_pressed_text_color|按下时字体的颜色|
|effect_default_selector|使用xml selector|
|effect_round_radius|圆角|
|effect_top_left_radius|左上圆角|
|effect_top_right_radius|右上圆角|
|effect_bottom_left_radius|左下圆角|
|effect_bottom_right_radius|右下圆角|


如何使用`EffectViewContainer` 时请将 button 的事件设置在`EffectViewContainer`上。