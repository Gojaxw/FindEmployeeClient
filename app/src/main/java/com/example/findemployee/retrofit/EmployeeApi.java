package com.example.findemployee.retrofit;

import com.example.findemployee.model.Employee;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface EmployeeApi {
    @POST("/employee/getAll")
    Call<List<Employee>> getAllEmployee(@Body Employee employee);
    @GET("/employee/get-all")
    Call<List<Employee>> getAllEmployees();
    @POST("/employee/login")
    Call<Employee> logIn(@Body Employee employee);
    @POST("/employee/save")
    Call<Employee> signUp(@Body Employee employee);

    @GET("employee/save-new-coordinate")
    Call<Employee> setNewCoordinate(@Body Employee employee);


}
