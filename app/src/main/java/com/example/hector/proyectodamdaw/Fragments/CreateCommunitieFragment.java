package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.hector.proyectodamdaw.Activitys.SingleCommunitieActivity;
import com.example.hector.proyectodamdaw.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by Hector on 17/05/2018.
 */

public class CreateCommunitieFragment  extends Fragment {

    private AppDataSources bd;
    ProgressDialog Dialog;
    String userToken;
    EditText nameNewCommunity;
    EditText  descriptionNewCommunity;
    Button createComm;
    String jsonCreateCommunity;
    String name;
    String description;
    Comprobations comprobations;

    public CreateCommunitieFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_community_fragment, container, false);

        nameNewCommunity = (EditText)view.findViewById(R.id.edtNameNewCommunity);
        descriptionNewCommunity = (EditText)view.findViewById(R.id.edtDescriptionNeCommunity);
        createComm = (Button) view.findViewById(R.id.btnCreateCom);
        bd = new AppDataSources(getContext());
        comprobations = new Comprobations();
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        createComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nameEmpty;
                boolean descriptionEmpty;

                name=nameNewCommunity.getText().toString();
                description=descriptionNewCommunity.getText().toString();

                nameEmpty =comprobations.checkEmptyFields(name);
                if (nameEmpty == false){
                    descriptionEmpty =comprobations.checkEmptyFields(description);
                    if (descriptionEmpty == false){
                        jsonCreateCommunity= createJsonCreateCommunity(name,description);
                        try {
                            createCommunityAsync(jsonCreateCommunity);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast toastAlert = Toast.makeText(getContext(),  "El campo descripcion no puede estar vacio", Toast.LENGTH_SHORT);
                        toastAlert.show();                    }

                }else{
                    Toast toastAlert = Toast.makeText(getContext(), "El campo nombre no puede estar vaci", Toast.LENGTH_SHORT);
                    toastAlert.show();                }
            }
        });

    }

    private String createJsonCreateCommunity(String name, String description) {
        String jsonRegister;

        jsonRegister=  ("{\"name\": \"" + name + "\", \"description\": \"" + description + "\"}");
        return jsonRegister;
    }

    private void createCommunityAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.43.219:3000/community/new";

        GlobalVariables globales = GlobalVariables.getInstance();
        final int idUser=globales.getIdUserSqlite();

        final Cursor cursorUserToken = bd.searchUserToken(idUser);
        if (cursorUserToken.moveToFirst() != false){
            userToken = cursorUserToken.getString(0);
        }

        client.addHeader("Authorization", "Bearer " + userToken);
        client.post(getContext(), Url, entity , "application/json",new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Verificando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String jsCommId="";

                String strResponse = new String(responseBody);
                JSONObject jsResponse= null;
                try {
                    jsResponse = new JSONObject(strResponse);
                    jsCommId = jsResponse.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                bd.saveCommunity(1, true, 0, name, description, jsCommId);
                bd.saveCommunityUser(jsCommId, idUser, "owner", false);

                //Poner en id de la comunidad creada en variable gobal
                GlobalVariables globales = GlobalVariables.getInstance().getInstance();

                globales.setCommunityId(idUserSqlite);

                globales.setCommunityId(jsCommId);


                //Envia a SingleCommunityActivity, creas la comunidad y entras en ella directamente
                Intent intent = new Intent(getContext(), SingleCommunitieActivity.class );
                startActivity(intent);
                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String badResponse = "No se ha podido crear la comunidad, ha habido un problema al conectar con el servidor o comunidad ya existente. " + mensajeError;
                Toast toastAlerta = Toast.makeText(getContext(), badResponse, Toast.LENGTH_LONG);
                toastAlerta.show();
                Dialog.dismiss();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });

    }
}
