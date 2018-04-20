package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.Comprobations;
import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 20/04/2018.
 */

public class EditProfileFragment extends Fragment{

    EditText nameEdit;
    EditText surnameEdit;
    EditText aliasEdit;
    EditText oldPasswEdit;
    EditText passwNewEdit;
    EditText repeatNewPasswEdit;
    RadioButton rdbPublicProfile;
    RadioButton rdbPrivateProfile;
    ImageButton imgbLoadImage;
    Button btnAcceptEditProfile;
    private String strUserName;
    private String strSurname;
    private String strAlias;
    private String strOldPassw;
    private String strNewPassw;
    private String strRepeatNewPassw;
    private Boolean publicProfile;
    private String strUri= null;
    private Boolean strUserNameEmpty;
    private Boolean strSurnameEmpty;
    private Boolean strAliasEmpty;
    private Boolean strOldPasswEmpty;
    private Boolean strNewPasswEmpty;
    private Boolean passwSame;

    Comprobations comprobations;
    public static final int miniumLenghtPassw = 8;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        nameEdit = (EditText)view.findViewById(R.id.edtNameEdit);
        surnameEdit = (EditText)view.findViewById(R.id.edtSurnameEdit);
        aliasEdit = (EditText)view.findViewById(R.id.edtAliasEdit);
        oldPasswEdit = (EditText)view.findViewById(R.id.edtOldPasswEdit);
        passwNewEdit = (EditText)view.findViewById(R.id.edtPasswNewEdit);
        repeatNewPasswEdit = (EditText)view.findViewById(R.id.edtRepeatNewPasswEdit);
        rdbPublicProfile = (RadioButton)view.findViewById(R.id.rdbProfilePublic);
        rdbPrivateProfile = (RadioButton)view.findViewById(R.id.rdbProfilePrivate);
        imgbLoadImage= (ImageButton)view.findViewById(R.id.imgbLoadImage);
        btnAcceptEditProfile = (Button) view.findViewById(R.id.btnAcceptEditProfile);

        comprobations = new Comprobations();

        //AQUI CONSULTA A LA BASE DE DATOS PARA RECUPERAR INFORMACION DEL USUARIO
        //LUEGO CARGARLA EN LOS DISTINTOS COMPONENTES

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


        btnAcceptEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUserName=nameEdit.getText().toString();
                strSurname=surnameEdit.getText().toString();
                strAlias=aliasEdit.getText().toString();
                strOldPassw=oldPasswEdit.getText().toString();
                strNewPassw=passwNewEdit.getText().toString();
                strRepeatNewPassw=repeatNewPasswEdit.getText().toString();

                if (rdbPublicProfile.isChecked()==true){
                    publicProfile=true;
                }else {
                    if (rdbPrivateProfile.isChecked()==true){
                        publicProfile=false;
                    }
                }

                strUserNameEmpty =comprobations.checkEmptyFields(strUserName);
                if (strUserNameEmpty == false){
                    strSurnameEmpty =comprobations.checkEmptyFields(strSurname);
                    if (strSurnameEmpty == false){
                        strAliasEmpty =comprobations.checkEmptyFields(strAlias);
                        if (strAliasEmpty == false){
                            strOldPasswEmpty =comprobations.checkEmptyFields(strOldPassw);
                            if (strOldPasswEmpty == false){
                                if (strOldPassw.length()>= miniumLenghtPassw){
                                    strNewPasswEmpty =comprobations.checkEmptyFields(strNewPassw);
                                    if (strNewPasswEmpty == false){
                                        passwSame= comprobations.checkStringsEquals(strNewPassw, strRepeatNewPassw);
                                        if (passwSame==true){

                                            if (strUri==null){
                                                //CREAR JSON CON LOS DATOS "RECUERDA RADIOBUTONS"
                                                //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS
                                                //SI LA RESPUESTA ES CORRECTA:
                                                    //GUARDAR  INFO EN LA BD LOCAL CON UNA QUERY SIN DATO URI
                                            }else{
                                                //CREAR JSON CON LOS DATOS "RECUERDA RADIOBUTONS Y URI IMAGEN"
                                                //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS
                                                //SI LA RESPUESTA ES CORRECTA:
                                                    //GUARDAR  INFO EN LA BD LOCAL CON UNA QUERY CON DATO URI
                                            }

                                        }else{
                                            Toast toastError = Toast.makeText(getContext(),R.string.toastPasswSame, Toast.LENGTH_LONG);
                                            toastError.show();
                                        }
                                    }else{
                                        Toast toastError = Toast.makeText(getContext(),R.string.toastPasswEmpty, Toast.LENGTH_LONG);
                                        toastError.show();
                                    }
                                }else{
                                    Toast toastError = Toast.makeText(getContext(),R.string.toastPasswLenght, Toast.LENGTH_LONG);
                                    toastError.show();
                                }
                            }else{
                                Toast toastError = Toast.makeText(getContext(),R.string.toastOldPasswEmpty, Toast.LENGTH_LONG);
                                toastError.show();
                            }
                        }else{
                            Toast toastError = Toast.makeText(getContext(),R.string.toastAliasEmpty, Toast.LENGTH_LONG);
                            toastError.show();
                        }
                    }else{
                        Toast toastError = Toast.makeText(getContext(),R.string.toastSurnameEmpty, Toast.LENGTH_LONG);
                        toastError.show();
                    }
                }else{
                    Toast toastError = Toast.makeText(getContext(),R.string.toastNameEmpty, Toast.LENGTH_LONG);
                    toastError.show();
                }

            }
        });

        imgbLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AQUI ACCIONES AL DAR A CARGAR IMAGEN
            }
        });

    }

}
