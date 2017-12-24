package az.v2c.v2cvendor.application;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import az.v2c.v2cvendor.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For v2c-client-android project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class GlobalApplication extends Application {
    private static GlobalApplication instance;
    private static Retrofit retrofit;

    public static GlobalApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    @Override
    public void onCreate() {
        instance = this;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        initPicasso();
        super.onCreate();
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }
}
