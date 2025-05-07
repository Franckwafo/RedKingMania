package org.calma.redkingmania.miniGame;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.utils.Controleur;

import java.util.Random;

public class OddOneOutGame extends MiniGame {

    private FrameLayout gameContainer;
    private Context context;
    private CountDownTimer timer;
    private String[] emojis = {"😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣"};

    public OddOneOutGame(Context ctx, View view) {
        super(ctx, view);
        this.context = ctx;
        this.gameContainer = (FrameLayout) view;

        setName(Session.getSession().getCtx().getString(R.string.odd_game));
        setDescription(Session.getSession().getCtx().getString(R.string.odd_game_describ));
    }

    @Override
    public void start(TextView timerView) {
        gameContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        View gameView = inflater.inflate(R.layout.mini_game_odd_one_out, gameContainer, false);

        LinearLayout emojiContainer = gameView.findViewById(R.id.emoji_container);

        // Choisir un emoji "principal"
        Random random = new Random();
        String mainEmoji = emojis[random.nextInt(emojis.length)];
        String oddEmoji;
        do {
            oddEmoji = emojis[random.nextInt(emojis.length)];
        } while (oddEmoji.equals(mainEmoji));

        int oddPosition = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            TextView emojiView = new TextView(context);
            emojiView.setText(i == oddPosition ? oddEmoji : mainEmoji);
            emojiView.setTextSize(29f);
            emojiView.setPadding(12, 12, 12, 12);

            String finalOddEmoji = oddEmoji;
            emojiView.setOnClickListener(v -> {
                timer.cancel();
                if (((TextView) v).getText().toString().equals(finalOddEmoji)) {
                    win();
                } else {
                    loos();
                }
            });

            emojiContainer.addView(emojiView);
        }

        timer = new CountDownTimer(5000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerView.setText(String.format("%.1f s", millisUntilFinished / 1000f));
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
        Toast.makeText(context, R.string.odd_game_win_msg, Toast.LENGTH_SHORT).show();
        int crystals = new Random().nextInt(5000) + 5;

        Session.getSession().getUser().setNbCristaux(Session.getSession().getUser().getNbCristaux() + crystals);
        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_cristale);
        cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbCristaux()));

        Session.getSession().updateUser();
        Session.getSession().getModal_game().closeModal();
    }

    @Override
    public void loos() {
        Toast.makeText(context, R.string.odd_game_loos_msg, Toast.LENGTH_SHORT).show();
        Session.getSession().getModal_game().closeModal();
    }
}
