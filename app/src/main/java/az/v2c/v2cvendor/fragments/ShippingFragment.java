package az.v2c.v2cvendor.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.adapters.RequestAdapter;
import az.v2c.v2cvendor.adapters.ShipAdapter;
import az.v2c.v2cvendor.application.GlobalApplication;
import az.v2c.v2cvendor.interfaces.RequestService;
import az.v2c.v2cvendor.models.Request;
import az.v2c.v2cvendor.models.WrapperRequestPostBody;
import az.v2c.v2cvendor.models.WrapperRequestsResponse;
import az.v2c.v2cvendor.models.WrapperShipRequestPostBody;
import az.v2c.v2cvendor.models.WrapperSimpleResponse;
import az.v2c.v2cvendor.tools.Auth;
import az.v2c.v2cvendor.tools.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;
    private View mView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rvShipping)
    RecyclerView mRecyclerView;

    private ArrayList<Request> mList;
    private ShipAdapter mAdapter;

    private RequestService mService;
    private Call<WrapperRequestsResponse> mCall;
    private Call<WrapperSimpleResponse> mCheckpointCall;
    private Call<WrapperSimpleResponse> mCompleteCall;

    public ShippingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shipping, container, false);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
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
        mSwipeRefreshLayout.setOnRefreshListener(this::getData);

        mList = new ArrayList<>();

        mAdapter = new ShipAdapter(getContext(), mList);
        mAdapter.setOnItemClickListener(new ShipAdapter.OnItemClickListener() {
            @Override
            public void onNext(int position) {
                onPostCheckpoint(mList.get(position));
            }

            @Override
            public void onComplete(int position) {
                onPostComplete(mList.get(position));
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mService = GlobalApplication.getRetrofit().create(RequestService.class);
    }

    /**
     * Http
     */

    void getData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mCall = mService.getShippedRequests(Auth.getToken(getContext()), "shipped");
        mCall.enqueue(new Callback<WrapperRequestsResponse>() {
            @Override
            public void onResponse(Call<WrapperRequestsResponse> call, Response<WrapperRequestsResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    switch (response.body().getStatus()) {
                        case 200:
                            mList.clear();
                            mList.addAll(response.body().getRequests());
                            mAdapter.notifyDataSetChanged();
                            break;
                    }
                } else {
                    // Request Failed
                    Log.e(TAG, "onResponse: Request Failed");
                }
            }

            @Override
            public void onFailure(Call<WrapperRequestsResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
                Crashlytics.log(t.getMessage());
                // Request Failed
                if (!mCall.isCanceled()) {
                    Log.e(TAG, "onFailure: Request Failed");
                }
            }
        });
    }

    private void onPostCheckpoint(Request request) {

        mCheckpointCall = mService.postCheckpoint(request.getId(), Auth.getToken(getContext()));
        mCheckpointCall.enqueue(new Callback<WrapperSimpleResponse>() {
            @Override
            public void onResponse(Call<WrapperSimpleResponse> call, Response<WrapperSimpleResponse> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getStatus()) {
                        case 200:
                            getData();
                            break;
                        default:
                            Log.d(TAG, "Error occurred");
                    }
                } else {
                    // Request Failed
                    try {
                        Log.d(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, response.raw().toString());
                    Log.e(TAG, "onResponse: Request Failed");
                }
            }

            @Override
            public void onFailure(Call<WrapperSimpleResponse> call, Throwable t) {
                t.printStackTrace();
                Crashlytics.log(t.getMessage());
                // Request Failed
                if (!mCheckpointCall.isCanceled()) {
                    Log.e(TAG, "onFailure: Request Failed");
                }
            }
        });
    }

    private void onPostComplete(Request request) {
        mCompleteCall = mService.postComplete(request.getId(), Auth.getToken(getContext()));
        mCompleteCall.enqueue(new Callback<WrapperSimpleResponse>() {
            @Override
            public void onResponse(Call<WrapperSimpleResponse> call, Response<WrapperSimpleResponse> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getStatus()) {
                        case 200:
                            getData();
                            break;
                        default:
                            Log.d(TAG, "Error occurred");
                    }
                } else {
                    // Request Failed
                    try {
                        Log.d(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, response.raw().toString());
                    Log.e(TAG, "onResponse: Request Failed");
                }
            }

            @Override
            public void onFailure(Call<WrapperSimpleResponse> call, Throwable t) {
                t.printStackTrace();
                Crashlytics.log(t.getMessage());
                // Request Failed
                if (!mCompleteCall.isCanceled()) {
                    Log.e(TAG, "onFailure: Request Failed");
                }
            }
        });
    }

    /**
     * Interface
     */

    public interface OnFragmentInteractionListener {
    }
}
