package org.calma.redkingmania.item;

import org.calma.redkingmania.item.type.Item_p;

import java.util.Date;

public class Item_b_p extends Item_p {
    public Item_b_p(String idItem, String idPropriete, String nom, String type, int nbEffet, Date dateTime) {
        super(idItem, idPropriete, nom, type, nbEffet, dateTime);
    }

    @Override
    public void action() {
        System.out.println("bois plus");
    }
}
