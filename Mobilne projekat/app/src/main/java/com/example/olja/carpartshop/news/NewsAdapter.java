package com.example.olja.carpartshop.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olja.carpartshop.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Olja on 5/25/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CarPartViewHolder> {

    private ArrayList<News> listData;
    private final NewsOnClickHandler mClickHandler;

    public interface NewsOnClickHandler {
        void onClick(int newsId);
    }

    public NewsAdapter(NewsOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public CarPartViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.news_item;
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
        holder.dateForNews.setText(parsedDate);
    }

    @Override
    public int getItemCount() {
        if (null == listData) {
            return 0;
        }
        return listData.size();
    }


    public void setNews(List<News> newsEntries) {
        listData = (ArrayList<News>) newsEntries;
        notifyDataSetChanged();
    }


    public String parse(Date date){
       String retVal =  android.text.format.DateFormat.format("yyyy-MM-dd HH:mm",date).toString();

        return retVal;
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
            mClickHandler.onClick(listData.get(adapterPosition).getID());
        }
    }
}
