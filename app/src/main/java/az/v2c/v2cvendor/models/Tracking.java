package az.v2c.v2cvendor.models;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For v2c-client-android project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class Tracking {
    private int id;
    private long latitude, longitude;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
