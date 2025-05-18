package org.calma.redkingmania;
//TODO CLEAN MON CODE
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.item.type.Item_c;
import org.calma.redkingmania.modal.Modal_QrScanner;
import org.calma.redkingmania.modal.Modal_forge;
import org.calma.redkingmania.modal.Modal_mini_game;
import org.calma.redkingmania.modal.Modal_shop;
import org.calma.redkingmania.modal.Modal_user;
import org.calma.redkingmania.observer.ObserverConstructions;
import org.calma.redkingmania.observer.ObserverItemInventaire;
import org.calma.redkingmania.recyclerView.Adapter_construction;
import org.calma.redkingmania.recyclerView.Adapter_item;
import org.calma.redkingmania.shop.Article_construction;
import org.calma.redkingmania.shop.Article_item;
import org.calma.redkingmania.shop.Shop;
import org.calma.redkingmania.utils.Controleur;
import org.calma.redkingmania.utils.Song;

import java.io.Serializable;
import java.util.ArrayList;

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
    private Adapter_construction adp;

    private Modal_shop modal_shop;

    private Shop shop;
    private Modal_mini_game modal_game;
    private Modal_forge modalForge;

    private Modal_user modalUser;


    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable productionRunnable;

    private MediaPlayer back_song;

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
        instance = new Session(constructions, items, user);
    }

    // Méthodes
    public void init() {

        RecyclerView r_list = ((Activity) ctx).findViewById(R.id.rc_liste);
        r_list.setLayoutManager(new LinearLayoutManager(ctx));
        adp = new Adapter_construction(this.constructions);
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

        Controleur.initBoutique(ctx);

        modal_game = new Modal_mini_game();
        modalForge = new Modal_forge(ctx);

        ImageView forge = ((Activity) ctx).findViewById(R.id.img_forge);
        forge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalForge.show_forge();
            }
        });

        modalUser = new Modal_user();
        ImageView user = ((Activity) ctx).findViewById(R.id.user_icon);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modalUser.start();
            }
        });

        ImageView qrScanButton = ((Activity) ctx).findViewById(R.id.scan_icon);
        qrScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modal_QrScanner scannerFragment = new Modal_QrScanner();
                AppCompatActivity activity = (AppCompatActivity) ctx;
                scannerFragment.show(activity.getSupportFragmentManager(), "qrScanner");
            }
        });


        back_song = MediaPlayer.create(ctx, R.raw.back_song);
        back_song.setLooping(true);
        back_song.setVolume(0.3f, 0.3f); // 30% du volume max à gauche et à droite
        back_song.start();


//        Song.setBackSong(ctx,back_song);

    }

    public void stopSong(){
        this.back_song.stop();
    }

    public void initCpt(){
        Context ctx = this.getCtx();

        TextView cpt_e = ((Activity) ctx).findViewById(R.id.cpt_erable);
        TextView cpt_c = ((Activity) ctx).findViewById(R.id.cpt_cristale);
        TextView cpt_b = ((Activity) ctx).findViewById(R.id.cpt_boi);


        cpt_e.setText(String.valueOf(Controleur.formaterPrix(this.getUser().getNbErable())));
        cpt_c.setText(String.valueOf(Controleur.formaterPrix(this.getUser().getNbCristaux())));
        cpt_b.setText(String.valueOf(Controleur.formaterPrix(this.getUser().getNbBois())));
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
            if (this.construSelected.getItems().size()<4 && construSelected.isItemValide(itemSelected)){

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
            }else {
                this.setItemRemoveConstruSelected(null);
                this.setRemoveConstruSelected(null);
                this.setItemSelected(null);
                this.setConstruSelected(null);
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


    public Modal_forge getModalForge() {
        return modalForge;
    }

    public void setModalForge(Modal_forge modalForge) {
        this.modalForge = modalForge;
    }

    public Modal_mini_game getModal_game() {
        return modal_game;
    }

    public void setModal_game(Modal_mini_game modal_game) {
        this.modal_game = modal_game;
    }

    public Modal_shop getModal_shop() {
        return modal_shop;
    }

    public void setModal_shop(Modal_shop modal_shop) {
        this.modal_shop = modal_shop;
    }

    public Construction getRemoveConstruSelected() {
        return removeConstruSelected;
    }

    public void setRemoveConstruSelected(Construction removeConstruSelected) {
        this.removeConstruSelected = removeConstruSelected;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    public Adapter_construction getAdp() {
        return adp;
    }

    public void setAdp(Adapter_construction adp) {
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

    public void itemsAdd(Item i){
        this.items.add(i);
        adp_item.notifyDataSetChanged();
    }

    public void constructionsAdd(Construction c){
        this.constructions.add(c);
        adp.notifyDataSetChanged();
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

    public void shopItem(Article_item article, String datePeremption){
        Controleur.shopItem(ctx,article,datePeremption);
    }

    public void shopConstruction(Article_construction articleConstruction, String datePereption){
        Controleur.shopConstruction(ctx,articleConstruction,datePereption);
    }

}
