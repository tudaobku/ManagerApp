package com.example.managerapp.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.managerapp.Common;
import com.example.managerapp.Model.Order;
import com.example.managerapp.R;
import com.example.managerapp.order.OrderFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommingOrder extends Service implements ChildEventListener {

    DatabaseReference orderList;
    public CommingOrder() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        orderList = FirebaseDatabase.getInstance().getReference("Order");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        orderList.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        if(snapshot.child("supplierID").getValue().equals(Common.currentSupplier.getSupplierID())){
            Intent startIntent = new Intent(getBaseContext(), OrderFragment.class);
            PendingIntent content = PendingIntent.getActivity(getBaseContext(), 0,startIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder((getBaseContext()));
            builder.setAutoCancel((true))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("ManagerApp")
                    .setContentInfo("")
                    .setContentText("You have new order")
                    .setContentIntent(content)
                    .setSmallIcon(R.drawable.food);
            NotificationManager notificationManager = (NotificationManager)getBaseContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
        }

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}
