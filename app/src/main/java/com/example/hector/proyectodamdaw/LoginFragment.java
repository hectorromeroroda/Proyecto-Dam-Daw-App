package com.example.hector.proyectodamdaw;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Hector on 31-Mar-18.
 */

public class LoginFragment extends Fragment{

    TextView forgotPassw;
    TextView singUpLogin;
    EditText userLogin;
    EditText  passwLogin;
    Button acceptLogin;
    ProgressDialog Dialog;
    private String strUserLogin;
    private  String strUserPassw;
    private Boolean userLoginVacio;
    private Boolean userPasswVacio;
    private JSONObject jsonLogin;
    public static final int longitudMinimaContraseña = 8;

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        forgotPassw =(TextView) view.findViewById(R.id.txvForgotPassw);
        singUpLogin =(TextView) view.findViewById(R.id.txvSingUpLogin);
        userLogin = (EditText)view.findViewById(R.id.edtUserLogin);
        passwLogin = (EditText)view.findViewById(R.id.edtPasswLogin);
        acceptLogin = (Button) view.findViewById(R.id.btnAcceptLogin);

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

        singUpLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Caambiar de fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentLogin, new SingUpFragment());
                transaction.commit();
            }

        });

        acceptLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserLogin=userLogin.getText().toString();
                strUserPassw=passwLogin.getText().toString();

                userLoginVacio=comprobarCamposNoVacios(strUserLogin);
                if (userLoginVacio == false){
                    userPasswVacio=comprobarCamposNoVacios(strUserPassw);
                    if (userPasswVacio == false){
                        if (strUserPassw.length()>=longitudMinimaContraseña){
                            jsonLogin= crearJsonLogin(strUserLogin, strUserPassw);

                            //AQUI CREAR LA CONEXION CON EL SRVIDOR PARA ENVIAR EL JSON ETC
                            comprobarLoginCorrecto(jsonLogin);



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

    private boolean comprobarCamposNoVacios(String texto) {
        boolean vacio=false ;

        if ( (texto == null) || (texto.equals(""))){
            vacio=true;
        }
        return vacio;
    }

    private JSONObject crearJsonLogin(String usuario, String passw) {

        JSONObject objJsonLogin = new JSONObject();
        try {
            objJsonLogin.put("email",usuario);
            objJsonLogin.put("password",passw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objJsonLogin;
    }

    private void comprobarLoginCorrecto(final JSONObject jsonLogin) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "";
        client.get(getContext(), Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Descargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String valor = "No se ha podido recuperar los datos desde el servidor. " + mensajeError;
                Toast toastAlerta = Toast.makeText(getContext(), valor, Toast.LENGTH_LONG);
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
