package org.calma.redkingmania.shop;

public abstract class Article_shop {
    protected String id;
    protected int prix;
    protected String nom;

    protected String type;

    protected int nbProduction;

    public Article_shop(String id, int prix, String nom, String type, int nbProduction) {
        this.id = id;
        this.prix = prix;
        this.nom = nom;
        this.type = type;
        this.nbProduction = nbProduction;
    }


    public abstract void acheter();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
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

    public int getNbProduction() {
        return nbProduction;
    }

    public void setNbProduction(int nbProduction) {
        this.nbProduction = nbProduction;
    }
}
