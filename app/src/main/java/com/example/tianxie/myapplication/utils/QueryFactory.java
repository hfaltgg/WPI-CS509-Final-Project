/**
 * 
 */
package com.example.tianxie.myapplication.utils;

import com.example.tianxie.myapplication.DashBoardFragment;
import com.example.tianxie.myapplication.airport.Flight;
import com.example.tianxie.myapplication.airport.Flights;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author blake
 * @version 1.2
 *
 */
public class QueryFactory {
	
	/**
	 * Return a query string that can be passed to HTTP URL to request list of airports
	 *
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirports() {
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=airports";
	}

	public static String getAirplanes() {
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=airplanes";
	}

	public static String getFlightsByDep(String code, Date date) {
		String dateString = new SimpleDateFormat("yyyy_MM_dd").format(date);
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=departing&airport=" + code + "&day=" + dateString;
	}

	public static String getFlightsByArr(String code, Date date) {
		String dateString = new SimpleDateFormat("yyyy_MM_dd").format(date);
		return "?team=" + Saps.TEAM_NAME + "&action=list&list_type=arriving&airport=" + code + "&day=" + dateString;
	}

	public static String getResetDb() {
		return "?team=" + Saps.TEAM_NAME + "&action=resetDB";
	}
	
	/**
	 * Lock the server database so updates can be written
	 * 
	 * @param teamName is the name of the team to acquire the lock
	 * @return the String written to HTTP POST to lock server database 
	 */
	public static String lock (String teamName) {
		return "team=" + teamName + "&action=lockDB";
	}
	
	/**
	 * Unlock the server database after updates are written
	 * 
	 * @param teamName is the name of the team holding the lock
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock (String teamName) {
		return "team=" + teamName + "&action=unlockDB";
	}

	public static String bookRequset (String xmlString) {
		return "team=" + Saps.TEAM_NAME + "&action=buyTickets&flightData=" + xmlString;
	}

	public static String formXmlString(Flights flights, DashBoardFragment.SeatClass mClass) {
		StringBuilder xmlString = new StringBuilder();
		xmlString.append("<Flights>");
		for (Flight flight : flights) {
			if (mClass == DashBoardFragment.SeatClass.COACHCLASS) {
				xmlString.append("<Flight number=\"" + flight.getmFlightCode() + "\" seating=\"Coach\"/>");
			} else {
				xmlString.append("<Flight number=\"" + flight.getmFlightCode() + "\" seating=\"FirstClass\"/>");
			}
		}
		xmlString.append("</Flights>");
		return xmlString.toString();
	}
}
