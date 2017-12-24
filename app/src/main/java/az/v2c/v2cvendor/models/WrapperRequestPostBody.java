package az.v2c.v2cvendor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class WrapperRequestPostBody {
    @SerializedName("api_token")
    @Expose
    private String token;

    @SerializedName("responses")
    @Expose
    private
    List<Map<String, String>> requests;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Map<String, String>> getRequests() {
        return requests;
    }

    public void setRequests(List<Map<String, String>> requests) {
        this.requests = requests;
    }
}
