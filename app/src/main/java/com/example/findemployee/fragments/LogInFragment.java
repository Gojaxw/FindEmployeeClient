package com.example.findemployee.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findemployee.MainActivity;
import com.example.findemployee.R;
import com.example.findemployee.model.Employee;
import com.example.findemployee.retrofit.EmployeeApi;
import com.example.findemployee.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInFragment extends Fragment {

    MainActivity mainActivity;
    Button buttonLogIn,buttonToSignUp;
    EditText editFiledMail,editFieldPassword;
    public LogInFragment(MainActivity mainActivity) {
        this.mainActivity=mainActivity;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLogIn= getView().findViewById(R.id.buttonLogIn);
        buttonToSignUp=getView().findViewById(R.id.buttonToSignUp);
        editFiledMail=getView().findViewById(R.id.editFieldEmailAddress);
        editFieldPassword=getView().findViewById(R.id.editFieldPassword);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitService retrofitService =new RetrofitService();
                EmployeeApi employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
                String mail=String.valueOf(editFiledMail.getText());
                String password=String.valueOf(editFieldPassword.getText());
                Employee employee=new Employee();
                employee.setMail(mail);
                employee.setPassword(password);
                employeeApi.logIn(employee).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {

                        if(response.body().getId()==-1){
                            Toast.makeText(mainActivity,  "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(mainActivity, "Log in successful!", Toast.LENGTH_SHORT).show();
                            mainActivity.ifSignIn=true;
                            mainActivity.setEmployee(response.body());
                            mainActivity.changeFragment(mainActivity.calibrationFragment);
                        }

                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Toast.makeText(mainActivity, "Log in failed!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(mainActivity, employee.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(mainActivity.signUpFragment);
            }
        });

    }
}