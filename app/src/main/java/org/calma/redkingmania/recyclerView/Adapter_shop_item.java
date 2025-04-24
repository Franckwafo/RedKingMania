package org.calma.redkingmania.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.calma.redkingmania.R;
import org.calma.redkingmania.shop.Article_item;

import java.util.ArrayList;

public class Adapter_shop_item extends RecyclerView.Adapter<Holder_shop_item> {
    private final ArrayList<Article_item> liste;

    public Adapter_shop_item(ArrayList<Article_item> liste) {
        this.liste = liste;
    }

    @NonNull
    @Override
    public Holder_shop_item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_shop, parent, false);
        return new Holder_shop_item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_shop_item holder, int position) {
        holder.bind(liste.get(position));
    }

    @Override
    public int getItemCount() {
        return liste.size();
    }
}
