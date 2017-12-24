package az.v2c.v2cvendor.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.adapters.StockAdapter;
import az.v2c.v2cvendor.application.GlobalApplication;
import az.v2c.v2cvendor.interfaces.StockService;
import az.v2c.v2cvendor.models.Stock;
import az.v2c.v2cvendor.models.WrapperStockResponse;
import az.v2c.v2cvendor.tools.Auth;
import az.v2c.v2cvendor.tools.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StockFragment extends BaseFragment {

    private final String TAG = getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;
    private View mView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rvStock)
    RecyclerView mRecyclerView;
    @BindView(R.id.stock_vendor_photo)
    CircleImageView mVendorPhoto;
    @BindView(R.id.stock_vendor_name)
    TextView mVendorName;

    private ArrayList<Stock> mList;
    private StockAdapter mAdapter;

    private StockService mService;
    private Call<WrapperStockResponse> mCall;

    public StockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_stock, container, false);
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

        mAdapter = new StockAdapter(getContext(), mList);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);

        mService = GlobalApplication.getRetrofit().create(StockService.class);
    }

    /**
     * Http
     */

    void getData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mCall = mService.getStock(Auth.getToken(getContext()));
        mCall.enqueue(new Callback<WrapperStockResponse>() {
            @Override
            public void onResponse(Call<WrapperStockResponse> call, Response<WrapperStockResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    switch (response.body().getStatus()) {
                        case 200:
                            mList.clear();
                            mList.addAll(response.body().getData().getStock());
                            mAdapter.notifyDataSetChanged();

                            Picasso.with(getContext()).load(response.body().getData().getVendor().getPhoto()).resize(100, 100).into(mVendorPhoto);
                            mVendorName.setText(response.body().getData().getVendor().getName());
                            break;
                    }
                } else {
                    // Request Failed
                    Log.e(TAG, "onResponse: Request Failed");
                }
            }

            @Override
            public void onFailure(Call<WrapperStockResponse> call, Throwable t) {
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

    /**
     * Interface
     */

    public interface OnFragmentInteractionListener {
    }
}
