package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hector.proyectodamdaw.R;


/**
 * Created by Hector on 07-Apr-18.
 */

public class AllPostFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerPosts;

    public AllPostFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_post_fragment, container, false);

        recyclerPosts = (RecyclerView) view.findViewById(R.id.rcvAllPost);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }
}

