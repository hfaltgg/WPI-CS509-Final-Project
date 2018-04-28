package com.example.tianxie.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.example.tianxie.myapplication.airport.Flight;
import com.example.tianxie.myapplication.airport.Flights;
import com.example.tianxie.myapplication.dao.ServerInterface;
import com.example.tianxie.myapplication.utils.QueryFactory;
import com.example.tianxie.myapplication.utils.Saps;
import com.example.tianxie.myapplication.utils.TimezoneMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class FlightActivity extends AppCompatActivity {
    private TextView mDepAirport;
    private TextView mArrAirport;
    private TextView mFlightTime;
    private TextView mPrice;
    private TextView mFlightNumber;
    private TextView mDepTime;
    private TextView mArrTime;

    private TextView mTwoDepAirport;
    private TextView mTwoArrAirport;
    private TextView mTwoFlightTime;
    private TextView mTwoPrice;
    private TextView mTwoFlightNumber;
    private TextView mTwoDepTime;
    private TextView mTwoArrTime;

    private TextView mThreeDepAirport;
    private TextView mThreeArrAirport;
    private TextView mThreeFlightTime;
    private TextView mThreePrice;
    private TextView mThreeFlightNumber;
    private TextView mThreeDepTime;
    private TextView mThreeArrTime;

    private TextView mFourDepAirport;
    private TextView mFourArrAirport;
    private TextView mFourFlightTime;
    private TextView mFourPrice;
    private TextView mFourFlightNumber;
    private TextView mFourDepTime;
    private TextView mFourArrTime;

    private AppCompatButton mBookButton;
    private String xmlString;
    private DashBoardFragment.SeatClass mSeatClass;
    private Integer mNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        this.setTitle("Flight Info");
        Bundle bundle = getIntent().getExtras();
        mSeatClass = (DashBoardFragment.SeatClass)bundle.get("SEATCLASS");
        mNumber = bundle.getInt("NUMBER");

        Flight mFlight = Saps.mFlight;
        Flight mTwoFlight = Saps.mTwoFlight;
        Flight mThreeFlight = Saps.mThreeFlight;
        Flight mFourFlight = Saps.mFourFlight;
        Flights flights = new Flights();

        for (int i = 0; i < mNumber; i++) {
            flights.add(mFlight);
            if (mTwoFlight!=null) flights.add(mTwoFlight);
            if (mThreeFlight!=null) flights.add(mThreeFlight);
            if (mFourFlight!=null) flights.add(mFourFlight);
        }
        xmlString = QueryFactory.formXmlString(flights, DashBoardFragment.SeatClass.COACHCLASS);

        mDepAirport = (TextView) findViewById(R.id.flight_depairport_txt);
        mArrAirport = (TextView) findViewById(R.id.flight_arrairport_txt);
        mFlightTime = (TextView) findViewById(R.id.flight_flight_time);
        mPrice = (TextView) findViewById(R.id.flight_price_txt);
        mFlightNumber = (TextView) findViewById(R.id.flight_number_txt);
        mDepTime = (TextView) findViewById(R.id.flight_dep_date);
        mArrTime = (TextView) findViewById(R.id.flight_arr_date);
        mBookButton = (AppCompatButton) findViewById(R.id.flight_book_btn);

        mTwoDepAirport = (TextView) findViewById(R.id.flight_two_depairport_txt);
        mTwoArrAirport = (TextView) findViewById(R.id.flight_two_arrairport_txt);
        mTwoFlightTime = (TextView) findViewById(R.id.flight_two_flight_time);
        mTwoPrice = (TextView) findViewById(R.id.flight_two_price_txt);
        mTwoFlightNumber = (TextView) findViewById(R.id.flight_two_number_txt);
        mTwoDepTime = (TextView) findViewById(R.id.flight_two_dep_date);
        mTwoArrTime = (TextView) findViewById(R.id.flight_two_arr_date);

        mThreeDepAirport = (TextView) findViewById(R.id.flight_three_depairport_txt);
        mThreeArrAirport = (TextView) findViewById(R.id.flight_three_arrairport_txt);
        mThreeFlightTime = (TextView) findViewById(R.id.flight_three_flight_time);
        mThreePrice = (TextView) findViewById(R.id.flight_three_price_txt);
        mThreeFlightNumber = (TextView) findViewById(R.id.flight_three_number_txt);
        mThreeDepTime = (TextView) findViewById(R.id.flight_three_dep_date);
        mThreeArrTime = (TextView) findViewById(R.id.flight_three_arr_date);

        mFourDepAirport = (TextView) findViewById(R.id.flight_four_depairport_txt);
        mFourArrAirport = (TextView) findViewById(R.id.flight_four_arrairport_txt);
        mFourFlightTime = (TextView) findViewById(R.id.flight_four_flight_time);
        mFourPrice = (TextView) findViewById(R.id.flight_four_price_txt);
        mFourFlightNumber = (TextView) findViewById(R.id.flight_four_number_txt);
        mFourDepTime = (TextView) findViewById(R.id.flight_four_dep_date);
        mFourArrTime = (TextView) findViewById(R.id.flight_four_arr_date);

        DateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy HH:mm");
        mDepAirport.setText(mFlight.getmDepAirportCode());
        mArrAirport.setText(mFlight.getmArrAirportCode());
        mFlightTime.setText(mFlight.getmFlightTime() + " min");
        if (mSeatClass == DashBoardFragment.SeatClass.COACHCLASS) {
            mPrice.setText(" $ "+mFlight.getmCoachPrice() + "");
        } else {
            mPrice.setText(" $ "+mFlight.getmFirstPrice() + "");
        }
        mFlightNumber.setText("  " + mFlight.getmFlightCode());

        String timeZoneArrStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mFlight.getmArrAirportCode()), Saps.mLngWithCode.get(mFlight.getmArrAirportCode()));
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneArrStr));
        mArrTime.setText(dateFormat.format(mFlight.getmArrTime()) + "");

        String timeZoneDepStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mFlight.getmDepAirportCode()), Saps.mLngWithCode.get(mFlight.getmDepAirportCode()));
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneDepStr));
        mDepTime.setText(dateFormat.format(mFlight.getmDepTime()) + "");

        if (mTwoFlight!=null) {
            mTwoDepAirport.setVisibility(View.VISIBLE);
            mTwoArrAirport.setVisibility(View.VISIBLE);
            mTwoFlightTime.setVisibility(View.VISIBLE);
            mTwoFlightNumber.setVisibility(View.VISIBLE);
            mTwoPrice.setVisibility(View.VISIBLE);
            mTwoDepTime.setVisibility(View.VISIBLE);
            mTwoArrTime.setVisibility(View.VISIBLE);

            mTwoDepAirport.setText(mTwoFlight.getmDepAirportCode());
            mTwoArrAirport.setText(mTwoFlight.getmArrAirportCode());
            mTwoFlightTime.setText(mTwoFlight.getmFlightTime() + " min");
            mTwoFlightNumber.setText("  " + mTwoFlight.getmFlightCode());
            if (mSeatClass == DashBoardFragment.SeatClass.COACHCLASS) {
                mTwoPrice.setText(" $ "+mTwoFlight.getmCoachPrice());
            } else {
                mTwoPrice.setText(" $ "+mTwoFlight.getmFirstPrice() + "");
            }
            timeZoneDepStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mTwoFlight.getmDepAirportCode()), Saps.mLngWithCode.get(mTwoFlight.getmDepAirportCode()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneDepStr));
            mTwoDepTime.setText(dateFormat.format(mTwoFlight.getmDepTime()) + "");
            timeZoneArrStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mTwoFlight.getmArrAirportCode()), Saps.mLngWithCode.get(mTwoFlight.getmArrAirportCode()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneArrStr));
            mTwoArrTime.setText(dateFormat.format(mTwoFlight.getmArrTime()) + "");
        }

        if (mThreeFlight!=null) {
            mThreeDepAirport.setVisibility(View.VISIBLE);
            mThreeArrAirport.setVisibility(View.VISIBLE);
            mThreeFlightTime.setVisibility(View.VISIBLE);
            mThreeFlightNumber.setVisibility(View.VISIBLE);
            mThreePrice.setVisibility(View.VISIBLE);
            mThreeDepTime.setVisibility(View.VISIBLE);
            mThreeArrTime.setVisibility(View.VISIBLE);

            mThreeDepAirport.setText(mThreeFlight.getmDepAirportCode());
            mThreeArrAirport.setText(mThreeFlight.getmArrAirportCode());
            mThreeFlightTime.setText(mThreeFlight.getmFlightTime() + " min");
            mThreeFlightNumber.setText("  " + mThreeFlight.getmFlightCode());
            if (mSeatClass == DashBoardFragment.SeatClass.COACHCLASS) {
                mThreePrice.setText(" $ "+mThreeFlight.getmCoachPrice() + "");
            } else {
                mThreePrice.setText(" $ "+mThreeFlight.getmFirstPrice() + "");
            }
            timeZoneDepStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mThreeFlight.getmDepAirportCode()), Saps.mLngWithCode.get(mThreeFlight.getmDepAirportCode()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneDepStr));
            mThreeDepTime.setText(dateFormat.format(mThreeFlight.getmDepTime()) + "");
            timeZoneArrStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mThreeFlight.getmArrAirportCode()), Saps.mLngWithCode.get(mThreeFlight.getmArrAirportCode()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneArrStr));
            mThreeArrTime.setText(dateFormat.format(mThreeFlight.getmArrTime()) + "");
        }
        if (mFourFlight!=null) {
            mFourDepAirport.setVisibility(View.VISIBLE);
            mFourArrAirport.setVisibility(View.VISIBLE);
            mFourFlightTime.setVisibility(View.VISIBLE);
            mFourFlightNumber.setVisibility(View.VISIBLE);
            mFourPrice.setVisibility(View.VISIBLE);
            mFourDepTime.setVisibility(View.VISIBLE);
            mFourArrTime.setVisibility(View.VISIBLE);

            mFourDepAirport.setText(mFourFlight.getmDepAirportCode());
            mFourArrAirport.setText(mFourFlight.getmArrAirportCode());
            mFourFlightTime.setText(mFourFlight.getmFlightTime() + " min");
            mFourFlightNumber.setText("  " + mFourFlight.getmFlightCode());
            if (mSeatClass == DashBoardFragment.SeatClass.COACHCLASS) {
                mFourPrice.setText(" $ "+mFourFlight.getmCoachPrice() + "");
            } else {
                mFourPrice.setText(" $ "+mFourFlight.getmFirstPrice() + "");
            }
            timeZoneDepStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mFourFlight.getmDepAirportCode()), Saps.mLngWithCode.get(mFourFlight.getmDepAirportCode()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneDepStr));
            mFourDepTime.setText(dateFormat.format(mFourFlight.getmDepTime()) + "");
            timeZoneArrStr = TimezoneMapper.latLngToTimezoneString(Saps.mLatWithCode.get(mFourFlight.getmArrAirportCode()), Saps.mLngWithCode.get(mFourFlight.getmArrAirportCode()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneArrStr));
            mFourArrTime.setText(dateFormat.format(mFourFlight.getmArrTime()) + "");
        }

        mBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReserveSeat();
            }
        });
    }

    public void ReserveSeat() {
        new AsyncTask() {
            @Override
            protected Integer doInBackground(Object[] objects) {
                ServerInterface.INSTANCE.lock();
                ServerInterface.INSTANCE.bookRequest(xmlString);
                ServerInterface.INSTANCE.unlock();
                return 0;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                afterBookSeat();
            }
        }.execute();
    }

    public void afterBookSeat() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        Saps.mFlight = null;
        Saps.mTwoFlight = null;
        Saps.mThreeFlight = null;
        Saps.mFourFlight = null;
        super.onDestroy();
    }
}
