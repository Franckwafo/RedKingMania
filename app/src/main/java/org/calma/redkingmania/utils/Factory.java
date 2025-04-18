package org.calma.redkingmania.utils;

import org.calma.redkingmania.User;
import org.calma.redkingmania.construction.Chodron;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.construction.Foret;
import org.calma.redkingmania.construction.Mine;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.item.Item_b_c;
import org.calma.redkingmania.item.Item_b_p;
import org.calma.redkingmania.item.Item_c_c;
import org.calma.redkingmania.item.Item_c_p;
import org.calma.redkingmania.item.Item_e_c;
import org.calma.redkingmania.item.Item_e_p;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Factory {
    public static User buldUser(Map<String, Object> userInfo){
        User user = null;
        try {
            JSONObject userJson = new JSONObject(userInfo);

            user = new User(
                    userJson.getString("username"),
                    userJson.getString("pseudo"),
                    userJson.getInt("niveau"),
                    userJson.getInt("nbErable"),
                    userJson.getInt("nbCristaux"),
                    userJson.getInt("nbBois")
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public static Item buldItem(Map<String, Object> itemInfo){
        Item item = null;

        try {
            JSONObject itemJson = new JSONObject(itemInfo);

            String idItem = itemJson.getString("idItem");
            String idPropriete = itemJson.getString("idPropriete");
            String nom = itemJson.getString("nom");
            String type = itemJson.getString("type");
            int nbEfect = itemJson.getInt("nbEffet");
            String expiration = itemJson.getString("datePeremption");
            Date formate_expiration = Controleur.convertirStringEnDate(expiration);



            switch (type){
                case "b_c":
                    item = new Item_b_c(idItem,idPropriete,nom,type,nbEfect,formate_expiration);
                    break;
                case "b_p":
                    item = new Item_b_p(idItem,idPropriete,nom,type,nbEfect,formate_expiration);
                    break;
                case "c_c":
                    item = new Item_c_c(idItem,idPropriete,nom,type,nbEfect,formate_expiration);
                    break;
                case "c_p":
                    item = new Item_c_p(idItem,idPropriete,nom,type,nbEfect,formate_expiration);
                    break;
                case "e_c":
                    item = new Item_e_c(idItem,idPropriete,nom,type,nbEfect,formate_expiration);
                    break;
                case "e_p":
                    item = new Item_e_p(idItem,idPropriete,nom,type,nbEfect,formate_expiration);
                    break;
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public static ArrayList<Item> buldItems(List<Map<String, Object>> infoItems){
        ArrayList<Item> items = new ArrayList<>();
        for (Map<String, Object> itemMap : infoItems) {
            Item item = buldItem(itemMap);

            if (item != null) {

                items.add(item);
            }
        }

        return items;
    }


    public static Construction buldConstruction(Map<String, Object> constructionInfo){
        Construction c = null;
        try {

            JSONObject constuctionJson = new JSONObject(constructionInfo);

            String idConstruction = constuctionJson.getString("idConstruction");
            String idPropriete = constuctionJson.getString("idPropriete");
            String nom = constuctionJson.getString("nom");
            String type = constuctionJson.getString("type");
            int production = constuctionJson.getInt("production");
            String expiration = constuctionJson.getString("datePeremption");
            Date formate_expiration = Controleur.convertirStringEnDate(expiration);

            switch (type){
                case "c":
                    c = new Mine(idConstruction,nom,idPropriete,type,formate_expiration,buldItems((List<Map<String, Object>>) constructionInfo.get("items")),production);
                    break;
                case "b":
                    c = new Foret(idConstruction,nom,idPropriete,type,formate_expiration,buldItems((List<Map<String, Object>>) constructionInfo.get("items")),production);
                    break;
                case "e":
                    c = new Chodron(idConstruction,nom,idPropriete,type,formate_expiration,buldItems((List<Map<String, Object>>) constructionInfo.get("items")),production);
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    public static ArrayList<Construction> buldConstructions(List<Map<String, Object>> infoConstructions){
        ArrayList<Construction> constructions = new ArrayList<>();
        for (Map<String, Object> c : infoConstructions) {
            Construction newConstru = buldConstruction(c);
            if (newConstru != null) {
                constructions.add(newConstru);
            }
        }
        return constructions;
    }
}
