package org.calma.redkingmania.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.calma.redkingmania.MainMenu;
import org.calma.redkingmania.R;
import org.calma.redkingmania.Session;
import org.calma.redkingmania.User;
import org.calma.redkingmania.bd.AppDatabase;
import org.calma.redkingmania.bd.TokenSession;
import org.calma.redkingmania.construction.Construction;
import org.calma.redkingmania.item.Item;
import org.calma.redkingmania.modal.Modal_shop;
import org.calma.redkingmania.shop.Article_construction;
import org.calma.redkingmania.shop.Article_item;
import org.calma.redkingmania.shop.Shop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
                .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
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
                .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
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
                    .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
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
                    .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
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
                    .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
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


    public static void initBoutique(Context ctx) {
        AppDatabase db = AppDatabase.getInstance(ctx);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

        Call<ResultResponse> call = api.getShopData();

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResultResponse result = response.body();

                    if (result.getResult()) {
                        ArrayList<Article_item> items = Factory.buildShopItems(response.body().getShop_items());
                        ArrayList<Article_construction> constructions = Factory.buildShopConstructions(response.body().getShop_constructions());

                        Session.getSession().setShop(new Shop(items,constructions));


                        Session.getSession().setModal_shop(new Modal_shop(ctx));

                        ImageView img_shop = ((Activity) ctx).findViewById(R.id.img_shop);
                        img_shop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Session.getSession().getModal_shop().show_shop();
                            }
                        });

                        Toast.makeText(ctx, "Boutique chargée !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ctx, result.getmsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ctx, "Erreur de réponse de la boutique", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(ctx, "Erreur de communication avec la boutique", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void shopItem(Context ctx,Article_item articleItem, String datePeremption) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);



        Call<ResultResponse> call = api.addShopItem(Session.getSession().getUser().getUsername(),articleItem.getId(),datePeremption);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResultResponse result = response.body();

                    if (result.getResult()) {
                        //
                        Item i = Factory.buldItem(articleItem,result.getIdPropriete(),datePeremption);
                        Session.getSession().itemsAdd(i);

                        //
                    } else {
                        Toast.makeText(ctx, result.getmsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ctx, "Erreur de réponse de la boutique", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(ctx, "Erreur de communication avec la boutique", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void shopConstruction(Context ctx, Article_construction construction, String datePeremption) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://4w3.202330093.v2.157-245-242-119.cprapid.com/red_king_mania/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi api = retrofit.create(JsonPlaceHolderApi.class);

        // Appel à l'API pour ajouter une construction
        Call<ResultResponse> call = api.addShopConstruction(Session.getSession().getUser().getUsername(), construction.getId(), datePeremption);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResultResponse result = response.body();

                    if (result.getResult()) {
                        // Si la réponse est réussie, crée l'objet Construction et l'ajoute à la session
                        Construction c = Factory.buildConstruction(construction, result.getIdPropriete(), datePeremption);
                        Session.getSession().constructionsAdd(c);

                        // Si nécessaire, affiche un message de succès ou effectue d'autres actions
                    } else {
                        Toast.makeText(ctx, result.getmsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ctx, "Erreur de réponse de la boutique", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Toast.makeText(ctx, "Erreur de communication avec la boutique", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static String  getConstructionTypeFromSuffix(String input) {

        switch (input) {
            case "e":
                return "Erable";
            case "c":
                return "Cristale";
            case "b":
                return "Bois";
            default:
                return "Inconnu";
        }
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

    public static String getArticleEffectFromSuffix(Article_item a) {

        char lastChar = a.getType().charAt(a.getType().length() - 1);

        switch (lastChar) {
            case 'p':
                return "Ressource + " + a.getNbProduction();
            case 'c':
                return "Click par " + a.getNbProduction()+ " s";
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

    public static String getTimeLeft(Date expirationDate) {
        Date now = new Date();
        long diffInMillis = expirationDate.getTime() - now.getTime();

        if (diffInMillis <= 0) {
            return "Expiré";
        }

        long days = TimeUnit.MILLISECONDS.toDays(diffInMillis);
        diffInMillis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
        diffInMillis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        diffInMillis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

        return String.format("%d j, %02d h, %02d min, %02d s", days, hours, minutes, seconds);
    }

    public static int calculerPrix(int effect, char type) {
        Random random = new Random();
        int aleatoire = random.nextInt(10) + 5; // entre 1 et 10

        // Récupère l'heure, minute et seconde
        Calendar calendar = Calendar.getInstance();
        int heure = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seconde = calendar.get(Calendar.SECOND);
        int totalTemps = heure + minute + seconde;

        double base;

        if (type == 'c') {
            // Pour les autoclickers : plus effect est petit, mieux c'est
            base = Math.pow((120 / (effect + 1)), 4);
        } else if (type == 'p') {
            // Pour les producteurs : plus effect est grand, mieux c'est
            base = Math.pow(effect, 3) * 0.1;
        }else if (type == 'b') {
            // Pour les producteurs : plus effect est grand, mieux c'est
            base = Math.pow(effect, 4) * 0.1;
        }else {
            // Type inconnu
            return 100;
        }

        // Prix final : base * (aléatoire + temps)
        double prix = base * (((aleatoire * totalTemps)*(aleatoire * totalTemps)) / 50.0);

        return (int) prix;
    }


    public static String formaterPrix(long prix) {
        if (prix < 100_000) {
            return String.valueOf(prix);
        }

        String[] suffixes = {"", "K", "M", "B", "T"};
        int suffixIndex = 0;
        double montant = prix;

        while (montant >= 1000 && suffixIndex < suffixes.length - 1) {
            montant /= 1000.0;
            suffixIndex++;
        }

        // Formater pour avoir au maximum 2 chiffres après la virgule
        String montantFormate = String.format("%.2f", montant);

        // Supprimer ".00" si c'est un chiffre rond
        if (montantFormate.endsWith(".00")) {
            montantFormate = montantFormate.substring(0, montantFormate.length() - 3);
        }

        return montantFormate + suffixes[suffixIndex];
    }

    public static Date genererDateExpiration() {
        Random random = new Random();

        // Récupérer l'heure actuelle
        Calendar now = Calendar.getInstance();
        int heure = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int seconde = now.get(Calendar.SECOND);

        // Temps actuel pour influencer le random
        int influence = (heure * 3600) + (minute * 60) + seconde; // en secondes

        // Ajouter de la variabilité
        int randomHours = 5 + random.nextInt(44); // 5h à 48h (2 jours = 48h, 48 - 5 = 43 + 1)

        // Influence additionnelle basée sur l'heure actuelle
        randomHours += (influence % 5); // Influence douce pour éviter de trop casser le random de base

        // Empêcher de dépasser 48 heures (2 jours)
        if (randomHours > 48) {
            randomHours = 48;
        }

        // Créer la date d'expiration
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.HOUR_OF_DAY, randomHours);

        return expiration.getTime();
    }

}
