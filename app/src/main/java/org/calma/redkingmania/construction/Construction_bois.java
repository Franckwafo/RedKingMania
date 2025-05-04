package org.calma.redkingmania.construction;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.utils.Animation;
import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.Date;

public class Construction_bois extends Construction{
    public Construction_bois(String idConstruction, String name, String idPropriete, String type, Date epiration, ArrayList<Item> items, int nbProduct) {
        super(idConstruction, name, idPropriete, type, epiration, items, nbProduct);
    }

    @Override
    public void action(Session s) {
        Context ctx = s.getCtx();

        int nbSup = this.getNbSuplement();


        s.getUser().setNbBois(s.getUser().getNbBois()+this.getNbProduct()+nbSup);

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_boi);
        cpt.setText(Controleur.formaterPrix(s.getUser().getNbBois()));
        Session.getSession().updateUser();

        Animation.applyPulseAnimation(cpt,Session.getSession().getCtx());
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public boolean isItemValide(Item item) {
        boolean valide = false;
        if (item.getType().charAt(0) == 'b') {
            valide=true;
        }
        return valide;
    }
}
