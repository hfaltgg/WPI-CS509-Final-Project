package com.example.tianxie.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.tianxie.myapplication.airport.Airplanes;
import com.example.tianxie.myapplication.airport.Airports;
import com.example.tianxie.myapplication.airport.Flights;
import com.example.tianxie.myapplication.dao.ServerInterface;

public class CoverPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverpage);

        new AsyncTask() {
            @Override
            protected Flights doInBackground(Object[] objects) {
                Flights flights = new Flights();
                Airplanes airplanes = ServerInterface.INSTANCE.getAirplanes();
                Airports airports = ServerInterface.INSTANCE.getAirports();
                return flights;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                startApp();
            }
        }.execute();
    }

    private void startApp() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
