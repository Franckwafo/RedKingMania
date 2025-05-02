package org.calma.redkingmania.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("verif_log.php")
    Call<ResultResponse> checkUser(
            @Query("id") String param1,
            @Query("pswd") String param2
    );

    @GET("verif_token.php")
    Call<ResultResponse> checkToken(
            @Query("token") String param1
    );

    @GET("updateInfos.php")
    Call<ResultResponse> updateStats(
            @Query("stats") String param1
    );

    @GET("updateInfos.php")
    Call<ResultResponse> updateItems(
            @Query("items") String param1
    );

    @GET("updateInfos.php")
    Call<ResultResponse> updateConstrus(
            @Query("constructions") String param1
    );

    @GET("deleteItem.php")
    Call<ResultResponse> deleteItem(
            @Query("idPropriete") String param1
    );

    @GET("deleteConstruction.php")
    Call<ResultResponse> deleteConstruction(
            @Query("idPropriete") String param1
    );

    @GET("get_shop_data.php")
    Call<ResultResponse> getShopData();

    @GET("shop_item.php")
    Call<ResultResponse> addShopItem(
            @Query("idUser") String idUser,
            @Query("idItem") String idItem,
            @Query("datePeremption") String datePeremption
    );

    @GET("shop_construction.php")
    Call<ResultResponse> addShopConstruction(
            @Query("idUser") String idUser,
            @Query("idConstruction") String idConstruction,
            @Query("datePeremption") String datePeremption
    );

    @GET("qr_process.php")
    Call<ResultResponse> utiliserQr(
            @Query("id_qr") String idQr,
            @Query("id_user") String idUser
    );

}
