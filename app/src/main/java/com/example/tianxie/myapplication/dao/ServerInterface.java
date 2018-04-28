/**
 * 
 */
package com.example.tianxie.myapplication.dao;

import android.util.Log;

import com.example.tianxie.myapplication.airport.Airplanes;
import com.example.tianxie.myapplication.airport.Airports;
import com.example.tianxie.myapplication.airport.Flights;
import com.example.tianxie.myapplication.utils.QueryFactory;
import com.example.tianxie.myapplication.utils.Saps;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


/**
 * This class provides an interface to the CS509 server. It provides sample methods to perform
 * HTTP GET and HTTP POSTS
 *   
 * @author blake
 * @version 1.1
 * @since 2016-02-24
 *
 */
public enum ServerInterface {
	INSTANCE;
	
	private final String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";

	/**
	 * Return a collection of all the airports from server
	 * 
	 * Retrieve the list of airports available to the specified ticketAgency via HTTPGet of the server
	 *
	 * @return collection of Airports from server
	 */
	public Airports getAirports () {

        String url = mUrlBase + QueryFactory.getAirports();
        String xmlAirports = this.sendGetRequest(url);
        Log.i("GETGET", "getAirports: " + xmlAirports);
        Airports airports = DaoAirport.addAll(xmlAirports);
		return airports;
	}

	public Airplanes getAirplanes () {

        String url = mUrlBase + QueryFactory.getAirplanes();
        String xmlAirplanes = this.sendGetRequest(url);
        Airplanes airplanes = DaoAirplane.addAll(xmlAirplanes);
		return airplanes;
	}

	public Flights getFlightsByDep (String code, Date date) {

		String url = mUrlBase + QueryFactory.getFlightsByDep(code, date);
        String xmlFlights = this.sendGetRequest(url);
        Flights flights = DaoFlight.addAll(xmlFlights);
		return flights;
	}

    public Flights getFlightsByArr (String code, Date date) {

        String url = mUrlBase + QueryFactory.getFlightsByArr(code, date);
        String xmlFlights = this.sendGetRequest(url);
        Flights flights = DaoFlight.addAll(xmlFlights);
        return flights;
    }

	private String sendGetRequest (String mUrl) {
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();

		try {
			/**
			 * Create an HTTP connection to the server for a GET
			 */
			URL url = new URL(mUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public boolean bookRequest (String xmlString) {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String params = QueryFactory.bookRequset(xmlString);
			Log.d("post xmlString", xmlString);

			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();

			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to book seats");
			System.out.println(("\nResponse Code : " + responseCode));

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();

			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();

			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Lock the database for updating by the specified team. The operation will fail if the lock is held by another team.
	 *
	 * @return true if the server was locked successfully, else false
	 */
	public boolean lock () {
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", Saps.TEAM_NAME);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String params = QueryFactory.lock(Saps.TEAM_NAME);
			
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to lock database");
			System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Unlock the database previous locked by specified team. The operation will succeed if the server lock is held by the specified
	 * team or if the server is not currently locked. If the lock is held be another team, the operation will fail.
	 * 
	 * The server interface to unlock the server interface uses HTTP POST protocol
	 *
	 * @return true if the server was successfully unlocked.
	 */
	public boolean unlock () {
		URL url;
		HttpURLConnection connection;
		
		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			
			String params = QueryFactory.unlock(Saps.TEAM_NAME);
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
		    
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to unlock database");
			System.out.println(("\nResponse Code : " + responseCode));

			if (responseCode >= HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
