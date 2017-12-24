package az.v2c.v2cvendor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private InvoiceAdapter.OnItemClickListener mListener;
    private List<Request> mList;
    private Context mContext;

    public InvoiceAdapter(Context context, List<Request> list) {
        mList = list;
        mContext = context;
    }

    public void setOnItemClickListener(InvoiceAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public InvoiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.list_item_invoice, parent, false);

        return new InvoiceAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(InvoiceAdapter.ViewHolder viewHolder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnItemClickListener {
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.invoice_list_item_title)
        TextView title;
        @BindView(R.id.invoice_list_item_desc)
        TextView desc;
        @BindView(R.id.invoice_list_item_photo)
        ImageView photo;
        @BindView(R.id.invoice_list_item_counter_text)
        TextView counterText;
        @BindView(R.id.invoice_list_item_calendar_text)
        TextView calendarText;
        @BindView(R.id.invoice_list_item_price_text)
        TextView price;

        ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}