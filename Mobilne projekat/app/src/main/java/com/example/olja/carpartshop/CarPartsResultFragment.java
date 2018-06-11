package com.example.olja.carpartshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.olja.carpartshop.carPart.CarPartAdapter;
import com.example.olja.carpartshop.carPart.CarPartInformationsActivity;

/**
 * Created by Olja on 6/11/2018.
 */

public class CarPartsResultFragment extends Fragment implements CarPartAdapter.CarPartOnClickHandler{

    private RecyclerView carPartRecyclerView;
    private CarPartAdapter cartPartAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.car_parts_for_shop, container, false);

        carPartRecyclerView =  view.findViewById(R.id.carPartRecyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        carPartRecyclerView.setLayoutManager(layoutManager);
        carPartRecyclerView.setHasFixedSize(true);
        cartPartAdapter = new CarPartAdapter(this,getActivity());
        carPartRecyclerView.setAdapter(cartPartAdapter);


        return view;
    }
    @Override
    public void onClick(int carPartId) {
    /*    Context context = getco;
        Class destinationActivity = CarPartInformationsActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("carPartId",carPartId);
        startActivity(startChildActivityIntent);*/
    }
}
