package az.v2c.v2cvendor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.models.Request;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C) 2017 Kerimov's Creations.
 * <p>
 * For V2CVendor project
 * <p>
 * Contact
 * email: kerimovscreations@gmail.com
 * phone: +994 (50) 6325560
 */
public class ShipAdapter extends RecyclerView.Adapter<ShipAdapter.ViewHolder> {

    private ShipAdapter.OnItemClickListener mListener;
    private List<Request> mList;
    private Context mContext;

    public ShipAdapter(Context context, List<Request> list) {
        mList = list;
        mContext = context;
    }

    public void setOnItemClickListener(ShipAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ShipAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_item_ship_request, parent, false);

        return new ShipAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ShipAdapter.ViewHolder viewHolder, int position) {
        Request bItem = mList.get(position);

        Picasso.with(getContext()).load(bItem.getProduct().getPhoto()).into(viewHolder.photo);
        viewHolder.title.setText(bItem.getProduct().getName());
        viewHolder.desc.setText(bItem.getProduct().getDescription());
        viewHolder.counterText.setText(String.format(Locale.getDefault(),
                "%.1f x (%.1f %s)",
                bItem.getResponded_amount(),
                bItem.getProduct().getMin_quantity(),
                bItem.getProduct().getUnit().getName()));
        viewHolder.calendarText.setText(bItem.getResponded_date());
        viewHolder.price.setText(String.valueOf(bItem.getResponded_price()));

        viewHolder.nextBtn.setText(String.format(Locale.getDefault(), "Next checkpoint %d%%", bItem.getTracking_status()));
        viewHolder.progressBar.setProgress(bItem.getTracking_status());

        viewHolder.nextBtn.setVisibility(bItem.getTracking_status() == 80 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onNext(int position);

        void onComplete(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ship_list_item_title)
        TextView title;
        @BindView(R.id.ship_list_item_desc)
        TextView desc;
        @BindView(R.id.ship_list_item_photo)
        ImageView photo;
        @BindView(R.id.ship_list_item_counter_text)
        TextView counterText;
        @BindView(R.id.ship_list_item_calendar_text)
        TextView calendarText;
        @BindView(R.id.ship_list_item_price_text)
        TextView price;
        @BindView(R.id.ship_list_item_tracking_progress)
        ProgressBar progressBar;
        @BindView(R.id.ship_list_item_next_btn)
        Button nextBtn;
        @BindView(R.id.ship_list_item_done_btn)
        View completeBtn;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            nextBtn.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onNext(position);
                    }
                }
            });

            completeBtn.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onComplete(position);
                    }
                }
            });
        }
    }
}
