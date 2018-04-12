package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Hector on 03-Apr-18.
 */

public class SingUpFragment extends Fragment{

    EditText nameSingUp;
    EditText surnameSingUp;
    EditText emailSingUp;
    EditText repeatEmailSingUp;
    EditText passwSingUp;
    EditText repeatPasswSingUp;
    Button acceptSingUp;
    private String strUserName;
    private String strSurname;
    private String strEmail;
    private String strRepeatEmail;
    private String strPassw;
    private String strRepeatPassw;
    private String jsonRegister;
    private Boolean userNameEmpty;
    private Boolean surnameEmpty;
    private Boolean emailEmpty;
    private Boolean repeatEmailEmpty;
    private Boolean emailFormat;
    private boolean emailsSame;
    private Boolean passwEmpty;
    private Boolean repeatPasswEmpty;
    Comprobations comprobations;
    public static final int miniumLenghtPassw = 8;

    public SingUpFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sing_up_fragment, container, false);

        //Para permitir la conexion POST
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        nameSingUp = (EditText)view.findViewById(R.id.edtNameSingUp);
        surnameSingUp = (EditText)view.findViewById(R.id.edtSurnameSingUp);
        emailSingUp = (EditText)view.findViewById(R.id.edtEmailSingUp);
        repeatEmailSingUp = (EditText)view.findViewById(R.id.edtRepeatEmailSingUp);
        passwSingUp = (EditText)view.findViewById(R.id.edtPasswSingUp);
        repeatPasswSingUp = (EditText)view.findViewById(R.id.edtRepeatPasswSingUp);
        acceptSingUp = (Button) view.findViewById(R.id.btnAcceptSingUp);

        comprobations = new Comprobations();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        acceptSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserName=nameSingUp.getText().toString();
                strSurname=surnameSingUp.getText().toString();
                strEmail=emailSingUp.getText().toString();
                strRepeatEmail=repeatEmailSingUp.getText().toString();
                strPassw=passwSingUp.getText().toString();
                strRepeatPassw=repeatPasswSingUp.getText().toString();

                userNameEmpty =comprobations.checkEmptyFields(strUserName);
                if (userNameEmpty == false){
                    surnameEmpty =comprobations.checkEmptyFields(strSurname);
                    if (surnameEmpty == false){
                        emailEmpty =comprobations.checkEmptyFields(strEmail);
                        if (emailEmpty == false){
                            emailFormat= comprobations.checkEmailFormat(strEmail);
                            if (emailFormat=true){
                                repeatEmailEmpty =comprobations.checkEmptyFields(strRepeatEmail);
                                if (repeatEmailEmpty == false){
                                    emailsSame= comprobations.checkStringsEquals(strEmail, strRepeatEmail);
                                    if (emailsSame = true){
                                        passwEmpty =comprobations.checkEmptyFields(strPassw);
                                        if (passwEmpty=true){

                                        }else{
                                            //Aqui si el passw esta vacio
                                        }
                                    }else{
                                        //Aqui si los emails no son iguales
                                    }
                                }else{
                                    //Aqui si confirmacion email vacio
                                }
                            }else{
                                //Aqui si el formato del email es incorrecto
                            }
                        }else{
                            //Aqui si email esta vacio
                        }
                    }else{
                        //Aqui si apellidos esta vacio
                    }
                }else{
                    //Aqui si nombre esta vacio
                }


            }
        });

    }
}
