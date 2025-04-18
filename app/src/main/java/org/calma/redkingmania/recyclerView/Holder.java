package org.calma.redkingmania.recyclerView;

import static org.calma.redkingmania.utils.Geter_Items_img.get_img_src;
import android.app.Dialog;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.utils.Controleur;

import java.util.ArrayList;
import java.util.List;

public abstract class Holder extends RecyclerView.ViewHolder {
    protected View view;


    public Holder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }

    public void init_Img_item(Construction c) {

        ArrayList<Integer> itemCard = new ArrayList<>();
        itemCard.add(R.id.item_1);
        itemCard.add(R.id.item_2);
        itemCard.add(R.id.item_3);
        itemCard.add(R.id.item_4);

        List<Item> items = c.getItems();

        // On s'assure qu'on n'attribue que ce qu'on a (max 4 images)
        for (int i = 0; i < items.size() && i < itemCard.size(); i++) {
            Item currentItem = items.get(i);
            String imageName = get_img_src(currentItem.getIdItem());

            int resId = Session.getSession().getCtx()
                    .getResources()
                    .getIdentifier(imageName, "drawable", Session.getSession().getCtx().getPackageName());

            if (resId != 0) {
                ImageView imageView = view.findViewById(itemCard.get(i));
                imageView.setImageResource(resId);
                setImgClick(currentItem,imageView,resId);
                setLongClickItemConstru(imageView,currentItem,c);
            } else {
                Log.e("Image Load", "Image non trouvée pour l'ID : " + imageName);
            }
        }


    }

    public void setLongClickItemConstru(View view,Item i,Construction c){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Session.getSession().setRemoveConstruSelected(c);
                Session.getSession().setItemRemoveConstruSelected(i);
                Session.getSession().moveIteminInventaire();
                return true;
            }
        });
    }

    public void setLongClickConstruction(Construction c){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Session.getSession().setConstruSelected(c);
                Session.getSession().moveItem();
                return true;
            }
        });
    }

    public static void setImgClick(Item i,View img, int resId){
        Dialog dialog = new Dialog(Session.getSession().getCtx());
        dialog.setContentView(R.layout.modal_item);
        dialog.setCancelable(true);

        ImageView imgItem = dialog.findViewById(R.id.itemImage);
        imgItem.setImageResource(resId);
        TextView txt_nom = dialog.findViewById(R.id.itemNom);
        txt_nom.setText(i.getNom());
        TextView txt_type = dialog.findViewById(R.id.itemType);
        txt_type.setText("Type : " + Controleur.getItemTypeFromSuffix(i.getType()));
        TextView txt_effect = dialog.findViewById(R.id.itemEffet);
        txt_effect.setText("Effets : " + Controleur.getItemEffectFromSuffix(i.getType(),i));
        Button button = dialog.findViewById(R.id.btnFermer);
        button.setBackgroundColor(Color.parseColor(Controleur.getColorFromEffect(i.getNbEffet())));

        button.setOnClickListener(v -> {
            // Action quand on clique
            dialog.dismiss();
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });


    }


    public abstract void bind(Construction c);
}
