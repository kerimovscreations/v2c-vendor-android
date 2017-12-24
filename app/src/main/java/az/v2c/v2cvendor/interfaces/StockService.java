package az.v2c.v2cvendor.interfaces;

import az.v2c.v2cvendor.models.WrapperStockResponse;
import retrofit2.Call;
import retrofit2.http.GET;
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
public interface StockService {
    @GET("vendor/stock")
    Call<WrapperStockResponse> getStock(@Query("api_token") String token);
}
