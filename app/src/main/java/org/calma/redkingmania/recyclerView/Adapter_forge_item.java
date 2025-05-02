package org.calma.redkingmania.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.item.Item;

import java.util.ArrayList;

public class Adapter_forge_item extends RecyclerView.Adapter<Holder_forge_item> {

    private final ArrayList<Item> items;

    public Adapter_forge_item(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder_forge_item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forge, parent, false);
        return new Holder_forge_item(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_forge_item holder, int position) {
        holder.bind(items.get(position)); // Maintenant, c'est le ViewHolder qui fait le travail
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
