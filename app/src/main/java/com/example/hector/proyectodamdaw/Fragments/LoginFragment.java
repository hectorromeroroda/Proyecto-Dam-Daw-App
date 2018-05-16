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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.CommunitiesActivity;
import com.example.hector.proyectodamdaw.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

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
    GlobalVariables globalBariables;
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
        globalBariables= new GlobalVariables();

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

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.43.219:3000/login";

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
                String jsToken;
                String jsFirstName;
                String jsLastName;
                String jsEmail;
                String jsProfilePublic;
                String jsStikies;
                String jsCommMemmbers;
                String jsCommPublic;
                String jsCommContents;
                String jsCommId;
                String jsCommName;
                String jsCommDescription;
                String jscommRole;
                int idUserSqlite=0;
                JSONArray jsInvited = new JSONArray();
                JSONArray jsComunities = new JSONArray();
                String strResponse = new String(responseBody);

                try {
                    //Datos sobre el usuario
                    JSONObject jsResponse= new JSONObject(strResponse);
                    JSONObject features= new JSONObject();
                    features = jsResponse.getJSONObject("features");

                    jsStikies=features.getString("stickyQty");
                    jsFirstName = jsResponse.getString("first_name");
                    jsLastName = jsResponse.getString("last_name");
                    jsEmail = jsResponse.getString("email");
                    jsProfilePublic = jsResponse.getString("profile_is_public");
                    jsToken=jsResponse.getString("token");

                    int intTrue = 1;
                    int intFalse=0;
                    //Actualiza datos del usuario
                    Cursor  cursorIdUserSqlite= bd.userIdSqlite(jsEmail);
                    if (cursorIdUserSqlite.moveToFirst() != false){
                        idUserSqlite = cursorIdUserSqlite.getInt(0);
                        if (rememberMe.isChecked() == true) {
                            bd.updateUserLoginTokenRememberMe(jsToken, intTrue, idUserSqlite);
                        } else {
                            bd.updateUserLoginTokenRememberMe(jsToken, intFalse, idUserSqlite);
                        }
                        bd.updateUserLogin(Integer.parseInt(jsStikies),  Boolean.valueOf(jsProfilePublic), jsEmail, idUserSqlite);
                    }else{
                        if (rememberMe.isChecked() == true) {
                            bd.saveUserRegister(jsFirstName, jsLastName, jsEmail, Integer.parseInt(jsStikies), Boolean.valueOf(jsProfilePublic), jsToken, 1);
                        } else {
                            bd.saveUserRegister(jsFirstName, jsLastName, jsEmail, Integer.parseInt(jsStikies), Boolean.valueOf(jsProfilePublic), jsToken, 0);
                        }

                    }

                    //Datos sobre las comunidades a las que se esta invitado
                    jsInvited = jsResponse.getJSONArray("invited");
                    for (int index = 0; index < jsInvited.length(); index++) {
                        JSONObject object = jsInvited.getJSONObject(index);

                        JSONObject data = new JSONObject();
                        data = object.getJSONObject("data");
                        jsCommMemmbers = data.getString("members");
                        jsCommPublic = data.getString("public");
                        jsCommContents = data.getString("contents");
                        jsCommId = data.getString("_id");
                        jsCommName = data.getString("name");
                        jsCommDescription = data.getString("description");
                        jscommRole = data.getString("role");

                        Cursor cursorIdComminityExist = bd.searchIdCommunitie(jsCommId);
                        if (cursorIdComminityExist.moveToFirst() != false) {
                            bd.updateCommunityUserInvited(Integer.parseInt(jsCommMemmbers), Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription, jsCommId);
                        } else {
                            bd.saveCommunity(Integer.parseInt(jsCommMemmbers), Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription, jsCommId);
                            bd.saveCommunityUser(jsCommId, idUserSqlite, jscommRole, true);
                        }
                    }
                        //Datos sobre las comunidades a las que se pertenece
                        jsComunities = jsResponse.getJSONArray("communities");
                        for (int index1 = 0; index1 < jsComunities.length(); index1++) {
                            JSONObject object1 = jsComunities.getJSONObject(index1);
                            jscommRole = object1.getString("role");

                            JSONObject data1= new JSONObject();
                            data1 = object1.getJSONObject("data");
                            jsCommMemmbers=data1.getString("members");
                            jsCommPublic=data1.getString("public");
                            jsCommContents=data1.getString("contents");
                            jsCommId=data1.getString("_id");
                            jsCommName=data1.getString("name");
                            jsCommDescription=data1.getString("description");

                            Cursor cursorIdComminityExist1 = bd.searchIdCommunitie(jsCommId);
                            if (cursorIdComminityExist1.moveToFirst() != false){
                                bd.updateCommunity(Integer.parseInt(jsCommMemmbers), Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription, jsCommId);
                            }else {
                                bd.saveCommunity(Integer.parseInt(jsCommMemmbers),Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription,jsCommId);
                                bd.saveCommunityUser(jsCommId,idUserSqlite,jscommRole,false);
                            }

                        }

                    //Poner en id de usuario en variable gobal
                    globalBariables.setIdUserSqlite(idUserSqlite);
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
