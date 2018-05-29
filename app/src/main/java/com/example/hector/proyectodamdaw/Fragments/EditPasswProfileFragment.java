package com.example.hector.proyectodamdaw.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Otros.Comprobations;
import com.example.hector.proyectodamdaw.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditPasswProfileFragment extends Fragment{

    Button btnAcceptChanges;
    EditText oldPasswEdit;
    EditText passwNewEdit;
    EditText repeatNewPasswEdit;
    private String strOldPassw;
    private String strNewPassw;
    private String strRepeatNewPassw;
    private boolean oldPasswEmpty;
    private boolean newPasswEmpty;
    private  Boolean passwSame;
    private  String jsonEditPassw;
    Comprobations comprobations;
    public static final int miniumLenghtPassw = 8;

    public EditPasswProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_passw_profile_fragment, container, false);

        btnAcceptChanges = (Button) view.findViewById(R.id.btnAcceptChanges);
        oldPasswEdit = (EditText)view.findViewById(R.id.edtOldPasswEdit);
        passwNewEdit = (EditText)view.findViewById(R.id.edtPasswNewEdit);
        repeatNewPasswEdit = (EditText)view.findViewById(R.id.edtRepeatNewPasswEdit);

        comprobations = new Comprobations();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnAcceptChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strOldPassw=oldPasswEdit.getText().toString();
                strNewPassw=passwNewEdit.getText().toString();
                strRepeatNewPassw=repeatNewPasswEdit.getText().toString();

                oldPasswEmpty =comprobations.checkEmptyFields(strOldPassw);
                if (oldPasswEmpty == false){
                    if (strOldPassw.length()>= miniumLenghtPassw){
                        newPasswEmpty =comprobations.checkEmptyFields(strNewPassw);
                        if (newPasswEmpty == false){
                            if (strNewPassw.length()>= miniumLenghtPassw){
                                passwSame= comprobations.checkStringsEquals(strNewPassw, strRepeatNewPassw);
                                if (passwSame==true){

                                    //CREAR CONEXION HTTP-PUT PARA ENVIAR LOS DATOS


                                }else{
                                    Toast toastError = Toast.makeText(getContext(),R.string.toastPasswSame, Toast.LENGTH_LONG);
                                    toastError.show();
                                }
                            }else{
                                Toast toastError = Toast.makeText(getContext(),R.string.toatsNewPasswLenght, Toast.LENGTH_LONG);
                                toastError.show();
                            }
                        }else{
                            Toast toastError = Toast.makeText(getContext(),R.string.toatsNewPasswEmpty, Toast.LENGTH_LONG);
                            toastError.show();
                        }
                    }else{
                        Toast toastError = Toast.makeText(getContext(),R.string.toatsOldPasswLenght, Toast.LENGTH_LONG);
                        toastError.show();
                    }
                }else{
                    Toast toastError = Toast.makeText(getContext(),R.string.toatsOldPasswEmpty, Toast.LENGTH_LONG);
                    toastError.show();
                }
            }


        });


    }


    private class editPasswProfileAsync extends AsyncTask<String, Void, String> {
        String result = "";

        protected String doInBackground(String... argumentos) {

            try {

                URL url = new URL("http://192.168.1.39:3000/profile");
                //http://192.168.56.1:3000/profile
                //https://domo-200915.appspot.com/profile

                HttpClient httpclient = new DefaultHttpClient();
                HttpPut put= new HttpPut(String.valueOf(url));

                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("typeprofile", strOldPassw));
                pairs.add(new BasicNameValuePair("typeprofile", strNewPassw));
                put.setEntity(new UrlEncodedFormEntity(pairs));

                HttpResponse response = httpclient.execute(put);

                //Obtenemos una respuesta*/
                HttpEntity entity = response.getEntity();

                result = EntityUtils.toString(entity);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.i("ResponseObject: ", e.toString());
            }

            return result;
        }
    }

    protected void onPostExecute(String mensaje) {
        String responseContentError;
        String jsToken;
        String profileState;

        try {
            JSONObject jsResponse= new JSONObject(mensaje);
            responseContentError=jsResponse.getString("messageError");
            Toast toastResult = Toast.makeText(getContext(), responseContentError, Toast.LENGTH_LONG);
            toastResult.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //CREO QUE LA RESPUESTA ESPERADA TIENE QUE SER UN 200 OK O ALGO ASI
            JSONObject jsResponse= new JSONObject(mensaje);
            profileState= jsResponse.getString("profile_is_public");

            //SI LA RESPUESTA ES CORRECTA " EL ANTIGUO PASSW ES CORRECTO
            //NOTIFICAR CAMBIO CORRECTO AL USUARIO

            Toast toastResult = Toast.makeText(getContext(), R.string.ToastRespuestaProfileStae, Toast.LENGTH_LONG);
            toastResult.show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
