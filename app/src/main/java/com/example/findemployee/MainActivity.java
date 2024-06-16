package com.example.findemployee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.findemployee.calculation.AccelerationData;
import com.example.findemployee.calculation.CalculationDeviation;
import com.example.findemployee.calculation.Coordinates;
import com.example.findemployee.fragments.CalibrationFragment;
import com.example.findemployee.fragments.MapOfEmployee;
import com.example.findemployee.fragments.ProfileFragment;
import com.example.findemployee.fragments.SearchFragment;
import com.example.findemployee.fragments.LogInFragment;
import com.example.findemployee.fragments.SettingsFragment;
import com.example.findemployee.fragments.SignUpFragment;
import com.example.findemployee.model.Employee;
import com.example.findemployee.retrofit.EmployeeApi;
import com.example.findemployee.retrofit.RetrofitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity   implements SensorEventListener  {

    public LogInFragment LogInFragment=new LogInFragment(this);
    public SignUpFragment signUpFragment =new SignUpFragment(this);
    public BottomNavigationView bottomNavigationView;

    public CalibrationFragment calibrationFragment =new CalibrationFragment(this);
    private Employee employee;
    public CalculationDeviation calculationDeviation;
    public SearchFragment search=new SearchFragment(this);
    public SettingsFragment settings=new SettingsFragment();
    public ProfileFragment profile=new ProfileFragment(this);
    public Coordinates coordinates =new Coordinates(this);
    public MapOfEmployee mapOfEmployee=new MapOfEmployee(this);

    public AccelerationData data=new AccelerationData();
    int SensorType = Sensor.TYPE_ACCELEROMETER;//Sensor.TYPE_LINEAR_ACCELERATION;
    public ArrayList<Float> data_x = new ArrayList<>(),
            data_y = new ArrayList<>(),
            data_z = new ArrayList<>();
    public ArrayList<Employee> data_employees=new ArrayList<>();
    public ArrayList<Double> arrayListData =new ArrayList<>();
    public long time_last = 0, time_now = 0, del_time = 0;
    public boolean ifSignIn=false, calibration =false,compute=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Initialization
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);
        arrayListData.add(0.0);
        arrayListData.add(0.0);
        arrayListData.add(0.0);

       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(ifSignIn) {
                    if (item.getItemId() == R.id.profile) {
                        changeFragment(profile);
                        return true;
                    } else if (item.getItemId() == R.id.search) {
                        changeFragment(search);
                        return true;
                    } else if (item.getItemId() == R.id.settings) {
                        changeFragment(settings);
                        return true;
                    } else if (item.getItemId() == R.id.map_of_employee) {
                        changeFragment(mapOfEmployee);
                        return true;
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), "Please sign in", Toast.LENGTH_SHORT).show();
                }


               return false;
           }
       });
        if (sManager != null) {
            Sensor alSensor = sManager.getDefaultSensor(SensorType);
            if (alSensor != null) {
                sManager.registerListener((SensorEventListener) this, alSensor, SensorManager.SENSOR_DELAY_GAME);

            }
        } else {
            Toast.makeText(this, "Sensor service not detected.", Toast.LENGTH_SHORT).show();
        }
        changeFragment(LogInFragment);
        //getEmployees();
    }

    public void changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
    public Employee getEmployee() {
        return employee;
    }

    public void setCoordinatesOnServer(){
        RetrofitService retrofitService =new RetrofitService();
        EmployeeApi employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
        employeeApi.signUp(employee).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
//                System.out.println(response.body().getCoordinateX()+"\n"+response.body().getCoordinateY());

            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

            }
        });

    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (data_x.size() > 200) {
            mapOfEmployee.textViewAcceleration.setText(event.values[0]+"\n"+event.values[1]+"\n"+event.values[2]);
            Toast.makeText(MainActivity.this, "Сalibration ended!", Toast.LENGTH_SHORT).show();
              calculationDeviation =new CalculationDeviation(data_x,data_y,data_z);
             calculationDeviation.compute();
            compute=true;
            calibration =false;
            data_x.clear();
            data_y.clear();
            data_z.clear();
//            Toast.makeText(this, calculationDeviation.average_dispersion_x+"\n", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, calculationDeviation.average_dispersion_y+"\n", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, calculationDeviation.average_dispersion_z+"\n", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, calculationDeviation.average_x+"\n", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, calculationDeviation.average_y+"\n", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, calculationDeviation.average_z+"\n", Toast.LENGTH_SHORT).show();
            changeFragment(mapOfEmployee);

        }

        if (event.sensor.getType() == SensorType) {

            if(compute){
                mapOfEmployee.textViewAcceleration.setText(event.values[0]+"\n"+event.values[1]+"\n"+event.values[2]);
                time_now = System.currentTimeMillis();
                if (time_last == 0) {
                    del_time = System.currentTimeMillis() - time_now;
                } else {
                    del_time = time_now - time_last;
                }

                coordinates.calculateCoordinate(mapOfEmployee,
                        calculationDeviation.results.get(3),
                        calculationDeviation.results.get(4),
                        calculationDeviation.results.get(5),
                        del_time,
                        event.values[0],
                        event.values[1],
                        event.values[2],
                        compute,
                        calculationDeviation.results.get(0),
                        calculationDeviation.results.get(1),
                        calculationDeviation.results.get(2)
                );
                time_last = time_now;
                employee.setCoordinateX(coordinates.x_now);
                employee.setCoordinateY(coordinates.y_now);
            // if(coordinates.x_now>0.000001)   Toast.makeText(this, " "+coordinates.x_now+"   "+coordinates.y_now, Toast.LENGTH_SHORT).show();



            }



        }
        if (calibration) {
            data_x.add(event.values[0]);
            data_y.add(event.values[1]);
            data_z.add(event.values[2]);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}