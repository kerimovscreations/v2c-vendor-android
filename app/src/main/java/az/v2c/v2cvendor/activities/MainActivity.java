package az.v2c.v2cvendor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.crashlytics.android.Crashlytics;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.fragments.InvoiceFragment;
import az.v2c.v2cvendor.fragments.ProfileFragment;
import az.v2c.v2cvendor.fragments.RequestsFragment;
import az.v2c.v2cvendor.fragments.ShippingFragment;
import az.v2c.v2cvendor.fragments.StockFragment;
import az.v2c.v2cvendor.tools.Auth;
import az.v2c.v2cvendor.tools.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity implements StockFragment.OnFragmentInteractionListener, RequestsFragment.OnFragmentInteractionListener,
        ShippingFragment.OnFragmentInteractionListener, InvoiceFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private FragmentManager fm;
    private FragmentTransaction ft;

    private StockFragment mStockFragment;
    private RequestsFragment mRequestsFragment;
    private ShippingFragment mShippingFragment;
    private InvoiceFragment mInvoiceFragment;
    private ProfileFragment mProfileFragment;

    /**
     * Bottom navigation setup
     */

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        String tag;

        fm = getSupportFragmentManager();

        ft = fm.beginTransaction();

        for (int i = 0; i < fm.getFragments().size(); i++) {
            Fragment f = fm.getFragments().get(i);
            ft.hide(f);
        }

        boolean result = false;

        switch (item.getItemId()) {
            case R.id.navigation_stock:
                tag = getString(R.string.action_stock);

                if (mStockFragment != null)
                    ft.show(mStockFragment);
                else {
                    mStockFragment = new StockFragment();
                    ft.add(R.id.main_fragment_content, mStockFragment, tag);
                }
                result = true;
                break;
            case R.id.navigation_requests:
                tag = getString(R.string.action_requests);

                if (mRequestsFragment != null)
                    ft.show(mRequestsFragment);
                else {
                    mRequestsFragment = new RequestsFragment();
                    ft.add(R.id.main_fragment_content, mRequestsFragment, tag);
                }
                result = true;
                break;
            case R.id.navigation_shipping:
                tag = getString(R.string.action_shipping);

                if (mShippingFragment != null)
                    ft.show(mShippingFragment);
                else {
                    mShippingFragment = new ShippingFragment();
                    ft.add(R.id.main_fragment_content, mShippingFragment, tag);
                }
                result = true;
                break;
            case R.id.navigation_invoice:
                tag = getString(R.string.action_invoice);

                if (mInvoiceFragment != null)
                    ft.show(mInvoiceFragment);
                else {
                    mInvoiceFragment = new InvoiceFragment();
                    ft.add(R.id.main_fragment_content, mInvoiceFragment, tag);
                }
                result = true;
                break;
            case R.id.navigation_profile:
                tag = getString(R.string.action_profile);

                if (mProfileFragment != null)
                    ft.show(mProfileFragment);
                else {
                    mProfileFragment = new ProfileFragment();
                    ft.add(R.id.main_fragment_content, mProfileFragment, tag);
                }
                result = true;
                break;
        }

        if (result)
            ft.commitAllowingStateLoss();

        return result;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);

        if (Auth.isLogged(getContext()))
            initVars();
        else {
            finish();
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        if (!mStockFragment.isVisible()) {
            fm = getSupportFragmentManager();

            ft = fm.beginTransaction();
            for (int i = 0; i < fm.getFragments().size(); i++) {
                Fragment f = fm.getFragments().get(i);
                ft.hide(f);
            }

            ft.show(mStockFragment);
            ft.commit();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Initialization
     */

    void initVars() {
        setInitialFragment();

        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    void setInitialFragment() {
        String tag;

        fm = getSupportFragmentManager();

        ft = fm.beginTransaction();

        for (int i = 0; i < fm.getFragments().size(); i++) {
            Fragment f = fm.getFragments().get(i);
            ft.detach(f);
        }

        mStockFragment = null;
        mRequestsFragment = null;
        mShippingFragment = null;
        mInvoiceFragment = null;
        mProfileFragment = null;

        tag = getString(R.string.action_stock);

        mStockFragment = new StockFragment();
        ft.add(R.id.main_fragment_content, mStockFragment, tag);

        ft.commit();
    }

    /**
     * Interface
     */

    @Override
    public void onLogout() {
        Auth.logout(getContext());
        finish();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
