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

public class SignUpFragment extends Fragment {
    MainActivity mainActivity;
    Button buttonSignUp,buttonToLogIn;
    EditText nameField,surnameField,mailField,passwordField;

    public SignUpFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonSignUp=getView().findViewById(R.id.buttonSignUp);
        buttonToLogIn=getView().findViewById(R.id.buttonToLogIn);
        nameField=getView().findViewById(R.id.editFieldName);
        surnameField=getView().findViewById(R.id.editFieldSurname);
        mailField=getView().findViewById(R.id.editFieldEmailSignUp);
        passwordField=getView().findViewById(R.id.editFieldPasswordSignUP);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitService retrofitService =new RetrofitService();
                EmployeeApi employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
                String name=String.valueOf(nameField.getText());
                String surname=String.valueOf(surnameField.getText());
                String mail=String.valueOf(mailField.getText());
                String password=String.valueOf(passwordField.getText());
                Employee employee=new Employee();
                employee.setName(name);
                employee.setSurname(surname);
                employee.setMail(mail);
                employee.setPassword(password);

                employeeApi.signUp(employee).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {

                        mainActivity.ifSignIn=true;
                        mainActivity.setEmployee(response.body());
                        mainActivity.changeFragment(mainActivity.calibrationFragment);

                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Toast.makeText(mainActivity, "Sign up failed!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(mainActivity, employee.toString(), Toast.LENGTH_SHORT).show();
//                        Logger.getLogger(mainActivity.class.getName()).log(Level.SEVERE,"Error occurred",t);
                    }
                });
            }
        });
        buttonToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(mainActivity.LogInFragment);
            }
        });
    }
}