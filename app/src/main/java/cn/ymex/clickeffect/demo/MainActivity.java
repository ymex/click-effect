package cn.ymex.clickeffect.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.ymex.effect.ScaleEffect;
import cn.ymex.effect.SelectorEffect;
import cn.ymex.effect.view.EffectViewContainer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonEffect_lout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("------", "click R.id.buttonEffect_lout");
            }
        });

        findViewById(R.id.buttonEffect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("------", "click R.id.buttonEffect");
            }
        });

        EffectViewContainer container = findViewById(R.id.buttonEffect_lout);
        container.getDeputer().setEffect(new ScaleEffect(),new SelectorEffect());
    }
}
