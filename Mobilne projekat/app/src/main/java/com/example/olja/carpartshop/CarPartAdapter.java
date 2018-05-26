package com.example.olja.carpartshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Olja on 5/25/2018.
 */

public class CarPartAdapter extends RecyclerView.Adapter<CarPartAdapter.CarPartViewHolder> {

    private String[] listData;
    private final CarPartOnClickHandler mClickHandler;

    /*** The interface that receives onClick messages.*/
    public interface CarPartOnClickHandler {
        void onClick(String weatherForDay);
    }

    public CarPartAdapter(CarPartOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public CarPartViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.car_part_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CarPartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarPartViewHolder holder, int position) {
        String weatherForThisDay = listData[position];
        holder.currentTextView.setText(weatherForThisDay);
    }

    @Override
    public int getItemCount() {
        if (null == listData) {
            return 0;
        }
        return listData.length;
    }



    public void setListData(String[] weatherData) {
        listData = weatherData;
        notifyDataSetChanged();
    }


/*View holder kao unutrasnja klasa*/
    public class CarPartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView currentTextView;



        public CarPartViewHolder(View view) {
            super(view);
            currentTextView = (TextView) view.findViewById(R.id.tv_weather_data);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = listData[adapterPosition];
            mClickHandler.onClick(weatherForDay);
        }
    }
}
