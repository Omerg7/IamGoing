package com.example.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import java.util.Locale;
import android.widget.ImageView;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Driving extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView places_List;
    AutoCompleteTextView goingTo_List;
    Button Driving_Enter, selectTime;
    FirebaseDatabase database;
    DatabaseReference myref1;
    Switch leavingNow;
    DateFormat df;
    DatePicker picker;
    int day, month, hour, minute;
    ImageView car_animation;

    final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);
        Switch sw_leavingNow = (Switch)findViewById(R.id.leavingNow);
        sw_leavingNow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Toast.makeText(Driving.this, "The Switch is " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show();
                if(isChecked) {
                    Calendar israelTime = new GregorianCalendar(TimeZone.getTimeZone("GMT+2"));
                    hour = israelTime.get(Calendar.HOUR_OF_DAY);
                    minute = israelTime.get(Calendar.MINUTE);
                     day = israelTime.get(Calendar.DAY_OF_MONTH);
                     month = israelTime.get(Calendar.MONTH) + 1;
                     selectTime.setEnabled(false);
                } else {
                    Calendar israelTime = new GregorianCalendar(TimeZone.getTimeZone("GMT+2"));
                     hour = israelTime.get(Calendar.HOUR_OF_DAY);
                     minute = israelTime.get(Calendar.MINUTE);
                     day = israelTime.get(Calendar.DAY_OF_MONTH);
                     month = israelTime.get(Calendar.MONTH) + 1;
                    selectTime.setEnabled(true);

                }
            }
        });

        database = FirebaseDatabase.getInstance();
        myref1 = database.getReference();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Places);
        places_List = (AutoCompleteTextView) findViewById(R.id.places_list);
        places_List.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, GoingTo);
        goingTo_List = (AutoCompleteTextView) findViewById(R.id.goingTo_List);
        goingTo_List.setAdapter(adapter);

        Driving_Enter = (Button) findViewById(R.id.Driving_Enter);
        Driving_Enter.setOnClickListener(this);

        car_animation = (ImageView)findViewById(R.id.car_animation);
        car_animation.post(new Runnable() {
            @Override
            public void run() {
                startImageAnimation();
            }
        });





final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
    @Override
    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
        c.set(Calendar.YEAR, selectedYear);
        c.set(Calendar.MONTH, selectedMonth);
        c.set(Calendar.DAY_OF_MONTH, selectedDay);
        day = selectedDay;
        month = (selectedMonth+1);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                selectTime.setText("date:"+selectedDay+"/"+ (selectedMonth+1)+ "    " + String.format(Locale.getDefault(), "%02d:%02d", hour, minute));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(Driving.this, /*style,*/ onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();









    }
};




       selectTime = (Button) findViewById(R.id.selectTime);
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Driving.this, date ,c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }





    private static final String[] Places = new String[]{
            "Yuvalim", "Shkenya"
    };
    private static final String[] GoingTo = new String[]{
            "Yuvalim", "Shkenya"
    };

    @Override
    public void onClick(View view) {

        if (view == Driving_Enter) {
            String GoingFrom = places_List.getText().toString();
            String GoingTo = goingTo_List.getText().toString();

            if (MainActivity.nowConnectedInThisPhone == null)
                Toast.makeText(Driving.this, "Null", Toast.LENGTH_LONG).show();
            else {
                Query q = myref1.child("Users").orderByValue();
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userExist = false;
                        for (DataSnapshot dst : snapshot.getChildren()) {
                            User u = dst.getValue(User.class);
                            if (u.getName().equals(MainActivity.nowConnectedInThisPhone.getName())) {
                                userExist = true;
                                Ride newRide = new Ride(MainActivity.nowConnectedInThisPhone, GoingFrom, GoingTo, day, month, hour, minute);
                                //u.setRide(newRide);
                                myref1.child("Rides").push().setValue(newRide);
                                Toast.makeText(Driving.this, "database update successfully", Toast.LENGTH_LONG).show();
                                ObjectAnimator animation = ObjectAnimator.ofFloat(car_animation, "translationX",(car_animation.getWidth()), 0);
                                animation.setDuration(2100);
                                animation.start();



                            }

                        }
                        if (!userExist)
                            Toast.makeText(Driving.this, "database does not include user " + MainActivity.nowConnectedInThisPhone.getName(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }



    }
    private void startImageAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(car_animation, "translationX",(car_animation.getWidth()), 0);
        animation.setDuration(1100);
        animation.start();
    }
}


