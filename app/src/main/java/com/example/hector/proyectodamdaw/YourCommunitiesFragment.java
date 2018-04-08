package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Hector on 03-Apr-18.
 */

public class YourCommunitiesFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViewYourCommunities;
    protected RecyclerView recyclerViewYourInvitations;

    public YourCommunitiesFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.your_communities_fragment, container, false);

        recyclerViewYourCommunities = (RecyclerView) view.findViewById(R.id.rcvYourCommunities);
        recyclerViewYourInvitations = (RecyclerView) view.findViewById(R.id.rcvYourinvitations);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }
}