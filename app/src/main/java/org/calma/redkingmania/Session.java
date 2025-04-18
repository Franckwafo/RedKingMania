package org.calma.redkingmania;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.item.type.Item_c;
import org.calma.redkingmania.modal.Modal_shop;
import org.calma.redkingmania.observer.ObserverConstructions;
import org.calma.redkingmania.observer.ObserverItemInventaire;
import org.calma.redkingmania.recyclerView.Adapter;
import org.calma.redkingmania.recyclerView.Adapter_item;
import org.calma.redkingmania.utils.Controleur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;

public class Session implements Serializable {
    // Instance unique
    private static Session instance;

    // Attributs
    private Context ctx;
    private ArrayList<Construction> constructions;
    private ArrayList<Item> items;
    private User user;
    private Item itemSelected;
    private Construction construSelected;
    private Item itemRemoveConstruSelected;
    private Construction removeConstruSelected;
    private Adapter_item adp_item;
    private Adapter adp;

    private Modal_shop modal_shop;


    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable productionRunnable;

    // Constructeur privé
    private Session(ArrayList<Construction> constructions, ArrayList<Item> items, User user) {
        this.constructions = constructions;
        this.items = items;
        this.user = user;
    }

    // Getter Singleton
    public static Session getSession() {
        if (instance == null) {
            throw new IllegalStateException("La session n'a pas encore été initialisée !");
        }
        return instance;
    }

    // Initialisation de la session
    public static void initSession(ArrayList<Construction> constructions, ArrayList<Item> items, User user) {
        if (instance == null) {
            instance = new Session(constructions, items, user);
        }
    }

