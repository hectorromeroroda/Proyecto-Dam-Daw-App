package com.example.hector.proyectodamdaw;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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
    ProgressDialog Dialog;
    private String strUserLogin;
    private  String strUserPassw;
    private Boolean userLoginVacio;
    private Boolean userPasswVacio;
    private String jsonLogin;
    public static final int longitudMinimaContraseña = 8;
    Comprobations comprobations;

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        forgotPassw =(TextView) view.findViewById(R.id.txvForgotPassw);
        userLogin = (EditText)view.findViewById(R.id.edtUserLogin);
        passwLogin = (EditText)view.findViewById(R.id.edtPasswLogin);
        acceptLogin = (Button) view.findViewById(R.id.btnAcceptLogin);
        registerLogin = (Button) view.findViewById(R.id.btnRegisterLogin);

        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

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

                userLoginVacio=comprobations.comprobarCamposNoVacios(strUserLogin);
                if (userLoginVacio == false){
                    userPasswVacio=comprobations.comprobarCamposNoVacios(strUserPassw);
                    if (userPasswVacio == false){
                        if (strUserPassw.length()>=longitudMinimaContraseña){
                            jsonLogin= crearJsonLogin(strUserLogin, strUserPassw);

                            //AQUI CREAR LA CONEXION CON EL SRVIDOR PARA ENVIAR EL JSON ETC
                            //comprobarLoginCorrecto();


                        }else{
                            Toast toastAlerta = Toast.makeText(getContext(), R.string.toastLenghtPassw, Toast.LENGTH_SHORT);
                            toastAlerta.show();
                        }
                    }else{
                        Toast toastAlerta = Toast.makeText(getContext(), R.string.toastPassw, Toast.LENGTH_SHORT);
                        toastAlerta.show();
                    }
                }else{
                    Toast toastAlerta = Toast.makeText(getContext(), R.string.toastUser, Toast.LENGTH_SHORT);
                    toastAlerta.show();
                }
            }
        });

    }

    private String crearJsonLogin(String usuario, String passw) {
        String strJsonLogin;

        strJsonLogin=  ("{\"email\":\"" + usuario + ",\"password\":\"" + passw +"\"}");
        Toast toastAlerta = Toast.makeText(getContext(), strJsonLogin, Toast.LENGTH_SHORT);
        toastAlerta.show();
        return strJsonLogin;
    }

    private void comprobarLoginCorrecto(String JsonLogin) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.nuestradireccion");


        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("JsonLogin", JsonLogin));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Hace la petición
            HttpResponse response = httpclient.execute(httppost);


            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }


}
