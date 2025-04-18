package org.calma.redkingmania.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.construction.Construction;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Holder> {
    private ArrayList<Construction> data;

    public Adapter(ArrayList<Construction> data) {
        this.data = data;
    }

    public ArrayList<Construction> getData() {
        return data;
    }

    public void setData(ArrayList<Construction> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int typeChat = 0;
        Holder holder = null;

        switch (viewType){
            case 1:
                typeChat = R.layout.row_erable;
                break;
            case 2:
                typeChat = R.layout.row_foret;
                break;
            case 3:
                typeChat = R.layout.row_cristal;
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(typeChat,parent,false);


        switch (viewType){
            case 1:
                holder = new Holder_chodron(v);
                break;
            case 2:
                holder = new Holder_foret(v);
                break;
            case 3:
                holder = new Holder_cristal(v);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(this.data.get(position));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
