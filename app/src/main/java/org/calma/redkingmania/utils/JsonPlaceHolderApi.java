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
}
