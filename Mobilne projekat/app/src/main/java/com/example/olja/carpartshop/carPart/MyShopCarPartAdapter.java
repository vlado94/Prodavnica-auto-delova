package com.example.olja.carpartshop.carPart;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.olja.carpartshop.Constants;
import com.example.olja.carpartshop.MainActivity;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.shop.Shop;
import com.example.olja.carpartshop.shop.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olja on 6/9/2018.
 */

public class MyShopCarPartAdapter extends RecyclerView.Adapter<MyShopCarPartAdapter.CarPartViewHolder>  {

    private ArrayList<CarPart> listData;
    private final MyShopCarPartAdapter.CarPartOnClickHandler mClickHandler;
    private Context contextAdapter;

    public interface CarPartOnClickHandler {
        void onClick(int newsId);
    }

    public MyShopCarPartAdapter(MyShopCarPartAdapter.CarPartOnClickHandler clickHandler,Context context2) {
        mClickHandler = clickHandler;
        contextAdapter = context2;
    }

    @Override
    public MyShopCarPartAdapter.CarPartViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.car_part_item_admin;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MyShopCarPartAdapter.CarPartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyShopCarPartAdapter.CarPartViewHolder holder, int position) {
        CarPart carPart = listData.get(position);

        if(carPart.getImage() != null){
            if(carPart.getImage().length > 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(carPart.getImage(), 0, carPart.getImage().length);
                holder.carPartIcon.setImageBitmap(bitmap);
            }
        }


        holder.carPartName.setText(carPart.getName());
        holder.carPartPrice.setText(String.valueOf(carPart.getPrice() + " RSD"));
        holder.carPartId = carPart.getID();

    }

    @Override
    public int getItemCount() {
        if (null == listData) {
            return 0;
        }
        return listData.size();
    }


    public void setCarParts(List<CarPart> carPartsEntries) {
        listData = (ArrayList<CarPart>) carPartsEntries;
        notifyDataSetChanged();
    }

    /*unutrasnja klasa*/
    public class CarPartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView carPartName;
        public final TextView carPartPrice;
        public final ImageView carPartIcon;
        public final ImageView callCarPartIcon;
        public int carPartId;

        public CarPartViewHolder(final View view) {
            super(view);
            carPartName = (TextView) view.findViewById(R.id.carPartName_a);
            carPartPrice= (TextView) view.findViewById(R.id.carPartPrice_a);
            carPartIcon= (ImageView) view.findViewById(R.id.car_part_icon_a);
            callCarPartIcon= (ImageView) view.findViewById(R.id.change_product_a);

            callCarPartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit(carPartId);
                }
            });

            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(listData.get(adapterPosition).getID());
        }

        private void openInformation(int id){
            Class destinationActivity = CarPartInformationsActivity.class;
            Intent startChildActivityIntent = new Intent((Activity) contextAdapter,destinationActivity);
            startChildActivityIntent.putExtra("carPartId",carPartId);
            contextAdapter.startActivity(startChildActivityIntent);
        }

        public void edit(int id) {
            Constants.currentCarPart = id;
            MainActivity activity = (MainActivity) contextAdapter;
            activity.setViewPager(11);
        }
    }
}
