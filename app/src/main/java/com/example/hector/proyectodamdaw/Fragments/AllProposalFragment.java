package com.example.hector.proyectodamdaw.Fragments;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.Content.Proposal;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.AdaptadorAllPostBD;
import com.example.hector.proyectodamdaw.Otros.AdaptadorAllProposalsBD;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Hector on 07-Apr-18.
 */

public class AllProposalFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewProposals;
    RadioButton rdbInProgress;
    RadioButton rdbclosed;
    String idComunidadActual;
    String userToken;
    int idUserSqlite;
    private AppDataSources bd;
    public AdaptadorAllProposalsBD adaptadorBd;
    private Proposal proposal = new Proposal();
    ProgressDialog Dialog;


    public AllProposalFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_proposal_fragment, container, false);

        recyclerViewProposals = (RecyclerView) view.findViewById(R.id.rcvProposals);
        rdbInProgress=(RadioButton)view.findViewById(R.id.rdbProposalInProgress);
        rdbclosed=(RadioButton)view.findViewById(R.id.rdbProposalClosed);
        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idComunidadActual=globales.getCommunityId();
        idUserSqlite=globales.getIdUserSqlite();

        RefreshCommuities();

        //ReciclerView de comunidades a las que pertenece
        adaptadorBd = new AdaptadorAllProposalsBD(getContext(),proposal,bd.todosProposalCommunity(idComunidadActual));
        recyclerViewProposals.setAdapter(adaptadorBd);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewProposals.setLayoutManager(layoutManager);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }

    private void RefreshCommuities() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/community/" + idComunidadActual;

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
                String jsId;
                String jsTitle;
                String jsDescription;
                String jsType;
                String strResponse = new String(responseBody);
                JSONArray jsContents = new JSONArray();
                boolean yaExiste= false;

                try {
                    JSONObject jsResponse= new JSONObject(strResponse);

                    jsContents = jsResponse.getJSONArray("contents");
                    for (int index = 0; index < jsContents.length(); index++) {
                        JSONObject objContenido = jsContents.getJSONObject(index);

                        yaExiste= false;

                        jsId = objContenido.getString("_id");
                        jsTitle = objContenido.getString("title");
                        jsDescription = objContenido.getString("description");
                        jsType = objContenido.getString("type");

                        switch (jsType) {
                            case "Request":
                                Cursor cursorIdPropositionExist = bd.searchIdProposition(jsTitle);
                                if (cursorIdPropositionExist.moveToFirst() != false) {
                                    bd.updatePropositiontId(jsId,jsTitle);
                                }else{
                                    bd.saveProposal1(jsTitle,jsDescription,jsId,idComunidadActual);
                                }
                                break;
                        }

                    }

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
