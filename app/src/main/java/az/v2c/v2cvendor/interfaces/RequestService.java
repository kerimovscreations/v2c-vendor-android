package az.v2c.v2cvendor.interfaces;

import az.v2c.v2cvendor.models.WrapperRequestPostBody;
import az.v2c.v2cvendor.models.WrapperRequestsResponse;
import az.v2c.v2cvendor.models.WrapperShipRequestPostBody;
import az.v2c.v2cvendor.models.WrapperSimpleResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public interface RequestService {
    @GET("vendor/requests")
    Call<WrapperRequestsResponse> getRequests(@Query("api_token") String token);

    @GET("vendor/requests")
    Call<WrapperRequestsResponse> getShippedRequests(@Query("api_token") String token, @Query("type") String type);

    @POST("vendor/respond")
    Call<WrapperSimpleResponse> postRequest(@Body WrapperRequestPostBody body);

    @POST("vendor/send")
    Call<WrapperSimpleResponse> shipRequest(@Body WrapperShipRequestPostBody body);

    @FormUrlEncoded
    @POST("vendor/checkpoint")
    Call<WrapperSimpleResponse> postCheckpoint(@Field("id") int request_id, @Field("api_token") String token);

    @FormUrlEncoded
    @POST("vendor/complete")
    Call<WrapperSimpleResponse> postComplete(@Field("id") int request_id, @Field("api_token") String token);
}

