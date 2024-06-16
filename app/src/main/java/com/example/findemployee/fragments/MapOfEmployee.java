package com.example.findemployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.findemployee.MainActivity;
import com.example.findemployee.R;
import com.example.findemployee.map.MapOfEmployeeView;
import com.example.findemployee.model.Employee;
import com.example.findemployee.retrofit.EmployeeApi;
import com.example.findemployee.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapOfEmployee extends Fragment {

    public MainActivity mainActivity;
    public TextView textViewCoordinate,textViewAcceleration;



    public Button buttonClearCoordinate;
    public boolean ifFragment=false,ifSetCoordinate=false;
   public EmployeeApi employeeApi;
   public RetrofitService retrofitService =new RetrofitService();
    public ArrayList<Employee> employeeArrayList=new ArrayList<>();
    public MapOfEmployee(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public MapOfEmployeeView employeeCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitService retrofitService =new RetrofitService();
         employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        employeeCard = getView().findViewById(R.id.mapOfEmployeeView);
        textViewCoordinate = getView().findViewById(R.id.textViewCoordinate);
        buttonClearCoordinate = getView().findViewById(R.id.buttonClearCoordinate);
        textViewAcceleration=getView().findViewById(R.id.textViewAcceleration);
        employeeCard.setContext(mainActivity);
        buttonClearCoordinate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.coordinates.clearCoordinates();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ifFragment=true;
    }

    public void setTextCoordinates() {

//            Double x=coordinates_data.get(0)*1000,y=coordinates_data.get(1)*1000,z=coordinates_data.get(2)*1000;
//            Integer x_1=x.intValue(),y_1=y.intValue(),z_1=z.intValue();
//            Double x_2=x_1/1000.1,y_2=y_1/1000.1,z_2=z_1/1000.1;

            double x = mainActivity.coordinates.x_now*1000;
            long xl=(long) x;
            x=xl/1000.0;
            double y = mainActivity.coordinates.y_now*1000;
        long yl=(long) y;
        y=yl/1000.0;
            double z = mainActivity.coordinates.z_now*1000;
        long zl=(long) z;
        z=zl/1000.0;
            //if(x!=0)      Toast.makeText(mainActivity, x+"", Toast.LENGTH_SHORT).show();
            textViewCoordinate.setText("X: " + x + "\nY: " + y + "\nZ: " + z);
            mainActivity.setCoordinatesOnServer();

        employeeApi.getAllEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                employeeArrayList=new ArrayList<>();

                employeeArrayList= (ArrayList<Employee>) response.body();
                employeeCard.setCoordinates(employeeArrayList);




            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(mainActivity, "Fail", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
