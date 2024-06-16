package com.example.findemployee.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.findemployee.model.Employee;
import com.example.findemployee.retrofit.EmployeeApi;
import com.example.findemployee.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapOfEmployeeView extends View {
    protected Paint paint = new Paint();

    public double      size ;
    public double coordinateX=0.0, coordinateY=0.0,coordinateZ=0.0;

    boolean flag = false;
    public ArrayList<Employee> employees=new ArrayList<>();

    public double coefficient_of_change;
    public boolean ifSetCoordinate=false;

    public void setContext(Context context) {
        this.context = context;
    }

    //Toast.makeText(getContext(),""+size,Toast.LENGTH_SHORT).show();

    Context context;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        size= Math.min(getWidth(), getHeight());
        coefficient_of_change=5*(size/500)*100;
    }
    public double returnSize(){
        return size;
    }

    public void setCoordinates(ArrayList<Employee> employeeArrayList) {
        employees=employeeArrayList;
        ifSetCoordinate=true;
        this.invalidate();

    }

    public MapOfEmployeeView(Context context) {
        super(context);

    }

    public MapOfEmployeeView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Toast.makeText(context, coordinateX+"   "+coordinateY, Toast.LENGTH_SHORT).show();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        canvas.drawRect(0, 0, (float) size, (float) size, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);
        if(ifSetCoordinate) {
            for (int i = 0; i < employees.size(); i++) {
                canvas.drawCircle((float) (employees.get(i).getCoordinateX()), (float) (employees.get(i).getCoordinateY()), 30, paint);
            }
        }else{
            canvas.drawCircle((float) (getHeight()/2), (float) (getWidth()/2), 50, paint);
        }
//        canvas.drawCircle((float) (coordinateX), (float) (coordinateY), 30, paint);

    }


}
