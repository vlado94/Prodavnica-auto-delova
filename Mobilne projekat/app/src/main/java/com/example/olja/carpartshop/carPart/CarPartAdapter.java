package com.example.olja.carpartshop.carPart;

import android.content.Context;
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

    public interface CarPartOnClickHandler {
        void onClick(int newsId);
    }

    public CarPartAdapter(CarPartAdapter.CarPartOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
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

       holder.carPartName.setText(carPart.getName());
        //holder.shopPhone.setText(shop.getPhone());
        //String parsedDate = parse(shop.getPubishDate());

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
       /* public final TextView shopPhone;
        public final ImageView iconCallShop;*/

        public CarPartViewHolder(final View view) {
            super(view);
            carPartName = (TextView) view.findViewById(R.id.carPartName);
            /*shopPhone = (TextView) view.findViewById(R.id.shopPhone);
            iconCallShop = (ImageView) view.findViewById(R.id.callShop);

            iconCallShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//ovdje treba implementirati poziv
                    Toast.makeText(view.getContext(),
                            "The favorite list would appear on clicking this icon",
                            Toast.LENGTH_LONG).show();
                }
            });*/
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(listData.get(adapterPosition).getID());
        }
    }
}
