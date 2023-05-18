package it_school.sumdu.edu.parkadmin.others;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import it_school.sumdu.edu.parkadmin.R;
import it_school.sumdu.edu.parkadmin.owners.OwnerListActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity{

    ProgressBar pb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, OwnerListActivity.class);
            startActivity(i);
            finish();
        }, 3*1000);
        progress();
    }

    public void progress()
    {
        pb = findViewById(R.id.progressBar);

        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 0, 100);
        animation.setDuration(2700);
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }
}
