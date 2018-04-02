package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hector on 31-Mar-18.
 */

public class LoginFragment extends Fragment{

    public LoginFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);


    }
}
