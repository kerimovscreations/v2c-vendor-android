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
public class WrapperLoginResponse {

    private int status;

    @SerializedName("data")
    @Expose
    private LoginResponse loginResponse;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
