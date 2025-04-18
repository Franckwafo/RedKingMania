package org.calma.redkingmania.item;

import org.calma.redkingmania.item.type.Item_c;

import java.util.Date;

public class Item_e_c extends Item_c {
    public Item_e_c(String idItem, String idPropriete, String nom, String type, int nbEffet, Date dateTime) {
        super(idItem, idPropriete, nom, type, nbEffet, dateTime);
    }

    @Override
    public void action() {
        System.out.println("erable click");
    }
}
