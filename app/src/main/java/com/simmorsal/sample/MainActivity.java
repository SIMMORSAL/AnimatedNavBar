package com.simmorsal.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.simmorsal.animatednavbar.Interfaces.OnNavViewClickListener;
import com.simmorsal.animatednavbar.NavBarLayout;
import com.simmorsal.animatednavbar.NavView;
import com.simmorsal.animatednavbar.navViews.NavBarRoundedTop;
import com.simmorsal.animatednavbar.navViews.NavBarSlideFromTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    int i = 2;
    private ViewPager viewPager;
    private NavBarLayout navBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializer();
        tests();
        navBar();
        nbr();
        nbs();
    }

    private void initializer() {
        viewPager = findViewById(R.id.viewPager);
        navBarLayout = findViewById(R.id.navBarLayout);
    }

    private void tests() {
//        AlertDialog.Builder
    }

    private void navBar() {
        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        adapter.addItem(new Fragment1().setColor(Color.parseColor("#E91E63")));
        adapter.addItem(new Fragment1().setColor(Color.parseColor("#3F51B5")));
        adapter.addItem(new Fragment1().setColor(Color.parseColor("#FF9800")));
        adapter.addItem(new Fragment1().setColor(Color.parseColor("#E64A19")));
        adapter.addItem(new Fragment1().setColor(Color.parseColor("#5D4037")));


        navBarLayout.addNavView(
                new NavBarSlideFromTop(this)
                        .setBackgroundOverColor(Color.parseColor("#E64A19"))
                        .setIcon(R.drawable.ic_audiotrack_black_24dp)
                        .setIconSize(24)
        );

        navBarLayout.addNavView(
                new NavBarSlideFromTop(this)
                        .setBackgroundOverColor(Color.parseColor("#009688"))
                        .setIcon(R.drawable.ic_audiotrack_black_24dp)
                        .setIconSize(24)
        );

        viewPager.setAdapter(adapter);
        navBarLayout.setDefaultTab(2);
        navBarLayout.setViewPager(viewPager);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navBarLayout.setOnNavViewClickListener(new OnNavViewClickListener() {
                    @Override
                    public void onClick(NavView view, int position) {
                        Log.i("11111", "MainActivity => onClick: " + "GETS CALLED");
                        if (view.getId() == R.id.nbs1)
                            Toast.makeText(MainActivity.this, "The good", Toast.LENGTH_SHORT).show();

                        else if (view.getId() == R.id.nbs3)
                            Toast.makeText(MainActivity.this, "The bad", Toast.LENGTH_SHORT).show();

                        else if (position == 4) {
                            Toast.makeText(MainActivity.this, "The ugly", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }, 5000);
    }

    private void nbr() {
        final NavBarRoundedTop nbr = findViewById(R.id.nbr);
        nbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i + 2;
                nbr.setStrokeWidth(i);
            }
        });
    }

    private void nbs() {
        final NavBarSlideFromTop nbs1 = findViewById(R.id.nbs1);
        final NavBarSlideFromTop nbs2 = findViewById(R.id.nbs2);
        final NavBarSlideFromTop nbs3 = findViewById(R.id.nbs3);

        nbs1.setIcon(R.drawable.ic_audiotrack_black_24dp)
                .setBackgroundOverColor(Color.parseColor("#E91E63"))
                .activate(false);

        nbs2.setTitle("ASDF")
                .setBackgroundOverColor(Color.parseColor("#3F51B5"))
                .setTitleSize(18);

        nbs3.setTitle("QWERTY")
                .setTitleSize(12)
                .setBackgroundOverColor(Color.parseColor("#FF9800"))
                .setIcon(R.drawable.ic_face_black_24dp);

//        nbs1.setOnClickListener(new View.OnNavViewClickListener() {
//            @Override
//            public void onClick(View v) {
//                nbs1.activate(true);
//                if (nbs2.isActive())
//                    nbs2.deactivate(true);
//                if (nbs3.isActive())
//                    nbs3.deactivate(true);
//            }
//        });
//
//        nbs2.setOnClickListener(new View.OnNavViewClickListener() {
//            @Override
//            public void onClick(View v) {
//                nbs2.activate(true);
//                if (nbs1.isActive())
//                    nbs1.deactivate(true);
//                if (nbs3.isActive())
//                    nbs3.deactivate(true);
//            }
//        });
//
//        nbs3.setOnClickListener(new View.OnNavViewClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                nbs3.activate(true);
//                if (nbs2.isActive())
//                    nbs2.deactivate(true);
//                if (nbs1.isActive())
//                    nbs1.deactivate(true);
//            }
//        });
    }
}
