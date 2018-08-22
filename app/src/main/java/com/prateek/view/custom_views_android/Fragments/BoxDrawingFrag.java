package com.prateek.view.custom_views_android.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prateek.view.custom_views_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoxDrawingFrag extends Fragment {


    public BoxDrawingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_box_drawing, container, false);
    }

}
