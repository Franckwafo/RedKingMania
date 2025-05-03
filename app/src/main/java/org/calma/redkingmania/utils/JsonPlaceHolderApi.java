package org.calma.redkingmania.utils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST("verif_log.php")
    Call<ResultResponse> checkUser(
            @Field("id") String param1,
            @Field("pswd") String param2
    );

    @FormUrlEncoded
    @POST("verif_token.php")
    Call<ResultResponse> checkToken(
            @Field("token") String param1
    );

    @FormUrlEncoded
    @POST("updateInfos.php")
    Call<ResultResponse> updateStats(
            @Field("stats") String param1
    );

    @FormUrlEncoded
    @POST("updateInfos.php")
    Call<ResultResponse> updateItems(
            @Field("items") String param1
    );

    @FormUrlEncoded
    @POST("updateInfos.php")
    Call<ResultResponse> updateConstrus(
            @Field("constructions") String param1
    );

    @FormUrlEncoded
    @POST("deleteItem.php")
    Call<ResultResponse> deleteItem(
            @Field("idPropriete") String param1
    );

    @FormUrlEncoded
    @POST("deleteConstruction.php")
    Call<ResultResponse> deleteConstruction(
            @Field("idPropriete") String param1
    );

    @POST("get_shop_data.php")
    Call<ResultResponse> getShopData(); // pas de champ, donc POST simple

    @FormUrlEncoded
    @POST("shop_item.php")
    Call<ResultResponse> addShopItem(
            @Field("idUser") String idUser,
            @Field("idItem") String idItem,
            @Field("datePeremption") String datePeremption
    );

    @FormUrlEncoded
    @POST("shop_construction.php")
    Call<ResultResponse> addShopConstruction(
            @Field("idUser") String idUser,
            @Field("idConstruction") String idConstruction,
            @Field("datePeremption") String datePeremption
    );

    @FormUrlEncoded
    @POST("qr_process.php")
    Call<ResultResponse> utiliserQr(
            @Field("id_qr") String idQr,
            @Field("id_user") String idUser
    );

    @FormUrlEncoded
    @POST("add_user.php")
    Call<ResultResponse> inscrireUtilisateur(
            @Field("username") String username,
            @Field("pseudo") String pseudo,
            @Field("pswd") String pswd,
            @Field("sexe") String sexe
    );
}
