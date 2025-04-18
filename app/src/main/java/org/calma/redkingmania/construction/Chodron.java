package org.calma.redkingmania.construction;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.item.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Chodron extends Construction{



    public Chodron(String idConstruction, String name, String idPropriete, String type, Date epiration, ArrayList<Item> items, int nbProduct) {
        super(idConstruction, name, idPropriete, type, epiration, items, nbProduct);
    }

    @Override
    public void action(Session s) {
        Context ctx = s.getCtx();

        int nbSup = this.getNbSuplement();

        s.getUser().setNbErable(s.getUser().getNbErable()+this.getNbProduct()+nbSup);

        TextView cpt = ((Activity) ctx).findViewById(R.id.cpt_erable);
        cpt.setText(String.valueOf(s.getUser().getNbErable()));

        Session.getSession().updateUser();
    }

    @Override
    public int getType() {
        return 1;
    }
}
