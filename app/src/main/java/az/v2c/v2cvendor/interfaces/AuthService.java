package az.v2c.v2cvendor.interfaces;

import az.v2c.v2cvendor.models.WrapperLoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public interface AuthService {
    @FormUrlEncoded
    @POST("vendor/login")
    Call<WrapperLoginResponse> emailLogin(@Field("email") String email, @Field("password") String pass);

    @FormUrlEncoded
    @POST("vendor/login")
    Call<WrapperLoginResponse> fbLogin(@Field("facebook_access_token") String facebook_id);
}
