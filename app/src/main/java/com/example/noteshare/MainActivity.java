package com.example.noteshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteshare.fragment.Contenu;
import com.example.noteshare.fragment.Document_Partage;
import com.example.noteshare.fragment.Important;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent zoneEcriture = new Intent(this, com.example.noteshare.zoneEcriture.class);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    // Détecter si le téléphone est secoué
                    if (x > 40 || y > 40 || z > 40) {
                        startActivity(zoneEcriture);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Pas utilisé
                }
            }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }





        EditText  recherche = (EditText) findViewById(R.id.recherche);
        TextView notes =(TextView) findViewById(R.id.notes);
        FloatingActionButton add =  findViewById(R.id.add);
        TextView important =(TextView) findViewById(R.id.important);
        TextView partage = (TextView) findViewById(R.id.partage);
        ImageView user = (ImageView)  findViewById(R.id.user);
        notes.setTextColor(getResources().getColor(R.color.orange));

        //Permission





        //acceder au zone de connection
        Intent zoneConnection = new Intent(this, Login.class);
        //intent pour accerder au zone de texte
             //Zone de recherche$=
         Intent zoneRecherche = new Intent (this,Recherche.class);
         // Profil


         //redirection vers document partagé
        if(getIntent().getExtras() != null) {
            Boolean redirect = getIntent().getExtras().getBoolean("colab");
            if(redirect){
                partage.setTextColor(getResources().getColor(R.color.orange));
                important.setTextColor(getResources().getColor(R.color.black));
                notes.setTextColor(getResources().getColor(R.color.black));
                Document_Partage.isload=false;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenu, new Document_Partage())

                        .commit();
            }

        }

        recherche.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    zoneRecherche.putExtra("value",recherche.getText().toString());
                  startActivity(zoneRecherche);

                  return true;
                }
                return false;
            }
        });

       important.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              important.setTextColor(getResources().getColor(R.color.orange));
           notes.setTextColor(getResources().getColor(R.color.black));
                partage.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenu, new Important())

                        .commit();
            }
        });
        partage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partage.setTextColor(getResources().getColor(R.color.orange));
                important.setTextColor(getResources().getColor(R.color.black));
                notes.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenu, new Document_Partage())

                        .commit();
            }
        });

     notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               notes.setTextColor(getResources().getColor(R.color.orange));
                important.setTextColor(getResources().getColor(R.color.black));
                partage.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenu, new Contenu(),null)
                         .setReorderingAllowed(true)
                        .commit();
            }
        });
   //acceder au zone d'ecriture
     add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(zoneEcriture);
         }
     });

    //Zone de connection
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
                Boolean isConnected = sharedPref.getBoolean("isConnected", false);
                String  email =  sharedPref.getString("email", "");
                if(isConnected){
                    AlertDialog.Builder
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Deconnexion");

                    builder.setPositiveButton("Deconnexion", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences prf = getSharedPreferences("Login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edipref = prf.edit();
                            edipref.putBoolean("isConnected",false);
                            edipref.apply();
                        }
                    });

                    builder.show();
                }else{
                    startActivity(zoneConnection);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
       finishAffinity();
        //finish();
    }


}