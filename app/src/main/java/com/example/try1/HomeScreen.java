package com.example.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {
    Button btn_driving, btn_searching;
    public boolean shouldallowback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btn_driving = (Button)findViewById(R.id.btn_driving);
        btn_driving.setOnClickListener(this);
        btn_searching = (Button)findViewById(R.id.btn_searching);
        btn_searching.setOnClickListener(this);
       shouldallowback = false;

    }

    @Override
    public void onClick(View view) {
        if (view == btn_searching){
            Intent intent_Searching = new Intent(HomeScreen.this, Searching.class);
            startActivity(intent_Searching);
        }
        if(view == btn_driving){
            Intent intent_Driving = new Intent(HomeScreen.this, Driving.class);
            startActivity(intent_Driving);
        }

    }

    @Override

    public void onBackPressed() {
        if (shouldallowback) {
            super.onBackPressed();
        }


        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater() .inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.item_rides){
            Intent intent = new Intent(this, My_rides.class);
            startActivity(intent);
            return true;
        }
        if (id==R.id.item_back){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}