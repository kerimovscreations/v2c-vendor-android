package az.v2c.v2cvendor.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.tools.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.logout_btn)
    View mLogoutBtn;

    private View mView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Delegates
     */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, mView);
        initVars();

        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Initialization
     */

    void initVars() {

    }

    /**
     * Click handlers
     */

    @OnClick(R.id.logout_btn)
    public void onLogout(View view) {
        if (mListener != null)
            mListener.onLogout();
    }

    /**
     * Interface
     */

    public interface OnFragmentInteractionListener {
        void onLogout();
    }
}
