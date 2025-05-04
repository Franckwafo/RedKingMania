package org.calma.redkingmania;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.List;

public class Inscription extends AppCompatActivity {

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

        setContentView(R.layout.activity_inscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainInscription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.titreSpinner);
        List<String> titres = new ArrayList<>();
        titres.add("Rois");
        titres.add("Reine");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ///////////////////////

        Button btnInscrire = findViewById(R.id.btnValiderInscription);

        EditText username = findViewById(R.id.username);
        EditText pseudo = findViewById(R.id.pseudo);
        EditText password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        Spinner titreSpinner = findViewById(R.id.titreSpinner);

        btnInscrire.setOnClickListener(v -> {
            String usernameVal = username.getText().toString().trim();
            String pseudoVal = pseudo.getText().toString().trim();
            String passwordVal = password.getText().toString().trim();
            String confirmPasswordVal = confirmPassword.getText().toString().trim();
            String titreChoisi = titreSpinner.getSelectedItem().toString();
            String titreValue = titreChoisi.equals("Rois") ? "m" : "f";

            if (passwordVal.equals(confirmPasswordVal) && !passwordVal.isEmpty() && !usernameVal.isEmpty() && !pseudoVal.isEmpty()) {
                Controleur.inscription(this,usernameVal,pseudoVal,passwordVal,titreValue);
                Toast.makeText(this, "Infos valides !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Les mots de passe ne correspondent pas ou des champ sont invalide", Toast.LENGTH_SHORT).show();
            }
        });

    }
}