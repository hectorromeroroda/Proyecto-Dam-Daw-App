package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditTypeProfileFragment extends Fragment{

    Button btnAcceptChanges;
    RadioButton rdbPublicProfile;
    RadioButton rdbPrivateProfile;
    private Boolean publicProfile;

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

        //AQUI CONSULTA A LA BASE DE DATOS PARA RECUPERAR INFORMACION DEL USUARIO SOBRE SU ESTADO DE PERFIL ACTUAL
        //LUEGO CARGARLA EN EL RADIOBUTON CORRESPONDIENTE

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

                //CREAR JSON CON LOS DATOS DE LOS RADIOBUTONS
                //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS
                //GUARDAR DATOS EN LA BD LOCAL
                //NOTIFICAR LOS CAMBIOS AL USUARIO

            }


        });

    }

}
