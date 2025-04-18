package org.calma.redkingmania.item;

import java.io.Serializable;
import java.util.Date;

public abstract class Item implements Serializable {
    private String idItem;
    private String idPropriete;
    private String nom;
    private String type;
    private int nbEffet;

    private Date datePeremption;

    public Item(String idItem, String idPropriete, String nom, String type, int nbEffet, Date dateTime) {
        this.idItem = idItem;
        this.idPropriete = idPropriete;
        this.nom = nom;
        this.type = type;
        this.nbEffet = nbEffet;
        this.datePeremption = dateTime;
    }

    public String getIdPropriete() {
        return idPropriete;
    }

    public void setIdPropriete(String idPropriete) {
        this.idPropriete = idPropriete;
    }

    public Date getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }

    public abstract void action();

    // === Getters et Setters ===

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbEffet() {
        return nbEffet;
    }

    public void setNbEffet(int nbEffet) {
        this.nbEffet = nbEffet;
    }

    // Optionnel : méthode toString pour debug
    @Override
    public String toString() {
        return "Item{" +
                "idItem='" + idItem + '\'' +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", nbEffet=" + nbEffet +
                ", date=" + datePeremption +
                '}';
    }
}
