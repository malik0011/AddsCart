package com.as2developers.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Set;

public class SetDate extends AppCompatActivity {
    TextView selectedDate;
    String SelectedDateS;
    Button selectDate,nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);
        setContentView(R.layout.activity_set_date);
        selectedDate = (TextView) findViewById(R.id.selectedDate);
        selectDate = (Button) findViewById(R.id.selectDate);
        nextBtn = (Button) findViewById(R.id.nextBtn);

        //initialize calender
        Calendar calendar = Calendar.getInstance();

        //get year
        int year = calendar.get(Calendar.YEAR);
        //get month
        int month = calendar.get(Calendar.MONTH);
        //get day
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        SelectedDateS ="";
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(SetDate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1+=1;
                        SelectedDateS = i2 +"/" + i1 + "/" +i;
                        selectedDate.setText(SelectedDateS);
                    }
                },year,month,day
                );

                //Disable date
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis()+24*60*60*1000);
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis()+5*24*60*60*1000);
                datePicker.show();
            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(SelectedDateS.isEmpty())){
                    Toast.makeText(SetDate.this, "Selected Date:"+SelectedDateS, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SetDate.this,ConfirmPickupActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(SetDate.this, "Please Selected A Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
