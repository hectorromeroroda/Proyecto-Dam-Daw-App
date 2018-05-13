package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Comprobations;
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
    private boolean oldEmailEmpty;
    private boolean oldEmailFormat;
    private boolean newEmailEmpty;
    private boolean newEmailFormat;
    private boolean repeatNewEmailEmpty;
    private boolean emailsSame;
    Comprobations comprobations;

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

        comprobations = new Comprobations();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnAcceptChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strOldEmail=oldEmailEdit.getText().toString();
                strNewEmail=emailNewEdit.getText().toString();
                strRepeatNewEmail=repeatNewEmailEdit.getText().toString();

                //COMPROBAR DIRECCION EMAIL EN API CON UN GET Y EL ID DEL USUARIO Y GUARDARLA EN BD-----------------------------------------------------------------

                oldEmailEmpty =comprobations.checkEmptyFields(strOldEmail);
                if (oldEmailEmpty == false){
                    oldEmailFormat= comprobations.checkEmailFormat(strOldEmail);
                    if (oldEmailFormat==true){
                        newEmailEmpty =comprobations.checkEmptyFields(strNewEmail);
                        if (newEmailEmpty == false){
                            newEmailFormat= comprobations.checkEmailFormat(strNewEmail);
                            if (newEmailFormat==true){
                                emailsSame= comprobations.checkStringsEquals(strNewEmail, strRepeatNewEmail);
                                if (emailsSame == true){
                                    //COMPROBAR CON LA BD LOCAL QUE EMAIL ANTIGUO ES CORRECTO "COMENTAR LLUIS SI COMPROBAMOS EN API"
                                    //CREAR JSON CON LOS DATOS DE LOS EDITTEXT
                                    //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS
                                    //GUARDAR NUEVOS DATOS EN LA BD LOCAL
                                    //MOTIFICAR CAMBIOS AL USUARIO
                                }else{
                                    Toast toastAlert = Toast.makeText(getContext(),  R.string.toastEmailSame, Toast.LENGTH_SHORT);
                                    toastAlert.show();
                                }
                            }else{
                                Toast toastAlert = Toast.makeText(getContext(),  R.string.toatsFormatNewEmail, Toast.LENGTH_SHORT);
                                toastAlert.show();
                            }
                        }else{
                            Toast toastAlert = Toast.makeText(getContext(),  R.string.toatsNewEmailEmpty, Toast.LENGTH_SHORT);
                            toastAlert.show();
                        }
                    }else{
                        Toast toastAlert = Toast.makeText(getContext(),  R.string.toatsFormatOldEmail, Toast.LENGTH_SHORT);
                        toastAlert.show();
                    }
                }else{
                    Toast toastAlert = Toast.makeText(getContext(),  R.string.toatsOldEmailEmpty, Toast.LENGTH_SHORT);
                    toastAlert.show();
                }
            }


        });


    }



}

