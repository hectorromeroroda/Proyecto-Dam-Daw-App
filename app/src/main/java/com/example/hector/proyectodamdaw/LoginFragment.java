package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Hector on 31-Mar-18.
 */

public class LoginFragment extends Fragment{

    TextView forgotPassw;
    TextView singUpLogin;
    EditText userLogin;
    EditText  passwLogin;
    Button acceptLogin;
    private String strUserLogin;
    private  String strUserPassw;
    private Boolean userLoginVacio;
    private Boolean userPasswVacio;

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        forgotPassw =(TextView) view.findViewById(R.id.txvForgotPassw);
        singUpLogin =(TextView) view.findViewById(R.id.txvSingUpLogin);
        userLogin = (EditText)view.findViewById(R.id.edtUserLogin);
        passwLogin = (EditText)view.findViewById(R.id.edtPasswLogin);
        acceptLogin = (Button) view.findViewById(R.id.btnAcceptLogin);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        forgotPassw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

               //AQUI ACCION A REALIZAR AL PULSAR "FORGOT PASSWORD"
            }

        });

        singUpLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Caambiar de fragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contentLogin, new SingUpFragment());
                transaction.commit();
            }

        });

        acceptLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strUserLogin=userLogin.getText().toString();
                strUserPassw=passwLogin.getText().toString();

                userLoginVacio=comprobarCamposNoVacios(strUserLogin);
                if (userLoginVacio == false){
                    userPasswVacio=comprobarCamposNoVacios(strUserPassw);
                    if (userPasswVacio == false){

                        //AQUI CONSTRUIR EL JSON
                    }else{
                        Toast toastAlerta = Toast.makeText(getContext(), R.string.toastPassw, Toast.LENGTH_SHORT);
                        toastAlerta.show();
                    }
                }else{
                    Toast toastAlerta = Toast.makeText(getContext(), R.string.toastUser, Toast.LENGTH_SHORT);
                    toastAlerta.show();
                }
            }
        });

    }

    private boolean comprobarCamposNoVacios(String texto) {
        boolean vacio=false ;

        if ( (texto == null) || (texto.equals(""))){
            vacio=true;
        }
        return vacio;
    }
}
