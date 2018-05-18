package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.hector.proyectodamdaw.Activitys.CommunitiesActivity;
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
    Button btnCreateCommunity;
    String jsonCreateCommunity;
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
        btnCreateCommunity = (Button) view.findViewById(R.id.btnCreateCommunity);
        bd = new AppDataSources(getContext());
        comprobations = new Comprobations();
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        String name;
        String description;
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
                //AQUI MENSAJE DESRIPCION NO VACIA
            }

        }else{
            //AQUI MENSAJE NOMBRE NO VACIO
        }



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
        int idUser=globales.getIdUserSqlite();

        Cursor cursorUserToken = bd.searchUserToken(idUser);
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

            //AQUI MIRAR SI LA RESPUESTA ES CORRECTA, ENTONCES GUARDAR EN BD Y REFRESCAR... (HAY KE PENSARLO TODABIA)---------------------------------------
                //HAY KE TENER EN CUENTA QUE CUANDO CREAS UNA COMUNIDAD TE AGREGA A ESA COMUNIDAD, POR EL TEMA DE LA BD LOCAL QUE HAY KE GUARDAR EN LA TABLA COMUNITYUSER Y TAMBIEN EN LA DE COMMUNITY

                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String badResponse = "No se ha podido enviar la informacion al servidor. " + mensajeError;
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
