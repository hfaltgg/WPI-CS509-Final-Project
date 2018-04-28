package com.example.tianxie.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.tianxie.myapplication.DashBoardFragment.SeatClass;
import com.example.tianxie.myapplication.DashBoardFragment.StopOvers;
import com.example.tianxie.myapplication.DashBoardFragment.TripType;
import com.example.tianxie.myapplication.airport.Flight;
import com.example.tianxie.myapplication.utils.Saps;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchDoubleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private SearchDoubleAdapter mAdapter;
    private RadioButton mPrice;
    private RadioButton mDuration;
    private List<Pair<Flight, Flight>> myDataset;
    private SeatClass mSeatClass;
    private TripType mTripType;
    private StopOvers mStopOvers;
    private Integer mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDataset = Saps.mDoubleFlights;
        Bundle bundle = getIntent().getExtras();
        mSeatClass = (SeatClass)bundle.get("SEATCLASS");
        mTripType = (TripType)bundle.get("TRIPTYPE");
        mStopOvers = (StopOvers)bundle.get("STOPOVERS");
        mNumber = bundle.getInt("NUMBER");

        setContentView(R.layout.activity_search);
        this.setTitle("Results");
        mListView = (ListView) findViewById(R.id.search_listview);
        mPrice = (RadioButton) findViewById(R.id.search_radio_price);
        mDuration = (RadioButton) findViewById(R.id.search_radio_duration);

        mAdapter = new SearchDoubleAdapter(myDataset, mSeatClass, mTripType, mStopOvers, mNumber);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mPrice.setChecked(true);
        sortByPrice();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent mIntent = new Intent(this, FlightActivity.class);
        Saps.mFlight = Saps.mDoubleFlights.get(position).first;
        Saps.mTwoFlight = Saps.mDoubleFlights.get(position).second;
        if (mTripType == TripType.ROUND) {
            Saps.mThreeFlight = findBackFlight(Saps.mTwoFlight);
        } else {
            Saps.mThreeFlight = null;
        }
        Saps.mFourFlight = null;
        Bundle bundle = new Bundle();
        bundle.putSerializable("SEATCLASS", mSeatClass);
        bundle.putInt("NUMBER", mNumber);
        mIntent.putExtras(bundle);
        startActivity(mIntent);
    }

    public Flight findBackFlight(Flight flight) {
        Flight mRes = null;
        for (Flight mFlight : Saps.mBackFlights) {
            if (flight.getmArrTime().before(mFlight.getmDepTime())) {
                mRes = mFlight;
                break;
            }
        }
        return mRes;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.search_radio_price:
                if (checked)
                    sortByPrice();
                break;
            case R.id.search_radio_duration:
                if (checked)
                    sortByDuration();
                break;
        }
    }

    public void sortByPrice() {
        Collections.sort(myDataset, new Comparator<Pair<Flight, Flight>>() {
            @Override
            public int compare(Pair<Flight, Flight> o1, Pair<Flight, Flight> o2) {
                if (mSeatClass == SeatClass.COACHCLASS) {
                    if (o1.first.getmCoachPrice() + o1.second.getmCoachPrice() < o2.first.getmCoachPrice() + o2.second.getmCoachPrice()) {
                        return -1;
                    } else if (o1.first.getmCoachPrice() + o1.second.getmCoachPrice() > o2.first.getmCoachPrice() + o2.second.getmCoachPrice()) {
                        return 1;
                    }
                } else {
                    if (o1.first.getmFirstPrice() + o1.second.getmFirstPrice() < o2.first.getmFirstPrice() + o2.second.getmFirstPrice()) {
                        return -1;
                    } else if (o1.first.getmFirstPrice() + o1.second.getmFirstPrice() > o2.first.getmFirstPrice() + o2.second.getmFirstPrice()) {
                        return 1;
                    }
                }
                return 0;
            }
        });
        mAdapter.setDateset(myDataset);
        mAdapter.notifyDataSetChanged();
    }

    public void sortByDuration() {
        Collections.sort(myDataset, new Comparator<Pair<Flight, Flight>>() {
            @Override
            public int compare(Pair<Flight, Flight> o1, Pair<Flight, Flight> o2) {
                if (o1.first.getmFlightTime() + o1.second.getmFlightTime() < o2.first.getmFlightTime() + o2.second.getmFlightTime()) {
                    return -1;
                } else if (o1.first.getmFlightTime() + o1.second.getmFlightTime() > o2.first.getmFlightTime() + o2.second.getmFlightTime()) {
                    return 1;
                }
                return 0;
            }
        });
        mAdapter.setDateset(myDataset);
        mAdapter.notifyDataSetChanged();
    }
}

