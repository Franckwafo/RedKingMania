package org.calma.redkingmania.shop;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.utils.Controleur;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article_item extends Article_shop {
    public Article_item(String id, int prix, String nom, String type, int nbProduction) {
        super(id, prix, nom, type, nbProduction);
    }

    @Override
    public void acheter() {
        Context ctx = Session.getSession().getCtx();

        if (Session.getSession().getUser().getNbErable()-this.prix<0){
            Toast.makeText(ctx, R.string.shop_action_erreur, Toast.LENGTH_SHORT).show();
        }
        else {
//            System.out.println("Item "+this.nom+" Acheter !!!" );
            Session.getSession().getUser().setNbErable(Session.getSession().getUser().getNbErable()-this.prix);

            TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_erable);
            cpt.setText(Controleur.formaterPrix(Session.getSession().getUser().getNbErable()));
            Session.getSession().updateUser();

            Date nouvelleDate = Controleur.genererDateExpiration();

            // Formater la date en String
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateFormatee = format.format(nouvelleDate);

            Session.getSession().shopItem(this,dateFormatee);


        }
    }

}
