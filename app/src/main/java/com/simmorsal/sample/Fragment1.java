package com.simmorsal.sample;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {

    private View v;
    private int color;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment1, container, false);
        v.findViewById(R.id.rootView).setBackgroundColor(color);
        return v;
    }

    public Fragment1 setColor(int color) {
        this.color = color;
        return this;
    }

}
