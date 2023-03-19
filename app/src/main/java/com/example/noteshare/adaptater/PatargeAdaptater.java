package com.example.noteshare.adaptater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteshare.DataBase.Firebase;
import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.Login;
import com.example.noteshare.MainActivity;
import com.example.noteshare.R;
import com.example.noteshare.ZoneEcriturePartage;
import com.example.noteshare.fragment.Document_Partage;
import com.example.noteshare.model.DataModel;
import com.example.noteshare.model.DataModelPartage;
import com.example.noteshare.zoneEcriture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatargeAdaptater extends RecyclerView.Adapter<PatargeAdaptater.ViewHolder>  {

    protected static List<DataModelPartage> tittle;
     static Context context;

    Lire_Ecrire_BD dataDB;
    Firebase colabData;


    public PatargeAdaptater(Context ctx, List<DataModelPartage> tittle) {
        this.context = ctx;
        this.tittle = tittle;
        dataDB = new Lire_Ecrire_BD(context);
        colabData = new Firebase(context);
    }

    public  PatargeAdaptater() {

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        CardView notes;
        TextView titre;
        TextView contenu;
        TextView date;
        ImageView aime;
        public ViewHolder (View view) {
            super(view);
            titre = view.findViewById(R.id.titre);
            notes = view.findViewById(R.id.notesPatarge);
            contenu = view.findViewById(R.id.contenu);
            date = view.findViewById(R.id.date);
            aime =view.findViewById(R.id.partage);
            notes.setOnCreateContextMenuListener(this);
            //Menu Contextuel;


        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.add(getAdapterPosition(),103,1,"Supprime");
            contextMenu.add(getAdapterPosition(),104,2,"Ajouter un membre");
        }
    }





    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notepartage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataModel data = tittle.get(position);
        holder.titre.setText(data.getTitre());
        holder.contenu.setText(data.getContenu());
        holder.date.setText(data.getDate().toString().substring(0,16));

        Intent i = new Intent(context, ZoneEcriturePartage.class);
        i.putExtra("titre",data.getTitre());
        i.putExtra("contenu",data.getContenu());
        i.putExtra("date",data.getDate());
        i.putExtra("id",data.getId());

        //Ouvrir le zone d'ecriture
        holder.notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(i);
            }
        });
        //rendre une note important


    }

    @Override
    public int getItemCount() {
        return tittle.size();
    }

    public  void removeItem(int position) {
       DataModel m = tittle.get(position);
        Firebase cola = new Firebase();
        Log.d("id",""+position);
        DatabaseReference ref = cola.getMyRef().child(m.getId());
        ref.removeValue();
        tittle.remove(position);
        Intent f = new Intent(context, MainActivity.class);
        f.putExtra("colab",true);
        context.startActivity(f);
    }

    public void addMembre(int position,String mailInvite) {
        DataModelPartage m =  tittle.get(position);
        Firebase cola = new Firebase();
        ArrayList membre = m.getTabMembre();
        if(!membre.contains(mailInvite)){
            membre.add(mailInvite);
        }


        Log.d("p", "-------------"+membre.toString());
       DataModelPartage model = new DataModelPartage(m.getId(),m.getTitre(),m.getContenu(),m.getDate(),m.getLike(),membre);
        Map<String,Object> data = new HashMap<>();
        data.put(m.getId(),model);

        cola.getMyRef().updateChildren(data);

        Intent f = new Intent(context, MainActivity.class);
        f.putExtra("colab",true);
        context.startActivity(f);




    }
    // Pour partarger un document


}
