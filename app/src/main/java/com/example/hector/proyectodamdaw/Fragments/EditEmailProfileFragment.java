package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditEmailProfileFragment extends Fragment{

    Button btnAcceptChanges;
    EditText oldEmailEdit;
    EditText emailNewEdit;
    EditText repeatNewEmailEdit;
    private String strOldEmail;
    private String strNewEmail;
    private String strRepeatNewEmail;

    public EditEmailProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_email_profile_fragment, container, false);

        btnAcceptChanges = (Button) view.findViewById(R.id.btnAcceptEditEmail);
        oldEmailEdit = (EditText)view.findViewById(R.id.edtOldEmailEdit);
        emailNewEdit = (EditText)view.findViewById(R.id.edtNewEmailEdit);
        repeatNewEmailEdit = (EditText)view.findViewById(R.id.edtRepeatNewEmailEdit);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnAcceptChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //CREAR JSON CON LOS DATOS DE LOS EDITTEXT
                //COMPROBAR CON LA BD LOCAL QUE EMAIL ANTIGUO ES CORRECTO "COMENTAR LLUIS SI COMPROBAMOS EN API"
                //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS
                //GUARDAR NUEVOS DATOS EN LA BD LOCAL
                //MOTIFICAR CAMBIOS AL USUARIO

            }


        });


    }

}

