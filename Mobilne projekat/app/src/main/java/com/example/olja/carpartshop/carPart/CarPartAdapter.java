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

import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.shop.Shop;
import com.example.olja.carpartshop.shop.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olja on 6/9/2018.
 */

public class CarPartAdapter extends RecyclerView.Adapter<CarPartAdapter.CarPartViewHolder>  {

    private ArrayList<CarPart> listData;
    private final CarPartAdapter.CarPartOnClickHandler mClickHandler;
    private Context contextAdapter;

    public interface CarPartOnClickHandler {
        void onClick(int newsId);
    }

    public CarPartAdapter(CarPartAdapter.CarPartOnClickHandler clickHandler,Context context2) {
        mClickHandler = clickHandler;
        contextAdapter = context2;
    }

    @Override
    public CarPartAdapter.CarPartViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.car_part_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CarPartAdapter.CarPartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarPartAdapter.CarPartViewHolder holder, int position) {
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
        holder.phone = "0653268080";

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
        public final ImageView infoCarPartIcon;
        public String phone;
        public int carPartId;

        public CarPartViewHolder(final View view) {
            super(view);
            carPartName = (TextView) view.findViewById(R.id.carPartName);
            carPartPrice= (TextView) view.findViewById(R.id.carPartPrice);
            carPartIcon= (ImageView) view.findViewById(R.id.car_part_icon);
            callCarPartIcon= (ImageView) view.findViewById(R.id.callCarPartIcon);
            infoCarPartIcon= (ImageView) view.findViewById(R.id.infoCarPartIcon);

            callCarPartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhoneNumber(phone);
                }
            });

            infoCarPartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openInformation(carPartId);
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

        public void callPhoneNumber(String phoneNumber) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

            if (ContextCompat.checkSelfPermission(contextAdapter, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) contextAdapter, new String[]{Manifest.permission.CALL_PHONE}, 101);
            } else {
                contextAdapter.startActivity(intent);

            }
        }
    }
}
