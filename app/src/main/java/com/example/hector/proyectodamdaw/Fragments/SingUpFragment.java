package com.example.hector.proyectodamdaw.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.CommunitiesActivity;
import com.example.hector.proyectodamdaw.Otros.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
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
 * Created by Hector on 03-Apr-18.
 */

public class SingUpFragment extends Fragment{

    EditText nameSingUp;
    EditText surnameSingUp;
    EditText emailSingUp;
    EditText repeatEmailSingUp;
    EditText passwSingUp;
    EditText repeatPasswSingUp;
    Button acceptSingUp;
    ProgressDialog Dialog;
    private String strUserName;
    private String strSurname;
    private String strEmail;
    private String strRepeatEmail;
    private String strPassw;
    private String strRepeatPassw;
    private String jsonRegister;
    private Boolean userNameEmpty;
    private Boolean surnameEmpty;
    private Boolean emailEmpty;
    private Boolean repeatEmailEmpty;
    private Boolean emailFormat;
    private boolean emailsSame;
    private Boolean passwEmpty;
    private Boolean repeatPasswEmpty;
    private  Boolean passwSame;
    Comprobations comprobations;
    public static final int miniumLenghtPassw = 8;
    private AppDataSources bd;

    public SingUpFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sing_up_fragment, container, false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        nameSingUp = (EditText)view.findViewById(R.id.edtNameSingUp);
        surnameSingUp = (EditText)view.findViewById(R.id.edtSurnameSingUp);
        emailSingUp = (EditText)view.findViewById(R.id.edtEmailSingUp);
        repeatEmailSingUp = (EditText)view.findViewById(R.id.edtRepeatEmailSingUp);
        passwSingUp = (EditText)view.findViewById(R.id.edtPasswSingUp);
        repeatPasswSingUp = (EditText)view.findViewById(R.id.edtRepeatPasswSingUp);
        acceptSingUp = (Button) view.findViewById(R.id.btnAcceptSingUp);

        comprobations = new Comprobations();
        bd = new AppDataSources(getContext());

        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


        acceptSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName=nameSingUp.getText().toString();
                strSurname=surnameSingUp.getText().toString();
                strEmail=emailSingUp.getText().toString();
                strRepeatEmail=repeatEmailSingUp.getText().toString();
                strPassw=passwSingUp.getText().toString();
                strRepeatPassw=repeatPasswSingUp.getText().toString();

