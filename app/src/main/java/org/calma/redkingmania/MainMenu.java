package org.calma.redkingmania;

import android.content.Intent;
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

            Toast.makeText(this,  "Salutation "+ Controleur.GetName(Session.getSession().getUser().getSex(),Session.getSession().getUser().getPseudo()), Toast.LENGTH_SHORT).show();

        } catch (IllegalStateException e) {
            // Affiche une erreur et termine l’activité
            Toast.makeText(this, "Erreur de session : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}