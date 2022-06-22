package com.example.try1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class My_rides extends HomeScreen{
    FirebaseDatabase database;
    DatabaseReference myref1;
    ArrayList<Ride> arrAllRides;
    ListView lv_my_rides;
    int chosenLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_rides);
        database = FirebaseDatabase.getInstance();
        myref1 = database.getReference();
        arrAllRides = new ArrayList<>();
        lv_my_rides = (ListView)findViewById(R.id.lv_my_rides);

        shouldallowback = true;





        Query q = myref1.child("Rides").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dst : snapshot.getChildren()) {
                    Ride r = dst.getValue(Ride.class);
                    if (r.getDriver().getName().equals(MainActivity.nowConnectedInThisPhone.getName()))
                        arrAllRides.add(r);

                }
                RidesAdapter myRideAdapter = new RidesAdapter(My_rides.this, 0, 0,arrAllRides);
                lv_my_rides.setAdapter(myRideAdapter);
                lv_my_rides.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String allHitchHickers="";
                        for (int num=0;num<arrAllRides.size();num++){
                            allHitchHickers+=arrAllRides.get(i).getArrHitchikers().get(num).getName();
                            allHitchHickers+=" - ";
                            allHitchHickers+=arrAllRides.get(i).getArrHitchikers().get(num).getNumber();

                        }

                        AlertDialog dialog = new AlertDialog.Builder(My_rides.this)
                                .setTitle(" Who Joined Your Ride: ")
                                .setMessage(allHitchHickers)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog.show();



                    }
                });

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
