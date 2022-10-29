package com.example.autometedcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
     private CardView Remote;
    private CardView SelfDriving;
    private CardView voiceControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Remote = findViewById(R.id.menu_button);
        SelfDriving = findViewById(R.id.menu_selfdriving);
        voiceControl = findViewById(R.id.menu_voice);

        // Adding set on click listni
        Remote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(MainActivity.this,RcCar.class);
                        startActivity(intent);
                    }
                }
        );




        // Adding set on click listni
        SelfDriving.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(MainActivity.this,SelfDvrivingMode.class);
                        startActivity(intent);
                    }
                }
        );

        // Adding set on click listni
        voiceControl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(MainActivity.this,voiceControl.class);
                        startActivity(intent);
                    }
                }
        );

    }
}