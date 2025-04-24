package org.calma.redkingmania.shop;

public class Article_item extends Article_shop {
    public Article_item(String id, int prix, String nom, String type, int nbProduction) {
        super(id, prix, nom, type, nbProduction);
    }

    @Override
    public void acheter() {
        System.out.println("Item "+this.nom+" Acheter !!!" );
    }

}
