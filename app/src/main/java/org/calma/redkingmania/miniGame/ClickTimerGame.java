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

public class ClickTimerGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private long elapsedTime;
    private boolean hasClicked = false;
    private CountDownTimer countDownTimer;
    private Random random = new Random();
    private float minTime, maxTime;

    public ClickTimerGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        int intervalType = random.nextInt(3); // 0, 1, 2 -> trois intervalles différents

        switch (intervalType) {
            case 0:
                minTime = 1.0f;
                maxTime = 2.0f;
                break;
            case 1:
                minTime = 4.0f;
                maxTime = 5.0f;
                break;
            case 2:
                minTime = 8.0f;
                maxTime = 10.0f;
                break;
        }

        setName("Clique au bon moment !");
        setDescription("Cliquez entre "+ minTime+" et " +maxTime + " au bon moment pour gagner !");
    }

    @Override
    public void start(TextView timer) {
        // Nettoyer la vue
        gameContainer.removeAllViews();

        // Charger la vue
        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_click_timer, gameContainer, false);

        Button buttonClick = gameView.findViewById(R.id.button_click);
        TextView instructionText = gameView.findViewById(R.id.text_instruction);

        // Définir un intervalle aléatoire entre 1-2, 4-5 secondes


        // Démarre le compte à rebours qui dure 10 secondes maximum
        countDownTimer = new CountDownTimer((long) (maxTime * 1000), 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTime = (long) (maxTime * 1000) - millisUntilFinished;
                // Afficher le temps écoulé (en secondes)
                timer.setText(String.format("%.1f s", elapsedTime / 1000f));
            }

            @Override
            public void onFinish() {
                // Temps écoulé sans avoir cliqué : loose
                if (!hasClicked) {
                    loos();
                }
            }
        }.start();

        // Bouton clique
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasClicked) return; // Empêche de cliquer plusieurs fois
                hasClicked = true;

                countDownTimer.cancel(); // Stopper le timer

                float secondsElapsed = elapsedTime / 1000f;

                // Vérifier si le joueur a cliqué dans la plage de temps
                if (secondsElapsed >= minTime && secondsElapsed <= maxTime) {
                    win();
                } else {
                    loos();
                }
            }
        });

        // Ajouter la vue
        gameContainer.addView(gameView);
    }

    @Override
    public void win() {
        Toast.makeText(context, "Bravo, parfait timing !", Toast.LENGTH_SHORT).show();
        Random random = new Random();

        // Générer un nombre flottant entre min et max
        float randomFloat = minTime + (maxTime - minTime) * random.nextFloat();


        int sup = Math.round(randomFloat);

        Session.getSession().getUser().setNbBois(Session.getSession().getUser().getNbBois()+sup);

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_erable);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbErable()));

        Session.getSession().updateUser();

        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, "Raté ! Mauvais timing...", Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
