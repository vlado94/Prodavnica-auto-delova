package com.example.olja.carpartshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nebojsa on 6/1/2018.
 */

public class MostPopularFragment extends Fragment {
    private static final String TAG = "MostPopularFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.most_popular_fragment, container, false);

        return  view;
    }

}