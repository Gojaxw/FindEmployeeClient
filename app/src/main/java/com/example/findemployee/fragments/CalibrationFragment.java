package com.example.findemployee.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.findemployee.MainActivity;
import com.example.findemployee.R;

public class CalibrationFragment extends Fragment {

    Button buttonCalibration;


    MainActivity mainActivity;
    public CalibrationFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calibration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonCalibration=getView().findViewById(R.id.buttonCalibration);
        buttonCalibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.calibration =true;
                Toast.makeText(mainActivity,"Calibration...",Toast.LENGTH_SHORT).show();
                mainActivity.changeFragment(mainActivity.mapOfEmployee);
            }
        });
    }
}