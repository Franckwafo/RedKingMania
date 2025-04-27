package org.calma.redkingmania.recyclerView;

import static org.calma.redkingmania.utils.Geter_img.get_construction_img_src;
import static org.calma.redkingmania.utils.Geter_img.get_item_img_src;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.shop.Article_construction;
import org.calma.redkingmania.shop.Article_shop;

public class Holder_shop_construction extends RecyclerView.ViewHolder {
    public ImageView itemImage, unite;
    public TextView itemNom, itemType, itemEffet, prix;

    public Holder_shop_construction(View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.itemImage);
        itemNom = itemView.findViewById(R.id.itemNom);
        itemType = itemView.findViewById(R.id.itemType);
        itemEffet = itemView.findViewById(R.id.itemEffet);
        prix = itemView.findViewById(R.id.prix);
        unite = itemView.findViewById(R.id.unite);
    }

    public void bind(Article_construction article) {
        itemNom.setText(article.getNom());
        itemType.setText("Type : " + article.getType());
        itemEffet.setText("Production : +" + article.getNbProduction());
        prix.setText(String.valueOf(article.getPrix()));

        // Image statique ici, tu peux adapter si tes articles ont une image personnalisée
        itemImage.setImageResource(R.drawable.bg_erable);
        /////////////
        String imageName = get_construction_img_src(article.getId());

        int resId = Session.getSession().getCtx()
                .getResources()
                .getIdentifier(imageName, "drawable", Session.getSession().getCtx().getPackageName());

        if (resId != 0) {
            itemImage.setImageResource(resId);
        } else {
            Log.e("Image Load", "Image non trouvée pour l'ID : " + imageName);
        }
        ///////////
        unite.setImageResource(R.drawable.cristale);

        itemView.setOnLongClickListener(v -> {
            article.acheter();
            return true; // indique que le long click est consommé
        });
    }
}
