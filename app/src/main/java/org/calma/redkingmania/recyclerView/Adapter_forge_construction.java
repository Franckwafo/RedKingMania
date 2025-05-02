package org.calma.redkingmania.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.construction.Construction;

import java.util.ArrayList;

public class Adapter_forge_construction extends RecyclerView.Adapter<Holder_forge_construction> {

    private final ArrayList<Construction> constu;

    public Adapter_forge_construction(ArrayList<Construction> constu) {
        this.constu = constu;
    }

    @NonNull
    @Override
    public Holder_forge_construction onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_forge, parent, false);
        return new Holder_forge_construction(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder_forge_construction holder, int position) {
        holder.bind(constu.get(position)); // Maintenant, c'est le ViewHolder qui fait le travail
    }

    @Override
    public int getItemCount() {
        return constu.size();
    }
}
