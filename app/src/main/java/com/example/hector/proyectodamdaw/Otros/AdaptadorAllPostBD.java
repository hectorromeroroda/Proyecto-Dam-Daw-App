package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.database.Cursor;

import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.DataBase.AppDataSources;

/**
 * Created by Hector on 24/05/2018.
 */

public class AdaptadorAllPostBD extends  AdaptadorAllPost{
    public Cursor cursor;


    public AdaptadorAllPostBD(Context contexto, Post post, Cursor cursor) {
        super(contexto, post);
        this.cursor = cursor;
    }

    public  Cursor getCursor(){
        return cursor;
    }

    public void setCursor (Cursor cursor){
        this.cursor=cursor;
    }

    public Post postPosicion(int posicion){
        cursor.moveToPosition(posicion);
        return AppDataSources.extraerPost(cursor);
    }

    public  int idPosicion (int posicion){
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(AdaptadorAllPost.ViewHolder holder, int posicion) {
        Post post = postPosicion(posicion);
        personalizaVista(holder, post);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
