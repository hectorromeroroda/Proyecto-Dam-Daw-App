package com.example.hector.proyectodamdaw.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.CommunitiesActivity;
import com.example.hector.proyectodamdaw.AdaptadorCommunities;
import com.example.hector.proyectodamdaw.AdaptadorCommunitiesBD;
import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Hector on 03-Apr-18.
 */

public class YourCommunitiesFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewYourCommunities;
    protected RecyclerView recyclerViewYourInvitations;
    private Communitie communitie = new Communitie();
    public AdaptadorCommunitiesBD adaptadorBD;
    private AppDataSources bd;
    ProgressDialog Dialog;
    GlobalVariables globalBariables;
    int idSqlite;
    String userToken;

    public YourCommunitiesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.your_communities_fragment, container, false);

        recyclerViewYourCommunities = (RecyclerView) view.findViewById(R.id.rcvYourCommunities);
        recyclerViewYourInvitations = (RecyclerView) view.findViewById(R.id.rcvYourinvitations);
        bd = new AppDataSources(getContext());
        globalBariables= new GlobalVariables();
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        idSqlite =globalBariables.getIdUserSqlite();

        RefreshCommuities();

        adaptadorBD = new AdaptadorCommunitiesBD(getContext(),communitie,bd.todasComunities());
        recyclerViewYourCommunities.setAdapter(adaptadorBD);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewYourCommunities.setLayoutManager(layoutManager);

    }

    private void RefreshCommuities() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/profile";

        //AKI CONSULTA BD PARA RECUPERAR TOKEN DEL USUARIO CON ID DE VARIABLE GLOBAL
        GlobalVariables globales = GlobalVariables.getInstance();
        int idUser=globales.getIdUserSqlite();

        Cursor cursorUserToken = bd.searchUserToken(idUser);
        if (cursorUserToken.moveToFirst() != false){
            userToken = cursorUserToken.getString(0);
        }

        client.setBasicAuth("Bearer",userToken);
        client.get(getContext(), Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Cargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseContentError;
                String jsToken;
                String jsFirstName;
                String jsLastName;
                String jsEmail;
                String jsProfilePublic;
                String jsStikies;
                String jsCommMemmbers;
                String jsCommPublic;
                String jsCommContents;
                String jsCommId;
                String jsCommName;
                String jsCommDescription;
                String jscommRole;
                int idUserSqlite=0;
                JSONArray jsInvited = new JSONArray();
                JSONArray jsComunities = new JSONArray();
                String strResponse = new String(responseBody);

                try {
                    //Datos sobre el usuario
                    JSONObject jsResponse= new JSONObject(strResponse);

                    //Datos sobre las comunidades a las que se esta invitado
                    jsInvited = jsResponse.getJSONArray("invited");
                    for (int index = 0; index < jsInvited.length(); index++) {
                        JSONObject object = jsInvited.getJSONObject(index);

                        JSONObject data = new JSONObject();
                        data = object.getJSONObject("data");
                        jsCommMemmbers = data.getString("members");
                        jsCommPublic = data.getString("public");
                        jsCommContents = data.getString("contents");
                        jsCommId = data.getString("_id");
                        jsCommName = data.getString("name");
                        jsCommDescription = data.getString("description");
                        jscommRole = data.getString("role");

                        Cursor cursorIdComminityExist = bd.searchIdCommunitie(jsCommId);
                        if (cursorIdComminityExist.moveToFirst() != false) {
                            bd.updateCommunityUserInvited(Integer.parseInt(jsCommMemmbers), Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription, jsCommId);
                        } else {
                            bd.saveCommunity(Integer.parseInt(jsCommMemmbers), Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription, jsCommId);
                            bd.saveCommunityUser(jsCommId, idUserSqlite, jscommRole, true);
                        }
                    }
                    //Datos sobre las comunidades a las que se pertenece
                    jsComunities = jsResponse.getJSONArray("communities");
                    for (int index1 = 0; index1 < jsComunities.length(); index1++) {
                        JSONObject object1 = jsComunities.getJSONObject(index1);
                        jscommRole = object1.getString("role");

                        JSONObject data1= new JSONObject();
                        data1 = object1.getJSONObject("data");
                        jsCommMemmbers=data1.getString("members");
                        jsCommPublic=data1.getString("public");
                        jsCommContents=data1.getString("contents");
                        jsCommId=data1.getString("_id");
                        jsCommName=data1.getString("name");
                        jsCommDescription=data1.getString("description");

                        Cursor cursorIdComminityExist1 = bd.searchIdCommunitie(jsCommId);
                        if (cursorIdComminityExist1.moveToFirst() != false){
                            bd.updateCommunity(Integer.parseInt(jsCommMemmbers), Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription, jsCommId);
                        }else {
                            bd.saveCommunity(Integer.parseInt(jsCommMemmbers),Boolean.valueOf(jsCommPublic), Integer.parseInt(jsCommContents), jsCommName, jsCommDescription,jsCommId);
                            bd.saveCommunityUser(jsCommId,idUserSqlite,jscommRole,false);
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
