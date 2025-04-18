package org.calma.redkingmania.item;

import org.calma.redkingmania.item.type.Item_p;
import org.threeten.bp.LocalDateTime;

import java.util.Date;

public class Item_e_p extends Item_p {
    public Item_e_p(String idItem, String idPropriete, String nom, String type, int nbEffet, Date dateTime) {
        super(idItem, idPropriete, nom, type, nbEffet, dateTime);
    }

    @Override
    public void action() {
        System.out.println("erable plus");
    }
}
