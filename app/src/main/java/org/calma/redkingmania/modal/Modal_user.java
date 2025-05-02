package org.calma.redkingmania.modal;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.User;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.utils.Animation;
import org.calma.redkingmania.utils.Controleur;

public class Modal_user {

    private Dialog dialog;
    private User u;
    private int prix;

    public Modal_user() {
        // Préparer le bouton pour ouvrir la modale utilisateur
        ImageView imgUser = ((Activity) Session.getSession().getCtx()).findViewById(R.id.user_icon); // à lier dans ton layout principal
        imgUser.setOnClickListener(v -> start());

        u = Session.getSession().getUser();

        int ref = 10000;
        prix = (Controleur.timeGetVariablePrix())*ref*u.getNiveau();
    }

    public void start() {

        Activity activity = (Activity) Session.getSession().getCtx();


        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.modal_user);
        dialog.setCancelable(true);

        Button btn_retour = dialog.findViewById(R.id.btn_retour);

        btn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeModal();
            }
        });



        // Lier les vues
        ImageView roiImage = dialog.findViewById(R.id.iv_user_king);
        Controleur.SetPP(roiImage,u.getSex());

        TextView nom = dialog.findViewById(R.id.tv_user_name);
        Controleur.SetName(nom,u.getSex(),u.getPseudo());

        TextView niveau = dialog.findViewById(R.id.tv_user_level);
        niveau.setText("Niveau : " + u.getNiveau() );


//
        TextView tvErable = dialog.findViewById(R.id.tv_gold);
        tvErable.setText("Erable "+Controleur.formaterPrix(u.getNbErable()));
        TextView tvBoi = dialog.findViewById(R.id.tv_wood);
        tvBoi.setText("Boi "+Controleur.formaterPrix(u.getNbBois()));
        TextView tvCristal = dialog.findViewById(R.id.tv_rock);
        tvCristal.setText("Cristale "+Controleur.formaterPrix(u.getNbCristaux()));

        Button btnLevelUp = dialog.findViewById(R.id.btn_level_up);
        btnLevelUp.setText("Level up pour " + Controleur.formaterPrix(prix)+ " de chaque unite");



        btnLevelUp.setOnClickListener(v -> {
            if(u.getNbBois()-prix>=0 &&
               u.getNbCristaux()-prix>=0&&
               u.getNbErable()-prix>=0 ){

                Session.getSession().getUser().setNbBois(u.getNbBois()-prix);
                Session.getSession().getUser().setNbErable(u.getNbErable()-prix);
                Session.getSession().getUser().setNbCristaux(u.getNbErable()-prix);

                TextView cpt_erable = ((Activity) Session.getSession().getCtx()).findViewById(R.id.cpt_erable);
                TextView cpt_bois = ((Activity) Session.getSession().getCtx()).findViewById(R.id.cpt_boi);
                TextView cpt_cristale = ((Activity) Session.getSession().getCtx()).findViewById(R.id.cpt_cristale);

                cpt_bois.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbBois()));
                cpt_erable.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbErable()));
                cpt_cristale.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbCristaux()));




                Session.getSession().getUser().setNiveau(u.getNiveau()+1);

                for(Item i : Session.getSession().getItems()){
                    i.setDatePeremption(Controleur.ajouterJours(i.getDatePeremption(),Session.getSession().getUser().getNiveau()));
                }

                for (Construction c : Session.getSession().getConstructions()){
                    c.setEpiration(Controleur.ajouterJours(c.getEpiration(),Session.getSession().getUser().getNiveau()));
                }

                Session.getSession().getAdp().notifyDataSetChanged();
                Session.getSession().getAdp_item().notifyDataSetChanged();

                Session.getSession().updateUser();
                Session.getSession().updateItems();
                Session.getSession().updateConstructions();
                closeModal();

                int ref = 10000;
                prix = (Controleur.timeGetVariablePrix())*ref*u.getNiveau();

                Animation.ExplodAnim();


            }else {
                Toast.makeText(Session.getSession().getCtx(), "Level UP Impossible", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.getWindow().setLayout(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        dialog.show();
    }

    public void closeModal() {
        dialog.dismiss();
    }
}
