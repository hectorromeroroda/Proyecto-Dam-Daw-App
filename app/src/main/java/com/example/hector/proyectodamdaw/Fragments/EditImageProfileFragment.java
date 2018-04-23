package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditImageProfileFragment extends Fragment{


    public EditImageProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_image_profile_fragment, container, false);


        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }

}
