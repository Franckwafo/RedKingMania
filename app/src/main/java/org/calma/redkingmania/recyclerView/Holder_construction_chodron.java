package org.calma.redkingmania.recyclerView;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.utils.Animation;

public class Holder_construction_chodron extends Holder_construction {

    public Holder_construction_chodron(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Construction c) {
        init_Img_item(c);
        setLongClickConstruction(c);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session s = Session.getSession();
                c.action(s);
                Animation.applyPulseAnimation(view,Session.getSession().getCtx());
                Animation.explodeErableAnim(view,1);
                View myImage = ((Activity) Session.getSession().getCtx()).findViewById(R.id.cpt_erable);
                Animation.applyPulseAnimation(myImage,Session.getSession().getCtx());
            }
        });

        Animation.applyPulseAnimation(view,Session.getSession().getCtx());

    }
}
