package org.calma.redkingmania.modal;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.recyclerView.Adapter_forge_construction;
import org.calma.redkingmania.recyclerView.Adapter_forge_item;
import org.calma.redkingmania.recyclerView.Adapter_item;
import org.calma.redkingmania.utils.Controleur;

import java.util.Calendar;
import java.util.Random;

public class Modal_forge {

    private Dialog dialog;
    private RecyclerView rv;
    private RecyclerView.Adapter<?> currentAdapter; // AJOUTÉ !
    private int prix;

    public Modal_forge(Context context) {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);    // 0-23
        int minute = now.get(Calendar.MINUTE);       // 0-59
        int second = now.get(Calendar.SECOND);       // 0-59

        int h2 = hour % 10;
        int m2 = minute % 10;
        int s2 = second % 10;

        int divisor = h2 + m2 + s2 + 1;

        Random r = new Random();
        int baseMax = 100000 * (Session.getSession().getConstructions().size() + Session.getSession().getItems().size());
        prix = (r.nextInt(baseMax) + divisor) / divisor;

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.modal_forge);
        dialog.setCancelable(false);

        TextView title = dialog.findViewById(R.id.tv_title_forge);
        TextView description = dialog.findViewById(R.id.tv_instruction_forge);
        description.setText(Session.getSession().getCtx().getString(R.string.modal_forge_describ) +" "+ Controleur.formaterPrix(this.prix) +" "+ Session.getSession().getCtx().getString(R.string.modal_forge_describ_next) );
        Switch switchMode = dialog.findViewById(R.id.switch_forge_mode);
        rv = dialog.findViewById(R.id.rv_items_forge);
        Button btnAnnuler = dialog.findViewById(R.id.btn_annuler_forge);

        rv.setLayoutManager(new LinearLayoutManager(context));

        // Comportement du switch : afficher selon le mode
        switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentAdapter = new Adapter_forge_item(Session.getSession().getItems()); // Mise à jour
            } else {
                currentAdapter = new Adapter_forge_construction(Session.getSession().getConstructions()); // Mise à jour
            }
            rv.setAdapter(currentAdapter);
        });

        // Chargement initial
        currentAdapter = new Adapter_forge_construction(Session.getSession().getConstructions());
        rv.setAdapter(currentAdapter);

        // Bouton d’annulation
        btnAnnuler.setOnClickListener(v -> dialog.dismiss());
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void refreshForge() {
        if (currentAdapter != null) {
            currentAdapter.notifyDataSetChanged(); // C'est l'ADAPTER qui doit être notifié !
        }
    }

    public void show_forge() {
        dialog.show();
    }
}
