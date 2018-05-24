package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.database.Cursor;

import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.Content.Proposal;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;

/**
 * Created by Hector on 24/05/2018.
 */

public class AdaptadorAllProposalsBD extends  AdaptadorAllProposals{
    public Cursor cursor;


    public AdaptadorAllProposalsBD(Context contexto, Proposal proposal, Cursor cursor) {
        super(contexto, proposal);
        this.cursor = cursor;
    }

    public  Cursor getCursor(){
        return cursor;
    }

    public void setCursor (Cursor cursor){
        this.cursor=cursor;
    }

    public Proposal proposalPosicion(int posicion){
        cursor.moveToPosition(posicion);
        return AppDataSources.extraerProposal(cursor);
    }

    public  int idPosicion (int posicion){
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(AdaptadorAllProposals.ViewHolder holder, int posicion) {
        Proposal proposal = proposalPosicion(posicion);
        personalizaVista(holder, proposal);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}

