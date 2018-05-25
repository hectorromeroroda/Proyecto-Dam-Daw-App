package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 25/05/2018.
 */

public class InviteUserFragment extends Fragment {

    public InviteUserFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.invite_user_fragment, container, false);





        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }


}
