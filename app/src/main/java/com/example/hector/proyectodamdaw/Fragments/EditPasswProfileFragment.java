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

public class EditPasswProfileFragment extends Fragment{

    Button btnAcceptChanges;
    EditText oldPasswEdit;
    EditText passwNewEdit;
    EditText repeatNewPasswEdit;
    private String strOldPassw;
    private String strNewPassw;
    private String strRepeatNewPassw;
    private boolean oldPasswEmpty;
    private boolean newPasswEmpty;
    private  Boolean passwSame;
    Comprobations comprobations;
    public static final int miniumLenghtPassw = 8;

    public EditPasswProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_passw_profile_fragment, container, false);

        btnAcceptChanges = (Button) view.findViewById(R.id.btnAcceptChanges);
        oldPasswEdit = (EditText)view.findViewById(R.id.edtOldPasswEdit);
        passwNewEdit = (EditText)view.findViewById(R.id.edtPasswNewEdit);
        repeatNewPasswEdit = (EditText)view.findViewById(R.id.edtRepeatNewPasswEdit);

        comprobations = new Comprobations();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnAcceptChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strOldPassw=oldPasswEdit.getText().toString();
                strNewPassw=passwNewEdit.getText().toString();
                strRepeatNewPassw=repeatNewPasswEdit.getText().toString();

                oldPasswEmpty =comprobations.checkEmptyFields(strOldPassw);
                if (oldPasswEmpty == false){
                    if (strOldPassw.length()>= miniumLenghtPassw){
                        newPasswEmpty =comprobations.checkEmptyFields(strNewPassw);
                        if (newPasswEmpty == false){
                            if (strNewPassw.length()>= miniumLenghtPassw){
                                passwSame= comprobations.checkStringsEquals(strNewPassw, strRepeatNewPassw);
                                if (passwSame==true){

                                    //CREAR JSON CON LOS DATOS LOS EDITTEXT
                                    //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS
                                    //SI LA RESPUESTA ES CORRECTA " EL ANTIGUO PASSW ES CORRECTO
                                    //NOTIFICAR CAMBIO CORRECTO AL USUARIO

                                }else{
                                    Toast toastError = Toast.makeText(getContext(),R.string.toastPasswSame, Toast.LENGTH_LONG);
                                    toastError.show();
                                }
                            }else{
                                Toast toastError = Toast.makeText(getContext(),R.string.toatsNewPasswLenght, Toast.LENGTH_LONG);
                                toastError.show();
                            }
                        }else{
                            Toast toastError = Toast.makeText(getContext(),R.string.toatsNewPasswEmpty, Toast.LENGTH_LONG);
                            toastError.show();
                        }
                    }else{
                        Toast toastError = Toast.makeText(getContext(),R.string.toatsOldPasswLenght, Toast.LENGTH_LONG);
                        toastError.show();
                    }
                }else{
                    Toast toastError = Toast.makeText(getContext(),R.string.toatsOldPasswEmpty, Toast.LENGTH_LONG);
                    toastError.show();
                }
            }


        });


    }

}
