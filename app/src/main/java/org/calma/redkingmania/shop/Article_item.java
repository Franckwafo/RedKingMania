package org.calma.redkingmania.shop;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Article_item extends Article_shop {
    public Article_item(String id, int prix, String nom, String type, int nbProduction) {
        super(id, prix, nom, type, nbProduction);
    }

    @Override
    public void acheter() {
        Context ctx = Session.getSession().getCtx();

        if (Session.getSession().getUser().getNbErable()-this.prix<0){
            Toast.makeText(ctx, "Acheter impossible !!!", Toast.LENGTH_SHORT).show();
        }
        else {
            System.out.println("Item "+this.nom+" Acheter !!!" );
            Session.getSession().getUser().setNbErable(Session.getSession().getUser().getNbErable()-this.prix);

            TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_erable);
            cpt.setText(String.valueOf(Session.getSession().getUser().getNbErable()));
            Session.getSession().updateUser();

            Date dateAujourdhui = new Date();

            // Utilisation de Calendar pour ajouter un jour
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateAujourdhui); // Initialisation avec la date d'aujourd'hui
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Ajout d'un jour

            // Nouvelle date après l'ajout du jour
            Date nouvelleDate = calendar.getTime();

            // Formater la date en String
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateFormatee = format.format(nouvelleDate);

            Session.getSession().shopItem(this,dateFormatee);


        }
    }

}
