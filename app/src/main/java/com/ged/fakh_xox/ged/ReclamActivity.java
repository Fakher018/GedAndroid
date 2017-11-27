package com.ged.fakh_xox.ged;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ReclamActivity extends AppCompatActivity {
   private String IP="http://10.0.2.2:18080/ged-web/rest/reclamation";
   private EditText EditSujet;
   private EditText EditMsg;
   private Spinner SpinnerType;
   private Button btnValdier;
   private ListView listreclam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclam);
        EditSujet = (EditText)findViewById(R.id.EditTextSujet);
        EditMsg = (EditText)findViewById(R.id.EditTextMsg);
        SpinnerType = (Spinner)findViewById(R.id.SpinnerType);
        btnValdier = (Button)findViewById(R.id.BtnValider);
        listreclam = (ListView)findViewById(R.id.ListReclam);
        btnValdier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("subject",EditSujet.getText().toString());
                jsonObject.addProperty("message",EditMsg.getText().toString());
                jsonObject.addProperty("type",SpinnerType.getSelectedItem().toString());
                Ion.with(getApplicationContext())
                        .load(IP)
                        .setJsonObjectBody(jsonObject)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if(result!=null) {
                                    if(result.get("Resultat").getAsString().equals("200")) {
                                        Toast.makeText(getApplicationContext(),
                                                "Reclamation Added Successfuly To DataBase",Toast.LENGTH_LONG).show();
                                    }
                                }
                                else if(e!=null){
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
     Ion.with(getApplicationContext())
             .load("GET",IP)
             .asJsonArray()
             .setCallback(new FutureCallback<JsonArray>() {
                 @Override
                 public void onCompleted(Exception e, JsonArray result) {
                     ArrayList<String> list = new ArrayList<String>();

                     if(result!=null) {
                         for(int i=0;i<result.size();i++) {
                            list.add(result.get(i).getAsJsonObject().get("subject").getAsString());
                         }
                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                                 list);
                         listreclam.setAdapter(adapter);
                     }
                     else if(e!=null) {
                         Toast.makeText(getApplicationContext(),
                                 e.getMessage(),Toast.LENGTH_LONG).show();
                     }
                 }
             });
    }
}
