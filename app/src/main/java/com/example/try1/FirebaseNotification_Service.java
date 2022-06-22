package com.example.try1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseNotification_Service extends Service {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Query q;
    NotificationCompat.Builder builder;
    NotificationManager manager;
    User lastJoined;
    public FirebaseNotification_Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        q = myRef.child("Rides").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dst : snapshot.getChildren()) {
                    Ride r = dst.getValue(Ride.class);
                    if(r.getDriver().equals(MainActivity.nowConnectedInThisPhone)) {
                        lastJoined = r.getArrHitchikers().get(r.getArrHitchikers().size()-1);
                        builder= new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.contactdriver)
                                .setContentTitle("I Am Going")
                                .setContentText(lastJoined.getName() +" has joined your ride")
                                .setDefaults(NotificationCompat.DEFAULT_ALL)
                                .setPriority(NotificationCompat.PRIORITY_MAX);
                        Intent notification_intent = new Intent(getBaseContext(),MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,notification_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    public void onStop(){
    }
}


