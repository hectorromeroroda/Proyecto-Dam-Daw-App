package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 24/05/2018.
 */

public class AdaptadorAllPost extends RecyclerView.Adapter<AdaptadorAllPost.ViewHolder>{
    protected Post Post; //Post a mostrar
    protected View.OnClickListener onClickListener;
    protected LayoutInflater inflador; //Crea Layouts a partir del XML
    protected Context contexto; //Lo necesitamos para el inflador

    public AdaptadorAllPost(Context contexto, Post post) {
        this.contexto = contexto;
        this.Post = post;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView PostTitle, PostDescription, PostContent;

        public ViewHolder(View itemView) {
            super(itemView);
            PostTitle = (TextView) itemView.findViewById(R.id.txvPostTitle);
            PostDescription = (TextView) itemView.findViewById(R.id.txvPostDescription);
            PostContent = (TextView) itemView.findViewById(R.id.txvPostcontent);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    public AdaptadorAllPost.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.activity_row_post, null);
        v.setOnClickListener(onClickListener);
        return new AdaptadorAllPost.ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    public void onBindViewHolder(AdaptadorAllPost.ViewHolder holder, int posicion) {

    }

    // Personalizamos un ViewHolder a partir de una comunidad
    public void personalizaVista(AdaptadorAllPost.ViewHolder holder, Post post) {
        holder.PostTitle.setText(post.getTitle());
        holder.PostDescription.setText(post.getDescription());
        holder.PostContent.setText(String.valueOf(post.getContent()));
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
