package com.example.hector.proyectodamdaw.Otros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hector.proyectodamdaw.Content.Communitie;
import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 26/05/2018.
 */

public class AdaptadorAllOtherCommunities  extends RecyclerView.Adapter<AdaptadorAllOtherCommunities.ViewHolder>{
    protected Communitie communitie; //Comunidad a mostrar
    protected View.OnClickListener onClickListener;
    protected LayoutInflater inflador; //Crea Layouts a partir del XML
    protected Context contexto; //Lo necesitamos para el inflador
    public AdaptadorAllOtherCommunities(Context contexto, Communitie communitie) {
        this.contexto = contexto;
        this.communitie = communitie;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView CommunitieName, ComminitieDescription, numUsersCommunitie;

        public ViewHolder(View itemView) {
            super(itemView);
            CommunitieName = (TextView) itemView.findViewById(R.id.txvRowNameOtherCommunitie);
            ComminitieDescription = (TextView) itemView.findViewById(R.id.txvrowDescriptionOtherCommunitie);
            numUsersCommunitie = (TextView) itemView.findViewById(R.id.txvRowNumUsersOtherCommunitie);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    public AdaptadorAllOtherCommunities.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.activity_row_other_communitie, null);
        v.setOnClickListener(onClickListener);
        return new AdaptadorAllOtherCommunities.ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    public void onBindViewHolder(AdaptadorAllOtherCommunities.ViewHolder holder, int posicion) {

    }

    // Personalizamos un ViewHolder a partir de una comunidad
    public void personalizaVista(AdaptadorAllOtherCommunities.ViewHolder holder, Communitie communitie) {
        holder.CommunitieName.setText(communitie.getName());
        holder.ComminitieDescription.setText(communitie.getDescription());
        holder.numUsersCommunitie.setText(String.valueOf(communitie.getnumUsers()));
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


