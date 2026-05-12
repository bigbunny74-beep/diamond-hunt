package com.diamondhunt.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private static final int SPLASH_DURATION = 2200; // ms before launching game

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full screen immersive
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_splash);

        ImageView icon    = findViewById(R.id.splash_icon);
        TextView  title   = findViewById(R.id.splash_title);
        TextView  loading = findViewById(R.id.splash_loading);

        // Start invisible
        icon.setAlpha(0f);
        title.setAlpha(0f);
        loading.setAlpha(0f);

        // Animate icon: scale up + fade in
        AnimationSet iconAnim = new AnimationSet(true);
        ScaleAnimation scale = new ScaleAnimation(
            0.6f, 1.0f, 0.6f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(700);
        AlphaAnimation iconFade = new AlphaAnimation(0f, 1f);
        iconFade.setDuration(700);
        iconAnim.addAnimation(scale);
        iconAnim.addAnimation(iconFade);
        iconAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        iconAnim.setFillAfter(true);

        // Animate title: fade in after icon
        AlphaAnimation titleFade = new AlphaAnimation(0f, 1f);
        titleFade.setDuration(500);
        titleFade.setStartOffset(500);
        titleFade.setFillAfter(true);

        // Animate loading text: fade in last
        AlphaAnimation loadFade = new AlphaAnimation(0f, 1f);
        loadFade.setDuration(400);
        loadFade.setStartOffset(900);
        loadFade.setFillAfter(true);

        icon.startAnimation(iconAnim);
        title.startAnimation(titleFade);
        loading.startAnimation(loadFade);

        // After animations, fade out and launch MainActivity
        new Handler().postDelayed(() -> {
            // Fade entire screen to black then launch
            View root = getWindow().getDecorView().getRootView();
            AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
            fadeOut.setDuration(400);
            fadeOut.setFillAfter(true);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation a) {}
                @Override public void onAnimationRepeat(Animation a) {}
                @Override public void onAnimationEnd(Animation a) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            });
            root.startAnimation(fadeOut);
        }, SPLASH_DURATION);
    }
}
