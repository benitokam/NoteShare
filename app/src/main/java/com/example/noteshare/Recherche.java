package com.example.noteshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.adaptater.adaptater;
import com.example.noteshare.model.DataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recherche extends AppCompatActivity {

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        RecyclerView liste = (RecyclerView) findViewById(R.id.liste) ;
        TextView recherche = (TextView) findViewById(R.id.valueSearch);

        String value = getIntent().getExtras().getString("value");
        recherche.setText(recherche.getText()+" "+ value);
        Lire_Ecrire_BD db = new Lire_Ecrire_BD(this);
        List<DataModel> contenu = new ArrayList<>();
        Cursor cursor = db.getDataRecherche(value);

        while (cursor.moveToNext()){
            contenu.add(new DataModel(cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("titre")),
                    cursor.getString(cursor.getColumnIndex("contenu")),
                    new Date(cursor.getString(cursor.getColumnIndex("date_modif")))  ,
                    cursor.getInt(cursor.getColumnIndex("isLike")) > 0
            ));
        }



        if(contenu.size() < 1 ){

            Toast.makeText(this,getResources().getString(R.string.Aucun_element),Toast.LENGTH_LONG).show();
        }

        Log.d("contenu",contenu.size()+"");

        adaptater adapter  = new adaptater(this,contenu);





        liste.setLayoutManager(new LinearLayoutManager(this));
        liste.setAdapter(adapter );

    }
}