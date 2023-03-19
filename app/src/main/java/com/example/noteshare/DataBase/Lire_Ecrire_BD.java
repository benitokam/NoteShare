package com.example.noteshare.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.noteshare.model.DataModel;

import java.util.Date;

public class Lire_Ecrire_BD {
    public SQLiteDatabase maDataBase;
    private  note  notehelper; // La classe qui implemment la base de donnée

        public Lire_Ecrire_BD(Context ctx) {
         notehelper = new note(ctx, "note",null,1);
        }
        public SQLiteDatabase open() {
            maDataBase = notehelper.getWritableDatabase();
            return maDataBase;
        }

        //Inserer les données
    public void add(DataModel data) {
        ContentValues values = new ContentValues();
        values.put(notehelper.getTitre(),data.getTitre());
        values.put(notehelper.getContenu(),data.getContenu());
        values.put(notehelper.getIsLike(),data.getLike());
        values.put(notehelper.getDate(), String.valueOf(data.getDate()));

        open().insert(notehelper.getTable_Nom(), null, values);
        open().close();

    }

    public void update(String Titre, String Contenu, String id, Date date) {
        ContentValues values  = new ContentValues();
        values.put(notehelper.getContenu(),Contenu);
        values.put(notehelper.getTitre(),Titre);
        values.put(notehelper.getDate(),String.valueOf(date));

       open().update(notehelper.getTable_Nom(), values, "id = ?", new String[]{id});
    }
    public void updateImportant(String id,Boolean etat) {
        ContentValues values  = new ContentValues();
         values.put(notehelper.getIsLike(),etat);

        open().update(notehelper.getTable_Nom(), values, "id = ?", new String[]{id});
    }
    //Supprimer une donnée

    public void delete(String value) {
            open().delete(notehelper.getTable_Nom(),notehelper.getTitre()+"=?",new String[]{value});
    }

    //accerder au donnée
    public Cursor getData(String Requette) {
        Cursor c = open().rawQuery(Requette+" "+notehelper.getTable_Nom()+";",null);


        return c;
    }
    public Cursor getDataImportant(String Requette) {
        Cursor c = open().rawQuery(Requette+" "+notehelper.getTable_Nom()+" where "+notehelper.getIsLike()+"=true",null);


        return c;
    }
    public Cursor getDataRecherche(String value) {
        Cursor c = open().rawQuery("Select * from "+notehelper.getTable_Nom()+" where "+notehelper.getTitre()+" Like '%"+value+"%' ",null);


        return c;
    }
}
