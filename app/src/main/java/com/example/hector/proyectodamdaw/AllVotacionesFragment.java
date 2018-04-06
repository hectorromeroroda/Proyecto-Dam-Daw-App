package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Hector on 06-Apr-18.
 */

public class AllVotacionesFragment extends Fragment {



    public AllVotacionesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_votaciones_fragment, container, false);



        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }
}

