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

import androidx.core.content.ContextCompat;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.utils.Controleur;

import java.util.Random;

public class ColorReflexGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private boolean canClick = false;
    private CountDownTimer waitTimer;
    private Random random = new Random();

    public ColorReflexGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName(Session.getSession().getCtx().getString(R.string.color_reflex));
        setDescription(Session.getSession().getCtx().getString(R.string.color_reflex_describ));
    }

    @Override
    public void start(TextView timer) {
        gameContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_reflex_color, gameContainer, false);

        TextView reflexButton = gameView.findViewById(R.id.button_reflex);
        reflexButton.setBackgroundColor(ContextCompat.getColor(context, R.color.c_red)); // Rouge au départ

        // Choisir un délai aléatoire entre 2 et 5 secondes
        int delay = 2000 + random.nextInt(3000);

        waitTimer = new CountDownTimer(delay, delay) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                canClick = true;
                reflexButton.setBackgroundColor(ContextCompat.getColor(context, R.color.c_green)); // Passe au vert
            }
        }.start();

        reflexButton.setOnClickListener(v -> {
            if (!canClick) {
                loos();
                waitTimer.cancel();
            } else {
                win();
            }
        });

        gameContainer.addView(gameView);
    }

    @Override
    public void win() {
        Toast.makeText(context, Session.getSession().getCtx().getString(R.string.color_reflex_win_msg), Toast.LENGTH_SHORT).show();
        int reward = 50 + random.nextInt(20000); // Récompense bois

        Session.getSession().getUser().setNbCristaux(Session.getSession().getUser().getNbCristaux() + reward);

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_cristale);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbCristaux()));

        Session.getSession().updateUser();
        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, Session.getSession().getCtx().getString(R.string.color_reflex_loose_msg), Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
