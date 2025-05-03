package org.calma.redkingmania.miniGame;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.utils.Controleur;

import java.util.Random;

public class TapSpamGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private int tapCount = 0;
    private CountDownTimer timer;
    private int requiredTaps;

    public TapSpamGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName("Tappe vite !");
        setDescription("Appuie rapidement sur le bouton pour gagner !");
    }

    @Override
    public void start(TextView timerView) {
        gameContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_tap_spam, gameContainer, false);

        Button tapButton = gameView.findViewById(R.id.tap_button);
        TextView tapCounter = gameView.findViewById(R.id.tap_counter);

        tapCount = 0;
        requiredTaps = new Random().nextInt(10) + 20; // entre 20 et 30 taps requis

        timer = new CountDownTimer(5000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText((millisUntilFinished / 1000.0) + " s");
            }

            @Override
            public void onFinish() {
                if (tapCount >= requiredTaps) {
                    win();
                } else {
                    loos();
                }
            }
        }.start();

        tapButton.setOnClickListener(v -> {
            tapCount++;
            tapCounter.setText("Clics : " + tapCount);
        });

        gameContainer.addView(gameView);
    }

    @Override
    public void win() {
        Toast.makeText(context, "Tu as cliqué assez vite !", Toast.LENGTH_SHORT).show();
        int bonus = new Random().nextInt(300) + 10;

        Session.getSession().getUser().setNbErable(
                Session.getSession().getUser().getNbErable() + bonus*tapCount
        );

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_erable);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbErable()));

        Session.getSession().updateUser();
        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, "Trop lent ! Essaie encore...", Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
