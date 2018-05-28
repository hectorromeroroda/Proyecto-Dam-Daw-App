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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    int numeroPreguntasSeleccionada;
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

        Spinner spinnerEditProfile = (Spinner) view.findViewById(R.id.spnNumeroPreguntas);
        String[] items = {"1 pregunta", "2 preguntas", "3 preguntas", "4 preguntas","5 preguntas"};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item,items);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerEditProfile.setAdapter(spinnerArrayAdapter);
        spinnerEditProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {

                switch (pos) {
                    case 0:

                        Pregunta2.setVisibility(View.INVISIBLE);
                        Respuesta2a.setVisibility(View.INVISIBLE);
                        Respuesta2b.setVisibility(View.INVISIBLE);
                        Pregunta3.setVisibility(View.INVISIBLE);
                        Respuesta3a.setVisibility(View.INVISIBLE);
                        Respuesta3b.setVisibility(View.INVISIBLE);
                        Pregunta4.setVisibility(View.INVISIBLE);
                        Respuesta4a.setVisibility(View.INVISIBLE);
                        Respuesta4b.setVisibility(View.INVISIBLE);
                        Pregunta5.setVisibility(View.INVISIBLE);
                        Respuesta5a.setVisibility(View.INVISIBLE);
                        Respuesta5b.setVisibility(View.INVISIBLE);
                        numeroPreguntasSeleccionada=1;
                        break;
                    case 1:
                        Pregunta2.setVisibility(View.VISIBLE);
                        Respuesta2a.setVisibility(View.VISIBLE);
                        Respuesta2b.setVisibility(View.VISIBLE);

                        Pregunta3.setVisibility(View.INVISIBLE);
                        Respuesta3a.setVisibility(View.INVISIBLE);
                        Respuesta3b.setVisibility(View.INVISIBLE);
                        Pregunta4.setVisibility(View.INVISIBLE);
                        Respuesta4a.setVisibility(View.INVISIBLE);
                        Respuesta4b.setVisibility(View.INVISIBLE);
                        Pregunta5.setVisibility(View.INVISIBLE);
                        Respuesta5a.setVisibility(View.INVISIBLE);
                        Respuesta5b.setVisibility(View.INVISIBLE);
                        numeroPreguntasSeleccionada=2;
                        break;
                    case 2:
                        Pregunta2.setVisibility(View.VISIBLE);
                        Respuesta2a.setVisibility(View.VISIBLE);
                        Respuesta2b.setVisibility(View.VISIBLE);
                        Pregunta3.setVisibility(View.VISIBLE);
                        Respuesta3a.setVisibility(View.VISIBLE);
                        Respuesta3b.setVisibility(View.VISIBLE);

                        Pregunta4.setVisibility(View.INVISIBLE);
                        Respuesta4a.setVisibility(View.INVISIBLE);
                        Respuesta4b.setVisibility(View.INVISIBLE);
                        Pregunta5.setVisibility(View.INVISIBLE);
                        Respuesta5a.setVisibility(View.INVISIBLE);
                        Respuesta5b.setVisibility(View.INVISIBLE);
                        numeroPreguntasSeleccionada=3;
                        break;
                    case 3:
                        Pregunta2.setVisibility(View.VISIBLE);
                        Respuesta2a.setVisibility(View.VISIBLE);
                        Respuesta2b.setVisibility(View.VISIBLE);
                        Pregunta3.setVisibility(View.VISIBLE);
                        Respuesta3a.setVisibility(View.VISIBLE);
                        Respuesta3b.setVisibility(View.VISIBLE);
                        Pregunta4.setVisibility(View.VISIBLE);
                        Respuesta4a.setVisibility(View.VISIBLE);
                        Respuesta4b.setVisibility(View.VISIBLE);

                        Pregunta5.setVisibility(View.INVISIBLE);
                        Respuesta5a.setVisibility(View.INVISIBLE);
                        Respuesta5b.setVisibility(View.INVISIBLE);
                        numeroPreguntasSeleccionada=4;
                        break;
                    case 4:
                        Pregunta2.setVisibility(View.VISIBLE);
                        Respuesta2a.setVisibility(View.VISIBLE);
                        Respuesta2b.setVisibility(View.VISIBLE);
                        Pregunta3.setVisibility(View.VISIBLE);
                        Respuesta3a.setVisibility(View.VISIBLE);
                        Respuesta3b.setVisibility(View.VISIBLE);
                        Pregunta4.setVisibility(View.VISIBLE);
                        Respuesta4a.setVisibility(View.VISIBLE);
                        Respuesta4b.setVisibility(View.VISIBLE);
                        Pregunta5.setVisibility(View.VISIBLE);
                        Respuesta5a.setVisibility(View.VISIBLE);
                        Respuesta5b.setVisibility(View.VISIBLE);
                        numeroPreguntasSeleccionada=5;
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });




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
                String jsonNewVotacion="";

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

                if (numeroPreguntasSeleccionada==1){
                    jsonNewVotacion= createJsonNewVotacion1Preguntas(strNombre,strDescripcion,strContenido,strPregunta1,strRespuesta1a,strRespuesta1b);
                }else{
                    if (numeroPreguntasSeleccionada==2){
                        jsonNewVotacion= createJsonNewVotacion2Preguntas(strNombre,strDescripcion,strContenido,strPregunta1,strRespuesta1a,strRespuesta1b,strPregunta2,strRespuesta2a,
                                strRespuesta2b);
                    }else{
                        if (numeroPreguntasSeleccionada==3){
                            jsonNewVotacion= createJsonNewVotacion3Preguntas(strNombre,strDescripcion,strContenido,strPregunta1,strRespuesta1a,strRespuesta1b,strPregunta2,strRespuesta2a,
                                    strRespuesta2b,strPregunta3,strRespuesta3a,strRespuesta3b);
                        }else{
                            if (numeroPreguntasSeleccionada==4){
                                jsonNewVotacion= createJsonNewVotacion4Preguntas(strNombre,strDescripcion,strContenido,strPregunta1,strRespuesta1a,strRespuesta1b,strPregunta2,strRespuesta2a,
                                        strRespuesta2b,strPregunta3,strRespuesta3a,strRespuesta3b,strPregunta4,strRespuesta4a,strRespuesta4b);
                            }else {
                                if (numeroPreguntasSeleccionada==5){
                                    jsonNewVotacion= createJsonNewVotacion5Preguntas(strNombre,strDescripcion,strContenido,strPregunta1,strRespuesta1a,strRespuesta1b,strPregunta2,strRespuesta2a,
                                            strRespuesta2b,strPregunta3,strRespuesta3a,strRespuesta3b,strPregunta4,strRespuesta4a,strRespuesta4b,strPregunta5,strRespuesta5a,strRespuesta5b);
                                }
                            }
                        }
                    }
                }

                try {
                    createVotaciontAsync(jsonNewVotacion);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                }

        });

    }

    private String createJsonNewVotacion5Preguntas(String nombre, String descripcion, String cuerpo, String tituloPregunta1, String respuesta1a, String respuesta1b, String tituloPregunta2, String respuesta2a, String respuesta2b,
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

    private String createJsonNewVotacion4Preguntas(String nombre, String descripcion, String cuerpo, String tituloPregunta1, String respuesta1a, String respuesta1b, String tituloPregunta2, String respuesta2a, String respuesta2b,
                                                   String tituloPregunta3, String respuesta3a, String respuesta3b, String tituloPregunta4, String respuesta4a, String respuesta4b) {
        String strJsonNewVotacion;

        strJsonNewVotacion=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + cuerpo + "\"," +
                " \"data\":[" +
                "{\"title\": \"" + tituloPregunta1 + "\", \"options\": [{\"value\": \"" + respuesta1a + "\"},{\"value\": \"" + respuesta1b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta2 + "\", \"options\": [{\"value\": \"" + respuesta2a + "\"},{\"value\": \"" + respuesta2b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta3 + "\", \"options\": [{\"value\": \"" + respuesta3a + "\"},{\"value\": \"" + respuesta3b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta4 + "\", \"options\": [{\"value\": \"" + respuesta4a + "\"},{\"value\": \"" + respuesta4b + "\"}]}]}");
        return strJsonNewVotacion;
    }

    private String createJsonNewVotacion3Preguntas(String nombre, String descripcion, String cuerpo, String tituloPregunta1, String respuesta1a, String respuesta1b, String tituloPregunta2, String respuesta2a, String respuesta2b,
                                                   String tituloPregunta3, String respuesta3a, String respuesta3b) {
        String strJsonNewVotacion;

        strJsonNewVotacion=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + cuerpo + "\"," +
                " \"data\":[" +
                "{\"title\": \"" + tituloPregunta1 + "\", \"options\": [{\"value\": \"" + respuesta1a + "\"},{\"value\": \"" + respuesta1b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta2 + "\", \"options\": [{\"value\": \"" + respuesta2a + "\"},{\"value\": \"" + respuesta2b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta3 + "\", \"options\": [{\"value\": \"" + respuesta3a + "\"},{\"value\": \"" + respuesta3b + "\"}]}]}");
        return strJsonNewVotacion;
    }

    private String createJsonNewVotacion2Preguntas(String nombre, String descripcion, String cuerpo, String tituloPregunta1, String respuesta1a, String respuesta1b, String tituloPregunta2, String respuesta2a, String respuesta2b) {
        String strJsonNewVotacion;

        strJsonNewVotacion=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + cuerpo + "\"," +
                " \"data\":[" +
                "{\"title\": \"" + tituloPregunta1 + "\", \"options\": [{\"value\": \"" + respuesta1a + "\"},{\"value\": \"" + respuesta1b + "\"}]}," +
                "{\"title\": \"" + tituloPregunta2 + "\", \"options\": [{\"value\": \"" + respuesta2a + "\"},{\"value\": \"" + respuesta2b + "\"}]}]}");
        return strJsonNewVotacion;
    }

    private String createJsonNewVotacion1Preguntas(String nombre, String descripcion, String cuerpo, String tituloPregunta1, String respuesta1a, String respuesta1b) {
        String strJsonNewVotacion;

        strJsonNewVotacion=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + cuerpo + "\"," +
                " \"data\":[" +
                "{\"title\": \"" + tituloPregunta1 + "\", \"options\": [{\"value\": \"" + respuesta1a + "\"},{\"value\": \"" + respuesta1b + "\"}]}]}");
        return strJsonNewVotacion;
    }

    private void createVotaciontAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.43.219:3000/community/" + idComunidadActual + "/content/new/poll";

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
