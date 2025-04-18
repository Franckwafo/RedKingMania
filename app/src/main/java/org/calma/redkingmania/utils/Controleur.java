package org.calma.redkingmania.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.calma.redkingmania.MainMenu;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.User;
import org.calma.redkingmania.bd.AppDatabase;
import org.calma.redkingmania.bd.TokenSession;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controleur {

    public static Date convertirStringEnDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateInfo(Context ctx,String info, String elementForUpdate){
        AppDatabase db = AppDatabase.getInstance(ctx);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4w3.202330093.157-245-242-119.cprapid.com/red_king_mania/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

        Call<ResultResponse> call = null;

        switch (elementForUpdate){
            case "c":
                call = api.updateConstrus(info);
                break;
            case "i":
                call = api.updateItems(info);
                break;
            case "u":
                call = api.updateStats(info);
                break;
        }

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Récupérer le résultat de l'API

                } else {
                    Toast.makeText(ctx, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(ctx, "Erreur de communiction", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public static void VerifUser(Context ctx, String userName, String pswd){
        AppDatabase db = AppDatabase.getInstance(ctx);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4w3.202330093.157-245-242-119.cprapid.com/red_king_mania/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

        Call<ResultResponse> call = api.checkUser(userName, pswd);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Récupérer le résultat de l'API
                    boolean rslt = response.body().getResult();

                    // Afficher le résultat
                    if (rslt) {
                        Toast.makeText(ctx, response.body().getmsg(), Toast.LENGTH_SHORT).show();
                        db.tokenSessionDao().inserer(new TokenSession(response.body().getToken()));

                        User user = Factory.buldUser(response.body().getUser());
                        ArrayList<Item> items = Factory.buldItems(response.body().getItems_sans_construction());
                        ArrayList<Construction> constructions = Factory.buldConstructions(response.body().getConstructions());

                        Session.initSession(constructions, items, user);

                        Intent intent = new Intent(ctx, MainMenu.class);
                        ctx.startActivity(intent);

                    } else {
                        Toast.makeText(ctx, response.body().getmsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ctx, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(ctx, "Erreur de communiction", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static boolean VerifLastConect(Context ctx){
        AppDatabase db = AppDatabase.getInstance(ctx);
        boolean rslt = false;


        TokenSession tks = db.tokenSessionDao().getlastToken();

        if (tks!=null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://4w3.202330093.157-245-242-119.cprapid.com/red_king_mania/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

            Call<ResultResponse> call = api.checkToken(tks.getToken());

            call.enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Récupérer le résultat de l'API
                        boolean rslt = response.body().getResult();

                        // Afficher le résultat
                        if (rslt) {
                            Toast.makeText(ctx, response.body().getmsg(), Toast.LENGTH_SHORT).show();

                            User user = Factory.buldUser(response.body().getUser());
                            ArrayList<Item> items = Factory.buldItems(response.body().getItems_sans_construction());
                            ArrayList<Construction> constructions = Factory.buldConstructions(response.body().getConstructions());

                            Session.initSession(constructions, items, user);

                            Intent intent = new Intent(ctx, MainMenu.class);
                            ctx.startActivity(intent);
                        } else {
                            Toast.makeText(ctx, response.body().getmsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ctx, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Erreur de communiction", Toast.LENGTH_SHORT).show();

                }
            });
        }

        return rslt;
    }

    public static boolean DeletItemExpired(Context ctx,String idItem){
        AppDatabase db = AppDatabase.getInstance(ctx);
        boolean rslt = false;


        TokenSession tks = db.tokenSessionDao().getlastToken();

        if (tks!=null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://4w3.202330093.157-245-242-119.cprapid.com/red_king_mania/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

            Call<ResultResponse> call = api.deleteItem(idItem);

            call.enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Récupérer le résultat de l'API
                        boolean rslt = response.body().getResult();

                        // Afficher le résultat
                        if (rslt) {

                        } else {
                            Toast.makeText(ctx, response.body().getmsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ctx, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Erreur de communiction", Toast.LENGTH_SHORT).show();

                }
            });
        }

        return rslt;
    }


    public static boolean DeletConstructionExpired(Context ctx,String idConstruction){
        AppDatabase db = AppDatabase.getInstance(ctx);
        boolean rslt = false;


        TokenSession tks = db.tokenSessionDao().getlastToken();

        if (tks!=null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://4w3.202330093.157-245-242-119.cprapid.com/red_king_mania/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

            Call<ResultResponse> call = api.deleteConstruction(idConstruction);

            call.enqueue(new Callback<ResultResponse>() {
                @Override
                public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Récupérer le résultat de l'API
                        boolean rslt = response.body().getResult();

                        // Afficher le résultat
                        if (rslt) {

                        } else {
                            Toast.makeText(ctx, response.body().getmsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ctx, "Erreur de réponse", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResultResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Erreur de communiction", Toast.LENGTH_SHORT).show();

                }
            });
        }

        return rslt;
    }

    public static String getItemTypeFromSuffix(String input) {

        char lastChar = input.charAt(input.length() - 1);

        switch (lastChar) {
            case 'p':
                return "Production";
            case 'c':
                return "Auto Clicker";
            default:
                return "Inconnu";
        }
    }

    public static String getItemEffectFromSuffix(String input,Item i) {

        char lastChar = input.charAt(input.length() - 1);

        switch (lastChar) {
            case 'p':
                return "Ressource + " + i.getNbEffet();
            case 'c':
                return "Click par " + i.getNbEffet()+ " s";
            default:
                return "Inconnu";
        }
    }

    public static String getColorFromEffect(int effectValue) {
        effectValue = Math.max(1, Math.min(effectValue, 999));

        // Définir les paliers
        int[] paletteThresholds = {1, 101, 201, 301, 401, 501, 801, 999};
        int[][] paletteColors = {
                {255, 0, 0},      // Rouge
                {255, 165, 0},    // Orange
                {0, 255, 0},      // Vert
                {0, 255, 255},    // Bleu clair
                {0, 0, 255},      // Bleu foncé
                {138, 43, 226},   // Violet
                {255, 105, 180},  // Rose
                {255, 105, 180}   // Blanc ou une autre couleur finale
        };

        for (int i = 0; i < paletteThresholds.length - 1; i++) {
            int min = paletteThresholds[i];
            int max = paletteThresholds[i + 1];

            if (effectValue >= min && effectValue <= max) {
                float ratio = (effectValue - min) / (float) (max - min);

                int[] start = paletteColors[i];
                int[] end = paletteColors[i + 1];

                int r = (int) (start[0] + (end[0] - start[0]) * ratio);
                int g = (int) (start[1] + (end[1] - start[1]) * ratio);
                int b = (int) (start[2] + (end[2] - start[2]) * ratio);

                return String.format("#%02X%02X%02X", r, g, b);
            }
        }

        // Valeur par défaut (devrait pas arriver)
        return "#FFFFFF";
    }



}
