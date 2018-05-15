package com.example.hector.proyectodamdaw.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Fragments.EditEmailProfileFragment;
import com.example.hector.proyectodamdaw.Fragments.EditImageProfileFragment;
import com.example.hector.proyectodamdaw.Fragments.EditPasswProfileFragment;
import com.example.hector.proyectodamdaw.Fragments.EditTypeProfileFragment;
import com.example.hector.proyectodamdaw.R;

public class EditProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppDataSources bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Para poner como seleccionado el item  que se quiera del navigationdrawer
        navigationView.setCheckedItem(R.id.nav_my_community);

        bd = new AppDataSources(this);

        Spinner spinnerEditProfile = (Spinner) findViewById(R.id.spnEditProfile);
        String[] items = {"Editar email", "Editar password", "Editar tipo de perfil", "Editar imagen de perfil"};

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,items);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerEditProfile.setAdapter(spinnerArrayAdapter);
        spinnerEditProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {

                Fragment currentFragment;
                switch (pos) {
                    case 0:
                        //Cambiar de fragment
                        Fragment fragmentEditEmail = new EditEmailProfileFragment();
                         currentFragment= getSupportFragmentManager().findFragmentById(R.id.contentEditProfile);

                        if (currentFragment == null) {
                            //carga del primer fragment justo en la carga inicial de la app
                            loadFragment(fragmentEditEmail);
                        } else{
                            if (!currentFragment.getClass().getName().equalsIgnoreCase(fragmentEditEmail.getClass().getName())) {
                                loadFragment(fragmentEditEmail);
                            }
                        }
                        break;
                    case 1:
                        //Cambiar de fragment
                        Fragment fragmentEditPassw = new EditPasswProfileFragment();
                        currentFragment= getSupportFragmentManager().findFragmentById(R.id.contentEditProfile);

                        if (currentFragment == null) {
                            //carga del primer fragment justo en la carga inicial de la app
                            loadFragment(fragmentEditPassw);
                        } else{
                            if (!currentFragment.getClass().getName().equalsIgnoreCase(fragmentEditPassw.getClass().getName())) {
                                loadFragment(fragmentEditPassw);
                            }
                        }
                        break;
                    case 2:
                        //Cambiar de fragment
                        Fragment fragmentEditType = new EditTypeProfileFragment();
                        currentFragment= getSupportFragmentManager().findFragmentById(R.id.contentEditProfile);

                        if (currentFragment == null) {
                            //carga del primer fragment justo en la carga inicial de la app
                            loadFragment(fragmentEditType);
                        } else{
                            if (!currentFragment.getClass().getName().equalsIgnoreCase(fragmentEditType.getClass().getName())) {
                                loadFragment(fragmentEditType);
                            }
                        }
                        break;
                    case 3:
                        //Cambiar de fragment
                        Fragment fragmentEditImage = new EditImageProfileFragment();
                        currentFragment= getSupportFragmentManager().findFragmentById(R.id.contentEditProfile);

                        if (currentFragment == null) {
                            //carga del primer fragment justo en la carga inicial de la app
                            loadFragment(fragmentEditImage);
                        } else{
                            if (!currentFragment.getClass().getName().equalsIgnoreCase(fragmentEditImage.getClass().getName())) {
                                loadFragment(fragmentEditImage);
                            }
                        }
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });

    }

    private void loadFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentEditProfile, newFragment,newFragment.getClass().getName()).commit();
        //.addToBackStack(null)    ---SIRVE PARA GUARDAR EL FRAGMEN EN LA PILA, PERO ESTE NO LO NECESITAMOS

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.communities, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //AQUI TIENEN QUE IR LAS BUSQUEDAS
                Toast toastAlerta = Toast.makeText(getApplicationContext(), "Para ver que funciona EditProfile activity", Toast.LENGTH_SHORT);
                toastAlerta.show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //AQUI TIENEN QUE IR LAS BUSQUEDAS

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //Accion al dar boton logout
            boolean state = true;
            int intFalse=0;
            bd.updateUserRememberMe(intFalse,state);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_community) {
            Intent intent = new Intent(this, SingleCommunitieActivity.class );

            startActivityForResult(intent,123);
        } else if (id == R.id.nav_community_selector) {
            Intent intent = new Intent(this, CommunitiesActivity.class );

            startActivityForResult(intent,123);
        } else if (id == R.id.nav_events) {

        } else if (id == R.id.nav_chat) {
            /*Intent intent = new Intent(this, CreateContentActivity.class );

            startActivityForResult(intent,123);*/
        } else if (id == R.id.nav_statistics) {


        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class );
            startActivityForResult(intent,123);
        }

        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
