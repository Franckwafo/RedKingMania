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

public class Article_construction extends Article_shop{
    public Article_construction(String id, int prix, String nom, String type, int nbProduction) {
        super(id, prix, nom, type, nbProduction);
    }

    @Override
    public void acheter() {
        Context ctx = Session.getSession().getCtx();

        // Vérification si l'utilisateur a assez de ressources pour acheter la construction
        if (Session.getSession().getUser().getNbCristaux() - this.prix < 0) {
            Toast.makeText(ctx, "Acheter impossible !!!", Toast.LENGTH_SHORT).show();
        } else {
            // Affichage de la confirmation de l'achat dans la console
            System.out.println("Construction " + this.nom + " Acheter !!!");

            // Mise à jour du nombre de ressources de l'utilisateur
            Session.getSession().getUser().setNbCristaux(Session.getSession().getUser().getNbCristaux() - this.prix);

            // Mise à jour de l'affichage du compteur de ressources
            TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_cristale);
            cpt.setText(String.valueOf(Session.getSession().getUser().getNbCristaux()));

            // Mise à jour des informations de l'utilisateur dans la session
            Session.getSession().updateUser();

            // Calcul de la date d'achat de la construction
            Date dateAujourdhui = new Date();

            // Utilisation de Calendar pour ajouter un jour à la date d'aujourd'hui
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateAujourdhui); // Initialisation avec la date d'aujourd'hui
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Ajout d'un jour

            // Nouvelle date après l'ajout du jour
            Date nouvelleDate = calendar.getTime();

            // Formatage de la date en String pour l'envoyer à la session ou à la base de données
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateFormatee = format.format(nouvelleDate);

            // Appel à la méthode de la session pour acheter la construction
            // Tu pourrais ici aussi appeler une méthode différente pour gérer les constructions si nécessaire
            Session.getSession().shopConstruction(this, dateFormatee);
        }
    }


}
