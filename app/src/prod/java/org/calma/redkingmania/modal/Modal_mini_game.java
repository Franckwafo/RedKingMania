package org.calma.redkingmania.modal;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.miniGame.ClickTimerGame;
import org.calma.redkingmania.miniGame.ColorReflexGame;
import org.calma.redkingmania.miniGame.FindTheColorGame;
import org.calma.redkingmania.miniGame.MemoryFlashGame;
import org.calma.redkingmania.miniGame.MemoryNumberGame;
import org.calma.redkingmania.miniGame.MiniGame;
import org.calma.redkingmania.miniGame.OddOneOutGame;
import org.calma.redkingmania.miniGame.TapSpamGame;
import org.calma.redkingmania.miniGame.Test;
import org.calma.redkingmania.utils.Factory;

public class Modal_mini_game {
    private Dialog dialog;
    private MiniGame game;
    private TextView timerTextView;
    private FrameLayout gameContainer;
    private TextView titre, description;

    public Modal_mini_game() {
        // Préparer le bouton pour ouvrir le mini-jeu
        ImageView img_mini_game = ((Activity) Session.getSession().getCtx()).findViewById(R.id.img_game);
        img_mini_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void start() {
        Activity activity = (Activity) Session.getSession().getCtx();

        // 1. Créer un nouveau Dialog
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.modal_mini_game); // Le layout de ta modale !
        dialog.setCancelable(true); // Peut être annulé en cliquant à l'extérieur

        // 2. Trouver les composants de la vue DANS le dialog
        timerTextView = dialog.findViewById(R.id.timer_modal);
        gameContainer = dialog.findViewById(R.id.game_container);
        titre = dialog.findViewById(R.id.title_modal);
        description = dialog.findViewById(R.id.description_modal);

        // 3. Créer le mini-jeu
        game = Factory.GetGame(activity, gameContainer);
        if (game == null) {
            throw new IllegalStateException("Le MiniGame doit être défini avant de démarrer !");
        }

        // 4. Mettre à jour les textes
        titre.setText(game.getName());
        description.setText(game.getDescription());

        // 5. (Optionnel) Démarrer le mini-jeu ici
         game.start(timerTextView);

        // 6. Afficher le dialog
        dialog.show();
    }

    public void closeModal(){
        dialog.dismiss();
    }
}
