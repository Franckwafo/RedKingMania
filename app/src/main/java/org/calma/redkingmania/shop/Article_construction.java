package org.calma.redkingmania.shop;

public class Article_construction extends Article_shop{
    public Article_construction(String id, int prix, String nom, String type, int nbProduction) {
        super(id, prix, nom, type, nbProduction);
    }

    @Override
    public void acheter() {
        System.out.println("Construction "+this.nom+" Acheter !!!" );
    }

}
