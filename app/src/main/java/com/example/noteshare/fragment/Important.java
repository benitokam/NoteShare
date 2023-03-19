package com.example.noteshare.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.R;
import com.example.noteshare.adaptater.adaptater;
import com.example.noteshare.model.DataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Important extends Fragment {

    @SuppressLint("Range")

    private  Boolean isload = false;
    adaptater  adapter;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contenu,container,false);
        RecyclerView liste = (RecyclerView) v.findViewById(R.id.liste) ;
        //recupes les donnée dans la base de donnée
        Lire_Ecrire_BD db = new Lire_Ecrire_BD(getActivity());
        List<DataModel> contenu = new ArrayList<>();
        Cursor cursor = db.getDataImportant("SELECT * FROM ");

        while (cursor.moveToNext()){
            contenu.add(new DataModel(cursor.getString(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("titre")),
                    cursor.getString(cursor.getColumnIndex("contenu")),
                    new Date(cursor.getString(cursor.getColumnIndex("date_modif")))  ,
                    cursor.getInt(cursor.getColumnIndex("isLike")) > 0
            ));
        }





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
                Toast.makeText(getActivity(),getResources().getString(R.string.supprimer),Toast.LENGTH_LONG).show();
                adapter.removeItem(item.getGroupId());
                return true;

        }

        return true;
    }

    @Override
    public void onViewCreated( View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Toast.makeText(getActivity(),"oui",Toast.LENGTH_LONG).show();

    }
}