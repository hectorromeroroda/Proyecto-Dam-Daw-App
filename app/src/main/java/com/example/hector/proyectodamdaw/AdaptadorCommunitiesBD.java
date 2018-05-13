package com.example.hector.proyectodamdaw;

import android.content.Context;
import android.database.Cursor;

import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;

/**
 * Created by Hector on 10/05/2018.
 */

public class AdaptadorCommunitiesBD extends AdaptadorCommunities{
    public Cursor cursor;


    public AdaptadorCommunitiesBD(Context contexto, Communitie communitie, Cursor cursor) {
        super(contexto, communitie);
        this.cursor = cursor;
    }

    public  Cursor getCursor(){
        return cursor;
    }

    public void setCursor (Cursor cursor){
        this.cursor=cursor;
    }

    public Communitie communitiePosicion(int posicion){
        cursor.moveToPosition(posicion);
        return AppDataSources.extraerCommunity(cursor);
    }

    public  int idPosicion (int posicion){
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Communitie communitie = communitiePosicion(posicion);
        personalizaVista(holder, communitie);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}

