package com.example.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Searching extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView searching_from, searching_to;
   ImageButton searching_ride;
    int day, month, hour, minute;
    ListView lv_searching;
    FirebaseDatabase database;
    DatabaseReference myref1;
    ArrayList<Ride> arrAllRides;
    ArrayList<Ride>search_Ride_list;

    String stringFrom;
    static Ride chosenRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        arrAllRides = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myref1 = database.getReference();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Places);
       searching_from = (AutoCompleteTextView) findViewById(R.id.searching_from);
        searching_from.setAdapter(adapter);
        stringFrom = searching_from.getText().toString();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Places);
        searching_to = (AutoCompleteTextView) findViewById(R.id.searching_to);
        searching_to.setAdapter(adapter);

        searching_ride = (ImageButton) findViewById(R.id.searching_ride);
        lv_searching = (ListView) findViewById(R.id.lv_searching);


        searching_ride.setOnClickListener(this);
        Calendar israelTime = new GregorianCalendar(TimeZone.getTimeZone("GMT+2"));
        hour = israelTime.get(Calendar.HOUR_OF_DAY);
        minute = israelTime.get(Calendar.MINUTE);
        day = israelTime.get(Calendar.DAY_OF_MONTH);
        month = israelTime.get(Calendar.MONTH) + 1;






        Query q = myref1.child("Rides").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dst : snapshot.getChildren()) {
                    Ride r = dst.getValue(Ride.class);
                    if(r.getDay()< day && r.getMonth() <= month) {
                        r=null;
                        myref1.child("Rides").child(dst.getKey()).setValue(r);
                    }
                }
                for (DataSnapshot dst : snapshot.getChildren()) {
                    Ride r = dst.getValue(Ride.class);
                    r.setChosenRideKey(dst.getKey());
                    arrAllRides.add(r);
                }
                RidesAdapter myRideAdapter = new RidesAdapter(Searching.this, 0, 0,arrAllRides);
                lv_searching.setAdapter(myRideAdapter);
                lv_searching.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        chosenRide = arrAllRides.get(i);
                        Intent intent = new Intent(Searching.this, Ride_detalils_Activity.class);
                        startActivity(intent);



                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private static final String[] Places = new String[]{
            "Shkenya", "Yuvalim"
    };


    @Override
    public void onClick(View view) {
        int SearchListLength;
        
        if (view == searching_ride){
          SearchListLength= arrAllRides.size();
          stringFrom = searching_from.getText().toString();
          String stringTo = searching_to.getText().toString();
          search_Ride_list = new ArrayList<Ride>();

         //   Toast.makeText(Searching.this, "string from = "+stringFrom, Toast.LENGTH_LONG).show();



            for (int i=0; i <SearchListLength; i++){

                if (arrAllRides.get(i).getGoingFrom().toLowerCase(Locale.ROOT).equals(stringFrom.toLowerCase(Locale.ROOT)) && arrAllRides.get(i).getGoingTo().toLowerCase(Locale.ROOT).equals(stringTo.toLowerCase(Locale.ROOT))){
                    Ride found_ride = arrAllRides.get(i);
                    search_Ride_list.add(found_ride);
                }


            }
            RidesAdapter myRideAdapter = new RidesAdapter(Searching.this, 0, 0,search_Ride_list);
            lv_searching.setAdapter(myRideAdapter);
            

        }


    }
}









