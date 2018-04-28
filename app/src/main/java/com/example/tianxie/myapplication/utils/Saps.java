/**
 * 
 */
package com.example.tianxie.myapplication.utils;

import android.util.Pair;

import com.example.tianxie.myapplication.airport.Airplanes;
import com.example.tianxie.myapplication.airport.Airports;
import com.example.tianxie.myapplication.airport.Flight;
import com.example.tianxie.myapplication.airport.Flights;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author blake
 * 
 * System Adaptive Parameters (SAPS)
 * 
 * Constants and values for limits, bounds and configuration of system
 *
 */
public class Saps {
	/**
	 * Constant values used for latitude and longitude range validation
	 */
	public static Flight mFlight = null;
	public static Flight mTwoFlight = null;
	public static Flight mThreeFlight = null;
	public static Flight mFourFlight = null;
	public static Flights mFlights = new Flights();
	public static Flights mBackFlights = null;
	public static List<Flights> mMutiFlights = new ArrayList<>();
	public static List<Pair<Flight, Flight>> mDoubleFlights = new ArrayList<>();
	public static Airplanes mAirplanes = new Airplanes();
	public static Airports mAirports = new Airports();
	public static HashMap<String, Integer> mCoachNumWithModel = new HashMap<>();
	public static HashMap<String, Integer> mFirstNumWithModel = new HashMap<>();
	public static HashMap<String, Double> mLatWithCode = new HashMap<>();
	public static HashMap<String, Double> mLngWithCode = new HashMap<>();
	public static final double MAX_LATITUDE = 90.0;
	public static final double MIN_LATITUDE = -90.0;
	public static final double MAX_LONGITUDE = 180.0;
	public static final double MIN_LONGITUDE = -180.0;
	public static final String TEAM_NAME = "Boston_Uprising";
	
}
