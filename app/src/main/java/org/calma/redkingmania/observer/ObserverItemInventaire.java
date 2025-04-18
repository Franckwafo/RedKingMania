package org.calma.redkingmania.observer;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.calma.redkingmania.Session;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ObserverItemInventaire {
    private final ArrayList<Item> data;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final long interval = 2000; // 2 secondes
    private final Runnable runnable;

    public ObserverItemInventaire(ArrayList<Item> data) {
        this.data = data;

        runnable = new Runnable() {
            @Override
            public void run() {
                checkItems();
                handler.postDelayed(this, interval); // Re-boucle
            }
        };
    }

    public void start() {
        handler.post(runnable); // Démarre la boucle
    }

    public void stop() {
        handler.removeCallbacks(runnable); // Arrête la boucle
    }

    private void checkItems() {
        Date now = new Date();
        Iterator<Item> iterator = data.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getDatePeremption().before(now)) {
                Toast.makeText(Session.getSession().getCtx(), "Item : " + item.getNom() + " détruit", Toast.LENGTH_SHORT).show();
                Controleur.DeletItemExpired(Session.getSession().getCtx(), item.getIdPropriete());
                iterator.remove();
                Session.getSession().getAdp_item().notifyDataSetChanged();
            }
        }
    }
}
