package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.Content.Proposal;
import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 24/05/2018.
 */

public class AdaptadorAllProposals extends RecyclerView.Adapter<AdaptadorAllProposals.ViewHolder>{
    protected Proposal Proposal; //Propuesta a mostrar
    protected View.OnClickListener onClickListener;
    protected LayoutInflater inflador; //Crea Layouts a partir del XML
    protected Context contexto; //Lo necesitamos para el inflador

    public AdaptadorAllProposals(Context contexto, Proposal proposal) {
        this.contexto = contexto;
        this.Proposal = proposal;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ProposalTitle, ProposalDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ProposalTitle = (TextView) itemView.findViewById(R.id.txvRowNameProposal);
            ProposalDescription = (TextView) itemView.findViewById(R.id.txvRowDescriptionProposal);

        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    public AdaptadorAllProposals.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.activity_row_proposal_in_progress, null);
        v.setOnClickListener(onClickListener);
        return new AdaptadorAllProposals.ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    public void onBindViewHolder(AdaptadorAllProposals.ViewHolder holder, int posicion) {

    }

    // Personalizamos un ViewHolder a partir de una comunidad
    public void personalizaVista(AdaptadorAllProposals.ViewHolder holder, Proposal proposal) {
        holder.ProposalTitle.setText(proposal.getTitle());
        holder.ProposalDescription.setText(proposal.getDescription());
    }
    // Indicamos el número de elementos de la lista

    public int getItemCount() {
        return 5;
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }


}
