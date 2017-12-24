package az.v2c.v2cvendor.models;

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
public class WrapperInnerStockResponse {

    private Vendor vendor;

    private ArrayList<Stock> stock;

    public ArrayList<Stock> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Stock> stock) {
        this.stock = stock;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
