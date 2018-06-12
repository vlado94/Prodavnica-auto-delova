package com.example.olja.carpartshop.shop;


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

import com.example.olja.carpartshop.R;
import com.example.olja.carpartshop.carPart.CarPart;

import java.util.List;

/**
 * Created by Olja on 6/12/2018.
 */

public class ListShopFragment extends Fragment  implements ShopAdapter.ShopOnClickHandler {


    private RecyclerView shopsRecyclerView;
    private ShopAdapter shopAdapter;
    public ListShopFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shops_search_fragment, container, false);
        shopsRecyclerView =  view.findViewById(R.id.recyclerview_shops);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager((getActivity()), LinearLayoutManager.VERTICAL, false);
        shopsRecyclerView.setLayoutManager(layoutManager);
        shopsRecyclerView.setHasFixedSize(true);
        shopAdapter = new ShopAdapter(this,getActivity());
        shopsRecyclerView.setAdapter(shopAdapter);
        Bundle bundle = getArguments();
        List<Shop> carParts = bundle.getParcelableArrayList("data");
        shopAdapter.setShops(carParts);
        return view;

    }

    @Override
    public void onClick(int shopId) {
        Context context = this.getActivity();
        Class destinationActivity = ShopDetailsActivity.class;
        Intent startChildActivityIntent = new Intent(context, destinationActivity);
        startChildActivityIntent.putExtra("shopId",shopId);
        startActivity(startChildActivityIntent);
    }
}
