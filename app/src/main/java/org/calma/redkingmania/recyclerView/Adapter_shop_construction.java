package org.calma.redkingmania.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.calma.redkingmania.R;
import org.calma.redkingmania.shop.Article_construction;
import org.calma.redkingmania.shop.Article_shop;

import java.util.ArrayList;
import java.util.List;

public class Adapter_shop_construction extends RecyclerView.Adapter<Holder_shop_construction> {
    private final ArrayList<Article_construction> liste;

    public Adapter_shop_construction(ArrayList<Article_construction> liste) {
        this.liste = liste;
    }

    @NonNull
    @Override
    public Holder_shop_construction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shop, parent, false);
        return new Holder_shop_construction(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_shop_construction holder, int position) {
        holder.bind(liste.get(position));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }
}
