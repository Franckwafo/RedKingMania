package org.calma.redkingmania.construction;

import android.content.Context;

import org.calma.redkingmania.Session;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.item.Item_c_c;
import org.calma.redkingmania.item.Item_c_p;
import org.calma.redkingmania.item.type.Item_c;
import org.calma.redkingmania.item.type.Item_p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public abstract class Construction implements Serializable {
    private String idConstruction;
    private String name;
    private String idPropriete;
    private String type;
    private Date epiration;
    private ArrayList<Item> items;
    private int nbProduct;

    public Construction(String idConstruction, String name, String idPropriete, String type, Date epiration, ArrayList<Item> items, int nbProduct) {
        this.idConstruction = idConstruction;
        this.name = name;
        this.idPropriete = idPropriete;
        this.type = type;
        this.epiration = epiration;
        this.items = items;
        this.nbProduct = nbProduct;
    }

    public String getIdConstruction() {
        return idConstruction;
    }

    public void setIdConstruction(String idConstruction) {
        this.idConstruction = idConstruction;
    }

    public String getIdPropriete() {
        return idPropriete;
    }

    public void setIdPropriete(String idPropriete) {
        this.idPropriete = idPropriete;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbProduct() {
        return nbProduct;
    }

    public void setNbProduct(int nbProduct) {
        this.nbProduct = nbProduct;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEpiration() {
        return epiration;
    }

    public void setEpiration(Date epiration) {
        this.epiration = epiration;
    }

    public int getNbSuplement(){
        int nbSup = 0;

        for (Item i : this.items){
            if (i instanceof Item_p){
                nbSup += i.getNbEffet();
            }
        }

        return nbSup;
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }

    public abstract void action(Session s);
    public abstract int getType();
    public abstract boolean isItemValide(Item item);
}
