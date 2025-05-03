package org.calma.redkingmania.miniGame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FindTheColorGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private CountDownTimer timer;
    private String targetColorName;
    private int scoreBonus;

    public FindTheColorGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName("Trouve la couleur !");
        setDescription("Clique sur le bouton de la bonne couleur !");
    }

    @Override
    public void start(TextView timerView) {
        gameContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_find_color, gameContainer, false);

        TextView instruction = gameView.findViewById(R.id.text_instruction);
        List<Button> buttons = new ArrayList<>();
        buttons.add(gameView.findViewById(R.id.color_btn_1));
        buttons.add(gameView.findViewById(R.id.color_btn_2));
        buttons.add(gameView.findViewById(R.id.color_btn_3));

        // Couleurs et noms
        String[] colorNames = {"Rouge", "Vert", "Bleu"};
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        int correctIndex = new Random().nextInt(3);

        targetColorName = colorNames[correctIndex];
        instruction.setText("Clique sur le bouton " + targetColorName + " !");

        // Mélanger les couleurs entre les boutons
        List<Integer> colorList = new ArrayList<>();
        for (int color : colors) colorList.add(color);
        Collections.shuffle(colorList);

        for (int i = 0; i < buttons.size(); i++) {
            Button btn = buttons.get(i);
            int currentColor = colorList.get(i);
            btn.setBackgroundColor(currentColor);
            btn.setOnClickListener(v -> {
                if (currentColor == colors[correctIndex]) {
                    win();
                } else {
                    loos();
                }
                timer.cancel();
            });
        }

        timer = new CountDownTimer(4000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText((millisUntilFinished / 1000.0) + " s");
            }

            @Override
            public void onFinish() {
                loos();
            }
        }.start();

        gameContainer.addView(gameView);
    }

    @Override
    public void win() {
        Toast.makeText(context, "Bien joué !", Toast.LENGTH_SHORT).show();
        scoreBonus = new Random().nextInt(15000) + 50;

        Session.getSession().getUser().setNbBois(Session.getSession().getUser().getNbBois() + scoreBonus);

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_boi);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbBois()));

        Session.getSession().updateUser();
        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, "Mauvaise couleur ou trop tard !", Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
