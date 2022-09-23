package edu.northeastern.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ClickyClicky extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: -");
    }

    public void AonClick(View view) {
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: A");
    }

    public void BonClick(View view) {
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: B");
    }

    public void ConClick(View view) {
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: C");
    }

    public void DonClick(View view) {
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: D");
    }

    public void EonClick(View view) {
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: E");
    }

    public void FonClick(View view) {
        TextView pressedText = (TextView) findViewById(R.id.pressed_text);
        pressedText.setText("Pressed: F");
    }
}