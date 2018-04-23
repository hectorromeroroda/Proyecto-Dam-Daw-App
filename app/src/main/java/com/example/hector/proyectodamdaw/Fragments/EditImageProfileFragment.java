package com.example.hector.proyectodamdaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hector.proyectodamdaw.R;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditImageProfileFragment extends Fragment{

    ImageButton imgbLoadImage;
    ImageButton imgbTakeImage;
    ImageView imgUser;
    Button btnAcceptChanges;

    public EditImageProfileFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.edit_image_profile_fragment, container, false);

        imgbLoadImage= (ImageButton)view.findViewById(R.id.imgbLoadImageEdit);
        imgbTakeImage= (ImageButton)view.findViewById(R.id.imgbLoadImageCamera);
        imgUser = (ImageView)view.findViewById(R.id.imgUserEdit) ;
        btnAcceptChanges = (Button) view.findViewById(R.id.btnAcceptEditImage);

        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        btnAcceptChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //COMPROBAR QUE SE HAYA CARGADO ALGUNA IMAGEN
                //SUBIR IMAGEN AL FTP?
                //CRREAR JSON CON LOS DATOS?
                //CREAR CONEXION HTTP-POST PARA ENVIAR LOS DATOS DE LA URL?
                //GUARDAR IMAGEN EN ALMACENAMIENTO LOCAL

            }


        });


        imgbLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AQUI ACCIONES AL DAR A CARGAR IMAGEN DESDE ARCHIVO
            }
        });

        imgbTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AQUI ACCIONES AL DAR A CARGAR IMAGEN DESDE LA CAMARA
            }
        });

    }
}

