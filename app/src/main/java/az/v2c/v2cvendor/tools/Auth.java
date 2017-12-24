package az.v2c.v2cvendor.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.login.LoginManager;

import az.v2c.v2cvendor.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For v2c-client-android project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class Auth {

    public static boolean isLogged(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(context.getResources().getString(R.string.local_preference), Context.MODE_PRIVATE);

        return mPrefs.contains(context.getString(R.string.local_preference_token));
    }

    public static String getToken(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(context.getResources().getString(R.string.local_preference), Context.MODE_PRIVATE);

        return mPrefs.getString(context.getString(R.string.local_preference_token), "");
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences mPrefs = context.getSharedPreferences(context.getResources().getString(R.string.local_preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(context.getString(R.string.local_preference_token), token);
        prefsEditor.apply();
    }

    public static void removeToken(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences(context.getResources().getString(R.string.local_preference), Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.remove(context.getString(R.string.local_preference_token));
        prefsEditor.apply();
    }

    public static void logout(Context context) {
        LoginManager.getInstance().logOut();
        removeToken(context);

        Realm.init(context);
        Realm realm;
        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception e) {
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
        }

        realm.executeTransaction(realm1 -> realm1.deleteAll());
    }
}
