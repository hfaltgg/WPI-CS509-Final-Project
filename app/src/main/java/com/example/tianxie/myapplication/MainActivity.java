package com.example.tianxie.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("");
    }

    public void onRadioButtonClicked(View view) {
        DashBoardFragment currentFragment = (DashBoardFragment) this.getFragmentManager().findFragmentById(R.layout.fragment_dashboard);
        currentFragment.onRadioButtonClicked(view);
    }
}
