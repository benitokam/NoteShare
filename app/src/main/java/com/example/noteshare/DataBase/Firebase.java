package com.example.noteshare.DataBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.noteshare.model.DataModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Firebase {
    public Firebase(){}
    public Firebase(Context ctx) {
        this.ctx = ctx;

    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Collaboration");
   Context ctx;


    public DatabaseReference getMyRef() {
         return myRef;
    }

    public void addData(Map<String,Object> data) {
         myRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot snapshot) {
                 myRef.updateChildren(data);

             }

             @Override
             public void onCancelled(DatabaseError error) {
                 Toast.makeText(ctx,"Fail to add data",Toast.LENGTH_LONG).show();
             }
         });
    }



    //a revoir apres




}
