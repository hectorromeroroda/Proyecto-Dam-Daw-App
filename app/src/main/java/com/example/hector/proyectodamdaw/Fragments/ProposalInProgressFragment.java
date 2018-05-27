package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.SingleCommunitieActivity;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by Hector on 09-Apr-18.
 */

public class ProposalInProgressFragment extends Fragment{

    TextView NameProposal;
    TextView CuerpoProposal;
    TextView QuestionProposal;
    RadioButton AcceptProposal;
    RadioButton DiscardProposal;
    Button sendProposal;
    String userToken;
    String idComunidadActual;
    private AppDataSources bd;
    ProgressDialog Dialog;
    int idUserSqlite;
    String idProposal;
    String respuestaTitulo;
    String respuestaCuerpo;
    String  respuestaPregunta;
    String strRespuesta="";


    public ProposalInProgressFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.proposal_in_progress_fragment, container, false);

        NameProposal =(TextView) view.findViewById(R.id.txvNameProposal);
        CuerpoProposal =(TextView) view.findViewById(R.id.txvCuerpoProposal);
        QuestionProposal =(TextView) view.findViewById(R.id.txvQuestionProposal);
        sendProposal = (Button) view.findViewById(R.id.btnSendResultProposal);
        AcceptProposal =(RadioButton)view.findViewById(R.id.rdbAcceptProposal);
        DiscardProposal =(RadioButton)view.findViewById(R.id.rdbDiscardProposal);

        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idUserSqlite=globales.getIdUserSqlite();
        idComunidadActual=globales.getCommunityId();
        idProposal=globales.getProposalId();

        //Envia id propuesta para recuperar la informacion de esta
        EnvioIdProposicion();

        //Carga la informacion recivida en los textView
        NameProposal.setText(respuestaTitulo);
        CuerpoProposal.setText(respuestaCuerpo);
        QuestionProposal.setText(respuestaPregunta);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        AcceptProposal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String jsRespuesta;

                if (AcceptProposal.isChecked()== true){
                    strRespuesta="Si";
                }else{
                    if (DiscardProposal.isChecked()== true) {
                        strRespuesta = "No";
                    }
                }

                if ( (strRespuesta == null) || (strRespuesta.equals(""))){
                    Toast toastAlerta = Toast.makeText(getContext(), "Deve seleccionar una respuesta", Toast.LENGTH_LONG);
                    toastAlerta.show();
                }else{



                }
            }
    });


    }

    //FALTA MODIFICAR EL JSON, CRELO COMO SE NECESITE----------------------------------------------------------------------------------------------------------------------
    private String createJsonResultProposition(String idUsuario, String role) {
        String strJsonLogin;

        strJsonLogin=  ("{\"invited\": \"" + idUsuario + "\", \"role\": \"" + role +"\"}");
        return strJsonLogin;
    }

    private void EnvioIdProposicion() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/community/" + idComunidadActual + "/content/" + idProposal;

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
                String jsPregunta;
                String jsCuerpo;
                String jsTitulo;

                try {
                    JSONObject jsResponse= new JSONObject(strResponse);
                    JSONObject data= new JSONObject();

                    data = jsResponse.getJSONObject("data");
                    jsPregunta=data.getString("option");
                    jsTitulo=jsResponse.getString("title");
                    jsCuerpo=jsResponse.getString("body");
                    respuestaTitulo=jsTitulo;
                    respuestaCuerpo =jsCuerpo;
                    respuestaPregunta=jsPregunta;

                    //Guardar pregunta de la propuesta
                    bd.updateProposalPregunta(respuestaPregunta,idProposal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    private void sendResultProposal(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.43.219:3000/community/" + idComunidadActual + "/content/" + idProposal;

        final Cursor cursorUserToken = bd.searchUserToken(idUserSqlite);
        if (cursorUserToken.moveToFirst() != false){
            userToken = cursorUserToken.getString(0);
        }

        client.addHeader("Authorization", "Bearer " + userToken);
        client.put(getContext(), Url, entity , "application/json",new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Verificando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                //Guardar en bd local el resultado y poner ke ya ha sido votada a true
                bd.updateProposalRespuesta( Boolean.valueOf(strRespuesta),true,idProposal);

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

