package org.calma.redkingmania.miniGame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;

public class Test extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;

    public Test(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName("Mini-jeu Test");
        setDescription("Cliquez sur Win ou Loose pour tester !");
    }

    @Override
    public void start(TextView timer) {
        // Nettoyer la vue
        gameContainer.removeAllViews();

        // Charger le layout XML
        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_test, gameContainer, false);

        // Trouver les boutons
        Button winButton = gameView.findViewById(R.id.button_win);
        Button looseButton = gameView.findViewById(R.id.button_loose);

        // Set les actions
        winButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                win();
            }
        });

        looseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loos();
            }
        });

        // Ajouter la vue du jeu dans le container
        gameContainer.addView(gameView);
    }

    @Override
    public void win() {
        Toast.makeText(context, "Bravo, vous avez gagné !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loos() {
        Toast.makeText(context, "Dommage, vous avez perdu !", Toast.LENGTH_SHORT).show();
    }
}
