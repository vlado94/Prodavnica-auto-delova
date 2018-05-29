package com.example.olja.carpartshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olja.carpartshop.database.News;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        holder.shortDescription.setText(weatherForThisDay.getShortDescription());

        String parsedDate = parse(weatherForThisDay.getPubishDate());

       /* Date now = Calendar.getInstance().getTime();
        if(weatherForThisDay.getPubishDate().compareTo(now) == 0){
            holder.
        }*/
        holder.dateForNews.setText(parsedDate);
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

    public String parse(Date date){
        String fullDate = date.toString();
        String[] splitedStrings = fullDate.split("G");

        return splitedStrings[0];
    }


/*View holder kao unutrasnja klasa*/
    public class CarPartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView shortDescription;
    public final TextView dateForNews;




        public CarPartViewHolder(View view) {
            super(view);
            shortDescription = (TextView) view.findViewById(R.id.shortDescription);
            dateForNews = (TextView) view.findViewById(R.id.dateForNews);
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
