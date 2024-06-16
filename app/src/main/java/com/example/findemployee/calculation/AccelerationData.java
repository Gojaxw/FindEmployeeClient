package com.example.findemployee.calculation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class AccelerationData {

    private static MutableLiveData<ArrayList<Double>> liveData =new MutableLiveData<>();

    public static MutableLiveData<ArrayList<Double>> getLiveData() {
        return liveData;
    }

    public void setLiveData(MutableLiveData<ArrayList<Double>> liveData) {
        this.liveData = liveData;
    }
}
