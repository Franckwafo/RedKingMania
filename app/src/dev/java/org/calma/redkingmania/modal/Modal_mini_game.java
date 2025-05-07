package org.calma.redkingmania.modal;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast.makeText(Session.getSession().getCtx(), R.string.mini_game_dev_msg, Toast.LENGTH_SHORT).show();
    }

    public void closeModal(){
        dialog.dismiss();
    }
}
