package com.example.noteshare.fragment;

import static com.example.noteshare.R.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;


import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteshare.DataBase.Firebase;
import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.Login;
import com.example.noteshare.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.noteshare.adaptater.PatargeAdaptater;
import com.example.noteshare.adaptater.adaptater;
import com.example.noteshare.model.DataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class Contenu extends Fragment {
 @SuppressLint("Range")

  private  Boolean isload = false;
    adaptater  adapter;
    AlertDialog.Builder builder;
    PatargeAdaptater adaptaterPatarge = new PatargeAdaptater();
    EditText input;
    SharedPreferences sharedPref;
    Boolean isConnected ;
    String  email;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(layout.fragment_contenu,container,false);
         RecyclerView liste = (RecyclerView) v.findViewById(id.liste) ;
      //recupes les donnée dans la base de donnée
          Lire_Ecrire_BD db = new Lire_Ecrire_BD(getActivity());
        List<DataModel> contenu = new ArrayList<>();
        Cursor cursor = db.getData("SELECT * FROM ");

        while (cursor.moveToNext()){
            contenu.add(new DataModel(cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("titre")),
                    cursor.getString(cursor.getColumnIndex("contenu")),
                    new Date(cursor.getString(cursor.getColumnIndex("date_modif")))  ,
                    cursor.getInt(cursor.getColumnIndex("isLike")) > 0
                    ));
        }

        //Recupere les données dans firebase





        adapter  = new adaptater(getActivity(),contenu);



   //
        if(getActivity().getIntent().getExtras()!=null){
            boolean i = getActivity().getIntent().getExtras().getBoolean("id");
            if(i){
              adapter.notifyDataSetChanged();
            }

        }

        liste.setLayoutManager(new LinearLayoutManager(getActivity()));
        liste.setAdapter(adapter );


        return v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
         switch (item.getItemId()){
             case 101:
                   Toast.makeText(getActivity(),getResources().getString(string.supprimer),Toast.LENGTH_LONG).show();
                   adapter.removeItem(item.getGroupId());
                   return true;
             case 102:
              sharedPref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
            isConnected = sharedPref.getBoolean("isConnected", false);
               email =  sharedPref.getString("email", "");
                 if(isConnected && !email.isEmpty()){

                     builder = new AlertDialog.Builder(getContext());
                     builder.setTitle("Saisir le mail de votre colaborateur");
                     EditText input = new EditText(getContext());
                     input.setInputType(InputType.TYPE_CLASS_TEXT);
                     builder.setView(input);
                     builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             String mail = input.getText().toString();
                             Toast.makeText(getActivity(),getResources().getString(string.Partager) ,Toast.LENGTH_LONG).show();

                             adapter.partageItem(item.getGroupId(),mail);
                         }
                     });
                     builder.show();
                 }else{
                     Toast.makeText(getActivity(),getResources().getString(string.pas_connect),Toast.LENGTH_LONG).show();
                     Intent i = new Intent (getActivity(), Login.class);
                     getActivity().startActivity(i);
                 }


             return true;
             case 103:

                 adaptaterPatarge.removeItem(item.getGroupId());
                 return true;
             case 104 : {
                 sharedPref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                 isConnected = sharedPref.getBoolean("isConnected", false);
                 email =  sharedPref.getString("email", "");
                 if(isConnected && !email.isEmpty()){
                     builder = new AlertDialog.Builder(getContext());
                     builder.setTitle("Saisir le mail de votre colaborateur");
                     input = new EditText(getContext());
                     input.setInputType(InputType.TYPE_CLASS_TEXT);
                     builder.setView(input);
                     builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {
                             String mail = input.getText().toString();
                             Toast.makeText(getActivity(),getResources().getString(string.Ajout_reussi),Toast.LENGTH_LONG).show();

                             adaptaterPatarge.addMembre(item.getGroupId(),mail);
                         }
                     });
                     builder.show();
                 }else{
                     Toast.makeText(getActivity(),getResources().getString(string.pas_connect),Toast.LENGTH_LONG).show();
                     Intent i = new Intent (getActivity(), Login.class);
                     getActivity().startActivity(i);
                 }


                     return true;
             }

         }

        return true;
    }

    @Override
    public void onViewCreated( View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}