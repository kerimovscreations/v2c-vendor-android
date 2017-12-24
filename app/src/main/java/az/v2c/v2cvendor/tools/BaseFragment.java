package az.v2c.v2cvendor.tools;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class BaseFragment extends Fragment {

    @Override
    public void startActivity(Intent intent) {
        if (getActivity() != null)
            getActivity().startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (getActivity() != null)
            getActivity().startActivityForResult(intent, requestCode);
    }

    @Nullable
    @Override
    public Context getContext() {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            return ((BaseActivity) getActivity()).getContext();
        else
            return super.getContext();
    }

    public AppCompatActivity getAppCompatActivity() {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            return ((BaseActivity) getActivity()).getAppCompatActivity();
        else
            return (AppCompatActivity) getContext();
    }
}
