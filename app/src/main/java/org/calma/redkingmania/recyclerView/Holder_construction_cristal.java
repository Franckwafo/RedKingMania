package org.calma.redkingmania.recyclerView;

import static org.calma.redkingmania.utils.Geter_img.get_construction_img_src;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.utils.Animation;

public class Holder_construction_cristal extends Holder_construction {
    public Holder_construction_cristal(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Construction c) {
        init_Img_item(c);
        setLongClickConstruction(c);

        ImageView img = itemView.findViewById(R.id.center_image);

        String imageName = get_construction_img_src(c.getIdConstruction());

        int resId = Session.getSession().getCtx()
                .getResources()
                .getIdentifier(imageName, "drawable", Session.getSession().getCtx().getPackageName());

        if (resId != 0) {
            img.setImageResource(resId);
        } else {
            Log.e("Image Load", "Image non trouvée pour l'ID : " + imageName);
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session s = Session.getSession();
                c.action(s);
                Animation.applyPulseAnimation(view,Session.getSession().getCtx());
                Animation.explodeErableAnim(view,2);
                View myImage = ((Activity) Session.getSession().getCtx()).findViewById(R.id.cpt_cristale);
                Animation.applyPulseAnimation(myImage,Session.getSession().getCtx());
            }
        });
        Animation.applyPulseAnimation(view,Session.getSession().getCtx());
    }
}
