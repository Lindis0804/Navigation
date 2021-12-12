package com.ldh.heimerdingerslab;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ldh.heimerdingerslab.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    int splash_time_out = 3000;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            }
        }, splash_time_out);

        // Set cho progress bar chạy
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        CountDownTimer countDownTimer = new CountDownTimer(3000,1) {
            int current = 0;
            @Override
            public void onTick(long l) {
                current = current+20;
                progressBar.setProgress(current);

            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

    }
}