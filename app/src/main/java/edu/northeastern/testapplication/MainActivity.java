package edu.northeastern.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aboutMeOnClick(View view) {
        Intent intent = new Intent(this, AboutMeActivity.class);
        startActivity(intent);
    }

    public void linkCollectorOnClick(View view) {
        Intent intent = new Intent(this, LinkCollectorActivity.class);
        startActivity(intent);
    }

    public void ClickyClickyOnClick(View view) {
        Intent intent = new Intent(this, ClickyClicky.class);
        startActivity(intent);
    }

    public void primeOnClick(View view) {
        Intent intent = new Intent(this, PrimeActivity.class);
        startActivity(intent);
    }
}