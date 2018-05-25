package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.database.Cursor;

import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.Content.Votacion;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;

/**
 * Created by Hector on 25/05/2018.
 */

public class AdaptadorAllVotacionesBD extends AdaptadorAllVotaciones{
    public Cursor cursor;


    public AdaptadorAllVotacionesBD(Context contexto, Votacion votacion, Cursor cursor) {
        super(contexto, votacion);
        this.cursor = cursor;
    }

    public  Cursor getCursor(){
        return cursor;
    }

    public void setCursor (Cursor cursor){
        this.cursor=cursor;
    }

    public Votacion votacionPosicion(int posicion){
        cursor.moveToPosition(posicion);
        return AppDataSources.extraerVotacion(cursor);
    }

    public  int idPosicion (int posicion){
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(AdaptadorAllVotaciones.ViewHolder holder, int posicion) {
        Votacion votacion = votacionPosicion(posicion);
        personalizaVista(holder, votacion);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}

