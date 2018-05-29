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
 * Created by Hector on 21/05/2018.
 */

public class CreateProposalFragment extends Fragment {

    ProgressDialog Dialog;
    EditText titulo;
    EditText  descripcion;
    EditText  cuerpo;
    EditText  pregunta;
    Button btnEnviar;
    String strNombre;
    String strDescripcion;
    String strPregunta;
    String strCuerpo;
    String userToken;
    String idComunidadActual;
    int idUserSqlite;
    Comprobations comprobations;
    private AppDataSources bd;

    public CreateProposalFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_proposal_fragment, container, false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        titulo = (EditText)view.findViewById(R.id.edtNameNewProposal);
        descripcion = (EditText)view.findViewById(R.id.edtDescriptionNewProposal);
        cuerpo = (EditText)view.findViewById(R.id.edtCuerpoNewProposal);
        pregunta = (EditText)view.findViewById(R.id.edtPreguntaNewProposal);
        btnEnviar = (Button) view.findViewById(R.id.btnSendNewProposal);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idComunidadActual=globales.getCommunityId();

        comprobations = new Comprobations();
        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                boolean nombreVacio;
                boolean descripcionVacio;
                boolean preguntaVacio;
                boolean cuerpoVacio;
                String JsonCreateProposal;

                strNombre=titulo.getText().toString();
                strDescripcion=descripcion.getText().toString();
                strPregunta=pregunta.getText().toString();
                strCuerpo=cuerpo.getText().toString();

                nombreVacio =comprobations.checkEmptyFields(strNombre);
                if (nombreVacio == false) {
                    descripcionVacio =comprobations.checkEmptyFields(strDescripcion);
                    if (descripcionVacio == false) {
                        preguntaVacio =comprobations.checkEmptyFields(strPregunta);
                        if (preguntaVacio == false) {
                            cuerpoVacio =comprobations.checkEmptyFields(strCuerpo);
                            if (cuerpoVacio == false) {
                                JsonCreateProposal= createJsonNewProposal(strNombre,strDescripcion,strPregunta,strCuerpo);
                                try {
                                    createProposalAsync(JsonCreateProposal);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    }
                }
            }
        });

    }

    private String createJsonNewProposal(String nombre, String descripcion, String pregunta, String strCuerpo) {
        String strJsonNewProposal;

        strJsonNewProposal=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + strCuerpo + "\", " +
                "\"data\": {\"option\": \"" + pregunta + "\"}}");
        return strJsonNewProposal;
    }

    private void createProposalAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.43.219/community/" + idComunidadActual + "/content/new/request";

        GlobalVariables globales = GlobalVariables.getInstance();
        idUserSqlite=globales.getIdUserSqlite();

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

                bd.saveProposal(strNombre,strDescripcion,strPregunta,strCuerpo,idComunidadActual,false);

                //Envia a SingleCommunityActivity al crear la propuesta
                Intent intent = new Intent(getContext(), SingleCommunitieActivity.class );
                startActivity(intent);
                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String badResponse = "No se ha podido crear la propuesta, ha habido un problema al conectar con el servidor. " + mensajeError;
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
