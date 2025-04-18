package org.calma.redkingmania.construction;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.item.Item;

import java.util.ArrayList;
import java.util.Date;

public class Mine extends Construction{
    public Mine(String idConstruction, String name, String idPropriete, String type, Date epiration, ArrayList<Item> items, int nbProduct) {
        super(idConstruction, name, idPropriete, type, epiration, items, nbProduct);
    }

    @Override
    public void action(Session s) {
        Context ctx = s.getCtx();

        int nbSup = this.getNbSuplement();

        s.getUser().setNbCristaux(s.getUser().getNbCristaux()+this.getNbProduct()+nbSup);

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_cristale);
        cpt.setText(String.valueOf(s.getUser().getNbCristaux()));
        Session.getSession().updateUser();
    }

    @Override
    public int getType() {
        return 3;
    }
}
