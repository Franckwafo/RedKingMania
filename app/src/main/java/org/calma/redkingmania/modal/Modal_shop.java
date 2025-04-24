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
import org.calma.redkingmania.recyclerView.Adapter_construction;
import org.calma.redkingmania.recyclerView.Adapter_item;
import org.calma.redkingmania.recyclerView.Adapter_shop_construction;
import org.calma.redkingmania.recyclerView.Adapter_shop_item;

public class Modal_shop {

    private Dialog dialog;
    public Modal_shop(Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.modal_shop);
        dialog.setCancelable(false); // empêche la fermeture en touchant à l’extérieur

        TextView title = dialog.findViewById(R.id.tv_title);
        Switch switchMode = dialog.findViewById(R.id.switch_mode);
        RecyclerView rv = dialog.findViewById(R.id.rv_elements);
        Button btnAcheter = dialog.findViewById(R.id.btn_acheter);
        Button btnAnnuler = dialog.findViewById(R.id.btn_annuler);

        rv.setLayoutManager(new LinearLayoutManager(context));

        // Adapter selon le mode sélectionné
        switchMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Afficher constructions
                rv.setAdapter(new Adapter_shop_construction(Session.getSession().getShop().getConstructionsBoutique()));
            } else {
                // Afficher items
                rv.setAdapter(new Adapter_shop_item(Session.getSession().getShop().getItemsBoutique()));
            }
        });

        // Initialiser avec les items par défaut
        rv.setAdapter(new Adapter_shop_item(Session.getSession().getShop().getItemsBoutique()));

        btnAnnuler.setOnClickListener(v -> dialog.dismiss());

        btnAcheter.setOnClickListener(v -> {
            // Logique d'achat ici
            dialog.dismiss();
        });


    }

    public void show_shop(){
        dialog.show();
    }
}
