package com.example.hector.proyectodamdaw.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Fragments.AllCalendarFragment;
import com.example.hector.proyectodamdaw.Fragments.AllPostFragment;
import com.example.hector.proyectodamdaw.Fragments.AllProposalFragment;
import com.example.hector.proyectodamdaw.Fragments.AllVotacionesFragment;
import com.example.hector.proyectodamdaw.Fragments.CreateCommunitieFragment;
import com.example.hector.proyectodamdaw.Fragments.OtherCommunitiesFragment;
import com.example.hector.proyectodamdaw.Otros.GlobalVariables;
import com.example.hector.proyectodamdaw.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SingleCommunitieActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabs;
    private AppDataSources bd;
    ProgressDialog Dialog;
    String userToken;
    String idComunidadActual;
    String idProposal="";
    int idUserSqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities);
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
        Dialog = new ProgressDialog(this);
        Dialog.setCancelable(false);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(R.string.tabVotaciones));
        tabs.addTab(tabs.newTab().setText(R.string.tabPost));
        tabs.addTab(tabs.newTab().setText(R.string.tabPropuestas));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        GlobalVariables globales = GlobalVariables.getInstance().getInstance();
        idComunidadActual=globales.getCommunityId();
        idUserSqlite=globales.getIdUserSqlite();
        globales.setProposalId(idProposal);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        TabAdapterSingle adapter = new TabAdapterSingle(getSupportFragmentManager(), tabs.getTabCount());
        viewPager.setAdapter(adapter);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        RefreshContentCommuities();
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
                Toast toastAlerta = Toast.makeText(getApplicationContext(), "Para ver que funciona Singlecommunities activity", Toast.LENGTH_SHORT);
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
            int state = 1;
            int intFalse=0;
            bd.updateUserRememberMe(intFalse,state);

            Intent intent = new Intent(this, LoginActivity.class );
            //Limpia la pila de activitys para llenarla empezando de 0
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
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
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, CreateContentActivity.class );
            startActivityForResult(intent,123);
        }else if (id == R.id.nav_invite_user) {
            Intent intent = new Intent(this, InviteUserActivity.class );
            startActivityForResult(intent,123);
        }

        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class TabAdapterSingle extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public TabAdapterSingle(FragmentManager fm, int tabCount) {
            super(fm);
            //Initializing tab count
            this.tabCount= tabCount;
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new AllVotacionesFragment();
                    break;
                case 1:
                    fragment = new AllPostFragment();
                    break;
                case 2:
                    fragment = new AllProposalFragment();
                    break;
            }
            return fragment;
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return 3;
        }
    }

    private void RefreshContentCommuities() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(0, 10000);

        String Url = "http://192.168.43.219:3000/community/" + idComunidadActual;

        Cursor cursorUserToken = bd.searchUserToken(idUserSqlite);
        if (cursorUserToken.moveToFirst() != false){
            userToken = cursorUserToken.getString(0);
        }

        client.addHeader("Authorization", "Bearer " + userToken);
        client.get(this, Url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialog.setMessage("Cargando datos...");
                Dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsId;
                String jsTitle;
                String jsDescription;
                String jsType;
                String strResponse = new String(responseBody);
                JSONArray jsContents = new JSONArray();
                boolean yaExiste= false;

                try {



                    JSONObject jsResponse= new JSONObject(strResponse);

                    jsContents = jsResponse.getJSONArray("contents");
                    for (int index = 0; index < jsContents.length(); index++) {
                        JSONObject objContenido = jsContents.getJSONObject(index);

                        yaExiste= false;

                        jsId = objContenido.getString("_id");
                        jsTitle = objContenido.getString("title");
                        jsDescription = objContenido.getString("description");
                        jsType = objContenido.getString("type");


                        switch (jsType) {
                            case "Request":
                                Cursor cursorIdPropositionExist = bd.searchIdProposition(jsTitle);
                                if (cursorIdPropositionExist.moveToFirst() != false) {
                                    bd.updatePropositiontId(jsId,jsTitle);
                                }else{
                                    bd.saveProposal1(jsTitle,jsDescription,jsId,idComunidadActual);
                                }
                                break;
                            case "Post":
                                Cursor cursorIdPostExist =bd.searchIdPost(jsTitle);
                                if (cursorIdPostExist.moveToFirst() != false) {
                                   bd.updatePostId(jsId,jsTitle);
                                }else{
                                    bd.savePost1(jsTitle,jsDescription,idComunidadActual,jsId);
                                }
                                break;
                            case "Poll":
                                Cursor cursorIdPollExist =bd.searchIdVotacion(jsTitle);
                                if (cursorIdPollExist.moveToFirst() != false) {
                                    bd.updatePolltId(jsId,jsTitle);
                                }else{
                                    bd.savePoll1(jsTitle,jsDescription,idComunidadActual,jsId);
                                }
                                break;
                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String mensajeError = new String(error.getMessage().toString());
                String valor = "No se ha podido recuperar los datos desde el servidor. " + mensajeError;
                Toast toastError = Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_SHORT);
                toastError.show();
                Dialog.dismiss();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });

    }


}
