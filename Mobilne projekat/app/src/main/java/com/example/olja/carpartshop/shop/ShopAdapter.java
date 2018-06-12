package com.example.olja.carpartshop.shop;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olja.carpartshop.MainActivity;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.ShopsSearchFragment;
import com.example.olja.carpartshop.news.News;
import com.example.olja.carpartshop.news.NewsAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olja on 6/8/2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {
    private ArrayList<Shop> listData;
    private final ShopAdapter.ShopOnClickHandler mClickHandler;
    private Context context;
    private String phone = "";
    public interface ShopOnClickHandler {
        void onClick(int newsId);
    }

    public ShopAdapter(ShopAdapter.ShopOnClickHandler clickHandler,Context context2) {
        mClickHandler = clickHandler;
        context = context2;
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
        String address = shop.getAddress().split(",")[2];
        holder.shopName.setText(shop.getName());
        holder.shopPhone.setText(shop.getAddress().split(",")[2] + ","+shop.getAddress().split(",")[1] + " " + shop.getAddress().split(",")[0]);
       phone = shop.getPhone();
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
        //public final TextView city;

        public ShopViewHolder(final View view) {
            super(view);
            shopName = (TextView) view.findViewById(R.id.shopName);
            shopPhone = (TextView) view.findViewById(R.id.shopPhone);
            iconCallShop = (ImageView) view.findViewById(R.id.callShop);
            //city = (TextView) view.findViewById(R.id.city);
           // city = (TextView) view.findViewById(R.id.city);
            iconCallShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhoneNumber(phone);
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

    public void callPhoneNumber(String shopPhone) {
        if(!shopPhone.equals(""))
            phone = shopPhone;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 101);
        } else {
            context.startActivity(intent);

        }
    }
}
