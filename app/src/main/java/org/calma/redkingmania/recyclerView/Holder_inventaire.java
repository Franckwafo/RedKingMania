package org.calma.redkingmania.recyclerView;

import static org.calma.redkingmania.utils.Geter_img.get_item_img_src;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.item.Item;

public class Holder_inventaire extends RecyclerView.ViewHolder{
    private View view;
    private ImageView img;

    public Holder_inventaire(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        img = view.findViewById(R.id.img_item);
    }

    public void setLongClickItem(Item i){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Session.getSession().setItemSelected(i);
                Session.getSession().moveItem();
                return true;
            }
        });
    }


    public void bind(Item i) {
        String imageName = get_item_img_src(i.getIdItem());

        int resId = Session.getSession().getCtx()
                .getResources()
                .getIdentifier(imageName, "drawable", Session.getSession().getCtx().getPackageName());

        if (resId != 0) {
            img.setImageResource(resId);
            Holder_construction.setImgClick(i,view,resId);
            setLongClickItem(i);

        } else {
            Log.e("Image Load", "Image non trouvée pour l'ID : " + imageName);
        }


    }
}