    // Méthodes
    public void init() {

        RecyclerView r_list = ((Activity) ctx).findViewById(R.id.rc_liste);
        r_list.setLayoutManager(new LinearLayoutManager(ctx));
        adp = new Adapter(this.constructions);
        r_list.setAdapter(adp);
        /////
        RecyclerView r_list_inventaire = ((Activity) ctx).findViewById(R.id.r_list_inventaire);
        r_list_inventaire.setLayoutManager(new LinearLayoutManager(ctx));
        adp_item = new Adapter_item(this.items);
        r_list_inventaire.setAdapter(adp_item);

        initAutoClicker();
        initCpt();

        ObserverItemInventaire observer = new ObserverItemInventaire(items);
        observer.start();

        ObserverConstructions observer_constru = new ObserverConstructions(constructions);
        observer_constru.start();

        //TODO coder les adapter et holder item et construction pour l inventaire et creer la table
        modal_shop = new Modal_shop(ctx);
        ImageView img_shop = ((Activity) ctx).findViewById(R.id.img_shop);
        img_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal_shop.show_shop();
            }
        });
    }

    public void initCpt(){
        Context ctx = this.getCtx();

        TextView cpt_e = ((Activity) ctx).findViewById(R.id.cpt_erable);
        TextView cpt_c = ((Activity) ctx).findViewById(R.id.cpt_cristale);
        TextView cpt_b = ((Activity) ctx).findViewById(R.id.cpt_boi);


        cpt_e.setText(String.valueOf(this.getUser().getNbErable()));
        cpt_c.setText(String.valueOf(this.getUser().getNbCristaux()));
        cpt_b.setText(String.valueOf(this.getUser().getNbBois()));
    }

    public void initAutoClicker(){
        for (Construction c : constructions){
            for (Item i : c.getItems()){
                if (i instanceof Item_c){
                    this.startAutoProduction(c,i.getNbEffet()*1000);
                }
            }
        }
    }

    public void startAutoProduction(final Construction c, final long intervalMillis) {
        productionRunnable = new Runnable() {
            @Override
            public void run() {
                c.action(Session.getSession());
                updateUser();
                handler.postDelayed(this, intervalMillis);
            }
        };

        handler.postDelayed(productionRunnable, intervalMillis);
    }

    public void stopAutoProduction() {
        if (handler != null && productionRunnable != null) {
            handler.removeCallbacks(productionRunnable);
        }
    }

    public void moveItem(){
        if (this.construSelected!=null && this.itemSelected!=null){
            if (this.construSelected.getItems().size()<4){
                Toast.makeText(Session.getSession().getCtx(), this.construSelected.getName(), Toast.LENGTH_SHORT).show();

                this.items.remove(this.itemSelected);
                this.construSelected.getItems().add(this.itemSelected);

                adp.notifyDataSetChanged();
                adp_item.notifyDataSetChanged();

                this.setItemRemoveConstruSelected(null);
                this.setRemoveConstruSelected(null);
                this.setItemSelected(null);
                this.setConstruSelected(null);

                stopAutoProduction();
                initAutoClicker();

                updateItems();
                updateConstructions();
            }

        }

    }

    public void moveIteminInventaire(){
        if (this.itemRemoveConstruSelected!=null && this.removeConstruSelected!=null){

            int index = constructions.indexOf(removeConstruSelected);

            this.constructions.get(index).removeItem(itemRemoveConstruSelected);

            this.items.add(this.itemRemoveConstruSelected);



            RecyclerView recyclerView = ((Activity) ctx).findViewById(R.id.rc_liste);

            recyclerView.setAdapter(null);
            recyclerView.setLayoutManager(null);
            recyclerView.setAdapter(adp);
            recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            adp.notifyDataSetChanged();
            adp_item.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(index);


            this.setItemRemoveConstruSelected(null);
            this.setRemoveConstruSelected(null);
            this.setItemSelected(null);
            this.setConstruSelected(null);

            stopAutoProduction();
            initAutoClicker();

            updateItems();
            updateConstructions();


        }
    }

    public void refrechActivity(){
        Activity activity = (Activity) ctx;
        Intent intent = activity.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.overridePendingTransition(0, 0); // supprimer animation sortante
        activity.finish();
        activity.overridePendingTransition(0, 0); // supprimer animation entrante
        ctx.startActivity(intent);
    }

    // Getters/Setters


    public Construction getRemoveConstruSelected() {
        return removeConstruSelected;
    }

    public void setRemoveConstruSelected(Construction removeConstruSelected) {
        this.removeConstruSelected = removeConstruSelected;
    }

    public Item getItemRemoveConstruSelected() {
        return itemRemoveConstruSelected;
    }

    public Adapter_item getAdp_item() {
        return adp_item;
    }

    public void setAdp_item(Adapter_item adp_item) {
        this.adp_item = adp_item;
    }

    public Adapter getAdp() {
        return adp;
    }

    public void setAdp(Adapter adp) {
        this.adp = adp;
    }

    public void setItemRemoveConstruSelected(Item itemRemoveConstruSelected) {
        this.itemRemoveConstruSelected = itemRemoveConstruSelected;
    }

    public Item getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(Item itemSelected) {
        this.itemSelected = itemSelected;
    }

    public Construction getConstruSelected() {
        return construSelected;
    }

    public void setConstruSelected(Construction construSelected) {
        this.construSelected = construSelected;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ArrayList<Construction> getConstructions() {
        return constructions;
    }

    public void setConstructions(ArrayList<Construction> constructions) {
        this.constructions = constructions;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void updateItems() {
        String items = "[";

        for (Item i : this.items) {
            items += "{";
            items += "\"idItem\":\"" + i.getIdItem() + "\",";
            items += "\"idUser\":\"" + Session.getSession().getUser().getUsername() + "\",";
            items += "\"idPropriete\":\"" + i.getIdPropriete() + "\",";
            items += "\"idConstruction\":" + null + ",";
            items += "\"datePeremption\":" +
                    ((i.getDatePeremption() != null) ? "\""+i.getDatePeremption().getTime()+"\"" : null) ;
            items += "},";
        }

        for (Construction c : this.constructions) {
            for (Item i : c.getItems()) {
                items += "{";
                items += "\"idUser\":\"" + Session.getSession().getUser().getUsername() + "\",";
                items += "\"idPropriete\":\"" + i.getIdPropriete() + "\",";
                items += "\"idConstruction\":\"" + c.getIdPropriete() + "\",";
                items += "\"datePeremption\":" +
                        ((i.getDatePeremption() != null) ? "\""+i.getDatePeremption().getTime()+"\"" : null) ;
                items += "},";
            }
        }

        // Supprime la dernière virgule si elle existe
        if (items.endsWith(",")) {
            items = items.substring(0, items.length() - 1);
        }

        items += "]";

        Controleur.updateInfo(this.ctx,items,"i");
    }

    public void updateConstructions() {
        String construs = "[";

        for (Construction c : this.constructions) {
            construs += "{";
            construs += "\"idPropriete\":\"" + c.getIdPropriete() + "\",";
            construs += "\"idUser\":\"" + Session.getSession().getUser().getUsername() + "\",";
            construs += "\"datePeremption\":" +
                    ((c.getEpiration() != null) ? "\""+c.getEpiration().getTime()+"\"" : null) ;

            construs += "},";
        }

        // Supprime la dernière virgule si elle existe
        if (construs.endsWith(",")) {
            construs = construs.substring(0, construs.length() - 1);
        }

        construs += "]";

        Controleur.updateInfo(this.ctx,construs,"c");
    }

    public void updateUser() {
        User user = Session.getSession().getUser(); // ou autre façon d'obtenir l'usager connecté

        String userStats = "{";

        userStats += "\"username\":\"" + user.getUsername() + "\",";
        userStats += "\"niveau\":" + user.getNiveau() + ",";
        userStats += "\"nbErable\":" + user.getNbErable() + ",";
        userStats += "\"nbCristaux\":" + user.getNbCristaux() + ",";
        userStats += "\"nbBois\":" + user.getNbBois();

        userStats += "}";

        Controleur.updateInfo(this.ctx,userStats,"u");
    }


}
