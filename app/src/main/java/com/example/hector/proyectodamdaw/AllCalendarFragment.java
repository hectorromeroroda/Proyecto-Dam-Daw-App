package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * Created by Hector on 08-Apr-18.
 */

public class AllCalendarFragment extends Fragment{

    protected RecyclerView.LayoutManager layoutManager;
    protected RecyclerView recyclerViwCalendar;

    public AllCalendarFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.all_calendar_fragment, container, false);

        recyclerViwCalendar = (RecyclerView) view.findViewById(R.id.rcvAllCalendar);



        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }
}

