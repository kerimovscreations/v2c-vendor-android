package az.v2c.v2cvendor.models;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class Request {
    private int id;
    private String request_date, response_date, complete_date;
    private int is_completed, is_agreed;
    private Customer customer;
    private Vendor vendor;
    private Product product;
    private Tracking tracking;
    private float requested_amount, responded_amount, requested_price, responded_price;
    private String requested_date, responded_date;
    private int tracking_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getResponse_date() {
        return response_date;
    }

    public void setResponse_date(String response_date) {
        this.response_date = response_date;
    }

    public String getComplete_date() {
        return complete_date;
    }

    public void setComplete_date(String complete_date) {
        this.complete_date = complete_date;
    }

    public int getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(int is_completed) {
        this.is_completed = is_completed;
    }

    public int getIs_agreed() {
        return is_agreed;
    }

    public void setIs_agreed(int is_agreed) {
        this.is_agreed = is_agreed;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Tracking getTracking() {
        return tracking;
    }

    public void setTracking(Tracking tracking) {
        this.tracking = tracking;
    }

    public float getRequested_amount() {
        return requested_amount;
    }

    public void setRequested_amount(float requested_amount) {
        this.requested_amount = requested_amount;
    }

    public float getResponded_amount() {
        return responded_amount;
    }

    public void setResponded_amount(float responded_amount) {
        this.responded_amount = responded_amount;
    }

    public float getRequested_price() {
        return requested_price;
    }

    public void setRequested_price(float requested_price) {
        this.requested_price = requested_price;
    }

    public float getResponded_price() {
        return responded_price;
    }

    public void setResponded_price(float responded_price) {
        this.responded_price = responded_price;
    }

    public String getRequested_date() {
        return requested_date;
    }

    public void setRequested_date(String requested_date) {
        this.requested_date = requested_date;
    }

    public String getResponded_date() {
        return responded_date;
    }

    public void setResponded_date(String responded_date) {
        this.responded_date = responded_date;
    }

    public int getTracking_status() {
        return tracking_status;
    }

    public void setTracking_status(int tracking_status) {
        this.tracking_status = tracking_status;
    }
}
