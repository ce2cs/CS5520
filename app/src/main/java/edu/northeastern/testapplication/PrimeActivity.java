package edu.northeastern.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class PrimeActivity extends AppCompatActivity {
    private int prime = 3;
    private int currentNum = 3;
    private boolean terminated = false;
    private Handler primeTextHandler = new Handler();
    private boolean finding = false;
    private boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);
        updatePrimeTextView();
    }

    public void findPrimesOnClick(View view) {
        terminated = false;
        finding = true;
        startPrimeThread();
    }

    private void startPrimeThread() {
        FindPrimes task = new FindPrimes();
        Thread th = new Thread(task);
        th.start();
    }

    public void terminateOnClick(View view) {
        terminated = true;
        finding = false;
    }

    public void pacifierSwitchOnClick(View view) {
        checked = ((CheckBox) view).isChecked();
    }

    private void updatePrimeTextView() {
        TextView primeText = (TextView) findViewById(R.id.primeText);
        primeText.setText("current found prime: " + prime);
    }

    class FindPrimes implements Runnable {

        private long lastUpdateTime = 0;

        @Override
        public void run() {
            while (!terminated) {
                currentNum += 2;
                if (isPrime(currentNum)) {
                    prime = currentNum;
                    long currentTime = System.currentTimeMillis() ;
                    if (currentTime - lastUpdateTime > 1000) {
                        lastUpdateTime = currentTime;
                        postUpdate();
                    }
                }
            }
        }

        private void postUpdate() {
            primeTextHandler.post(new Runnable() {
                @Override
                public void run() {
                    updatePrimeTextView();
                }
            });
        }

        private boolean isPrime(int n) {
            n = (int) Math.sqrt(n);
            for (int factor = 2; factor < n - 1; factor++) {
                if (n % factor == 0) return false;
            }
            return true;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        prime = savedInstanceState.getInt("prime", 3);
        currentNum = savedInstanceState.getInt("currentNum", 3);
        terminated = savedInstanceState.getBoolean("terminated", false);
        checked = savedInstanceState.getBoolean("checked", false);
        finding = savedInstanceState.getBoolean("finding", false);
        updatePrimeTextView();
        CheckBox pacifierSwitch = (CheckBox) findViewById(R.id.pacifierSwitch);
        pacifierSwitch.setChecked(checked);
        if (finding) {
            startPrimeThread();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("prime", prime);
        outState.putInt("currentNum", currentNum);
        outState.putBoolean("terminated", terminated);
        outState.putBoolean("checked", checked);
        outState.putBoolean("finding", finding);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.back_pressed_dialog, null);
        builder.setMessage("Are you sure to quit?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PrimeActivity.super.onBackPressed();
            }
        });
        builder.show();
    }
}