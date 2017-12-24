package az.v2c.v2cvendor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class WrapperStockResponse {
    private int status;

    @SerializedName("data")
    @Expose
    private WrapperInnerStockResponse data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WrapperInnerStockResponse getData() {
        return data;
    }

    public void setData(WrapperInnerStockResponse data) {
        this.data = data;
    }
}
