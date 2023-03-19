package com.example.noteshare.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.noteshare.DataBase.Firebase;
import com.example.noteshare.DataBase.Lire_Ecrire_BD;
import com.example.noteshare.R;
import com.example.noteshare.adaptater.PatargeAdaptater;
import com.example.noteshare.adaptater.adaptater;
import com.example.noteshare.model.DataModel;
import com.example.noteshare.model.DataModelPartage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Document_Partage extends Fragment {
    @SuppressLint("Range")

    public static   Boolean isload = false;
    PatargeAdaptater adapter;
    Firebase dataPartage;
    ProgressBar bar;
    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_document__partage, container,false);
    RecyclerView liste =  v.findViewById(R.id.liste) ;
    //recupes les donnée dans la base de donnée
        dataPartage = new Firebase(getActivity());
       bar =  v.findViewById(R.id.progressBar);

    List<DataModel> contenu = new ArrayList<>();

    bar.setMax(100);
    bar.setProgress(0);
   //bar.setVisibility(View.VISIBLE);

    //Recupere les données dans firebase





    adapter  = new PatargeAdaptater(getActivity(),getAllData());

        bar.setVisibility(View.INVISIBLE);

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
    public List<DataModelPartage> getAllData(){
        List<DataModelPartage> contenu = new ArrayList<>();
        Firebase  dataPartage = new Firebase(getActivity());

        dataPartage.getMyRef().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

            //    Toast.makeText(getActivity(),"oui",Toast.LENGTH_LONG).show();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Map<String,Object> value = (Map<String, Object>) ds.getValue();

                    ArrayList<String> array = (ArrayList<String>) value.get("tabMembre");
                    Boolean m =(Boolean) value.get("like");



                    if(array.contains("mamadoucirecamara@gmail.com")){
                        contenu.add(new DataModelPartage(value.get("id").toString(),value.get("titre").toString(),value.get("contenu").toString(),new Date(),m,array));
                    }



                    //  Log.d("data",d.toString());


                }
                //rechercher la page
                if(!isload){

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenu, new Document_Partage(),null)
                            .setReorderingAllowed(true)
                            .commit();
                    isload= true;
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });

        return contenu;
    }



    @Override
    public void onViewCreated( View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




}