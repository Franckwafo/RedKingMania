package org.calma.redkingmania.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;

public class Animation {
    public static void ExplodAnim() {
        FrameLayout container = ((Activity) Session.getSession().getCtx()).findViewById(R.id.anim_contenair);
        container.bringToFront();
        container.setVisibility(View.VISIBLE);

        int[] drawables = {
                R.drawable.erable,
                R.drawable.cristale,
                R.drawable.bois
        };

        int totalDuration = 1800;

        for (int i = 0; i < 50; i++) {
            final ImageView iv = new ImageView(Session.getSession().getCtx());
            int randImg = (int)(Math.random() * drawables.length);
            iv.setImageResource(drawables[randImg]);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(200, 200);
            params.gravity = Gravity.CENTER;
            iv.setLayoutParams(params);
            container.addView(iv);

            int delay = (int)(Math.random() * 600); // Délai entre 0 et 600 ms pour le feu d’artifice

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                // Animation de zoom au centre
                Animator zoom = AnimatorInflater.loadAnimator(Session.getSession().getCtx(), R.animator.animator_explode);
                zoom.setTarget(iv);
                zoom.start();

                // Explosion après zoom
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    int dx = (int)(Math.random() * 2000 - 1000); // -1000 à 1000 px
                    int dy = (int)(Math.random() * 2000 - 1000);
                    iv.animate()
                            .translationXBy(dx)
                            .translationYBy(dy)
                            .alpha(0f)
                            .setDuration(800)
                            .start();
                }, 200);
            }, delay);
        }

        // Nettoyage après l’animation complète
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            container.removeAllViews();
            container.setVisibility(View.GONE);
        }, totalDuration);
    }






}
