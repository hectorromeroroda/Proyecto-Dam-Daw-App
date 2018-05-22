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

import com.example.hector.proyectodamdaw.Activitys.SingleCommunitieActivity;
import com.example.hector.proyectodamdaw.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by Hector on 22/05/2018.
 */

public class CreateVotacionFragment extends Fragment {

    ProgressDialog Dialog;
    EditText titulo;
    EditText  descripcion;
    EditText  contenido;
    EditText Pregunta1;
    EditText  Respuesta1a;
    EditText  Respuesta1b;
    EditText Pregunta2;
    EditText  Respuesta2a;
    EditText  Respuesta2b;
    EditText Pregunta3;
    EditText  Respuesta3a;
    EditText  Respuesta3b;
    EditText Pregunta4;
    EditText  Respuesta4a;
    EditText  Respuesta4b;
    EditText Pregunta5;
    EditText  Respuesta5a;
    EditText  Respuesta5b;
    Button btnEnviar;
    String strNombre;
    String strDescripcion;
    String strContenido;
    String userToken;
    String strPregunta1;
    String strRespuesta1a;
    String strRespuesta1b;
    String strPregunta2;
    String strRespuesta2a;
    String strRespuesta2b;
    String strPregunta3;
    String strRespuesta3a;
    String strRespuesta3b;
    String strPregunta4;
    String strRespuesta4a;
    String strRespuesta4b;
    String strPregunta5;
    String strRespuesta5a;
    String strRespuesta5b;
    Comprobations comprobations;
    private AppDataSources bd;


    public CreateVotacionFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_votacion_fragment, container, false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        titulo = (EditText)view.findViewById(R.id.edtNewVotacionTitulo);
        descripcion = (EditText)view.findViewById(R.id.edtNewVotacionDescription);
        contenido = (EditText)view.findViewById(R.id.edtNewVotacionContenido);
        Pregunta1 = (EditText)view.findViewById(R.id.edtNewVotacionPregunta1);
        Respuesta1a = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta1a);
        Respuesta1b = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta1b);
        Pregunta2 = (EditText)view.findViewById(R.id.edtNewVotacionPregunta2);
        Respuesta2a = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta2a);
        Respuesta2b = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta2b);
        Pregunta3 = (EditText)view.findViewById(R.id.edtNewVotacionPregunta3);
        Respuesta3a = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta3a);
        Respuesta3b = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta3b);
        Pregunta4 = (EditText)view.findViewById(R.id.edtNewVotacionPregunta4);
        Respuesta4a = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta4a);
        Respuesta4b = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta4b);
        Pregunta5 = (EditText)view.findViewById(R.id.edtNewVotacionPregunta5);
        Respuesta5a = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta5a);
        Respuesta5b = (EditText)view.findViewById(R.id.edtNewVotacionRespuesta5b);
        btnEnviar = (Button) view.findViewById(R.id.btnSendNewVotacion);

        comprobations = new Comprobations();
        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        return  view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strNombre=titulo.getText().toString();
                strDescripcion=descripcion.getText().toString();
                strContenido=contenido.getText().toString();
                strPregunta1=Pregunta1.getText().toString();
                strRespuesta1a=Respuesta1a.getText().toString();
                strRespuesta1b=Respuesta1b.getText().toString();
                strPregunta2=Pregunta2.getText().toString();
                strRespuesta2a=Respuesta2a.getText().toString();
                strRespuesta2b=Respuesta2b.getText().toString();
                strPregunta3=Pregunta3.getText().toString();
                strRespuesta3a=Respuesta3a.getText().toString();
                strRespuesta3b=Respuesta3b.getText().toString();
                strPregunta4=Pregunta4.getText().toString();
                strRespuesta4a=Respuesta4a.getText().toString();
                strRespuesta4b=Respuesta4b.getText().toString();
                strPregunta5=Pregunta5.getText().toString();
                strRespuesta5a=Respuesta5a.getText().toString();
                strRespuesta5b=Respuesta5b.getText().toString();

                //AQUI FALTA COMPROBAR SI LOS CAMPOS ESTAN VACIOS Y HACER LLAMADA CREAR JSON Y LLAMADA A CREATEPOSTASYNC----------------------------------------------------

            }

        });

    }

    //FALTA MONTAR EL JSON CORRECTAMENTE-----------------------------------------------------------------------------------------------------------------------------
    private String createJsonNewVotacion(String nombre, String descripcion, String imagen,  String cuerpo) {
        String strJsonNewPost;

        strJsonNewPost=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"image\": \"" + imagen + "\", " +
                "\"body\": \"" + cuerpo + "\"}");
        return strJsonNewPost;
    }

    private void createVotaciontAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        //FALTA PONER LA URL CORRECTA QUE DIGA EL LLUIS------------------------------------------------------------------------------------
        String Url = "http://192.168.43.219:3000/votacion/new";

        GlobalVariables globales = GlobalVariables.getInstance();
        final int idUser=globales.getIdUserSqlite();

        final Cursor cursorUserToken = bd.searchUserToken(idUser);
        if (cursorUserToken.moveToFirst() != false){
            userToken = cursorUserToken.getString(0);
        }

        client.addHeader("Authorization", "Bearer " + userToken);
        client.post(getContext(), Url, entity , "application/json",new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Estableciendo conexion...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                GlobalVariables globales = GlobalVariables.getInstance().getInstance();
                String IdCommunidadActual=globales.getCommunityId();

               //AQUI LA QUERY PARA GUARDAR LA INFROMACION DE LA VOTACION EN LA BD LOCAL-----------------------------------------------------------------------------------------

                //Envia a SingleCommunityActivity al crear la votacion
                Intent intent = new Intent(getContext(), SingleCommunitieActivity.class );
                startActivity(intent);
                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String badResponse = "No se ha podido crear la votacion, ha habido un problema al conectar con el servidor" + mensajeError;
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
