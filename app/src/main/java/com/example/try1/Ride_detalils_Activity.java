package com.example.try1;

import static android.Manifest.permission.CALL_PHONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Ride_detalils_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_from, tv_to, tv_driver_name;
    TextView tv_time,tv_date, tv_number;
    ImageButton btn_contact;
    Button join_ride;
    FirebaseDatabase database;
    DatabaseReference myref1;
    String driver_number;
    User driver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detalils);
        database = FirebaseDatabase.getInstance();
        myref1 = database.getReference();

        Ride ride = Searching.chosenRide;
        tv_driver_name = (TextView) findViewById(R.id.tv_driver_name);
        join_ride = (Button) findViewById(R.id.btn_join_ride);
        tv_from = (TextView)findViewById(R.id.tv_from);
        tv_to = (TextView)findViewById(R.id.tv_to);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        btn_contact = (ImageButton) findViewById(R.id.btn_contact);
        tv_driver_name.setText(ride.getDriver().getName()+ "'s Ride");
        tv_from.setText("From:"+ ride.getGoingFrom());
        tv_to.setText("To:"+ ride.getGoingTo());
        tv_time.setText(ride.getHour()+ ":" + ride.getMinute());
        tv_date.setText(ride.getMonth()+ "/" + ride.getDay());
        driver_number = ride.getDriver().getNumber();
        driver = ride.getDriver();


        btn_contact.setOnClickListener(this);
        join_ride.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        if (view == btn_contact) {

            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:" + driver_number));
            if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(i);
            } else {
                requestPermissions(new String[]{CALL_PHONE}, 1);
            }


        }
        if (view == join_ride){
            if(Searching.chosenRide !=null && Searching.chosenRide.getArrHitchikers()==null){
              Searching.chosenRide.setArrHitchikers(new ArrayList<>());
            }
            Searching.chosenRide.getArrHitchikers().add(MainActivity.nowConnectedInThisPhone);
            myref1.child("Rides").child(Searching.chosenRide.getChosenRideKey()).setValue(Searching.chosenRide);


        }
    }
}