package org.calma.redkingmania.recyclerView;

import android.view.View;

import androidx.annotation.NonNull;

import org.calma.redkingmania.Session;
import org.calma.redkingmania.construction.Construction;

public class Holder_construction_foret extends Holder_construction {

    public Holder_construction_foret(@NonNull View itemView) {
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
            }
        });
    }
}
