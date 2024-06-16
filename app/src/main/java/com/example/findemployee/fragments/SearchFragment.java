package com.example.findemployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findemployee.MainActivity;
import com.example.findemployee.R;
import com.example.findemployee.adapter.EmployeeAdapter;
import com.example.findemployee.model.Employee;
import com.example.findemployee.retrofit.EmployeeApi;
import com.example.findemployee.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    MainActivity mainActivity ;
    private RecyclerView recyclerView;
    public SearchFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.listOfEmployees);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void loadEmployee() {
        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);
//        employeeApi.getAllEmployee()
//                .enqueue(new Callback<List<Employee>>() {
//                    @Override
//                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
//                        populateListView(response.body());
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Employee>> call, Throwable t) {
//
//                    }
//                });
    }

    private void populateListView(List<Employee> employeeList) {
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeeList);
        recyclerView.setAdapter(employeeAdapter);
    }
}