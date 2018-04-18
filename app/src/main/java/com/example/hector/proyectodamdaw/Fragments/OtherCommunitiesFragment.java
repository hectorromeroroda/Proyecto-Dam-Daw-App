package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 05/04/2018.
 */

public class OtherCommunitiesFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewOtherCommunities;
    protected RecyclerView recyclerViewOtherYourInvitations;

    public OtherCommunitiesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.other_communities_fragment, container, false);


        recyclerViewOtherCommunities = (RecyclerView) view.findViewById(R.id.rcvOtherCommunities);
        recyclerViewOtherYourInvitations = (RecyclerView) view.findViewById(R.id.rcvOtherYourinvitations);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }

}
