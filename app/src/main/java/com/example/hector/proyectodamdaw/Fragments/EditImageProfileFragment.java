package com.example.hector.proyectodamdaw.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.hector.proyectodamdaw.R;

import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Hector on 23/04/2018.
 */

public class EditImageProfileFragment extends Fragment{

    ImageButton imgbLoadImage;
    ImageButton imgbTakeImage;
    ImageView imgUser;
    Button btnAcceptChanges;
    private static final int SELECIONAR_IMAGEN = 10;

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

                openGaleryImage();
            }
        });

        imgbTakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AQUI ACCIONES AL DAR A CARGAR IMAGEN DESDE LA CAMARA
            }
        });

    }

    public void openGaleryImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECIONAR_IMAGEN);
    }

    public void putImageFromGalery(ImageView imageview, String uri) throws FileNotFoundException {
        if (uri != null){
            imageview.setImageBitmap(reduceBitmap(getContext(),uri,1024,1024));
        }else{
            imageview.setImageBitmap((null));
        }

    }

    public Bitmap reduceBitmap(Context context, String uri, int maxWidth, int maxHeigth) throws FileNotFoundException {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(uri)),null,options);
        options.inSampleSize = (int) Math.max(Math.ceil(options.outWidth/maxWidth), Math.ceil(options.outHeight/maxHeigth));
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(uri)),null,options);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == SELECIONAR_IMAGEN){
            String uri= data.getData().toString();
            try {
                putImageFromGalery(imgUser, uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

