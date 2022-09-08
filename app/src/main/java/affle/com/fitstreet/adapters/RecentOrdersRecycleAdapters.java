package affle.com.fitstreet.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import affle.com.fitstreet.R;
import affle.com.fitstreet.customviews.CustomTypefaceButton;
import affle.com.fitstreet.customviews.CustomTypefaceTextView;
import affle.com.fitstreet.customviews.SerializableArrayList;
import affle.com.fitstreet.interfaces.IOnItemClickListener;
import affle.com.fitstreet.models.response.ResMyOrders;
import affle.com.fitstreet.network.ServiceConstants;
import affle.com.fitstreet.ui.activities.MyOrdersViewActivity;
import affle.com.fitstreet.ui.activities.OrderDetailsActivity;
import affle.com.fitstreet.utils.AppConstants;
import butterknife.BindView;

/**
 * Created by akash on 2/8/16.
 */
public class RecentOrdersRecycleAdapters extends RecyclerView.Adapter<RecentOrdersRecycleAdapters.ViewHolder> implements View.OnClickListener {
    Activity activity;
    List<ResMyOrders.OrderDatum> mRecentOrderList = new ArrayList<>();
    LayoutInflater layoutInflater;
    private static IOnItemClickListener iOnItemClickListener;

    public static void setiOnItemClickListener(IOnItemClickListener iOnItemClickListener) {
        RecentOrdersRecycleAdapters.iOnItemClickListener = iOnItemClickListener;
    }

    public RecentOrdersRecycleAdapters(Activity activity, List<ResMyOrders.OrderDatum> list) {
        this.activity = activity;
        mRecentOrderList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_orders, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.llProductsImages.removeAllViews();
        holder.llProductDetailContainer.removeAllViews();
        holder.tvOrderNumber.setText("ORDER NUMBER : " + mRecentOrderList.get(position).getOrderID());
        holder.llBottomButton.setVisibility(View.VISIBLE);

        if (mRecentOrderList.get(position).getProducts() != null) {
            for (int i = 0; i < mRecentOrderList.get(position).getProducts().size(); i++) {
                ImageView imageView = new ImageView(activity);
                RelativeLayout relativeLayout = new RelativeLayout(activity);
                // relativeLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                relativeLayout.setLayoutParams(param);
                relativeLayout.addView(imageView);
                View view = new View(activity);
                view.setLayoutParams(params);
                view.setBackgroundColor(activity.getResources().getColor(R.color.c_black_overlay));
                CustomTypefaceTextView textView = new CustomTypefaceTextView(activity);
                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);
                textView.setText(activity.getResources().getText(R.string.order_cancelled));
                textView.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                textView.setTextColor(activity.getResources().getColor(R.color.c_white));
                relativeLayout.addView(view);
                relativeLayout.addView(textView);
                Glide.with(activity)
                        .load(mRecentOrderList.get(position).getProducts().get(i).getImage())
                        .placeholder(R.drawable.no_image_available)
                        .into(imageView);
                holder.llProductsImages.addView(relativeLayout);
                holder.llProductDetailContainer.setVisibility(View.VISIBLE);
                layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dynamicAddView = layoutInflater.inflate(R.layout.my_order_details_view, null);
                CustomTypefaceTextView tvProductName = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_product_name);
                CustomTypefaceTextView tvSize = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_size);
                CustomTypefaceTextView tvColor = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_color);
                CustomTypefaceTextView tvserial = (CustomTypefaceTextView) dynamicAddView.findViewById(R.id.tv_product_serial);
                holder.llProductDetailContainer.addView(dynamicAddView);

                if (Integer.parseInt(mRecentOrderList.get(position).getProducts().get(i).getStatus()) == ServiceConstants.ORDER_INACTIVE) {
                    view.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                }
                tvProductName.setText(mRecentOrderList.get(position).getProducts().get(i).getName());
                tvColor.setText(mRecentOrderList.get(position).getProducts().get(i).getColor());
                tvSize.setText(mRecentOrderList.get(position).getProducts().get(i).getSize());
                tvserial.setText(String.valueOf((i + 1)) + ". ");
            }
        }
        holder.btnCancelOrder.setTag(R.id.tag_position, position);
        holder.btnTrackOrder.setTag(R.id.tag_position, position);
        holder.btnCancelOrder.setOnClickListener(this);
        holder.btnTrackOrder.setOnClickListener(this);
        if (mRecentOrderList.get(position).getProducts() != null) {
            holder.tvNumberOfProductsText.setText(R.string.txt_number_of_product);
            holder.tvNumberOfProducts.setText(String.valueOf(mRecentOrderList.get(position).getProducts().size()));
        }
        try {
            Date simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(mRecentOrderList.get(position).getCreatedTime());
            SimpleDateFormat dtFormat = new SimpleDateFormat("MMM dd, yyyy");
            String date = dtFormat.format(simpleDateFormat);
            holder.tvProductDateText.setText(R.string.txt_ordered_on);
            holder.tvProductDate.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRecentOrderList.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int position = (int) v.getTag(R.id.tag_position);
        switch (v.getId()) {
            case R.id.btn_cancel_order:
                intent = new Intent(activity, MyOrdersViewActivity.class);
                List<ResMyOrders.Product> currentOrderList = new ArrayList<>();
                for (int i = 0; i < mRecentOrderList.size(); i++) {
                    if (mRecentOrderList.get(i).getOrderID().equals(mRecentOrderList.get(position).getOrderID())) {
                        currentOrderList.addAll(mRecentOrderList.get(i).getProducts());
                    }
                }
                intent.putExtra("serializableOrderList", new SerializableArrayList(currentOrderList));
                intent.putExtra("orderPosition", position);
                intent.putExtra("orderId", mRecentOrderList.get(position).getOrderID());
                activity.startActivityForResult(intent, AppConstants.RC_MY_ORDERS);
                break;
            case R.id.btn_track_order:
                intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("orderId", mRecentOrderList.get(position).getOrderID());
                activity.startActivityForResult(intent, AppConstants.RC_MY_ORDERS);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CustomTypefaceTextView tvOrderNumber;
        LinearLayout llBottomButton;
        CustomTypefaceTextView tvNumberOfProducts;
        CustomTypefaceTextView tvProductDate;
        CustomTypefaceButton btnCancelOrder;
        CustomTypefaceButton btnTrackOrder;
        LinearLayout llProductDetailContainer;
        LinearLayout llProductsImages;
        CustomTypefaceTextView tvProductDateText;
        CustomTypefaceTextView tvNumberOfProductsText;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderNumber = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_order_number);
            tvNumberOfProducts = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_products_number);
            tvProductDate = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_date);
            llProductsImages = (LinearLayout) itemView.findViewById(R.id.ll_product_images);
            llBottomButton = (LinearLayout) itemView.findViewById(R.id.ll_bottom_buttons);
            btnCancelOrder = (CustomTypefaceButton) itemView.findViewById(R.id.btn_cancel_order);
            btnTrackOrder = (CustomTypefaceButton) itemView.findViewById(R.id.btn_track_order);
            llProductDetailContainer = (LinearLayout) itemView.findViewById(R.id.ll_product_detail_container);
            tvProductDateText = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_product_date_text);
            tvNumberOfProductsText = (CustomTypefaceTextView) itemView.findViewById(R.id.tv_products_number_text);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iOnItemClickListener.onItemClick(view, getAdapterPosition(), 0);
        }
    }
}
