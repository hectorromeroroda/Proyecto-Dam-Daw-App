package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Otros.AdaptadorAllPostBD;
import com.example.hector.proyectodamdaw.Otros.AdaptadorCommunitiesBD;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
import com.example.hector.proyectodamdaw.R;


/**
 * Created by Hector on 07-Apr-18.
 */

public class AllPostFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerPosts;
    public AdaptadorAllPostBD adaptadorBd;
    private Post post = new Post();
    private AppDataSources bd;
    String idComunidadActual;

    public AllPostFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_post_fragment, container, false);

        recyclerPosts = (RecyclerView) view.findViewById(R.id.rcvAllPost);
        bd = new AppDataSources(getContext());

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idComunidadActual=globales.getCommunityId();
        
        //ReciclerView de comunidades a las que pertenece
        adaptadorBd = new AdaptadorAllPostBD(getContext(),post,bd.todosPostCommunity(idComunidadActual));
        recyclerPosts.setAdapter(adaptadorBd);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerPosts.setLayoutManager(layoutManager);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }


}

