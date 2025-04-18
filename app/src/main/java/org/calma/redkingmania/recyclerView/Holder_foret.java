package org.calma.redkingmania.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.construction.Chodron;
import org.calma.redkingmania.construction.Construction;

public class Holder_foret extends Holder {

    public Holder_foret(@NonNull View itemView) {
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
