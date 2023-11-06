package com.profejuan.menulateral;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //Variables
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ListView menu;

    //-----------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menu = findViewById(R.id.menu);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//Adaptador
                cambiarPantalla(i);
            }
        });

        cambiarPantalla(0);
    }

    void cambiarPantalla(int quePantalla) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment;

        switch (quePantalla) {//Distintas pantallas disponibles para la navegación
            case 0:
            default:
                fragment = new InicioFragment();
                setTitle("Inicio");//Pantalla Inicial de la aplicación
                break;
            case 1:
                fragment = new PrincipalFragment();
                setTitle("Energia por distrito");//Pantalla de base de datos
                break;
            case 2:
                fragment = new PerfilFragment();
                setTitle("Perfil");//Pantalla de perfil
                break;
        }

        transaction.replace(R.id.contenedorPantalla, fragment, "tag");
        transaction.commit();

        drawerLayout.closeDrawers();
    }


}