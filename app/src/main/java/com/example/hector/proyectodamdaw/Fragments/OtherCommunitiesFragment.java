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
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Otros.AdaotadorAllOtherCommunitiesBD;
import com.example.hector.proyectodamdaw.Otros.AdaptadorCommunitiesBD;
import com.example.hector.proyectodamdaw.Content.Communitie;
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
 * Created by Hector on 05/04/2018.
 */

public class OtherCommunitiesFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewOtherCommunities;
    private Communitie communitie = new Communitie();
    public AdaotadorAllOtherCommunitiesBD adaptadorBd;
    private AppDataSources bd;
    String userToken;
    ProgressDialog Dialog;

    public OtherCommunitiesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.other_communities_fragment, container, false);

        recyclerViewOtherCommunities = (RecyclerView) view.findViewById(R.id.rcvOtherCommunities);
        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        RefreshOtherCommunities();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //ReciclerView de comunidades que ni pertenece ni esta invitado
        adaptadorBd = new AdaotadorAllOtherCommunitiesBD(getContext(),communitie,bd.allOtherCommunities());
        recyclerViewOtherCommunities.setAdapter(adaptadorBd);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewOtherCommunities.setLayoutManager(layoutManager);
    }

    private void RefreshOtherCommunities() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/community/featured";

        GlobalVariables globales = GlobalVariables.getInstance();
        int idUser=globales.getIdUserSqlite();

        Cursor cursorUserToken = bd.searchUserToken(idUser);
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
                String jsCommMemmbers;
                String jsCommPublic;
                String jsCommContents;
                String jsCommId;
                String jsCommName;
                String jsCommDescription;
                String jscommRole;
                int idUserSqlite=0;
                JSONArray jsComunities = new JSONArray();
                String strResponse = new String(responseBody);

                try {

                    JSONObject jsResponse= new JSONObject(strResponse);

                    //Datos sobre las comunidades ke ni se pertenece ni se esta invitado
                    jsComunities = jsResponse.getJSONArray("communities");
                    for (int index1 = 0; index1 < jsComunities.length(); index1++) {
                        JSONObject objectPertenece = jsComunities.getJSONObject(index1);

                        jsCommMemmbers = objectPertenece.getString("members");
                        jsCommPublic = objectPertenece.getString("public");
                        jsCommContents = objectPertenece.getString("posts");
                        jsCommId = objectPertenece.getString("id");
                        jsCommName = objectPertenece.getString("name");
                        jsCommDescription = objectPertenece.getString("description");
                        jscommRole = objectPertenece.getString("role");

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