                userNameEmpty =comprobations.checkEmptyFields(strUserName);
                if (userNameEmpty == false){
                    surnameEmpty = comprobations.checkEmptyFields(strSurname);
                    if (surnameEmpty == false){
                        emailEmpty = comprobations.checkEmptyFields(strEmail);
                        if (emailEmpty == false){
                            emailFormat = comprobations.checkEmailFormat(strEmail);
                            if (emailFormat==true){
                                repeatEmailEmpty =comprobations.checkEmptyFields(strRepeatEmail);
                                if (repeatEmailEmpty == false){
                                    emailsSame= comprobations.checkStringsEquals(strEmail, strRepeatEmail);
                                    if (emailsSame == true){
                                        passwEmpty =comprobations.checkEmptyFields(strPassw);
                                        if (passwEmpty==false){
                                            if (strPassw.length()>= miniumLenghtPassw){
                                                repeatPasswEmpty =comprobations.checkEmptyFields(strRepeatPassw);
                                                if (repeatPasswEmpty==false){
                                                    passwSame= comprobations.checkStringsEquals(strPassw, strRepeatPassw);
                                                    if (passwSame==true){

                                                        jsonRegister= createJsonSingUp(strUserName, strSurname, strPassw, strEmail);
                                                        try {
                                                            registerUserAsync(jsonRegister);
                                                        } catch (UnsupportedEncodingException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }else{
                                                        Toast toastError = Toast.makeText(getContext(),R.string.toastPasswSame, Toast.LENGTH_LONG);
                                                        toastError.show();
                                                    }
                                                }else{
                                                    Toast toastError = Toast.makeText(getContext(),R.string.toastRepeatPasswEmpty, Toast.LENGTH_LONG);
                                                    toastError.show();
                                                }
                                            }else{
                                                Toast toastError = Toast.makeText(getContext(),R.string.toastPasswLenght, Toast.LENGTH_LONG);
                                                toastError.show();
                                            }
                                        }else{
                                            Toast toastError = Toast.makeText(getContext(),R.string.toastPasswEmpty, Toast.LENGTH_LONG);
                                            toastError.show();
                                        }
                                    }else{
                                        Toast toastError = Toast.makeText(getContext(),R.string.toastEmailSame, Toast.LENGTH_LONG);
                                        toastError.show();
                                    }
                                }else{
                                    Toast toastError = Toast.makeText(getContext(),R.string.toastRepeatEmailEmpty, Toast.LENGTH_LONG);
                                    toastError.show();
                                }
                            }else{
                                Toast toastError = Toast.makeText(getContext(),R.string.toastEmailFormat, Toast.LENGTH_LONG);
                                toastError.show();
                            }
                        }else{
                            Toast toastError = Toast.makeText(getContext(),R.string.toastEmailEmpty, Toast.LENGTH_LONG);
                            toastError.show();
                        }
                    }else{
                        Toast toastError = Toast.makeText(getContext(),R.string.toastSurnameEmpty, Toast.LENGTH_LONG);
                        toastError.show();
                    }
                }else{
                    Toast toastError = Toast.makeText(getContext(),R.string.toastNameEmpty, Toast.LENGTH_LONG);
                    toastError.show();
                }
            }
        });

    }



    private String createJsonSingUp(String nombre, String apellido, String passw, String email) {
        String jsonRegister;

        jsonRegister=  ("{\"firstname\": \"" + nombre + "\", \"lastname\": \"" + apellido + "\", \"password\": \"" + passw + "\", \"email\": \"" + email + "\"}");
        return jsonRegister;
    }

    private void registerUserAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "https://domo-200915.appspot.com/register";

        client.post(getContext(), Url, entity , "application/json",new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Verificando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseContentError;
                String jsFirstName;
                String jsLastName;
                String jsEmail;
                String jsProfilePublic;
                String jsStikies;
                String jsToken;
                int idUserSqlite=0;
                String strResponse = new String(responseBody);

                try {
                    JSONObject jsResponse= new JSONObject(strResponse);
                    JSONObject features= new JSONObject();
                    features = jsResponse.getJSONObject("features");

                    jsStikies=features.getString("stickyQty");
                    jsFirstName= jsResponse.getString("first_name");
                    jsLastName= jsResponse.getString("last_name");
                    jsEmail= jsResponse.getString("email");
                    jsProfilePublic= jsResponse.getString("profile_is_public");
                    jsToken=jsResponse.getString("token");

                    bd.saveUserRegister(jsFirstName, jsLastName, jsEmail, Integer.parseInt(jsStikies), Boolean.valueOf(jsProfilePublic), jsToken, 0);

                    Toast toastResult = Toast.makeText(getContext(), R.string.toastRegisterOk, Toast.LENGTH_LONG);
                    toastResult.show();

                    Cursor cursorIdUserSqlite= bd.userIdSqlite(jsEmail);
                    if (cursorIdUserSqlite.moveToFirst() != false) {
                        idUserSqlite = cursorIdUserSqlite.getInt(0);
                        //Poner en id de usuario en variable gobal
                        GlobalVariables globales = GlobalVariables.getInstance();
                        globales.setIdUserSqlite(idUserSqlite);
                    }

                    //Envia a AllComminities
                    Intent intent = new Intent(getContext(), CommunitiesActivity.class );
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String badResponse = "No se ha podido recuperar los datos desde el servidor. " + mensajeError;
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
