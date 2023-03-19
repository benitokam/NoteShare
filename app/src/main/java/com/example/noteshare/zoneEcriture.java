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

import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.fragment.Contenu;
import com.example.noteshare.model.DataModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class zoneEcriture extends AppCompatActivity {
   public   Boolean isExist = false;
   protected String id ="";
   FloatingActionButton save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone_ecriture);
        EditText contenu = (EditText) findViewById(R.id.contenu_note);
        EditText titre = (EditText) findViewById(R.id.titre_note);
        Lire_Ecrire_BD data = new Lire_Ecrire_BD(this);
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
        if(!id.isEmpty()){
            save.setVisibility(View.GONE);
        }
        //Sauvegarder les données
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titre.getText().toString().length()<1){
                    Toast.makeText(getApplication(), getResources().getString( R.string.document_pas_enregistrer),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplication(),getResources().getString(R.string.Enregistement_reussie),Toast.LENGTH_LONG).show();

                    data.add(new DataModel("0",titre.getText().toString(),contenu.getText().toString(),new Date(),false));
                }



            }
        });

        titre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!id.isEmpty()){
                    data.update(titre.getText().toString(),contenu.getText().toString(),id,new Date());
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
                     data.update(titre.getText().toString(),contenu.getText().toString(),id,new Date());
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