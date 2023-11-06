package com.profejuan.menulateral;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InicioFragment extends Fragment { //Pantalla inicial de la aplicacion

    //Variables
    TextView nombreTextView;
    private TextView tvClock; //Reloj
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()); //Formato del reloj

    //------------------------------------------------------------------------------------------------------------------------------//

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        nombreTextView = view.findViewById(R.id.nombreTextView);

        tvClock = view.findViewById(R.id.tvClock);//referencia del reloj

        runnable = new Runnable() {
            @Override
            public void run() {
                // Obtener la hora actual
                String currentTime = "Hora actual: " + dateFormat.format(new Date());

                // Actualizar el TextView con la hora actual
                tvClock.setText(currentTime);

                // Programar la siguiente actualización después de 1 segundo
                handler.postDelayed(this, 1000);
            }
        };
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Comenzar la actualización del reloj
        handler.post(runnable);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Detener la actualización del reloj
        handler.removeCallbacks(runnable);
    }
}
