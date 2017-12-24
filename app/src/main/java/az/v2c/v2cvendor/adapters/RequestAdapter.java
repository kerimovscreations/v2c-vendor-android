package az.v2c.v2cvendor.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.models.Request;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private RequestAdapter.OnItemClickListener mListener;
    private List<Request> mList;
    private Context mContext;

    public RequestAdapter(Context context, List<Request> list) {
        mList = list;
        mContext = context;
    }

    public void setOnItemClickListener(RequestAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_item_requests, parent, false);

        return new RequestAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(RequestAdapter.ViewHolder viewHolder, int position) {
        Request bItem = mList.get(position);

        if (bItem.getResponse_date() == null) {
            viewHolder.indicator.setText("Pending");
            viewHolder.indicatorLayout.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.request_indicator_pending_bg, null));

            viewHolder.counterText.setText(String.format(Locale.getDefault(),
                    "%.1f x (%.1f %s)",
                    bItem.getRequested_amount(),
                    bItem.getProduct().getMin_quantity(),
                    bItem.getProduct().getUnit().getName()));
            viewHolder.calendarText.setText(bItem.getRequest_date());
            viewHolder.price.setText(String.valueOf(bItem.getRequested_price()));

            viewHolder.sendBtn.setVisibility(View.VISIBLE);
            viewHolder.shipBtn.setVisibility(View.GONE);
            viewHolder.price.setEnabled(true);
        } else {
            if (bItem.getIs_agreed() == 1) {
                viewHolder.indicator.setText("Agreed");
                viewHolder.indicatorLayout.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.request_indicator_accepted_bg, null));

                viewHolder.sendBtn.setVisibility(View.GONE);
                viewHolder.shipBtn.setVisibility(View.VISIBLE);
            } else if (bItem.getIs_agreed() == 0) {
                viewHolder.indicator.setText("Not decided");
                viewHolder.indicatorLayout.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.request_indicator_not_responded_bg, null));

                viewHolder.sendBtn.setVisibility(View.GONE);
                viewHolder.shipBtn.setVisibility(View.GONE);
            } else {
                viewHolder.indicator.setText("Not agreed");
                viewHolder.indicatorLayout.setBackground(ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.request_indicator_rejected_bg, null));

                viewHolder.sendBtn.setVisibility(View.GONE);
                viewHolder.shipBtn.setVisibility(View.GONE);
            }

            viewHolder.counterText.setText(String.format(Locale.getDefault(),
                    "%.1f x (%.1f %s)",
                    bItem.getRequested_amount(),
                    bItem.getProduct().getMin_quantity(),
                    bItem.getProduct().getUnit().getName()));
            viewHolder.calendarText.setText(bItem.getRequest_date());
            viewHolder.price.setText(String.valueOf(bItem.getResponded_price()));
            viewHolder.price.setEnabled(false);
        }

        Picasso.with(getContext()).load(bItem.getProduct().getPhoto()).into(viewHolder.photo);
        viewHolder.title.setText(bItem.getProduct().getName());
        viewHolder.desc.setText(bItem.getProduct().getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onSend(int position);

        void onShip(int position);

        void onUpdatePrice(int position, String price);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.request_list_item_indicator_layout)
        View indicatorLayout;
        @BindView(R.id.request_list_item_indicator)
        TextView indicator;
        @BindView(R.id.request_list_item_title)
        TextView title;
        @BindView(R.id.request_list_item_desc)
        TextView desc;
        @BindView(R.id.request_list_item_photo)
        ImageView photo;
        @BindView(R.id.request_list_item_counter_text)
        TextView counterText;
        @BindView(R.id.request_list_item_calendar_text)
        TextView calendarText;
        @BindView(R.id.request_list_item_price_text)
        EditText price;
        @BindView(R.id.request_list_item_send_btn)
        View sendBtn;
        @BindView(R.id.request_list_item_ship_btn)
        View shipBtn;

        private PublishSubject<String> mSubject;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            sendBtn.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onSend(position);
                    }
                }
            });

            shipBtn.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onShip(position);
                    }
                }
            });

            mSubject = PublishSubject.create();
            mSubject.debounce(300, TimeUnit.MILLISECONDS)
                    .onBackpressureLatest()
                    .subscribe(s -> {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onUpdatePrice(position, s);
                            }
                        }
                    });

            price.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mSubject.onNext(editable.toString());
                }
            });
        }
    }
}
