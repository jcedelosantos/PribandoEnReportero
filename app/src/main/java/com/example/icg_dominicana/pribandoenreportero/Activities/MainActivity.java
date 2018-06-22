package com.example.icg_dominicana.pribandoenreportero.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.icg_dominicana.pribandoenreportero.Adapters.PagerAdapter;
import com.example.icg_dominicana.pribandoenreportero.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.id_tapLayout );
        tabLayout.addTab( tabLayout.newTab().setText( "Reportado" ) );
        tabLayout.addTab( tabLayout.newTab().setText( "Reportar" ) );
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL );


        final ViewPager viewPager = (ViewPager) findViewById( R.id.id_viewPager );
        PagerAdapter pagerAdapter = new PagerAdapter( getSupportFragmentManager(), tabLayout.getTabCount() );

        viewPager.setAdapter( pagerAdapter );
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );

        tabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem( position );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );
    }
}
