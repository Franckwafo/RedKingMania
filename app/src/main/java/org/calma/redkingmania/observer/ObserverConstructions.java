package org.calma.redkingmania.observer;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ObserverConstructions {
    private final ArrayList<Construction> data;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final long interval = 2000; // 2 secondes
    private final Runnable runnable;

    public ObserverConstructions(ArrayList<Construction> data) {
        this.data = data;

        runnable = new Runnable() {
            @Override
            public void run() {
                checkConstru();
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

    private void checkConstru() {
        Date now = new Date();
        Iterator<Construction> iterator = data.iterator();
        while (iterator.hasNext()) {
            Construction constru = iterator.next();
            if (constru.getEpiration()!=null){
                if (constru.getEpiration().before(now)) {
                Controleur.DeletConstructionExpired(Session.getSession().getCtx(), constru.getIdPropriete());
                iterator.remove();
                Session.getSession().getAdp().notifyDataSetChanged();
                Toast.makeText(Session.getSession().getCtx(), R.string.shop_construction_confirmation + Controleur.getTrsanslateName(constru.getName()) + " "  +R.string.obsv_message_detruit, Toast.LENGTH_SHORT).show();
                }
            }
            Iterator<Item> iteratorItem = constru.getItems().iterator();
            while(iteratorItem.hasNext()){
                Item i = iteratorItem.next();
                if (i.getDatePeremption().before(now)){
                    Controleur.DeletItemExpired(Session.getSession().getCtx(), i.getIdPropriete());
                    iteratorItem.remove();
                    RecyclerView recyclerView = ((Activity) Session.getSession().getCtx()).findViewById(R.id.rc_liste);

                    recyclerView.setAdapter(null);
                    recyclerView.setLayoutManager(null);
                    recyclerView.setAdapter(Session.getSession().getAdp());
                    recyclerView.setLayoutManager(new LinearLayoutManager(Session.getSession().getCtx()));

                    Session.getSession().getAdp().notifyDataSetChanged();
                    Toast.makeText(Session.getSession().getCtx(), R.string.obsv_message_construction_item_detruit +" " +  Controleur.getTrsanslateName(i.getNom())+ " "  +R.string.obsv_message_detruit, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
