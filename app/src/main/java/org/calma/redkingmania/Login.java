package org.calma.redkingmania;

import android.annotation.SuppressLint;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Controleur.VerifLastConect(Login.this);

        userName = findViewById(R.id.nomUtilisateur);
        pswd = findViewById(R.id.motDePasse);
        logButon = findViewById(R.id.btnSeConnecter);

        logButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controleur.VerifUser(Login.this,userName.getText().toString(),pswd.getText().toString());
            }
        });
    }
}