package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.CommunitiesActivity;
import com.example.hector.proyectodamdaw.Activitys.SingleCommunitieActivity;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
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
 * Created by Hector on 25/05/2018.
 */

public class InviteUserFragment extends Fragment {

    EditText  nombreusuarioinvitado;
    Button btnInvitarusuario;
    String nombreUsuarioInvitado;
    String userToken;
    String idComunidadActual;
    String jsUserId;
    String jsUserInvite;
    String rolUsuario= "user";
    private AppDataSources bd;
    ProgressDialog Dialog;
    RadioButton rdbEditor;
    RadioButton rdbAdmin;
    RadioButton rdbUser;
    int idUserSqlite;

    public InviteUserFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.invite_user_fragment, container, false);
        nombreusuarioinvitado = (EditText)view.findViewById(R.id.edtUserNameInvited);
        btnInvitarusuario = (Button) view.findViewById(R.id.btnInviteUserComunity);
        rdbEditor=(RadioButton)view.findViewById(R.id.rdbEditor);
        rdbAdmin=(RadioButton)view.findViewById(R.id.rdbAdmin);
        rdbUser=(RadioButton)view.findViewById(R.id.rdbUser);
        rdbUser.setChecked(true);

        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idUserSqlite=globales.getIdUserSqlite();
        idComunidadActual=globales.getCommunityId();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnInvitarusuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                nombreUsuarioInvitado=nombreusuarioinvitado.getText().toString();
                if ( (nombreUsuarioInvitado == null) || (nombreUsuarioInvitado.equals(""))){
                    //MENSAJE CAMPO DEVE ESTAR LLENO
                }else{
                    if (rdbAdmin.isChecked()==true){
                        rolUsuario="admin";
                    }else{
                        if (rdbEditor.isChecked()==true){
                            rolUsuario="editor";
                        }else{
                            if (rdbUser.isChecked()==true){
                                rolUsuario="user";
                            }
                        }
                    }

                    InviteUser(nombreUsuarioInvitado);


                }
            }

        });


    }

    private void InviteUser(String textoABuscar) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/profile/" + textoABuscar;

        Cursor cursorUserToken = bd.searchUserToken(idUserSqlite);
        if (cursorUserToken.moveToFirst() != false){
            userToken = cursorUserToken.getString(0);
        }

        client.addHeader("Authorization", "Bearer " + userToken);
        client.get(getContext(), Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Cargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String strResponse = new String(responseBody);

                JSONArray jsResponse = new JSONArray();

                try {
                  jsResponse= new JSONArray(strResponse);
                    for (int index = 0; index < jsResponse.length(); index++) {
                        JSONObject jsobjUsuario = jsResponse.getJSONObject(index);
                        jsUserId = jsobjUsuario.getString("_id");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if ( (jsUserId == null) || (jsUserId.equals(""))){
                    Toast toastAlerta = Toast.makeText(getContext(), "No existe ningun usuario con ese nombre", Toast.LENGTH_LONG);
                    toastAlerta.show();
                }else{
                    jsUserInvite=createJsonUserInvite(jsUserId,rolUsuario);

                    try {
                        inviteUserPost(jsUserInvite);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String mensaje = "No se ha podido enviar los datos  al servidor. " + mensajeError;
                Toast toastAlerta = Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG);
                toastAlerta.show();
                Dialog.dismiss();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });

    }

    private String createJsonUserInvite(String idUsuario, String role) {
        String strJsonLogin;

        strJsonLogin=  ("{\"invited\": \"" + idUsuario + "\", \"role\": \"" + role +"\"}");
        return strJsonLogin;
    }

    private void inviteUserPost(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.1.39:3000/community/" + idComunidadActual + "/invite";

        final Cursor cursorUserToken = bd.searchUserToken(idUserSqlite);
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


                //Envia a SingleCommunityActivity al invitar al usuario
                Intent intent = new Intent(getContext(), SingleCommunitieActivity.class );
                startActivity(intent);
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
