package com.prateek.view.custom_views_android;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.prateek.view.custom_views_android.Fragments.BoxDrawingFrag;
import com.prateek.view.custom_views_android.Fragments.CustomFanControllerFrag;
import com.prateek.view.custom_views_android.Fragments.DrawTextFrag;
import com.prateek.view.custom_views_android.Fragments.EditTextWithClearFrag;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FrameLayout frameLayout;
    private int navItemIndex = 0;
    private NavigationView navigationView;

    private static final String TAG_EDIT_TEXT_WITH_CLEAR = "edit_text_with_clear";
    private static final String TAG_FAN_CONTROLLER = "fan_controller";
    private static final String TAG_DRAW_TEXT = "draw_text";
    private static final String TAG_DRAW_BOX = "draw_box";
    private static String CURRENT_TAG = TAG_EDIT_TEXT_WITH_CLEAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        frameLayout = (FrameLayout) findViewById(R.id.main_container);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadHomeFragment();

    }

    private void loadHomeFragment() {
        selectNavMenu();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        //we check by calling getSupportFragmentManager().findFragmentByTag(CURRENT_TAG)
        //above method will return null if no fragments are found with CURRENT_TAG.
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_container, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();

    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new EditTextWithClearFrag();
            case 1:
                return new CustomFanControllerFrag();
            case 2:
                return new DrawTextFrag();
            case 3:
                return new BoxDrawingFrag();
        }
        return new EditTextWithClearFrag();

    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            navItemIndex=0;
            CURRENT_TAG=TAG_EDIT_TEXT_WITH_CLEAR;


        } else if (id == R.id.nav_gallery) {
            navItemIndex=1;
            CURRENT_TAG=TAG_FAN_CONTROLLER;


        } else if (id == R.id.nav_slideshow) {

            navItemIndex=2;
            CURRENT_TAG=TAG_DRAW_TEXT;

        } else if (id == R.id.nav_manage) {

            navItemIndex=3;
            CURRENT_TAG=TAG_DRAW_BOX;

        }

        loadHomeFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
