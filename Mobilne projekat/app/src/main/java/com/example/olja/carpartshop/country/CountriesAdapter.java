package com.example.olja.carpartshop.country;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olja.carpartshop.R;

import java.util.ArrayList;
import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {
    private ArrayList<Country> listData;
    private final CountriesAdapter.CountryOnClickHandler mClickHandler;

    public interface CountryOnClickHandler {
        void onClick(int weatherForDay);
    }

    public CountriesAdapter(CountriesAdapter.CountryOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public CountriesAdapter.CountryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.country_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new CountriesAdapter.CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountriesAdapter.CountryViewHolder holder, int position) {
        Country weatherForThisDay = listData.get(position);
        holder.countryId.setText(weatherForThisDay.getName());


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
        notifyDataSetChanged();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView countryId;
        // public final TextView dateForNews; mogao bi ovdje grb

        public CountryViewHolder(View view) {
            super(view);
            countryId = (TextView) view.findViewById(R.id.countryId);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int countryId = listData.get(adapterPosition).getID();
            mClickHandler.onClick(countryId);
        }
    }
}
