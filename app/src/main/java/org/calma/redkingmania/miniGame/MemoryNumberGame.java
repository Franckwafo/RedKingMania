package org.calma.redkingmania.miniGame;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.utils.Controleur;

import java.util.Random;

public class MemoryNumberGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private String numberToRemember;

    public MemoryNumberGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName("Souviens-toi du nombre !");
        setDescription("Mémorisez un nombre et retapez-le correctement.");
    }

    @Override
    public void start(TextView timerView) {
        gameContainer.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_memory_number, gameContainer, false);

        TextView numberText = gameView.findViewById(R.id.number_to_remember);
        EditText inputField = gameView.findViewById(R.id.input_number);
        Button validateButton = gameView.findViewById(R.id.validate_button);

        // Génère un nombre de 5 chiffres
        numberToRemember = String.valueOf(new Random().nextInt(90000) + 10000);
        numberText.setText(numberToRemember);
        inputField.setVisibility(View.GONE);
        validateButton.setVisibility(View.GONE);

        // Affiche le nombre pendant 2 secondes
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerView.setText((millisUntilFinished / 1000) + " s");
            }

            public void onFinish() {
                numberText.setText("Quel était le nombre ?");
                inputField.setVisibility(View.VISIBLE);
                validateButton.setVisibility(View.VISIBLE);
                timerView.setText("");
            }
        }.start();

        validateButton.setOnClickListener(v -> {
            if (inputField.getText().toString().equals(numberToRemember)) {
                win();
            } else {
                loos();
            }
        });

        gameContainer.addView(gameView);
    }

    @Override
    public void win() {
        Toast.makeText(context, "Bonne mémoire !", Toast.LENGTH_SHORT).show();
        int gain = new Random().nextInt(300) + 100;

        Session.getSession().getUser().setNbBois(Session.getSession().getUser().getNbBois() + gain);
        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_boi);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbBois()));

        Session.getSession().updateUser();
        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, "Perdu ! Ce n'était pas le bon nombre...", Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
