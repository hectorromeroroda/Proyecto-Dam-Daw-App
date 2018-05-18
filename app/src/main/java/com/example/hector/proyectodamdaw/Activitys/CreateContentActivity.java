package com.example.hector.proyectodamdaw.Activitys;

import android.content.Intent;
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

import com.example.hector.proyectodamdaw.DataBase.AppDataSources;
import com.example.hector.proyectodamdaw.Fragments.AllPostFragment;
import com.example.hector.proyectodamdaw.Fragments.AllProposalFragment;
import com.example.hector.proyectodamdaw.Fragments.AllVotacionesFragment;
import com.example.hector.proyectodamdaw.R;

public class CreateContentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabs;
    private AppDataSources bd;

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

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(R.string.tab1));
        tabs.addTab(tabs.newTab().setText(R.string.tab2));
        tabs.addTab(tabs.newTab().setText(R.string.tab3));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        TabAdapterCreate adapter = new TabAdapterCreate(getSupportFragmentManager(), tabs.getTabCount());
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
        }else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this, EditProfileActivity.class );
            startActivityForResult(intent,123);
        }

        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class TabAdapterCreate extends FragmentStatePagerAdapter {

        //integer to count number of tabs
        int tabCount;

        //Constructor to the class
        public TabAdapterCreate(FragmentManager fm, int tabCount) {
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
}
