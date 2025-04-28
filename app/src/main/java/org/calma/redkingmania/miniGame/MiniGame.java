package org.calma.redkingmania.miniGame;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public abstract class MiniGame {
    protected Context ctx;
    protected View view;
    protected String name;
    protected String description;

    public MiniGame(Context ctx, View view) {
        this.ctx = ctx;
        this.view = view;
    }


    public abstract void start(TextView timer);
    public abstract void win();
    public abstract void loos();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }


}
