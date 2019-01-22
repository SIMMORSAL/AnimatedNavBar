package com.simmorsal.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.simmorsal.animatednavbar.NavBarRoundedTop;

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
    }
}
