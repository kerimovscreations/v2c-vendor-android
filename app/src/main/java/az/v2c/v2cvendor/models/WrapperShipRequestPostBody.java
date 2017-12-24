package az.v2c.v2cvendor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class WrapperShipRequestPostBody {
    @SerializedName("api_token")
    @Expose
    private String token;

    @SerializedName("id")
    @Expose
    private int request_id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
