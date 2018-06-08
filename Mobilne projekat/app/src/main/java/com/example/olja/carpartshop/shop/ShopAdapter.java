package com.example.olja.carpartshop.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.ShopsSearchFragment;
import com.example.olja.carpartshop.news.News;
import com.example.olja.carpartshop.news.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olja on 6/8/2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private ArrayList<Shop> listData;
    private final ShopAdapter.ShopOnClickHandler mClickHandler;

    public interface ShopOnClickHandler {
        void onClick(int newsId);
    }

    public ShopAdapter(ShopAdapter.ShopOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public ShopAdapter.ShopViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.shop_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ShopAdapter.ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopAdapter.ShopViewHolder holder, int position) {
        Shop shop = listData.get(position);

        holder.shopName.setText(shop.getName());
        holder.shopPhone.setText(shop.getPhone());
        //String parsedDate = parse(shop.getPubishDate());

    }

    @Override
    public int getItemCount() {
        if (null == listData) {
            return 0;
        }
        return listData.size();
    }


    public void setShops(List<Shop> shopsEntries) {
        listData = (ArrayList<Shop>) shopsEntries;
        notifyDataSetChanged();
    }

    /*unutrasnja klasa*/
    public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView shopName;
        public final TextView shopPhone;
        public final ImageView iconCallShop;

        public ShopViewHolder(final View view) {
            super(view);
            shopName = (TextView) view.findViewById(R.id.shopName);
            shopPhone = (TextView) view.findViewById(R.id.shopPhone);
            iconCallShop = (ImageView) view.findViewById(R.id.callShop);

            iconCallShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//ovdje treba implementirati poziv
                    Toast.makeText(view.getContext(),
                            "The favorite list would appear on clicking this icon",
                            Toast.LENGTH_LONG).show();
                }
            });
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(listData.get(adapterPosition).getID());
        }
    }
}
