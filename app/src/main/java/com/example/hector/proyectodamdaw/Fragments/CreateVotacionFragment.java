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
import com.example.hector.proyectodamdaw.Otros.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
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
    EditText  fechaFinalizacion;
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
    String strFechaFinalizacion;
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
    String idComunidadActual;
    Comprobations comprobations;
    int idUserSqlite;
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
        fechaFinalizacion = (EditText)view.findViewById(R.id.edtFechaFinalizacion);
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

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idComunidadActual=globales.getCommunityId();
        idUserSqlite=globales.getIdUserSqlite();

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

                boolean nombreVacio;
                boolean descripcionVacio;
                boolean contenidoVacio;
                boolean pregunta1Vacia;
                boolean respuesta1aVacia;
                boolean respuesta1bVacia;
                boolean pregunta2Vacia;
                boolean respuesta2aVacia;
                boolean respuesta2bVacia;
                boolean pregunta3Vacia;
                boolean respuesta3aVacia;
                boolean respuesta3bVacia;
                boolean pregunta4Vacia;
                boolean respuesta4aVacia;
                boolean respuesta4bVacia;
                boolean pregunta5Vacia;
                boolean respuesta5aVacia;
                boolean respuesta5bVacia;
                boolean fechaFinalizacionVacia;
                String jsonNewVotacion;

                strNombre=titulo.getText().toString();
                strDescripcion=descripcion.getText().toString();
                strContenido=contenido.getText().toString();
                strFechaFinalizacion=fechaFinalizacion.getText().toString();
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

                nombreVacio =comprobations.checkEmptyFields(strNombre);
                if (nombreVacio == false) {
                    descripcionVacio =comprobations.checkEmptyFields(strDescripcion);
                    if (descripcionVacio == false) {
                        contenidoVacio =comprobations.checkEmptyFields(strContenido);
                        if (contenidoVacio == false) {
                            pregunta1Vacia =comprobations.checkEmptyFields(strPregunta1);
                            if (pregunta1Vacia == false) {
                                respuesta1aVacia =comprobations.checkEmptyFields(strRespuesta1a);
                                if (respuesta1aVacia == false) {
                                    respuesta1bVacia =comprobations.checkEmptyFields(strRespuesta1b);
                                    if (respuesta1bVacia == false) {
                                        pregunta2Vacia =comprobations.checkEmptyFields(strPregunta2);
                                        if (pregunta2Vacia == false) {
                                            respuesta2aVacia =comprobations.checkEmptyFields(strRespuesta2a);
                                            if (respuesta2aVacia == false) {
                                                respuesta2bVacia =comprobations.checkEmptyFields(strRespuesta2b);
                                                if (respuesta2bVacia == false) {
                                                    pregunta3Vacia =comprobations.checkEmptyFields(strPregunta3);
                                                    if (pregunta3Vacia == false) {
                                                        respuesta3aVacia =comprobations.checkEmptyFields(strRespuesta3a);
                                                        if (respuesta3aVacia == false) {
                                                            respuesta3bVacia =comprobations.checkEmptyFields(strRespuesta3b);
                                                            if (respuesta3bVacia == false){
                                                                pregunta4Vacia =comprobations.checkEmptyFields(strPregunta4);
                                                                if (pregunta4Vacia == false) {
                                                                    respuesta4aVacia =comprobations.checkEmptyFields(strRespuesta4a);
                                                                    if (respuesta4aVacia == false) {
                                                                        respuesta4bVacia =comprobations.checkEmptyFields(strRespuesta4b);
                                                                        if (respuesta4bVacia == false){
                                                                            pregunta5Vacia =comprobations.checkEmptyFields(strPregunta5);
                                                                            if (pregunta5Vacia == false) {
                                                                                respuesta5aVacia =comprobations.checkEmptyFields(strRespuesta5a);
                                                                                if (respuesta5aVacia == false) {
                                                                                    respuesta5bVacia =comprobations.checkEmptyFields(strRespuesta5b);
                                                                                    if (respuesta5bVacia == false){
                                                                                        fechaFinalizacionVacia =comprobations.checkEmptyFields(strFechaFinalizacion);
                                                                                        if (fechaFinalizacionVacia == false){
                                                                                            jsonNewVotacion=createJsonNewVotacion(strNombre,strDescripcion,strContenido,strPregunta1,strRespuesta1a,strRespuesta1b,strPregunta2,strRespuesta2a,
                                                                                                    strRespuesta2b,strPregunta3,strRespuesta3a,strRespuesta3b,strPregunta4,strRespuesta4a,strRespuesta4b,strPregunta5,strRespuesta5a,strRespuesta5b);

                                                                                            try {
                                                                                                createVotaciontAsync(jsonNewVotacion);
                                                                                            } catch (UnsupportedEncodingException e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                        }else{
                                                                                            Toast toastAlert = Toast.makeText(getContext(),  "El campo Fecha finalizacion es obligatiorio", Toast.LENGTH_SHORT);
                                                                                            toastAlert.show();
                                                                                        }
                                                                                    }else{
                                                                                        Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 2 de la pregunta 5 es obligatiorio", Toast.LENGTH_SHORT);
                                                                                        toastAlert.show();
                                                                                    }
                                                                                }else{
                                                                                    Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 1 de la pregunta 5 es obligatiorio", Toast.LENGTH_SHORT);
                                                                                    toastAlert.show();
                                                                                }
                                                                            }else{
                                                                                Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 5 es obligatiorio", Toast.LENGTH_SHORT);
                                                                                toastAlert.show();
                                                                            }
                                                                        }else{
                                                                            Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 2 de la pregunta 4bes obligatiorio", Toast.LENGTH_SHORT);
                                                                            toastAlert.show();
                                                                        }
                                                                    }else{
                                                                        Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 1 de la pregunta 4 es obligatiorio", Toast.LENGTH_SHORT);
                                                                        toastAlert.show();
                                                                    }
                                                                }else{
                                                                    Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 4 es obligatiorio", Toast.LENGTH_SHORT);
                                                                    toastAlert.show();
                                                                }
                                                            }else{
                                                                Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 2 de la pregunta 3 es obligatiorio", Toast.LENGTH_SHORT);
                                                                toastAlert.show();
                                                            }
                                                        }else{
                                                            Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 1 de la pregunta 3 es obligatiorio", Toast.LENGTH_SHORT);
                                                            toastAlert.show();
                                                        }
                                                    }else{
                                                        Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 3 es obligatiorio", Toast.LENGTH_SHORT);
                                                        toastAlert.show();
                                                    }
                                                }else{
                                                    Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 2 de la pregunta 2 es obligatiorio", Toast.LENGTH_SHORT);
                                                    toastAlert.show();
                                                }
                                            }else{
                                                Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 1 de la pregunta 2 es obligatiorio", Toast.LENGTH_SHORT);
                                                toastAlert.show();
                                            }
                                        }else{
                                            Toast toastAlert = Toast.makeText(getContext(),  "El campo pregunta 2 de la pregunta 1 es obligatiorio", Toast.LENGTH_SHORT);
                                            toastAlert.show();
                                        }
                                    }else{
                                        Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 2 de la pregunta 1 es obligatiorio", Toast.LENGTH_SHORT);
                                        toastAlert.show();
                                    }
                                }else{
                                    Toast toastAlert = Toast.makeText(getContext(),  "El campo Respuesta 1 de la pregunta 1 es obligatiorio", Toast.LENGTH_SHORT);
                                    toastAlert.show();
                                }
                            }else{
                                Toast toastAlert = Toast.makeText(getContext(),  "El campo pregunta 1 es obligatiorio", Toast.LENGTH_SHORT);
                                toastAlert.show();
                            }
                        }else{
                            Toast toastAlert = Toast.makeText(getContext(),  "El campo contenido es obligatiorio", Toast.LENGTH_SHORT);
                            toastAlert.show();
                        }
                    }else{
                        Toast toastAlert = Toast.makeText(getContext(),  "El campo descripcion es obligatiorio", Toast.LENGTH_SHORT);
                        toastAlert.show();
                    }
                }else{
                    Toast toastAlert = Toast.makeText(getContext(),  "El campo titulo es obligatiorio", Toast.LENGTH_SHORT);
                    toastAlert.show();
                }
            }

        });

    }

    private String createJsonNewVotacion(String nombre, String descripcion, String cuerpo, String tituloPregunta1, String respuesta1a, String respuesta1b, String tituloPregunta2, String respuesta2a, String respuesta2b,
    String tituloPregunta3, String respuesta3a, String respuesta3b, String tituloPregunta4, String respuesta4a, String respuesta4b, String tituloPregunta5, String respuesta5a, String respuesta5b) {
        String strJsonNewVotacion;

        strJsonNewVotacion=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + cuerpo + "\"," +
                " \"data\":[" +
                "{\"title\": \"" + tituloPregunta1 + "\", \"options\": [{\"value\": \"" + respuesta1a + "\"},{\"value\": \"" + respuesta1b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta2 + "\", \"options\": [{\"value\": \"" + respuesta2a + "\"},{\"value\": \"" + respuesta2b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta3 + "\", \"options\": [{\"value\": \"" + respuesta3a + "\"},{\"value\": \"" + respuesta3b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta4 + "\", \"options\": [{\"value\": \"" + respuesta4a + "\"},{\"value\": \"" + respuesta4b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta5 + "\", \"options\": [{\"value\": \"" + respuesta5a + "\"},{\"value\": \"" + respuesta5b + "\"}]}]}");
        return strJsonNewVotacion;
    }

    private void createVotaciontAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.1.39:3000/community/" + idComunidadActual + "/content/new/poll";

        final Cursor cursorUserToken = bd.searchUserToken(idUserSqlite);
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

                bd.savePoll(strNombre,strDescripcion,strContenido," ",strFechaFinalizacion, idComunidadActual,false,strPregunta1,strRespuesta1a,strRespuesta1b,strPregunta2,strRespuesta2a,
                        strRespuesta2b,strPregunta3,strRespuesta3a,strRespuesta3b,strPregunta4,strRespuesta4a,strRespuesta4b,strPregunta5,strRespuesta5a,strRespuesta5b);

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
