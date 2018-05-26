package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
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
 * Created by Hector on 09-Apr-18.
 */

public class ProposalInProgressFragment extends Fragment{

    TextView NameProposal;
    TextView DescriptionProposal;
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
    String respuestaDescripcion;
    String  respuestaPregunta;


    public ProposalInProgressFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.proposal_in_progress_fragment, container, false);

        NameProposal =(TextView) view.findViewById(R.id.txvNameProposal);
        DescriptionProposal =(TextView) view.findViewById(R.id.txvDescriptionProposal);
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
        DescriptionProposal.setText(respuestaDescripcion);
        QuestionProposal.setText(respuestaPregunta);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        AcceptProposal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String strRespuesta;



                if (AcceptProposal.isChecked()== true){
                    strRespuesta="Si";
                }else{
                    if (DiscardProposal.isChecked()== true) {
                        strRespuesta = "No";
                    }
                }

                //ENVIAR EL RESULTADO AL SERVIDOR------------------------------------------------------------------------------------------------------
                //GUARDAR EN LA BD LOCAL QUE YA A SIDO BOTADA Y EL RESULTADO DE LA VOTACION  SI  LA SUBIDA AL SERVIDOR ES CORRECTA----------------------







            }
    });


    }

    private void EnvioIdProposicion() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/¿?¿?¿¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿¿¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿?¿¿¿?¿?¿?¿¿?¿?¿?¿?¿?¿¿¿¿?";

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


                //RECIVIR JSON CON LA INFO DE LA PROPOSICION----------------------------------------------------------------------------------------
                String strResponse = new String(responseBody);

                try {
                    JSONObject jsResponse= new JSONObject(strResponse);

                    //POR AKI VOY-------------------------------------------------------------------------------------------------------------------------
                    //respuestaTitulo=;
                    //respuestaDescripcion=;
                    //respuestaPregunta=;

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
}

