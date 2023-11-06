package com.profejuan.menulateral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PerfilFragment extends Fragment {

    //Variables

    public static final String EXTRA_INFO = "default";
    private Button btnCapture;
    private ImageView imgCapture; //Contenedior de la imagen de camara
    public EditText Nombre; //Nombre del perfil
    public EditText Edad; //Edad del perfil
    public EditText Correo; //Correo del perfil
    private static final int Image_Capture_Code = 1;

    //------------------------------------------------------------------------------------------------------------------------//

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        //Referencias
        btnCapture = (Button) view.findViewById(R.id.tomarFotoButton);
        imgCapture = (ImageView) view.findViewById(R.id.fotoImageView);
        Nombre = view.findViewById(R.id.nombreEditText);
        Edad = view.findViewById(R.id.edadEditText);
        Correo = view.findViewById(R.id.correoEditText);

        //Guardar los datos del perfil con sharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Nombre.setText(sharedPreferences.getString("nombre", ""));
        Edad.setText(sharedPreferences.getString("edad", ""));
        Correo.setText(sharedPreferences.getString("correo", ""));
        Nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = sharedPreferences.edit();//Editor del sharedPreferences
                editor.putString("nombre", s.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Edad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = sharedPreferences.edit();//Editor del sharedPreferences
                editor.putString("edad", s.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = sharedPreferences.edit();//Editor del sharedPreferences
                editor.putString("correo", s.toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {//Boton de la camara
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt, Image_Capture_Code);
            }
        });
        String rutaImagenGuardada = sharedPreferences.getString("ruta_imagen", "");//Codigo para cargar la imagen guardada
        if (!rutaImagenGuardada.isEmpty()) {
            Bitmap imagenCargada = cargarImagenDesdeAlmacenamiento(rutaImagenGuardada);
            imgCapture.setImageBitmap(imagenCargada);
        }


        return view;
    }

    private String guardarImagenEnAlmacenamiento(Bitmap imagen) { //Metodo para guardar la imagen
        String rutaArchivo = null;
        try {
            File directorioAlmacenamiento = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File archivoImagen = new File(directorioAlmacenamiento, "imagen_perfil.jpg");//Como se guarda

            FileOutputStream fos = new FileOutputStream(archivoImagen);
            imagen.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            rutaArchivo = archivoImagen.getAbsolutePath();//Ruta del archivo
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rutaArchivo;
    }

    private Bitmap cargarImagenDesdeAlmacenamiento(String rutaArchivo) {//Motodo cargar imagen
        Bitmap imagen = null;
        try {
            File archivoImagen = new File(rutaArchivo);
            imagen = BitmapFactory.decodeFile(archivoImagen.getAbsolutePath());//Pillar la imagen en esa ruta
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagen;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {

            Bitmap bp = (Bitmap) data.getExtras().get("data");//Bitmap para la imagen
            imgCapture.setImageBitmap(bp);

        }
        if (requestCode == Image_Capture_Code && resultCode == Activity.RESULT_OK) {
            Bitmap imagenCapturada = (Bitmap) data.getExtras().get("data");
            imgCapture.setImageBitmap(imagenCapturada);

            // Guardar la imagen en el almacenamiento y obtener su ruta
            String rutaImagen = guardarImagenEnAlmacenamiento(imagenCapturada);

            // Guardar la ruta de la imagen en SharedPreferences
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ruta_imagen", rutaImagen);
            editor.apply();
        }


    }
}

