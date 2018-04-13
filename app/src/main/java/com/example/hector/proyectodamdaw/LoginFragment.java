package com.example.hector.proyectodamdaw;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
 * Created by Hector on 31-Mar-18.
 */

public class LoginFragment extends Fragment{

    TextView forgotPassw;
    EditText userLogin;
    EditText  passwLogin;
    Button acceptLogin;
    Button registerLogin;
    ProgressDialog Dialog;
    private String strUserLogin;
    private  String strUserPassw;
    private Boolean userLoginEmpty;
    private Boolean userPasswEmpty;
    private Boolean emailFormat;
    private String jsonLogin;
    public static final int miniumLenghtPassw = 8;
    Comprobations comprobations;
    loginUserAsync loginUserAsync;

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        forgotPassw =(TextView) view.findViewById(R.id.txvForgotPassw);
        userLogin = (EditText)view.findViewById(R.id.edtUserLogin);
        passwLogin = (EditText)view.findViewById(R.id.edtPasswLogin);
        acceptLogin = (Button) view.findViewById(R.id.btnAcceptLogin);
        registerLogin = (Button) view.findViewById(R.id.btnRegisterLogin);

        comprobations = new Comprobations();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



        forgotPassw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

               //AQUI ACCION A REALIZAR AL PULSAR "FORGOT PASSWORD"
            }

        });


        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Caambiar de fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentLogin, new SingUpFragment());
                transaction.commit();
            }
        });

        acceptLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                strUserLogin=userLogin.getText().toString();
                strUserPassw=passwLogin.getText().toString();

                userLoginEmpty =comprobations.checkEmptyFields(strUserLogin);
                if (userLoginEmpty == false){
                    emailFormat= comprobations.checkEmailFormat(strUserLogin);
                    if (emailFormat==true){
                        userPasswEmpty =comprobations.checkEmptyFields(strUserPassw);
                        if (userPasswEmpty == false){
                            if (strUserPassw.length()>= miniumLenghtPassw){
                                jsonLogin= createJsonLogin(strUserLogin, strUserPassw);

                                loginUserAsync = new loginUserAsync();
                                loginUserAsync.execute(jsonLogin);

                            }else{
                                Toast toastAlert = Toast.makeText(getContext(), R.string.toastLenghtPassw, Toast.LENGTH_SHORT);
                                toastAlert.show();
                            }
                        }else{
                            Toast toastAlert = Toast.makeText(getContext(),  R.string.toastPassw, Toast.LENGTH_SHORT);
                            toastAlert.show();
                        }
                    }else{
                        Toast toastAlert = Toast.makeText(getContext(),R.string.toastEmailFormat, Toast.LENGTH_LONG);
                        toastAlert.show();
                    }
                }else{
                    Toast toastAlert = Toast.makeText(getContext(),  R.string.toastUser, Toast.LENGTH_SHORT);
                    toastAlert.show();
                }
            }
        });

    }

    private String createJsonLogin(String usuario, String passw) {
        String strJsonLogin;

        strJsonLogin=  ("{\"email\": \"" + usuario + "\", \"password\": \"" + passw +"\"}");
        return strJsonLogin;
    }

    private String checkLoginCorreect(String JsonLogin) {
        String result="";
        InputStream inputStream = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://domo-200915.appspot.com/auth/login");
        //http://192.168.56.1:3000/auth/login
        //https://domo-200915.appspot.com/auth/login

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("jsonLogin",JsonLogin));

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

    private class loginUserAsync extends AsyncTask<String, Void, String> {
        String result = "";

        protected String doInBackground(String... argumentos) {

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.56.1:3000/auth/login");
                //http://192.168.56.1:3000/auth/login
                //https://domo-200915.appspot.com/auth/login

                // Add your data
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("jsonLogin", argumentos[0]));

                httppost.setEntity(new UrlEncodedFormEntity(params));

                //Set some headers to inform server about the type of the content
                //httppost.setHeader("Accept", "application/json");
                //httppost.setHeader("Content-type", "application/json");

                //Enviamos la info al server
                HttpResponse response = httpclient.execute(httppost);
                //Obtenemos una respuesta*/
                HttpEntity entity = response.getEntity();

                result = EntityUtils.toString(entity);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.i("ResponseObject: ", e.toString());
            }

            return result;
        }

        protected void onPostExecute(String mensaje) {

            //AQUI LAS ACCIONES A HACER CUANDO SE RECIVE LA INFORMACION DEL SERVIDOR
            //SI EL LOGIN ES CORRECTO, ENVIAR A ALLCOMMUNITIES ACTIVITY
            Toast toastResult = Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG);
            toastResult.show();

        }
    }


}
