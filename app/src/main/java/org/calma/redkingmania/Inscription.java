package org.calma.redkingmania;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.List;

public class Inscription extends AppCompatActivity {

    private EditText username ;
    private EditText pseudo ;
    private EditText password ;
    private EditText confirmPassword ;

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
        titres.add(this.getString(R.string.sing_titre_roi));
        titres.add(this.getString(R.string.sing_titre_reine));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ///////////////////////

        Button btnInscrire = findViewById(R.id.btnValiderInscription);

        username = findViewById(R.id.username);
        pseudo = findViewById(R.id.pseudo);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);

        if (savedInstanceState != null) {
            username.setText(savedInstanceState.getString("KEY_USERNAME", ""));
            pseudo.setText(savedInstanceState.getString("KEY_PSEUDO", ""));
            password.setText(savedInstanceState.getString("KEY_PASSWORD", ""));
            confirmPassword.setText(savedInstanceState.getString("KEY_CONFIRM_PASSWORD", ""));
        }

        Spinner titreSpinner = findViewById(R.id.titreSpinner);

        btnInscrire.setOnClickListener(v -> {
            String usernameVal = username.getText().toString().trim();
            String pseudoVal = pseudo.getText().toString().trim();
            String passwordVal = password.getText().toString().trim();
            String confirmPasswordVal = confirmPassword.getText().toString().trim();
            String titreChoisi = titreSpinner.getSelectedItem().toString();
            String titreValue = "";
            if (titreChoisi.equals(this.getString(R.string.sing_titre_roi))) {
                // C’est "Rois" ou sa traduction actuelle
                titreValue = "m";
            } else if (titreChoisi.equals(this.getString(R.string.sing_titre_reine))) {
                // C’est "Reine" ou sa traduction actuelle
                titreValue = "f";
            }

            if (passwordVal.equals(confirmPasswordVal) && !passwordVal.isEmpty() && !usernameVal.isEmpty() && !pseudoVal.isEmpty()) {
                Controleur.inscription(this,usernameVal,pseudoVal,passwordVal,titreValue);
                Toast.makeText(this, this.getString(R.string.sing_confirme_msg), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, this.getString(R.string.sing_errer_info), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("KEY_USERNAME", username.getText().toString());
        outState.putString("KEY_PSEUDO", pseudo.getText().toString());
        outState.putString("KEY_PASSWORD", password.getText().toString());
        outState.putString("KEY_CONFIRM_PASSWORD", confirmPassword.getText().toString());
    }
}