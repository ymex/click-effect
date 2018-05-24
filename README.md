# click effect

点击效果库

引用库

```
implementation  'cn.ymex:click-effect:1.0.0'
```

如何你受够了用xml写 selector ，也许你可以尝试别一种写法。使用`EffectViewContainer`包裹一个基础控件，
如button,textview等。不配置任何effect属性的情况下，默认有个半透明的效果。

```
<cn.ymex.effect.view.EffectViewContainer
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@mipmap/ic_launcher" />
</cn.ymex.effect.view.EffectViewContainer>
```

配置effect属性可实现更多自定义效果。

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
|effect_xml_selector|使用系统默认或自定义的xml selector|
|effect_round_radius|圆角|
|effect_top_left_radius|左上圆角|
|effect_top_right_radius|右上圆角|
|effect_bottom_left_radius|左下圆角|
|effect_bottom_right_radius|右下圆角|
|effect_stroke_width|描边宽度|
|effect_stroke_color|描边颜色|
|effect_pressed_stroke_color|按下时描边颜色，若其不存在，默认设置为effect_stroke_color的值|


如何使用`EffectViewContainer` 时请将 button 的事件设置在`EffectViewContainer`上。