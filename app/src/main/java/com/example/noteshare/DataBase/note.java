package com.example.noteshare.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.Date;

public class note extends SQLiteOpenHelper {
   private  static  final int Base_Version = 2;
   private static final String Base_Nom ="notes.db";
   private static final String Table_Nom= "contenus";

    //les noms des attribue
    private static  String id="id";
    private  static String titre = "titre";
    private static  String contenu = "contenu";
    private static  String isLike ="isLike";
    private  static  String dates = "date_modif";

     //Requette pour creer la base de donnée

    private static final String Requete_Creation_Bd = "create table "+
            Table_Nom+ " ("+ id+" integer primary key autoincrement, "+ titre+
             " text ,"+ contenu +" text ,"+isLike+" boolean ,"+dates+" Date )";



    public note(Context context,  String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(Requete_Creation_Bd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         db.execSQL("drop table "+Table_Nom+ ";");

         onCreate(db);
    }

    public static String getTable_Nom() {
        return Table_Nom;
    }

    public static String getId() {
        return id;
    }

    public static String getTitre() {
        return titre;
    }

    public static String getContenu() {
        return contenu;
    }

    public static String getIsLike() {
        return isLike;
    }

    public static String getDate() {
        return dates;
    }

    public static String getBase_Nom() {
        return Base_Nom;
    }
}
