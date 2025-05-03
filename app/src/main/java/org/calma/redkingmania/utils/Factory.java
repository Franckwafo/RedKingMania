package org.calma.redkingmania.utils;

import android.content.Context;
import android.view.View;

import org.calma.redkingmania.User;
import org.calma.redkingmania.construction.Construction_erable;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.construction.Construction_bois;
import org.calma.redkingmania.construction.Construction_cristale;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.item.Item_b_c;
import org.calma.redkingmania.item.Item_b_p;
import org.calma.redkingmania.item.Item_c_c;
import org.calma.redkingmania.item.Item_c_p;
import org.calma.redkingmania.item.Item_e_c;
import org.calma.redkingmania.item.Item_e_p;
import org.calma.redkingmania.miniGame.ClickTimerGame;
import org.calma.redkingmania.miniGame.ColorReflexGame;
import org.calma.redkingmania.miniGame.FindTheColorGame;
import org.calma.redkingmania.miniGame.MemoryFlashGame;
import org.calma.redkingmania.miniGame.MemoryNumberGame;
import org.calma.redkingmania.miniGame.MiniGame;
import org.calma.redkingmania.miniGame.OddOneOutGame;
import org.calma.redkingmania.shop.Article_construction;
import org.calma.redkingmania.shop.Article_item;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
                    userJson.getInt("nbBois"),
                    userJson.getString("sex")
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


    public static Item buldItem(Article_item article,String idPropriete, String datePeremption){
        Item item = null;

        // Format qui correspond à ta chaîne
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date dateFormat = null;
        try {
            dateFormat = format.parse(datePeremption);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (article.getType()){
            case "b_c":
                item = new Item_b_c(article.getId(),idPropriete,article.getNom(),article.getType(),article.getNbProduction(),dateFormat);
                break;
            case "b_p":
                item = new Item_b_p(article.getId(),idPropriete,article.getNom(),article.getType(),article.getNbProduction(),dateFormat);
                break;
            case "c_c":
                item = new Item_c_c(article.getId(),idPropriete,article.getNom(),article.getType(),article.getNbProduction(),dateFormat);
                break;
            case "c_p":
                item = new Item_c_p(article.getId(),idPropriete,article.getNom(),article.getType(),article.getNbProduction(),dateFormat);
                break;
            case "e_c":
                item = new Item_e_c(article.getId(),idPropriete,article.getNom(),article.getType(),article.getNbProduction(),dateFormat);
                break;
            case "e_p":
                item = new Item_e_p(article.getId(),idPropriete,article.getNom(),article.getType(),article.getNbProduction(),dateFormat);
                break;
        }

        return item;
    }

    public static Construction buildConstruction(Article_construction article, String idPropriete, String datePeremption) {
        Construction construction = null;

        // Format qui correspond à la chaîne de date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<Item> items = new ArrayList<Item>();

        Date dateFormat = null;
        try {
            dateFormat = format.parse(datePeremption); // Conversion de la chaîne en Date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Utilisation d'un switch pour créer l'objet construction selon le type
        switch (article.getType()) {
            case "e":
                construction = new Construction_erable(
                        article.getId(),                  // idConstruction
                        article.getNom(),                 // name
                        idPropriete,                      // idPropriete
                        article.getType(),                // type
                        dateFormat,                       // expiration (date d'expiration)
                        items,                            // items associés à la construction
                        article.getNbProduction()        // nbProduct
                );
                break;
            case "c":
                construction = new Construction_cristale(
                        article.getId(),                  // idConstruction
                        article.getNom(),                 // name
                        idPropriete,                      // idPropriete
                        article.getType(),                // type
                        dateFormat,                       // expiration (date d'expiration)
                        items,                            // items associés à la construction
                        article.getNbProduction()        // nbProduct
                );
                break;
            case "b":
                construction = new Construction_bois(
                        article.getId(),                  // idConstruction
                        article.getNom(),                 // name
                        idPropriete,                      // idPropriete
                        article.getType(),                // type
                        dateFormat,                       // expiration (date d'expiration)
                        items,                            // items associés à la construction
                        article.getNbProduction()        // nbProduct
                );
                break;
            default:
                // Si le type de construction n'est pas reconnu, on lance une exception ou on peut gérer autrement
                throw new IllegalArgumentException("Type de construction inconnu : " + article.getType());
        }

        return construction;
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
                    c = new Construction_cristale(idConstruction,nom,idPropriete,type,formate_expiration,buldItems((List<Map<String, Object>>) constructionInfo.get("items")),production);
                    break;
                case "b":
                    c = new Construction_bois(idConstruction,nom,idPropriete,type,formate_expiration,buldItems((List<Map<String, Object>>) constructionInfo.get("items")),production);
                    break;
                case "e":
                    c = new Construction_erable(idConstruction,nom,idPropriete,type,formate_expiration,buldItems((List<Map<String, Object>>) constructionInfo.get("items")),production);
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

    public static ArrayList<Article_item> buildShopItems(List<Map<String, String>> rawData) {
        ArrayList<Article_item> articles = new ArrayList<>();
        for (Map<String, String> data : rawData) {
            String id = data.get("idItem");
            //TODO metre en place le mechanisme de prix
            String nom = data.get("nom");
            String type = data.get("type");
            int nbProduction = Integer.parseInt(data.get("nbEffet"));
            int prix = Controleur.calculerPrix(nbProduction,type.charAt(type.length()-1));

            articles.add(new Article_item(id, prix, nom, type, nbProduction));
        }
        return articles;
    }

    public static ArrayList<Article_construction> buildShopConstructions(List<Map<String, String>> rawData) {
        ArrayList<Article_construction> articles = new ArrayList<>();
        for (Map<String, String> data : rawData) {
            String id = data.get("idConstruction");
            String nom = data.get("nom");
            //TODO metre en place le mechanisme de production possible proble au nivaux des nom des champ
            String type = data.get("type");
            int nbProduction = Integer.parseInt(data.get("production"));
            int prix = Controleur.calculerPrix(nbProduction,'b');

            articles.add(new Article_construction(id, prix, nom, type, nbProduction));
        }
        return articles;
    }

    public static MiniGame GetGame(Context ctx, View view){

        Random rdm = new Random();

        switch (rdm.nextInt(6)){
            case 0:
                return new ClickTimerGame(ctx,view);
            case 1:
                return new ColorReflexGame(ctx,view);
            case 2:
                return new FindTheColorGame(ctx,view);
            case 3:
                return new MemoryFlashGame(ctx,view);
            case 4:
                return new MemoryNumberGame(ctx,view);
            case 5:
                return new OddOneOutGame(ctx,view);
        }
        return null;
    }


}
