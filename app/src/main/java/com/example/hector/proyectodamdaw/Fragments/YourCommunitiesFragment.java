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
    protected RecyclerView.LayoutManager layoutManagerInvited;
    protected RecyclerView recyclerViewYourCommunities;
    protected RecyclerView recyclerViewYourInvitations;
    private Communitie communitie = new Communitie();
    private Communitie communitieInvited = new Communitie();
    public AdaptadorCommunitiesBD adaptadorBdPertenece;
    public AdaptadorCommunitiesBD adaptadorBdInvited;
    private AppDataSources bd;
    ProgressDialog Dialog;
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
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        boolean refreshData=globales.getRefreshData();
        idSqlite=globales.getIdUserSqlite();

        if (refreshData==true){
            RefreshCommuities();
        }

        //ReciclerView de comunidades a las que pertenece
        adaptadorBdPertenece = new AdaptadorCommunitiesBD(getContext(),communitie,bd.todasComunitiesPertenece(idSqlite));
        recyclerViewYourCommunities.setAdapter(adaptadorBdPertenece);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewYourCommunities.setLayoutManager(layoutManager);

        //ReciclerView de comunidades a las que estainvitado
        adaptadorBdInvited = new AdaptadorCommunitiesBD(getContext(),communitieInvited,bd.todasComunitiesInvited(idSqlite));
        recyclerViewYourInvitations.setAdapter(adaptadorBdInvited);
        layoutManagerInvited = new LinearLayoutManager(getContext());
        recyclerViewYourInvitations.setLayoutManager(layoutManagerInvited);
    }

    private void RefreshCommuities() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/profile";

        Cursor cursorUserToken = bd.searchUserToken(idSqlite);
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

                    JSONObject jsResponse= new JSONObject(strResponse);
                    JSONObject features= new JSONObject();
                    features = jsResponse.getJSONObject("features");

                    jsStikies=features.getString("stickyQty");
                    jsFirstName = jsResponse.getString("first_name");
                    jsLastName = jsResponse.getString("last_name");
                    jsEmail = jsResponse.getString("email");
                    jsProfilePublic = jsResponse.getString("profile_is_public");
                    jsToken=jsResponse.getString("token");

                    int intTrue = 1;
                    //Actualiza datos del usuario
                    Cursor  cursorIdUserSqlite= bd.userIdSqlite(jsEmail);
                    if (cursorIdUserSqlite.moveToFirst() != false) {
                        idUserSqlite = cursorIdUserSqlite.getInt(0);
                        bd.updateUserLoginTokenRememberMe(jsToken, intTrue, idUserSqlite);
                        bd.updateUserLogin(Integer.parseInt(jsStikies), Boolean.valueOf(jsProfilePublic), jsEmail, idUserSqlite);
                    }

                    //Datos sobre las comunidades a las que se esta invitado
                    jsInvited = jsResponse.getJSONArray("invited");
                    for (int index = 0; index < jsInvited.length(); index++) {
                        JSONObject objectInvited = jsInvited.getJSONObject(index);

                        jsCommMemmbers = objectInvited.getString("members");
                        jsCommPublic = objectInvited.getString("public");
                        jsCommContents = objectInvited.getString("posts");
                        jsCommId = objectInvited.getString("id");
                        jsCommName = objectInvited.getString("name");
                        jsCommDescription = objectInvited.getString("description");
                        jscommRole = objectInvited.getString("role");

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
