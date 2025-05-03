package org.calma.redkingmania.miniGame;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MemoryFlashGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private Set<Integer> correctIndices = new HashSet<>();
    private Set<Integer> userSelections = new HashSet<>();
    private boolean inputEnabled = false;

    public MemoryFlashGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName("Mémoire Éclair 🍁");
        setDescription("Mémorise les cases qui s’allument, puis retrouve-les !");
    }

    @Override
    public void start(TextView timerView) {
        gameContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_memory_flash, gameContainer, false);

        GridLayout grid = gameView.findViewById(R.id.grid_memory);
        grid.removeAllViews();
        grid.setColumnCount(4);
        grid.setRowCount(4);

        int total = 16;
        Random random = new Random();

        // Choisir aléatoirement 3 à 5 indices à mémoriser
        int nbToHighlight = 3 + random.nextInt(3);
        while (correctIndices.size() < nbToHighlight) {
            correctIndices.add(random.nextInt(total));
        }

        ArrayList<View> buttons = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            View cell = new View(context);
            cell.setLayoutParams(new GridLayout.LayoutParams());
            cell.setBackgroundColor(context.getColor(R.color.c_gray));
            cell.setPadding(8, 8, 8, 8);

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.width = 0;
            param.height = 0;
            param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            param.setMargins(8, 8, 8, 8);
            cell.setLayoutParams(param);

            int index = i;
            cell.setOnClickListener(v -> {
                if (!inputEnabled) return;
                userSelections.add(index);
                v.setBackgroundColor(context.getColor(R.color.teal_700));
                if (userSelections.size() == correctIndices.size()) {
                    checkWin();
                }
            });

            buttons.add(cell);
            grid.addView(cell);
        }

        // Flash les cases pendant 1 seconde
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                for (int i : correctIndices) {
                    buttons.get(i).setBackgroundColor(context.getColor(R.color.purple_200));
                }
            }

            @Override
            public void onFinish() {
                for (View cell : buttons) {
                    cell.setBackgroundColor(context.getColor(R.color.c_gray));
                }
                inputEnabled = true;
            }
        }.start();

        gameContainer.addView(gameView);
    }

    private void checkWin() {
        inputEnabled = false;
        if (userSelections.equals(correctIndices)) {
            win();
        } else {
            loos();
        }
    }

    @Override
    public void win() {
        Toast.makeText(context, "Mémoire parfaite !", Toast.LENGTH_SHORT).show();
        int gain = 100 + new Random().nextInt(30000);

        Session.getSession().getUser().setNbErable(Session.getSession().getUser().getNbErable() + gain);
        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_erable);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbErable()));

        Session.getSession().updateUser();
        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, "Tu as oublié une case ! ", Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
