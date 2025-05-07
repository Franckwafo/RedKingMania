package org.calma.redkingmania.utils;

import android.content.Context;
import android.media.MediaPlayer;

import org.calma.redkingmania.R;

public class Song {
    public static void clickSong(Context ctx){
        MediaPlayer song =  MediaPlayer.create(ctx, R.raw.pop);
        song.start();
    }

    public static void explodSong(Context ctx){
        MediaPlayer song =  MediaPlayer.create(ctx, R.raw.salutation);
        song.start();
    }
}
