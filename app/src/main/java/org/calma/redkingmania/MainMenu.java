package org.calma.redkingmania;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.calma.redkingmania.utils.Animation;
import org.calma.redkingmania.utils.Controleur;

public class MainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        boolean isTablet = (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        // Si ce n’est PAS une tablette, on force le mode portrait
        if (!isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        try {
            Session.getSession().setCtx(MainMenu.this);
            Session.getSession().init();

            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("event")) {
                String event = intent.getStringExtra("event");
                if ("scan".equals(event)) {
                    Animation.ExplodAnim();
                }
            }

            Toast.makeText(this,  this.getString(R.string.main_salutation) +" "+ Controleur.GetName(Session.getSession().getUser().getSex(),Session.getSession().getUser().getPseudo()), Toast.LENGTH_SHORT).show();

        } catch (IllegalStateException e) {
            // Affiche une erreur et termine l’activité
            Toast.makeText(this, this.getString(R.string.main_erreure_session)+" " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}