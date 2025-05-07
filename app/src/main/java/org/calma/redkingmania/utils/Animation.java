package org.calma.redkingmania.utils;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

        Song.explodSong(Session.getSession().getCtx());

        // Nettoyage après l’animation complète
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            container.removeAllViews();
            container.setVisibility(View.GONE);
        }, totalDuration);
    }


    public static void applyPulseAnimation(View view, Context context) {
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.animator_pulse);
        animatorSet.setTarget(view);
        animatorSet.start();
        Song.clickSong(context);
    }

    public static void explodeErableAnim(View containerView,int img) {
        if (containerView == null) return;

        Context context = containerView.getContext();

        if (!(containerView instanceof ViewGroup)) return;

        ViewGroup group = (ViewGroup) containerView;

        // Obtiens les dimensions de la vue conteneur
        int width = containerView.getWidth();
        int height = containerView.getHeight();

        // Si les dimensions ne sont pas encore prêtes (par ex. avant layout), retenter plus tard
        if (width == 0 || height == 0) {
            containerView.post(() -> explodeErableAnim(containerView,img));
            return;
        }



        // Crée une ImageView pour l’image érable
        final ImageView view = new ImageView(context);

        switch (img){
            case 1:
                view.setImageResource(R.drawable.erable);
                break;
            case 2:
                view.setImageResource(R.drawable.cristale);
                break;
            case 3:
                view.setImageResource(R.drawable.bois);
                break;
        }

        // Taille de l'image
        int imageSize = 200;

        // Position aléatoire
        int randX = (int) (Math.random() * (width - imageSize));
        int randY = (int) (Math.random() * (height - imageSize));

        // Rotation aléatoire
        float rotation = (float) (Math.random() * 360);
        view.setRotation(rotation);

        // Applique les paramètres de position
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(imageSize, imageSize);
        params.leftMargin = randX;
        params.topMargin = randY;
        view.setLayoutParams(params);

        // Ajoute la vue dans le conteneur
        group.addView(view);

        // Charge l'animation
        AnimatorSet explodeAnim = (AnimatorSet) AnimatorInflater.loadAnimator(
                context, R.animator.animator_explode_click
        );
        explodeAnim.setTarget(view);

        // Supprime l’image après l'animation
        explodeAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                group.removeView(view);
            }
        });

        explodeAnim.start();
    }











}
