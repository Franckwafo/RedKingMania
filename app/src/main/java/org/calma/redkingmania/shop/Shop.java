package org.calma.redkingmania.shop;

import org.calma.redkingmania.construction.Construction;

import java.util.ArrayList;

public class Shop {
    private  ArrayList<Article_item> itemsBoutique;
    private  ArrayList<Article_construction> constructionsBoutique;

    public Shop(ArrayList<Article_item> itemsBoutique, ArrayList<Article_construction> constructionsBoutique) {
        this.itemsBoutique = itemsBoutique;
        this.constructionsBoutique = constructionsBoutique;
    }

    public ArrayList<Article_item> getItemsBoutique() {
        return itemsBoutique;
    }

    public void setItemsBoutique(ArrayList<Article_item> itemsBoutique) {
        this.itemsBoutique = itemsBoutique;
    }

    public ArrayList<Article_construction> getConstructionsBoutique() {
        return constructionsBoutique;
    }

    public void setConstructionsBoutique(ArrayList<Article_construction> constructionsBoutique) {
        this.constructionsBoutique = constructionsBoutique;
    }
}
