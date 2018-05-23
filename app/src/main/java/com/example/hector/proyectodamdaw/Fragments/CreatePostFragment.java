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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Activitys.SingleCommunitieActivity;
import com.example.hector.proyectodamdaw.Comprobations;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.GlobalVariables;
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

public class CreatePostFragment extends Fragment {

    ProgressDialog Dialog;
    EditText titulo;
    EditText  descripcion;
    EditText  contenido;
    Button btnEnviar;
    String strNombre;
    String strDescripcion;
    String strContenido;
    String userToken;
    String idComunidadActual;
    Comprobations comprobations;
    private AppDataSources bd;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_post_fragment, container, false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        titulo = (EditText)view.findViewById(R.id.edtNameNewPost);
        descripcion = (EditText)view.findViewById(R.id.edtDescriptionNewPost);
        contenido = (EditText)view.findViewById(R.id.edtContenidoNewPost);
        btnEnviar = (Button) view.findViewById(R.id.btnSendNewProposal);

        comprobations = new Comprobations();
        bd = new AppDataSources(getContext());
        Dialog = new ProgressDialog(getContext());
        Dialog.setCancelable(false);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idComunidadActual=globales.getCommunityId();

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
                String jsonCreatePost;

                strNombre=titulo.getText().toString();
                strDescripcion=descripcion.getText().toString();
                strContenido=contenido.getText().toString();

                nombreVacio =comprobations.checkEmptyFields(strNombre);
                if (nombreVacio == false) {
                    descripcionVacio =comprobations.checkEmptyFields(strDescripcion);
                    if (descripcionVacio == false) {
                        contenidoVacio =comprobations.checkEmptyFields(strContenido);
                        if (contenidoVacio == false) {

                            jsonCreatePost= createJsonNewPost(strNombre,strDescripcion," ",strContenido);
                            try {
                                createPostAsync(jsonCreatePost);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast toastAlert = Toast.makeText(getContext(),  "El campo contenido es obligatiorio", Toast.LENGTH_SHORT);
                            toastAlert.show();
                        }
                    }else{
                        Toast toastAlert = Toast.makeText(getContext(),  "El campo descripcion es obligatiorio", Toast.LENGTH_SHORT);
                        toastAlert.show();
                    }
                }else{
                    Toast toastAlert = Toast.makeText(getContext(), "El campo nombre Titulo es obligatiorio", Toast.LENGTH_SHORT);
                    toastAlert.show();
                }
            }
        });
    }

    private String createJsonNewPost(String nombre, String descripcion, String cuerpo,  String imagen) {
        String strJsonNewPost;

        strJsonNewPost=  ("{\"title\": \"" + nombre + "\", \"description\": \"" + descripcion + "\", \"body\": \"" + cuerpo + "\", " +
                "\"image\": \"" + imagen + "\"}");
        return strJsonNewPost;
    }

    private void createPostAsync(String datos) throws UnsupportedEncodingException {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        StringEntity entity = new StringEntity(datos);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        String Url = "http://192.168.43.219:3000/" + idComunidadActual + "/content/new/post";

        GlobalVariables globales = GlobalVariables.getInstance();
        final int idUser=globales.getIdUserSqlite();

        final Cursor cursorUserToken = bd.searchUserToken(idUser);
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



                bd.savePost(strNombre,strDescripcion,strContenido,idComunidadActual);

                //Envia a SingleCommunityActivity al crear el post
                Intent intent = new Intent(getContext(), SingleCommunitieActivity.class );
                startActivity(intent);
                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String badResponse = "No se ha podido crear el post, ha habido un problema al conectar con el servidor" + mensajeError;
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
