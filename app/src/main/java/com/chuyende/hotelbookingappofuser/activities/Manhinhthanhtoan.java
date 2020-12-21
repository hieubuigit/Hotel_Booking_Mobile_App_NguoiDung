package com.chuyende.hotelbookingappofuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chuyende.hotelbookingappofuser.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Manhinhthanhtoan extends AppCompatActivity {

    EditText edtDateNDen, edtDatenDi;
    ImageButton imgDatenDen, ingDateNDi;
    Calendar calendarone, calendartwo;
    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhthanhtoan);
        //ngayden-ngaydi
        //onvaluetextchange thuc hien
        //try catch
        //tinh tong ngay new date()

        imgDatenDen = (ImageButton)findViewById(R.id.img_Ngayden);
        ingDateNDi = (ImageButton)findViewById(R.id.img_Ngaydi);
        //get datePickerDialog
        simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        imgDatenDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongay1();
            }
        });

        ingDateNDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongay2();
            }
        });


        setControl();
    }

    private void setControl() {
        edtDateNDen = findViewById(R.id.edit_Ngayden);
        edtDatenDi = findViewById(R.id.edit_Ngaydi);


    }


    private void chongay1()
    {
        calendarone = Calendar.getInstance();
        int ngay = calendarone.get(Calendar.DATE);
        int thang = calendarone.get(Calendar.MONTH);
        int nam = calendarone.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarone.set(i, i1, i2);
                edtDateNDen.setText(simpleDateFormat.format(calendarone.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    private void chongay2()
    {
        calendartwo = Calendar.getInstance();
        int ngay = calendartwo.get(Calendar.DATE);
        int thang = calendartwo.get(Calendar.MONTH);
        int nam = calendartwo.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendartwo.set(i, i1, i2);
                edtDatenDi.setText(simpleDateFormat.format(calendartwo.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }


}