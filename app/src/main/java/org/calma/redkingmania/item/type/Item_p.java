package org.calma.redkingmania.item.type;

import org.calma.redkingmania.item.Item;

import java.util.Date;

public class Item_p extends Item {
    public Item_p(String idItem, String idPropriete, String nom, String type, int nbEffet, Date dateTime) {
        super(idItem, idPropriete, nom, type, nbEffet, dateTime);
    }

    @Override
    public void action() {

    }
}
