package com.example.olja.carpartshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olja.carpartshop.database.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Olja on 5/25/2018.
 */

public class CarPartAdapter extends RecyclerView.Adapter<CarPartAdapter.CarPartViewHolder> {

    private ArrayList<News> listData;
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
        News weatherForThisDay = listData.get(position);
        holder.currentTextView.setText(weatherForThisDay.getShortDescription());
        holder.itemString2.setText(weatherForThisDay.getPubishDate().toString());
    }

    @Override
    public int getItemCount() {
        if (null == listData) {
            return 0;
        }
        return listData.size();
    }


    public void setTasks(List<News> taskEntries) {
        listData = (ArrayList<News>) taskEntries;
        notifyDataSetChanged();
    }
    /*public void setListData(String[] weatherData) {
        listData = weatherData;
        notifyDataSetChanged();
    }*/


/*View holder kao unutrasnja klasa*/
    public class CarPartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView currentTextView;
    public final TextView itemString2;



        public CarPartViewHolder(View view) {
            super(view);
            currentTextView = (TextView) view.findViewById(R.id.shortDescription);
            itemString2 = (TextView) view.findViewById(R.id.dateForNews);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = listData.get(adapterPosition).getShortDescription();
            mClickHandler.onClick(weatherForDay);
        }
    }
}
