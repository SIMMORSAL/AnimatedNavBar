package com.simmorsal.sample;

import android.os.Bundle;
import android.view.View;

import com.simmorsal.animatednavbar.NavBarRoundedTop;
import com.simmorsal.animatednavbar.NavBarSlideFromTop;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int i = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavBarRoundedTop nbr = findViewById(R.id.nbr);
        nbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = i + 2;
                nbr.setStrokeWidth(i);
            }
        });


        nbs();
    }

    private void nbs() {
        final NavBarSlideFromTop nbs1 = findViewById(R.id.nbs1);
        final NavBarSlideFromTop nbs2 = findViewById(R.id.nbs2);
        final NavBarSlideFromTop nbs3 = findViewById(R.id.nbs3);

        nbs1.setIcon(R.drawable.ic_audiotrack_black_24dp)
                .activate(false);

        nbs2.setTitle("ASDF")
                .setTitleSize(18);

        nbs3.setTitle("QWERTY")
                .setTitleSize(12)
                .setIcon(R.drawable.ic_face_black_24dp);

        nbs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbs1.activate(true);
                if (nbs2.isActive())
                    nbs2.deactivate(true);
                if (nbs3.isActive())
                    nbs3.deactivate(true);
            }
        });

        nbs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbs2.activate(true);
                if (nbs1.isActive())
                    nbs1.deactivate(true);
                if (nbs3.isActive())
                    nbs3.deactivate(true);
            }
        });

        nbs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nbs3.activate(true);
                if (nbs2.isActive())
                    nbs2.deactivate(true);
                if (nbs1.isActive())
                    nbs1.deactivate(true);
            }
        });
    }
}
