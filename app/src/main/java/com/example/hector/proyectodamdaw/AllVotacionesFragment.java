package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


/**
 * Created by Hector on 06-Apr-18.
 */

public class AllVotacionesFragment extends Fragment {

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewVotaciones;
    RadioButton rdbInProgress;
    RadioButton rdbclosed;



    public AllVotacionesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_votaciones_fragment, container, false);

        recyclerViewVotaciones = (RecyclerView) view.findViewById(R.id.rcvVotaciones);
        rdbInProgress=(RadioButton)view.findViewById(R.id.rdbVotacionInProgress);
        rdbclosed=(RadioButton)view.findViewById(R.id.rdbVotacionClosed);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if (rdbInProgress.isChecked()==true) {
            //código accion radiobuton
        } else
        if (rdbclosed.isChecked()==true) {
            // código  accion radiobuton
        }

    }
}

