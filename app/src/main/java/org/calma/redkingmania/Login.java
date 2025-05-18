package org.calma.redkingmania;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.calma.redkingmania.bd.TokenSession;
import org.calma.redkingmania.utils.Controleur;

public class Login extends AppCompatActivity {

    EditText userName;
    EditText pswd;

    Button logButon;
    Button inscription;

    TextView link_classement;

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

        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String event = "";

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("event")) {
            event = intent.getStringExtra("event");
        }

        Controleur.VerifLastConect(Login.this,event);

        userName = findViewById(R.id.nomUtilisateur);
        pswd = findViewById(R.id.motDePasse);

        if (savedInstanceState != null) {
            userName.setText(savedInstanceState.getString("username"));
            pswd.setText(savedInstanceState.getString("pswd"));
        }

        logButon = findViewById(R.id.btnSeConnecter);
        inscription = findViewById(R.id.btnInscrire);
        link_classement = findViewById(R.id.classementLink);


        logButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event = "";

                Intent intent = getIntent();
                if (intent != null && intent.hasExtra("event")) {
                    event = intent.getStringExtra("event");
                }
                Controleur.VerifUser(Login.this,userName.getText().toString(),pswd.getText().toString(),event);
            }
        });

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Inscription.class);
                startActivity(intent);
            }
        });

        link_classement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/classement.php";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", userName.getText().toString());
        outState.putString("pswd", pswd.getText().toString());
    }
}