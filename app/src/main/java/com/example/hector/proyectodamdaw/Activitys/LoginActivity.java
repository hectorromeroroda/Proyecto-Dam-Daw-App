package com.example.hector.proyectodamdaw.Activitys;

import android.content.Intent;
import android.database.Cursor;
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

import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Fragments.LoginFragment;
import com.example.hector.proyectodamdaw.Fragments.SingUpFragment;
import com.example.hector.proyectodamdaw.GlobalVariables;
import com.example.hector.proyectodamdaw.R;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppDataSources bd;
    private int idUserSqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

        //Para buscar si esta marcado el rememberMe
        bd = new AppDataSources(this);
        Cursor cursorRememberMeState = bd.rememmberMeUserLogin();

        if (cursorRememberMeState.moveToFirst() != false){
            idUserSqlite = cursorRememberMeState.getInt(0);

            //Poner en id de usuario en variable gobal
            GlobalVariables globales = GlobalVariables.getInstance();
            globales.setIdUserSqlite(idUserSqlite);
            globales.setRefreshData(true);

            //Envia a AllComminities
            Intent intent = new Intent(this, CommunitiesActivity.class );
            startActivity(intent);
        }else{
            //Cambiar de fragment
            Fragment fragmentLogin = new LoginFragment();
            Fragment fragmentSingUp = new SingUpFragment();
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.contentLogin);

            if (currentFragment == null) {
                //carga del primer fragment justo en la carga inicial de la app
                loadFragment(fragmentLogin);
            } else{
                if (currentFragment.getClass().getName().equalsIgnoreCase(fragmentLogin.getClass().getName())) {

                }else{
                    if (currentFragment.getClass().getName().equalsIgnoreCase(fragmentSingUp.getClass().getName())) {

                    }
                }
            }
        }

    }

    private void loadFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentLogin, newFragment,newFragment.getClass().getName()).commit();
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
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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
        }else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class );
            startActivityForResult(intent,123);
        }

        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
