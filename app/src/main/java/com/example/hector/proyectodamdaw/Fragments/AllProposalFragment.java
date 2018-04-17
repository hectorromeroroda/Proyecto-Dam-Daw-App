package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 07-Apr-18.
 */

public class AllProposalFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewProposals;
    RadioButton rdbInProgress;
    RadioButton rdbclosed;


    public AllProposalFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_proposal_fragment, container, false);

        recyclerViewProposals = (RecyclerView) view.findViewById(R.id.rcvProposals);
        rdbInProgress=(RadioButton)view.findViewById(R.id.rdbProposalInProgress);
        rdbclosed=(RadioButton)view.findViewById(R.id.rdbProposalClosed);



        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }

    @Override
    public void onResume() {

        //AQUI CODIGO PARA CARGAR EL ESTADO ANTES DE QUE RECARGUE LA ACTIVIDAD

        super.onResume();
    }

    @Override
    public void onPause() {

        //AQUI CODIGO PARA GUARDAR EL ESTADO ANTES DE QUE SE CIERRE LA ACTIVIDAD

        super.onPause();
    }


}
