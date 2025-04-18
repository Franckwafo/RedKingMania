package org.calma.redkingmania;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.construction.Chodron;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.recyclerView.Adapter;

import java.util.ArrayList;

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

        } catch (IllegalStateException e) {
            // Affiche une erreur et termine l’activité
            Toast.makeText(this, "Erreur de session : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}