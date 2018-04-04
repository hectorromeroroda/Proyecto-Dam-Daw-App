package com.example.hector.proyectodamdaw;

import android.os.Bundle;
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


    public SingUpFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sing_up_fragment, container, false);

        nameSingUp = (EditText)view.findViewById(R.id.edtNameSingUp);
        surnameSingUp = (EditText)view.findViewById(R.id.edtSurnameSingUp);
        emailSingUp = (EditText)view.findViewById(R.id.edtEmailSingUp);
        repeatEmailSingUp = (EditText)view.findViewById(R.id.edtRepeatEmailSingUp);
        passwSingUp = (EditText)view.findViewById(R.id.edtPasswSingUp);
        repeatPasswSingUp = (EditText)view.findViewById(R.id.edtRepeatPasswSingUp);
        acceptSingUp = (Button) view.findViewById(R.id.btnAcceptSingUp);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        acceptSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AQUI ACCION A REALIZAR AL PULSAR boton "AccepSingUp"
            }
        });

    }
}
