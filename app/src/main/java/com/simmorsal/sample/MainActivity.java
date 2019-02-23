package com.simmorsal.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.simmorsal.animatednavbar.Interfaces.OnNavViewClickListener;
import com.simmorsal.animatednavbar.NavBarLayout;
import com.simmorsal.animatednavbar.NavView;
import com.simmorsal.animatednavbar.navViews.NavViewRoundedTop;
import com.simmorsal.animatednavbar.navViews.NavViewSlideFromTop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    int i = 2;
    private ViewPager viewPager;
    private NavBarLayout navBarLayout;
    private NavViewSlideFromTop nvs1;
    private NavViewSlideFromTop nvs2;
    private NavViewSlideFromTop nvs3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializer();
        tests();
        navBar();
        nvr();
        nvs();
    }

    private void initializer() {
        viewPager = findViewById(R.id.viewPager);
        navBarLayout = findViewById(R.id.navBarLayout);

        nvs1 = findViewById(R.id.nvs1);
        nvs2 = findViewById(R.id.nvs2);
        nvs3 = findViewById(R.id.nvs3);
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
        adapter.addItem(new Fragment1().setColor(Color.parseColor("#009688")));


        navBarLayout.addNavView(
                new NavViewSlideFromTop(this)
                        .setBackgroundOverColor(Color.parseColor("#E64A19"))
                        .setIcon(R.drawable.ic_audiotrack_black_24dp)
                        .setIconSize(24)
        );

        navBarLayout.addNavView(
                new NavViewSlideFromTop(this)
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
                    public void onClick(NavView view, int position, boolean isActive) {
                        if (view.getId() == R.id.nvs1)
                            Toast.makeText(MainActivity.this, "The good", Toast.LENGTH_SHORT).show();

                        else if (view.getId() == R.id.nvs3 && !isActive)
                            Toast.makeText(MainActivity.this, "The bad", Toast.LENGTH_SHORT).show();

                        else if (position == 4 && isActive) {
                            Toast.makeText(MainActivity.this, "The ugly", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }, 3000);
    }

    private void nvr() {
        final NavViewRoundedTop nbr = findViewById(R.id.nbr);
        nbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i + 2;
                nbr.setStrokeWidth(i);
            }
        });
    }

    private void nvs() {

        nvs1.setIcon(R.drawable.ic_audiotrack_black_24dp)
                .setBackgroundOverColor(Color.parseColor("#E91E63"))
                .activate(false);

        nvs2.setTitle("ASDF")
                .setBackgroundOverColor(Color.parseColor("#3F51B5"))
                .setTitleSize(18);

        nvs3.setTitle("QWERTY")
                .setTitleSize(12)
                .setBackgroundOverColor(Color.parseColor("#FF9800"))
                .setIcon(R.drawable.ic_face_black_24dp);
    }
}
