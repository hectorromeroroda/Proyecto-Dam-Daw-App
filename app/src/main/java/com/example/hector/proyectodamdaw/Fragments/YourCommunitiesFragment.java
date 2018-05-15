package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hector.proyectodamdaw.AdaptadorCommunities;
import com.example.hector.proyectodamdaw.AdaptadorCommunitiesBD;
import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.GlobalVariables;
import com.example.hector.proyectodamdaw.R;


/**
 * Created by Hector on 03-Apr-18.
 */

public class YourCommunitiesFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewYourCommunities;
    protected RecyclerView recyclerViewYourInvitations;
    private Communitie communitie = new Communitie();
    public AdaptadorCommunitiesBD adaptadorBD;
    private AppDataSources bd;
    GlobalVariables globalBariables;

    public YourCommunitiesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.your_communities_fragment, container, false);

        recyclerViewYourCommunities = (RecyclerView) view.findViewById(R.id.rcvYourCommunities);
        recyclerViewYourInvitations = (RecyclerView) view.findViewById(R.id.rcvYourinvitations);
        bd = new AppDataSources(getContext());
        globalBariables= new GlobalVariables();

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        adaptadorBD = new AdaptadorCommunitiesBD(getContext(),communitie,bd.todasComunitiesPrueba( globalBariables.getIdUserSqlite()));
        recyclerViewYourCommunities.setAdapter(adaptadorBD);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewYourCommunities.setLayoutManager(layoutManager);

    }

}
