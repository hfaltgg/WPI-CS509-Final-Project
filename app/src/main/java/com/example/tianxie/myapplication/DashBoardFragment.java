package com.example.tianxie.myapplication;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tianxie.myapplication.airport.Flight;
import com.example.tianxie.myapplication.airport.Flights;
import com.example.tianxie.myapplication.dao.ServerInterface;
import com.example.tianxie.myapplication.utils.Saps;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class DashBoardFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private EditText mDepDate;
    private EditText mDepAirport;
    private EditText mArrDate;
    private EditText mArrAirport;
    private EditText mNumberTxt;
    private RadioButton mCoachClass;
    private RadioButton mFirstClass;
    private RadioButton mOnewayTrip;
    private RadioButton mRoundTrip;
    private RadioButton mNoStopovers;
    private RadioButton mOneStopovers;
    private RadioButton mTwoStopovers;
    private AppCompatButton mSearchBtn;

    private Date mStrToDepDate;
    private String mStrDepAirport;
    private Date mStrToArrDate;
    private String mStrArrAirport;

    private SeatClass mSeatClass;
    private TripType mTripType;
    private StopOvers mStopOvers;
    private Integer mNumber;

    private Calendar mCalendar;
    private DatePickerDialog.OnDateSetListener mDepDateListener;
    private DatePickerDialog.OnDateSetListener mArrDateListener;

    public enum SeatClass {
        COACHCLASS,
        FIRSTCLASS
    }
    public enum TripType {
        ONEWAY,
        ROUND
    }
    public enum StopOvers {
        NO,
        ONE,
        TWO
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashBoardFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DashBoardFragment newInstance(int columnCount) {
        DashBoardFragment fragment = new DashBoardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mDepAirport = (EditText) view.findViewById(R.id.dashboard_search_dep_airport);
        mDepDate = (EditText) view.findViewById(R.id.dashboard_search_dep_date);
        mArrAirport = (EditText) view.findViewById(R.id.dashboard_search_arr_airport);
        mArrDate = (EditText) view.findViewById(R.id.dashboard_search_arr_date);
        mNumberTxt = (EditText) view.findViewById(R.id.dashboard_search_num);

        mCoachClass = (RadioButton) view.findViewById(R.id.dashboard_radio_coachclass);
        mFirstClass = (RadioButton) view.findViewById(R.id.dashboard_radio_firstclass);
        mRoundTrip = (RadioButton) view.findViewById(R.id.dashboard_radio_roundtrip);
        mOnewayTrip = (RadioButton) view.findViewById(R.id.dashboard_radio_onewaytrip);
        mNoStopovers = (RadioButton) view.findViewById(R.id.dashboard_radio_nostopover);
        mOneStopovers = (RadioButton) view.findViewById(R.id.dashboard_radio_onestopover);
        mTwoStopovers = (RadioButton) view.findViewById(R.id.dashboard_radio_twostopover);
        mSearchBtn = (AppCompatButton) view.findViewById(R.id.dashboard_button_search);

        mCoachClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mFirstClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mRoundTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mOnewayTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mNoStopovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mOneStopovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        mTwoStopovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });

        mSeatClass = SeatClass.COACHCLASS;
        mCoachClass.setChecked(true);
        mTripType = TripType.ONEWAY;
        mOnewayTrip.setChecked(true);
        mStopOvers = StopOvers.NO;
        mNoStopovers.setChecked(true);
        mNumber = 1;

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });
        mCalendar = Calendar.getInstance();
        mDepDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDepDate();
            }
        };
        mArrDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateArrDate();
            }
        };
        mDepDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), mDepDateListener, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mArrDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), mArrDateListener, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    public void updateDepDate() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mDepDate.setText(sdf.format(mCalendar.getTime()));
    }

    public void updateArrDate() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mArrDate.setText(sdf.format(mCalendar.getTime()));
    }

    public void sendRequest() {

        Saps.mFlight = null;
        Saps.mTwoFlight = null;
        Saps.mThreeFlight = null;
        Saps.mFourFlight = null;
        Saps.mBackFlights = null;
        Saps.mFlights = new Flights();
        Saps.mMutiFlights = new ArrayList<>();
        Saps.mDoubleFlights = new ArrayList<>();

        mStrDepAirport = mDepAirport.getText().toString().toUpperCase();
        mStrArrAirport = mArrAirport.getText().toString().toUpperCase();
        mStrToDepDate = parseDate(mDepDate.getText().toString());
        mStrToArrDate = parseDate(mArrDate.getText().toString());
        if (mNumberTxt.getText()!=null && !"".equals(mNumberTxt.getText().toString())) {
            mNumber = Integer.parseInt(mNumberTxt.getText().toString());
        }
        if (mNumber < 0) mNumber = 1;

        if (mStrToDepDate == null && mStrToArrDate == null) {
            Toast toast = Toast.makeText(getContext(), "invalid date", Toast.LENGTH_SHORT);
            toast.show();
        } else if (mStrToDepDate != null && mStrToArrDate == null) {
            Toast toast = Toast.makeText(getContext(), "invalid parameter", Toast.LENGTH_SHORT);
            toast.show();
//            if (isValidCode(mStrDepAirport)) {
//                //exeu dep request
//                doSingleDepRequest();
//            } else {
//            }
        } else if (mStrToDepDate == null && mStrToArrDate != null) {
            Toast toast = Toast.makeText(getContext(), "invalid parameter", Toast.LENGTH_SHORT);
            toast.show();
//            if (isValidCode(mStrArrAirport)) {
//                //exeu arr request
//                doSingleArrRequest();
//            } else {
//            }
        } else if (mStrToDepDate != null && mStrToArrDate != null) {
            if (mStrToDepDate.after(mStrToArrDate)) {
                Toast toast = Toast.makeText(getContext(), "invalid date", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (isValidCode(mStrDepAirport) && isValidCode(mStrArrAirport)) {
                    if (mStopOvers == StopOvers.NO) {
                        doSingleDepRequest();
                    } else if (mStopOvers == StopOvers.ONE){
                        doDoubleArrRequest();
                    } else if (mStopOvers == StopOvers.TWO){
                        doMutipleRequest();
                    }
                } else if (isValidCode(mStrArrAirport)) {
                    Toast toast = Toast.makeText(getContext(), "invalid airport", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (isValidCode(mStrDepAirport)) {
                    Toast toast = Toast.makeText(getContext(), "invalid airport", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getContext(), "invalid airport", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    public boolean isValidCode (String code) {
        // If we don't have a 3 character code it can't be valid valid
        if ((code == null) || (code.length() != 3))
            return false;
        return true;
    }

    public Date parseDate(String strDate) {
        List<String> formatStrings = Arrays.asList("MM_dd_yyyy", "MM-dd-yyyy", "MM dd yyyy", "MM/dd/yyyy", "MM.dd.yyyy");
        for (String formatString : formatStrings) {
            try {
                return new SimpleDateFormat(formatString).parse(strDate);
            }
            catch (ParseException e) {}
        }

        return null;
    }

    public void doSingleDepRequest() {
        new AsyncTask() {
            @Override
            protected Flights doInBackground(Object[] objects) {
                Flights flights = ServerInterface.INSTANCE.getFlightsByDep(mStrDepAirport, mStrToDepDate);
                if (mTripType == TripType.ROUND) {
                    Flights backFlights = ServerInterface.INSTANCE.getFlightsByDep(mStrArrAirport, mStrToArrDate);
                    showBackResult(backFlights);
                }
                return flights;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                showSingleDepResult((Flights)o);
            }
        }.execute();
    }

    public void doDoubleArrRequest() {
        new AsyncTask() {
            @Override
            protected List doInBackground(Object[] objects) {
                Flights flightsDep = ServerInterface.INSTANCE.getFlightsByDep(mStrDepAirport, mStrToDepDate);
                Flights flightsArr = ServerInterface.INSTANCE.getFlightsByArr(mStrArrAirport, mStrToArrDate);
                if (mTripType == TripType.ROUND) {
                    Flights backFlights = ServerInterface.INSTANCE.getFlightsByDep(mStrArrAirport, mStrToArrDate);
                    showBackResult(backFlights);
                }
                HashSet<String> mAirport = new HashSet<>();
                HashSet<String> mResAirport = new HashSet<>();
                for (Flight flight : flightsDep) {
                    mAirport.add(flight.getmArrAirportCode());
                }
                for (Flight flight : flightsArr) {
                    if(mAirport.contains(flight.getmDepAirportCode())) {
                        mResAirport.add(flight.getmDepAirportCode());
                    }
                }
                List<Pair<Flight, Flight>> mRes = new ArrayList<Pair<Flight, Flight>>();
                for (String code : mResAirport) {
                    for (Flight flightDep : flightsDep) {
                        if (code.equals(flightDep.getmArrAirportCode())) {
                            for (Flight flightArr : flightsArr) {
                                if (code.equals(flightArr.getmDepAirportCode())) {
                                    mRes.add(new Pair<Flight, Flight>(flightDep, flightArr));
                                }
                            }
                        }
                    }
                }
                return mRes;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                showDoubleResult((List<Pair<Flight, Flight>>)o);
            }
        }.execute();
    }

    public void doMutipleRequest() {
        new AsyncTask() {
            @Override
            protected List<Flights> doInBackground(Object[] objects) {
                Flights flightsDep = ServerInterface.INSTANCE.getFlightsByDep(mStrDepAirport, mStrToDepDate);
                Flights flightsArr = ServerInterface.INSTANCE.getFlightsByArr(mStrArrAirport, mStrToArrDate);
                if (mTripType == TripType.ROUND) {
                    Flights backFlights = ServerInterface.INSTANCE.getFlightsByDep(mStrArrAirport, mStrToArrDate);
                    showBackResult(backFlights);
                }
                HashSet<String> mAirport = new HashSet<>();
                HashSet<String> mResAirport = new HashSet<>();
                for (Flight flight : flightsDep) {
                    mAirport.add(flight.getmArrAirportCode());
                }
                for (Flight flight : flightsArr) {
                    mResAirport.add(flight.getmDepAirportCode());
                }
                Flights mStopOverFlights = new Flights();
                for (String airportCode : mAirport) {
                    mStopOverFlights.addAll(ServerInterface.INSTANCE.getFlightsByDep(airportCode, mStrToDepDate));
                }

                List<Flights> mRes = new ArrayList<>();
                for (String code : mResAirport) {
                    for (Flight flightArr : flightsArr) {
                        if (code.equals(flightArr.getmDepAirportCode())) {
                            for (Flight flightStop : mStopOverFlights) {
                                if (code.equals(flightStop.getmArrAirportCode())) {
                                    for (Flight flightDep : flightsDep) {
                                        if (flightStop.getmDepAirportCode().equals(flightDep.getmArrAirportCode())) {
                                            Flights mTemp = new Flights();
                                            mTemp.add(flightDep);
                                            mTemp.add(flightStop);
                                            mTemp.add(flightArr);
                                            mRes.add(mTemp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return mRes;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                showMutiResult((List<Flights>)o);
            }
        }.execute();
    }

    private void showMutiResult (List<Flights> flights) {
        Saps.mMutiFlights = findMutiShowResult(flights);
        Intent intent = new Intent(getContext(), SearchThreeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SEATCLASS", mSeatClass);
        bundle.putSerializable("TRIPTYPE", mTripType);
        bundle.putSerializable("STOPOVERS", mStopOvers);
        bundle.putInt("NUMBER", mNumber);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<Flights> findMutiShowResult (List<Flights> flightss) {
        List<Flights> res = new ArrayList<>();
        for (Flights flights : flightss) {
            if (isValidMutiFlights(flights)) {
                res.add(flights);
            }
        }
        return res;
    }

    public boolean isValidMutiFlights (Flights flights) {
        long diffInMilliesOne = flights.get(0).getmArrTime().getTime() - flights.get(1).getmDepTime().getTime();
        long diffInMilliesTwo = flights.get(1).getmArrTime().getTime() - flights.get(2).getmDepTime().getTime();
        TimeUnit timeUnit = TimeUnit.MINUTES;
        long timeDiffOne = timeUnit.convert(diffInMilliesOne, TimeUnit.MILLISECONDS);
        long timeDiffTwo = timeUnit.convert(diffInMilliesTwo, TimeUnit.MILLISECONDS);
        if ((timeDiffOne > 30 && timeDiffOne < 180) && (timeDiffTwo > 30 && timeDiffTwo < 180)) {
            return true;
        }
        return false;
    }

    private void showBackResult (Flights flights) {
        Saps.mBackFlights = findBackShowResult(flights);
    }

    private Flights findBackShowResult (Flights flights) {
        Flights res = new Flights();
        for (Flight flight : flights) {
            if (isValidBackFlight(flight)) {
                res.add(flight);
            }
        }
        return res;
    }

    public boolean isValidBackFlight (Flight flight) {
        if (flight.getmArrAirportCode().equals(mStrDepAirport)) {
            return isValidSeatFlight(flight);
        }
        return false;
    }

    private void showSingleDepResult (Flights flights) {
        Saps.mFlights = findSingleDepShowResult(flights);
        Intent intent = new Intent(getContext(), SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SEATCLASS", mSeatClass);
        bundle.putSerializable("TRIPTYPE", mTripType);
        bundle.putSerializable("STOPOVERS", mStopOvers);
        bundle.putInt("NUMBER", mNumber);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private Flights findSingleDepShowResult (Flights flights) {
        Flights res = new Flights();
        for (Flight flight : flights) {
            if (isValidSingleDepFlight(flight)) {
                res.add(flight);
            }
        }
        return res;
    }

    public boolean isValidSingleDepFlight (Flight flight) {
        if (flight.getmArrAirportCode().equals(mStrArrAirport)) {
            DateFormat formater = new SimpleDateFormat("yyyy MMM dd");
            String arrTimeStr = formater.format(flight.getmArrTime());
            String mArrTimeStr = formater.format(mStrToArrDate);
            if (arrTimeStr.equals(mArrTimeStr)) {
                return isValidSeatFlight(flight);
            }
        }
        return false;
    }

    public boolean isValidSeatFlight (Flight flight) {
        if (mSeatClass == SeatClass.COACHCLASS) {
            if (mNumber <= flight.getmCoachRemainNum()) {
                return true;
            }
        } else {
            if (mNumber <= flight.getmFirstRemainNum()) {
                return true;
            }
        }
        return false;
    }

    private void showDoubleResult (List<Pair<Flight, Flight>> flightPairs) {
        Saps.mDoubleFlights = findDoubleShowResult(flightPairs);
        Intent intent = new Intent(getContext(), SearchDoubleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SEATCLASS", mSeatClass);
        bundle.putSerializable("TRIPTYPE", mTripType);
        bundle.putSerializable("STOPOVERS", mStopOvers);
        bundle.putInt("NUMBER", mNumber);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private List<Pair<Flight, Flight>> findDoubleShowResult (List<Pair<Flight, Flight>> flightPairs) {
        List<Pair<Flight, Flight>> res = new ArrayList<>();
        for (Pair<Flight, Flight> pair : flightPairs) {
            if (isValidDoubleFlightPair(pair.first, pair.second) && isValidSingleDepFlight(pair.second) && isValidSeatFlight(pair.first)) {
                res.add(pair);
            }
        }
        return res;
    }

    public boolean isValidDoubleFlightPair (Flight flightDep, Flight flightArr) {
        long diffInMillies = flightDep.getmArrTime().getTime() - flightArr.getmDepTime().getTime();
        TimeUnit timeUnit = TimeUnit.MINUTES;
        long timeDiff = timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (timeDiff > 30 && timeDiff < 180) {
            return true;
        }
        return false;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.dashboard_radio_coachclass:
                if (checked)
                    mSeatClass = SeatClass.COACHCLASS;
                    break;
            case R.id.dashboard_radio_firstclass:
                if (checked)
                    mSeatClass = SeatClass.FIRSTCLASS;
                    break;
            case R.id.dashboard_radio_onewaytrip:
                if (checked)
                    mTripType = TripType.ONEWAY;
                    break;
            case R.id.dashboard_radio_roundtrip:
                if (checked)
                    mTripType = TripType.ROUND;
                    break;
            case R.id.dashboard_radio_nostopover:
                if (checked)
                    mStopOvers = StopOvers.NO;
                    break;
            case R.id.dashboard_radio_onestopover:
                if (checked)
                    mStopOvers = StopOvers.ONE;
                    break;
            case R.id.dashboard_radio_twostopover:
                if (checked)
                    mStopOvers = StopOvers.TWO;
                    break;
        }
    }
}
