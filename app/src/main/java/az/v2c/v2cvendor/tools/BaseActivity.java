package az.v2c.v2cvendor.tools;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For v2c-client-android project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Context context;
    private AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        appCompatActivity = this;
    }

    public Context getContext() {
        return context;
    }

    public AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }
}
