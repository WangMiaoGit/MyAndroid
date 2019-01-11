package com.wang.myandroid.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wang.myandroid.R;
import com.wang.myandroid.common.blu.BleUtil;
import com.wang.myandroid.ui.BleActivity;
import com.wang.myandroid.ui.BleDemo;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFive extends Fragment {

private Button btn_go;
    public FragmentFive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_five, container, false);

        btn_go = view.findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ShowActivity.class);
//                Intent intent = new Intent(getContext(), BleActivity.class);
                Intent intent = new Intent(getContext(), BleDemo.class);
                startActivity(intent);
            }
        });



        return view;
    }

}
