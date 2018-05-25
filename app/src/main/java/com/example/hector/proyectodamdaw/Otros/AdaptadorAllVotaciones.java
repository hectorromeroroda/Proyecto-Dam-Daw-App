package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hector.proyectodamdaw.Content.Post;
import com.example.hector.proyectodamdaw.Content.Votacion;
import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 25/05/2018.
 */

public class AdaptadorAllVotaciones  extends RecyclerView.Adapter<AdaptadorAllVotaciones.ViewHolder>{
    protected Votacion Votacion; //Votacion a mostrar
    protected View.OnClickListener onClickListener;
    protected LayoutInflater inflador; //Crea Layouts a partir del XML
    protected Context contexto; //Lo necesitamos para el inflador

    public AdaptadorAllVotaciones(Context contexto, Votacion votacion) {
        this.contexto = contexto;
        this.Votacion = votacion;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView PollTitle, PollDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            PollTitle = (TextView) itemView.findViewById(R.id.txvRowNameVotacion);
            PollDescription = (TextView) itemView.findViewById(R.id.txvRowDescriptionVotacion);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    public AdaptadorAllVotaciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.activity_row_votacion_in_progress, null);
        v.setOnClickListener(onClickListener);
        return new AdaptadorAllVotaciones.ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    public void onBindViewHolder(AdaptadorAllVotaciones.ViewHolder holder, int posicion) {

    }

    // Personalizamos un ViewHolder a partir de una comunidad
    public void personalizaVista(AdaptadorAllVotaciones.ViewHolder holder, Votacion votacion) {
        holder.PollTitle.setText(votacion.getTitle());
        holder.PollDescription.setText(votacion.getDescription());

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
