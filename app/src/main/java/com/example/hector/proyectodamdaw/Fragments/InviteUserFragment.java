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
import android.widget.TextView;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.SingleCommunitieActivity;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Hector on 25/05/2018.
 */

public class InviteUserFragment extends Fragment {

    EditText  nombreusuarioinvitado;
    Button btnInvitarusuario;
    String nombreUsuarioInvitado;
    String userToken;
    String idComunidadActual;
    private AppDataSources bd;
    ProgressDialog Dialog;
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
                    InviteUser(nombreUsuarioInvitado);
                }
            }

        });


    }

    private void InviteUser(String textoABuscar) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/community/" + idComunidadActual + "/invite";

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

                //Envia a SingleCommunityActivity al crear la votacion
                Intent intent = new Intent(getContext(), SingleCommunitieActivity.class );
                startActivity(intent);
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


}
