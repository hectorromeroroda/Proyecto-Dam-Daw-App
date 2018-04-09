package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Hector on 09-Apr-18.
 */

public class ProposalClosedFragment extends Fragment{

    TextView NameProposalclosed;
    TextView DescriptionProposalclosed;
    TextView QuestionProposalClosed;
    RadioButton AcceptProposalClosed;
    RadioButton DiscardProposalclosed;



    public ProposalClosedFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.proposal_closed_fragment, container, false);

        NameProposalclosed =(TextView) view.findViewById(R.id.txvNameProposalClosed);
        DescriptionProposalclosed =(TextView) view.findViewById(R.id.txvDescriptionProposalClosed);
        QuestionProposalClosed =(TextView) view.findViewById(R.id.txvQuestionProposalClosed);
        AcceptProposalClosed =(RadioButton)view.findViewById(R.id.rdbAcceptProposalClosed);
        DiscardProposalclosed =(RadioButton)view.findViewById(R.id.rdbDiscardProposalclosed);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }
}


