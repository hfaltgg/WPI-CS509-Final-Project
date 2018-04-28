package com.example.tianxie.myapplication.dao;

import com.example.tianxie.myapplication.airport.Flight;
import com.example.tianxie.myapplication.airport.Flights;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by tian xie on 3/28/2018.
 */

public class DaoFlight {

    public static Flights addAll (String xmlFlights) throws NullPointerException {
        Flights flights = new Flights();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each flight to our collection
        Document docFlights = buildDomDoc (xmlFlights);
        NodeList nodesFlights = docFlights.getElementsByTagName("Flight");

        for (int i = 0; i < nodesFlights.getLength(); i++) {
            Element elementFlight = (Element) nodesFlights.item(i);
            Flight flight = buildFlight (elementFlight);

            if (flight.isValid()) {
                flights.add(flight);
            }
        }

        return flights;
    }

    static private Flight buildFlight (Node nodeFlight) {
        /**
         * Instantiate an empty Flight object
         */
        Flight flight = new Flight();

        Integer mFlightTime;
        Integer mFlightCode;
        Integer mFirstNum;
        Integer mCoachNum;
        String mFirstPrice;
        String mCoachPrice;
        String mAirplaneModel;
        String mDepAirportCode;
        String mArrAirportCode;
        Date mDepTime = new Date();
        Date mArrTime = new Date();

        // The airport element has attributes of Name and 3 character airport code
        Element elementFlight = (Element) nodeFlight;
        mAirplaneModel = elementFlight.getAttributeNode("Airplane").getValue();
        mFlightTime = Integer.parseInt(elementFlight.getAttributeNode("FlightTime").getValue());
        mFlightCode = Integer.parseInt(elementFlight.getAttributeNode("Number").getValue());

        // The latitude and longitude are child elements
        Element elementDepArr;
        Element elementCode;
        Element elementTime;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy MMM dd");
        elementDepArr = (Element)elementFlight.getElementsByTagName("Departure").item(0);
        elementCode = (Element) elementDepArr.getElementsByTagName("Code").item(0);
        elementTime = (Element) elementDepArr.getElementsByTagName("Time").item(0);
        mDepAirportCode = getCharacterDataFromElement(elementCode);
        String mStrDate = getCharacterDataFromElement(elementTime);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy MMM dd HH:mm z");
        try {
            mDepTime = formater.parse(mStrDate);
        }
        catch (ParseException e) {
        }

        elementDepArr = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
        elementCode = (Element) elementDepArr.getElementsByTagName("Code").item(0);
        elementTime = (Element) elementDepArr.getElementsByTagName("Time").item(0);
        mArrAirportCode = getCharacterDataFromElement(elementCode);
        mStrDate = getCharacterDataFromElement(elementTime);
        try {
            mArrTime = formater.parse(mStrDate);
        }
        catch (ParseException e) {

        }

        Element elementSeating = (Element)elementFlight.getElementsByTagName("Seating").item(0);
        Element elementFirst = (Element) elementSeating.getElementsByTagName("FirstClass").item(0);
        mFirstPrice = elementFirst.getAttributeNode("Price").getValue();
        mFirstNum = Integer.parseInt(getCharacterDataFromElement(elementFirst));
        Element elementCoach = (Element) elementSeating.getElementsByTagName("Coach").item(0);
        mCoachPrice = elementCoach.getAttributeNode("Price").getValue();
        mCoachNum = Integer.parseInt(getCharacterDataFromElement(elementCoach));

        /**
         * Update the Airport object with values from XML node
         */
        flight.setmFlightTime(mFlightTime);
        flight.setmFlightCode(mFlightCode);
        flight.setmAirplaneModel(mAirplaneModel);
        flight.setmFirstNum(mFirstNum);
        flight.setmCoachNum(mCoachNum);
        flight.setmFirstPrice(Double.parseDouble(mFirstPrice.replace(",", "").replace("$", "")));
        flight.setmCoachPrice(Double.parseDouble(mCoachPrice.replace(",", "").replace("$", "")));
        flight.setmDepAirportCode(mDepAirportCode);
        flight.setmArrAirportCode(mArrAirportCode);
        flight.setmDepTime(mDepTime);
        flight.setmArrTime(mArrTime);

        return flight;
    }

    /**
     * Builds a DOM tree from an XML string
     *
     * Parses the XML file and returns a DOM tree that can be processed
     *
     * @param xmlString XML String containing set of objects
     * @return DOM tree from parsed XML or null if exception is caught
     */
    static private Document buildDomDoc (String xmlString) {
        /**
         * load the xml string into a DOM document and return the Document
         */
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlString));

            return docBuilder.parse(inputSource);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieve character data from an element if it exists
     *
     * @param e is the DOM Element to retrieve character data from
     * @return the character data as String [possibly empty String]
     */
    private static String getCharacterDataFromElement (Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
