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

import org.json.JSONArray;
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
    String strRespuesta="";


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
        //EnvioIdProposicion();-----------------------------------------------------------------------------------------

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

                  //AQUI PEGAR CODIGO GUARDADO EN EL TXT-------------------------------

                }
            }
    });


    }


}

