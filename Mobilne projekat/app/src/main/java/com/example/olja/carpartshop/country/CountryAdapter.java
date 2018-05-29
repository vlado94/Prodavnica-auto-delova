package com.example.olja.carpartshop.country;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olja.carpartshop.CarPartAdapter;
import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.database.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private ArrayList<Country> listData;
    private final CountryAdapter.CountryOnClickHandler mClickHandler;

    public interface CountryOnClickHandler {
        void onClick(String weatherForDay);
    }

    public CountryAdapter(CountryAdapter.CountryOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public CountryAdapter.CountryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.d("fa","Usao u on create view");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.country_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CountryAdapter.CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryAdapter.CountryViewHolder holder, int position) {
        Country weatherForThisDay = listData.get(position);
        holder.nameOfCountry.setText(weatherForThisDay.getName());
        Log.d("fa","Usao u on bind");

    }

    @Override
    public int getItemCount() {
        if (null == listData) {
            return 0;
        }
        return listData.size();
    }


    public void setNews(List<Country> countriesEntries) {
        listData = (ArrayList<Country>) countriesEntries;
        Log.d("sdsada","Usao  u setNewa"+listData.size());
        notifyDataSetChanged();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView nameOfCountry;
        // public final TextView dateForNews; mogao bi ovdje grb




        public CountryViewHolder(View view) {
            super(view);
            nameOfCountry = (TextView) view.findViewById(R.id.nameOfCountry);
            //dateForNews = (TextView) view.findViewById(R.id.dateForNews);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = listData.get(adapterPosition).getName();
            mClickHandler.onClick(weatherForDay);
        }
    }
}
