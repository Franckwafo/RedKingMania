package org.calma.redkingmania.recyclerView;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.User;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.modal.Modal_forge;
import org.calma.redkingmania.utils.Controleur;

import java.util.Calendar;
import java.util.Date;

public class Holder_forge_item extends RecyclerView.ViewHolder {
    private final TextView tvNom;
    private final TextView tvDureeVie;

    public Holder_forge_item(@NonNull View itemView) {
        super(itemView);
        tvNom = itemView.findViewById(R.id.tv_nom_item_forge);
        tvDureeVie = itemView.findViewById(R.id.tv_duree_vie_item_forge);

        // Ajouter le clic ici directement sur l'itemView

    }

    public void bind(Item item) {
        tvNom.setText(Controleur.getTrsanslateName(item.getNom()));
        tvDureeVie.setText(Session.getSession().getCtx().getString(R.string.modal_valid_msg)+" " + Controleur.getTimeLeft(item.getDatePeremption()));

        itemView.setOnClickListener(v -> {
            if (Session.getSession().getUser().getNbBois()-Session.getSession().getModalForge().getPrix()>=0){
                Session.getSession().getUser().setNbBois(Session.getSession().getUser().getNbBois()-Session.getSession().getModalForge().getPrix());

                TextView cpt = ((Activity) Session.getSession().getCtx()).findViewById(R.id.cpt_boi);
                cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbBois()));

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(item.getDatePeremption());

                int minutesToAdd = (int) (Math.random() * 60) + 1;
                calendar.add(Calendar.MINUTE, minutesToAdd);

                Date newDate = calendar.getTime();

                item.setDatePeremption(newDate);
                Session.getSession().getModalForge().refreshForge();
                Session.getSession().updateItems();
                Session.getSession().updateUser();
            }else {
                Toast.makeText(Session.getSession().getCtx(), "Reparation impossible !", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
