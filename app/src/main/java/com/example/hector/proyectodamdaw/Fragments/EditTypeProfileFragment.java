package com.example.hector.proyectodamdaw.Fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditTypeProfileFragment extends Fragment{

    Button btnAcceptChanges;
    RadioButton rdbPublicProfile;
    RadioButton rdbPrivateProfile;
    private Boolean publicProfile;
    private Boolean resultCursorPublicProfile;
    private AppDataSources bd;
    private String jsonEditProfile;
    editTypeProfileAsync editTypeProfileAsync;

    public EditTypeProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_type_profile_fragment, container, false);

        btnAcceptChanges = (Button) view.findViewById(R.id.btnAcceptEditTypeProfile);
        rdbPublicProfile = (RadioButton)view.findViewById(R.id.rdbProfilePublic);
        rdbPrivateProfile = (RadioButton)view.findViewById(R.id.rdbProfilePrivate);
        bd = new AppDataSources(getContext());

        //Recupera el estado actual del perfil y lo carga en los radiobutons
        Cursor cursorTypeProfileMeState = bd.typeProfile(1);//VALOR INVENTADO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (cursorTypeProfileMeState.moveToFirst() != false){
            resultCursorPublicProfile = Boolean.valueOf(cursorTypeProfileMeState.getString(0));
            if (resultCursorPublicProfile==true){
                rdbPublicProfile.setChecked(true);
            }else{
                rdbPrivateProfile.setChecked(true);
            }
        }
        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnAcceptChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdbPublicProfile.isChecked()==true){
                    publicProfile=true;
                }else {
                    if (rdbPrivateProfile.isChecked()==true){
                        publicProfile=false;
                    }
                }

                //Crea la conexion httpPut para enviarla al servidor
                editTypeProfileAsync = new editTypeProfileAsync();
                editTypeProfileAsync.execute(jsonEditProfile);
            }


        });

    }

    private class editTypeProfileAsync extends AsyncTask<String, Void, String> {
        String result = "";

        protected String doInBackground(String... argumentos) {

            try {

                URL url = new URL("http://192.168.56.1:3000/profile");
                //http://192.168.56.1:3000/profile
                //https://domo-200915.appspot.com/profile

                HttpClient httpclient = new DefaultHttpClient();
                HttpPut put= new HttpPut(String.valueOf(url));

                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("typeprofile", publicProfile.toString()));
                put.setEntity(new UrlEncodedFormEntity(pairs));

                HttpResponse response = httpclient.execute(put);

                //Obtenemos una respuesta*/
                HttpEntity entity = response.getEntity();

                result = EntityUtils.toString(entity);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.i("ResponseObject: ", e.toString());
            }

            return result;
        }
    }

    protected void onPostExecute(String mensaje) {
        String responseContentError;
        String jsToken;
        String profileState;

        try {
            JSONObject jsResponse= new JSONObject(mensaje);
            responseContentError=jsResponse.getString("messageError");
            Toast toastResult = Toast.makeText(getContext(), responseContentError, Toast.LENGTH_LONG);
            toastResult.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            //CREO QUE LA RESPUESTA ESPERADA TIENE QUE SER UN 200 OK O ALGO ASI
            JSONObject jsResponse= new JSONObject(mensaje);
            profileState= jsResponse.getString("profile_is_public");

            //Guarda los datos en la base datos
           // bd.saveEditTypeProfile(publicProfile);
            Toast toastResult = Toast.makeText(getContext(), R.string.ToastRespuestaProfileStae, Toast.LENGTH_LONG);
            toastResult.show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
