package com.example.try1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase database;
    MyReceiver2 receiver_newtorkConnectivity = new MyReceiver2();
    DatabaseReference myref;
    EditText txt_name, txt_brings;
    String new_number;
    Button btn_login , btn_signup;
    private String m_Text = "";
    Intent my_notification;


    static User nowConnectedInThisPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myref = database.getReference();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver_newtorkConnectivity,intentFilter);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_brings = (EditText) findViewById(R.id.txt_brings);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        my_notification = new Intent(MainActivity.this, FirebaseNotification_Service.class);
        startService(my_notification);

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Title");
        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setView(input);
        



            if ((txt_name.getText().toString().trim().length() == 0) ||
                    (txt_brings.getText().toString().trim().length() == 0)) {
                Toast.makeText(this, "No Input", Toast.LENGTH_SHORT).show();
            } else {
                String new_username = txt_name.getText().toString();
                String new_password = txt_brings.getText().toString();

                Query q = myref.child("Users").orderByValue();
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean userALreadyExist = false;
                        for (DataSnapshot dst : snapshot.getChildren()) {
                            User u = dst.getValue(User.class);
                            if (u.getName().equals(new_username) ) {
                                if(view == btn_signup) {
                                    Toast.makeText(MainActivity.this, "username taken", Toast.LENGTH_LONG).show();
                                    userALreadyExist = true;
                                }
                                if(view == btn_login) {
                                    if (u.getPass().equals(new_password)) {
                                        Toast.makeText(MainActivity.this, "Successfully loged in", Toast.LENGTH_SHORT).show();
                                        nowConnectedInThisPhone = u;
                                        Toast.makeText(MainActivity.this, "now connected : "+u.getName(), Toast.LENGTH_SHORT).show();

                                        userALreadyExist = true;
                                        Intent intent_HomeScreen = new Intent(MainActivity.this, HomeScreen.class);
                                        startActivity(intent_HomeScreen);


                                        }
                                    else{
                                        Toast.makeText(MainActivity.this,"Wrong Name Or password",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                        if (userALreadyExist == false) {
                            if(view == btn_signup){

                                EditText inputTextField = new EditText(MainActivity.this);
                                inputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(" יצירת קשר ")
                                        .setMessage("הכנס מספר טלפון: ")
                                        .setView(inputTextField)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                new_number = inputTextField.getText().toString();
                                                Subscribe_New_User(new_username, new_password, new_number);

                                                Toast.makeText(MainActivity.this, "editext value is:" + new_number, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .create();
                                dialog.show();






                                
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }



    private void Subscribe_New_User(String new_username, String new_password, String new_number) {
        User newSubscriber = new User(new_username,new_password, new_number);
        myref.child("Users").child(new_username).setValue(newSubscriber);
        Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
        nowConnectedInThisPhone = newSubscriber;


    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver_newtorkConnectivity);

    }
}



