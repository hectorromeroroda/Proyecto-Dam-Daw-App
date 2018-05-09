package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
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
    CheckBox rememberMe;
    ProgressDialog Dialog;
    private String strUserLogin;
    private  String strUserPassw;
    private Boolean userLoginEmpty;
    private Boolean userPasswEmpty;
    private Boolean emailFormat;
    private String jsonLogin;
    public static final int miniumLenghtPassw = 8;
    Comprobations comprobations;
    private AppDataSources bd;

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
        rememberMe = (CheckBox) view.findViewById(R.id.ckbRememberMe);

        comprobations = new Comprobations();
        bd = new AppDataSources(getContext());

        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

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

                Fragment fragmentSingUp = new SingUpFragment();
                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.contentLogin);
                loadFragment(fragmentSingUp);
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

                                try {
                                    loginUserAsync(jsonLogin);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

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

    private void loadFragment(Fragment newFragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentLogin, newFragment,newFragment.getClass().getName())
                .addToBackStack(null)
                .commit();
    }

    private String createJsonLogin(String usuario, String passw) {
        String strJsonLogin;

        strJsonLogin=  ("{\"email\": \"" + usuario + "\", \"password\": \"" + passw +"\"}");
        return strJsonLogin;
    }

    private void loginUserAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity ent = new StringEntity(datos);

        String Url = "http://192.168.56.1:3000/login";

        client.post(getContext(), Url, ent , "application/json",new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Descargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //JSONObject jsResponse;
                //
                String responseContentError;
                String jsToken;
                String jsFirstName;
                String jsLastName;
                String jsEmail;
                String jsProfilePublic;
                String jsStikies;
                JSONArray jsInvited = new JSONArray();
                JSONArray jsComunities = new JSONArray();
                String strResponse = new String(responseBody);

                try {

                   // JSONObject jsResponse = new JSONObject(strResponse);
                    //responseContentError = jsResponse.getString("authError");

                    JSONObject jsResponse = new JSONObject(strResponse);
                    jsToken = jsResponse.getString("token");

                    jsStikies = jsResponse.getString("stickyQty");
                    jsFirstName = jsResponse.getString("first_name");
                    jsLastName = jsResponse.getString("last_name");
                    jsEmail = jsResponse.getString("email");
                    jsProfilePublic = jsResponse.getString("profile_is_public");
                    jsInvited = jsResponse.getJSONArray("invited");
                    jsComunities = jsResponse.getJSONArray("communities");

                    if (rememberMe.isChecked() == true) {
                        bd.saveUserLogin(jsToken, true);
                        //ACTUALIZAR DATOS USUARIO COMO EMAIL, STIKIES ETC
                        //GUARDAR LOS DATOS DE COMUNIDADEES A LAS QUE PERTENECE QUE ENVIA EL JSON (ID ENVIADO, NOMBRE, NUM USERS, NUM CONTENIDO, DESCRPCION
                        //GUARDAR LOS DATOS DE COMUNIDADEES A LAS QUE TIENE INVITACIONES QUE ENVIA EL JSON
                        //AQUI ENVIAR A ALLCOMUNITIES
                    } else {
                        bd.saveUserLogin(jsToken, false);
                        //ACTUALIZAR DATOS USUARIO COMO EMAIL, STIKIES ETC
                        //GUARDAR LOS DATOS DE COMUNIDADEES A LAS QUE PERTENECE QUE ENVIA EL JSON
                        //GUARDAR LOS DATOS DE COMUNIDADEES A LAS QUE TIENE INVITACIONES QUE ENVIA EL JSON
                        //AQUI ENVIAR A ALLCOMUNITIES
                    }

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
