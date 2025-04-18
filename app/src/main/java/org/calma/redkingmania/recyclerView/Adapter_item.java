package org.calma.redkingmania.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.item.Item;

import java.util.List;

public class Adapter_item extends RecyclerView.Adapter<Holder_inventaire>{

    private List<Item> itemList;

    // Constructeur
    public Adapter_item(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public Holder_inventaire onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_inventaire, parent, false);
        return new Holder_inventaire(view);
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_inventaire holder, int position) {
        Item currentItem = itemList.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
