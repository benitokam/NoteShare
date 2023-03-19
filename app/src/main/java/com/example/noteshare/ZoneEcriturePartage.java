package com.example.noteshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.noteshare.DataBase.Firebase;
import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.model.DataModel;
import com.example.noteshare.model.DataModelPartage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ZoneEcriturePartage extends AppCompatActivity {
    public   Boolean isExist = false;
    protected String id ="";
    protected static boolean ischange = true;
    FloatingActionButton save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_ecriture_partage);
        EditText contenu = (EditText) findViewById(R.id.contenu_note);
        EditText titre = (EditText) findViewById(R.id.titre_note);
        Lire_Ecrire_BD data = new Lire_Ecrire_BD(this);

        Firebase database = new Firebase(this);
        save = findViewById(R.id.save);
        //recuper les donnée dejà existant
        if(getIntent().getExtras() != null) {
            String titres_text= getIntent().getExtras().getString("titre");
            String contenu_text = getIntent().getExtras().getString("contenu");
            id = getIntent().getExtras().getString("id");
            if(titres_text!=null){
                titre.setText(titres_text);
            }else{
                titre.setText("");
            }
            if (contenu_text != null) {
                contenu.setText(contenu_text);
            } else {
                contenu.setText("");
            }
        }
        //recupe data

       database.getMyRef().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

            DataModelPartage p = snapshot.getValue(DataModelPartage.class);
                 ischange = false;
                  Log.d("datass",p.getContenu());
                  titre.setText(p.getTitre());
                  contenu.setText(p.getContenu());
                titre.setSelection(titre.length());
                  contenu.setSelection(contenu.length());

   ischange = true;






            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        //Sauvegarder les données


        titre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(ischange){
                  database.getMyRef().child(id).child("contenu").setValue(contenu.getText().toString());

                  database.getMyRef().child(id).child("titre").setValue(titre.getText().toString());
              }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        contenu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!id.isEmpty()){
                    Log.d("id",id);
                    database.getMyRef().child(id).child("contenu").setValue(contenu.getText().toString());

                    database.getMyRef().child(id).child("titre").setValue(titre.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });







    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("id",true);
        startActivity(intent);
        //finish();
    }
}