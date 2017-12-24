package az.v2c.v2cvendor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import az.v2c.v2cvendor.R;
import az.v2c.v2cvendor.models.Stock;
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
public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private List<Stock> mList;
    private Context mContext;

    public StockAdapter(Context context, List<Stock> list) {
        mList = list;
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_item_stock, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Stock bItem = mList.get(position);

        viewHolder.title.setText(bItem.getName());
        Picasso.with(getContext()).load(bItem.getPhoto()).into(viewHolder.photo);
        viewHolder.subtitle.setText(String.format(Locale.getDefault(), "Min: %.1f %s", bItem.getMin_quantity(), bItem.getUnit().getName()));
        viewHolder.stock.setText(String.format(Locale.getDefault(), "%.1f", bItem.getPivot().getAmount()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onAddStock(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.stock_list_item_image)
        RoundedImageView photo;
        @BindView(R.id.stock_list_item_title)
        TextView title;
        @BindView(R.id.stock_list_item_subtitle)
        TextView subtitle;
        @BindView(R.id.stock_list_item_stock)
        TextView stock;
        @BindView(R.id.stock_list_item_add_card_layout)
        View addCardLayout;
        @BindView(R.id.stock_list_item_layout)
        View layout;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            layout.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            });

            addCardLayout.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onAddStock(position);
                    }
                }
            });
        }
    }
}
