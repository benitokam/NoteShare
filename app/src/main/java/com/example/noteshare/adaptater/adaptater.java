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
import com.example.noteshare.model.DataModel;
import com.example.noteshare.model.DataModelPartage;
import com.example.noteshare.zoneEcriture;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adaptater extends RecyclerView.Adapter<adaptater.ViewHolder>  {

     protected List<DataModel> tittle;
     Context context;

    Lire_Ecrire_BD dataDB;
    Firebase colabData;

      public adaptater(Context ctx, List<DataModel> tittle) {
          this.context = ctx;
          this.tittle = tittle;
          dataDB = new Lire_Ecrire_BD(context);
         colabData = new Firebase(context);
      }
      public  adaptater() {}



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
         CardView notes;
         TextView titre;
         TextView contenu;
         TextView date;
         ImageView aime;
        public ViewHolder (View view) {
            super(view);
            titre = view.findViewById(R.id.titre);
            notes = view.findViewById(R.id.notes);
            contenu = view.findViewById(R.id.contenu);
            date = view.findViewById(R.id.date);
            aime =view.findViewById(R.id.aime);
            notes.setOnCreateContextMenuListener(this);
            //Menu Contextuel;


        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

              contextMenu.add(getAdapterPosition(),101,0,"Supprimer");
              contextMenu.add(getAdapterPosition(),102,1,"Partager");
        }
    }





    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          DataModel data = tittle.get(position);
             holder.titre.setText(data.getTitre());
             holder.contenu.setText(data.getContenu());
             holder.date.setText(data.getDate().toString().substring(0,16));
        if (data.getLike()) {
            holder.aime.setBackgroundResource(R.drawable.favorite_fill);
        } else {
            holder.aime.setBackgroundResource(R.drawable.favorie);
        }
        Intent i = new Intent(context, zoneEcriture.class);
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
        holder.aime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setLike(!data.getLike());
                dataDB.updateImportant(data.getId(),data.getLike());
                notifyDataSetChanged();
                if (data.getLike()) {
                    holder.aime.setBackgroundResource(R.drawable.favorite_fill);
                } else {
                    holder.aime.setBackgroundResource(R.drawable.favorie);
                }
            }

        });

    }

    @Override
    public int getItemCount() {
        return tittle.size();
    }
  public void removeItem(int position) {
     DataModel m = tittle.get(position);

     dataDB.delete(m.getTitre());
      Intent intent = new Intent(context, MainActivity.class);
      intent.putExtra("id",true);
      context.startActivity(intent);
  }

  // Pour partarger un document
    public void partageItem(int position,String mailInvite) {
        SharedPreferences sharedPref = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        Boolean isConnected = sharedPref.getBoolean("isConnected", false);
        String  email =  sharedPref.getString("email", "");
        if(isConnected && !email.isEmpty()){

            DataModel m = tittle.get(position);
            ArrayList<String> membre = new ArrayList<>();
            membre.add(email);
            membre.add(mailInvite);
            DataModelPartage dataModelPartage = new DataModelPartage(m.getId(),m.getTitre(),m.getContenu(),m.getDate()
            ,m.getLike(),membre
            );
            Map<String,Object> data = new HashMap<>();
            data.put(m.getId(),dataModelPartage);
            colabData.addData(data);
        }else{
            Intent i = new Intent (context, Login.class);
            context.startActivity(i);
        }

    }

}
