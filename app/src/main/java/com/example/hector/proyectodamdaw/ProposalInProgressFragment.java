package com.example.hector.proyectodamdaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Hector on 09-Apr-18.
 */

public class ProposalInProgressFragment extends Fragment{

    TextView NameProposal;
    TextView txvDescriptionProposal;
    TextView txvQuestionProposal;
    RadioButton rdbAcceptProposal;
    RadioButton rdbDiscardProposal;
    Button sendProposal;


    public ProposalInProgressFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.proposal_in_progress_fragment, container, false);

        NameProposal =(TextView) view.findViewById(R.id.txvNameProposal);
        txvDescriptionProposal =(TextView) view.findViewById(R.id.txvDescriptionProposal);
        txvQuestionProposal =(TextView) view.findViewById(R.id.txvQuestionProposal);
        sendProposal = (Button) view.findViewById(R.id.btnSendProposal);
        rdbAcceptProposal=(RadioButton)view.findViewById(R.id.rdbAcceptProposal);
        rdbDiscardProposal=(RadioButton)view.findViewById(R.id.rdbDiscardProposal);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);



    }
}


