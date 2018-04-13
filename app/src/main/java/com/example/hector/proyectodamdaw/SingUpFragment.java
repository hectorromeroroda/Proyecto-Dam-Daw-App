package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

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

                                                        registerUser(jsonRegister);

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

    private String registerUser(String JsonSingUp) {
        String result="";
        InputStream inputStream = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.56.1:3000/auth/register");
        //http://192.168.56.1:3000/auth/register
        //https://domo-200915.appspot.com/auth/register

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("jsonRegister",JsonSingUp));

            httppost.setEntity(new UrlEncodedFormEntity(params));

            //Set some headers to inform server about the type of the content
            //httppost.setHeader("Accept", "application/json");
            //httppost.setHeader("Content-type", "application/json");

            //Enviamos la info al server
            HttpResponse response = httpclient.execute(httppost);
            /*y obtenemos una respuesta*/
            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity);
            Toast toastResult = Toast.makeText(getContext(),result, Toast.LENGTH_LONG);
            toastResult.show();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            Toast toastError = Toast.makeText(getContext(),  e.toString()  , Toast.LENGTH_SHORT);
            toastError.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Toast toastError = Toast.makeText(getContext(),  e.toString()  , Toast.LENGTH_SHORT);
            toastError.show();
        }

        return result;

    }
}
