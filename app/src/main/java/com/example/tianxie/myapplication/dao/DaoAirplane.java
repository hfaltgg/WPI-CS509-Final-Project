package com.example.tianxie.myapplication.dao;

import android.util.Log;

import com.example.tianxie.myapplication.airport.Airplane;
import com.example.tianxie.myapplication.airport.Airplanes;
import com.example.tianxie.myapplication.utils.Saps;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by tian xie on 3/28/2018.
 */

public class DaoAirplane {

    public static Airplanes addAll (String xmlAirplanes) throws NullPointerException {
        Airplanes airplanes = new Airplanes();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each airplane to our collection
        Document docAirplanes = buildDomDoc (xmlAirplanes);
        NodeList nodesAirplanes = docAirplanes.getElementsByTagName("Airplane");

        for (int i = 0; i < nodesAirplanes.getLength(); i++) {
            Element elementAirplane = (Element) nodesAirplanes.item(i);
            Airplane airplane = buildAirplane (elementAirplane);

            if (airplane.isValid()) {
                airplanes.add(airplane);
            }
        }

        return airplanes;
    }

    static private Airplane buildAirplane (Node nodeAirplane) {
        /**
         * Instantiate an empty Airplane object
         */
        Airplane airplane = new Airplane();

        Integer mNumFirst;
        Integer mNumCoach;
        String mManufacturer;
        String mModel;

        // The airplane element has attributes of Name and 3 character airplane code
        Element elementAirplane = (Element) nodeAirplane;
        mManufacturer = elementAirplane.getAttributeNode("Manufacturer").getValue();
        mModel = elementAirplane.getAttributeNode("Model").getValue();

        // The latitude and longitude are child elements
        Element elementSeatNum;
        elementSeatNum = (Element)elementAirplane.getElementsByTagName("FirstClassSeats").item(0);
        mNumFirst = Integer.parseInt(getCharacterDataFromElement(elementSeatNum));

        elementSeatNum = (Element)elementAirplane.getElementsByTagName("CoachSeats").item(0);
        mNumCoach = Integer.parseInt(getCharacterDataFromElement(elementSeatNum));

        /**
         * Update the Airplane object with values from XML node
         */
        airplane.setmNumFirst(mNumFirst);
        airplane.setmNumCoach(mNumCoach);
        airplane.setmManufacturer(mManufacturer);
        airplane.setmModel(mModel);

        Saps.mCoachNumWithModel.put(mModel, mNumCoach);
        Saps.mFirstNumWithModel.put(mModel, mNumFirst);

        return airplane;
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
