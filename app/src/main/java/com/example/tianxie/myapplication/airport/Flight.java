package com.example.tianxie.myapplication.airport;

import com.example.tianxie.myapplication.utils.Saps;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by tian xie on 3/28/2018.
 */

public class Flight implements Comparable<Flight>, Comparator<Flight> {

    private Integer mFlightTime;
    private Integer mFlightCode;
    private Integer mFirstNum;
    private Integer mCoachNum;
    private Integer mFirstRemainNum;
    private Integer mCoachRemainNum;
    private Double mFirstPrice;
    private Double mCoachPrice;
    private String mAirplaneModel;
    private String mDepAirportCode;
    private String mArrAirportCode;
    private Date mDepTime;
    private Date mArrTime;

    public Flight () {
        mFlightTime = 0;
        mFlightCode = 0;
        mFirstNum = 0;
        mCoachNum = 0;
        mFirstRemainNum = 0;
        mCoachRemainNum = 0;
        mFirstPrice = 0.0;
        mCoachPrice = 0.0;
        mAirplaneModel = "";
        mDepAirportCode = "";
        mArrAirportCode = "";
        mDepTime = new Date();
        mArrTime = new Date();
    }

    public Flight (Integer mFlightTime, Integer mFlightCode, Integer mFirstNum, Integer mCoachNum, Double mFirstPrice, Double mCoachPrice,
                   String mAirplaneModel, String mDepAirportCode, String mArrAirportCode, Date mDepTime, Date mArrTime) {
        this.mFlightTime = mFlightTime;
        this.mFlightCode = mFlightCode;
        this.mFirstNum = mFirstNum;
        this.mCoachNum = mCoachNum;
        this.mFirstPrice = mFirstPrice;
        this.mCoachPrice = mCoachPrice;
        this.mAirplaneModel = mAirplaneModel;
        this.mDepAirportCode = mDepAirportCode;
        this.mArrAirportCode = mArrAirportCode;
        this.mDepTime = mDepTime;
        this.mArrTime = mArrTime;
    }

    public Integer getmFlightTime() {
        return mFlightTime;
    }

    public void setmFlightTime(Integer mFlightTime) {
        this.mFlightTime = mFlightTime;
    }

    public Integer getmFlightCode() {
        return mFlightCode;
    }

    public void setmFlightCode(Integer mFlightCode) {
        this.mFlightCode = mFlightCode;
    }

    public Integer getmFirstNum() {
        return mFirstNum;
    }

    public void setmFirstNum(Integer mFirstNum) {
        this.mFirstNum = mFirstNum;
        this.mFirstRemainNum = Saps.mFirstNumWithModel.get(mAirplaneModel) - mFirstNum;
    }

    public Integer getmCoachNum() {
        return mCoachNum;
    }

    public void setmCoachNum(Integer mCoachNum) {
        this.mCoachNum = mCoachNum;
        this.mCoachRemainNum = Saps.mCoachNumWithModel.get(this.getmAirplaneModel()) - this.mCoachNum;
    }

    public Integer getmFirstRemainNum() {
        return mFirstRemainNum;
    }

    public Integer getmCoachRemainNum() {
        return mCoachRemainNum;
    }

    public Double getmFirstPrice() {
        return mFirstPrice;
    }

    public void setmFirstPrice(Double mFirstPrice) {
        this.mFirstPrice = mFirstPrice;
    }

    public Double getmCoachPrice() {
        return mCoachPrice;
    }

    public void setmCoachPrice(Double mCoachPrice) {
        this.mCoachPrice = mCoachPrice;
    }

    public String getmAirplaneModel() {
        return mAirplaneModel;
    }

    public void setmAirplaneModel(String mAirplaneModel) {
        this.mAirplaneModel = mAirplaneModel;
    }

    public String getmDepAirportCode() {
        return mDepAirportCode;
    }

    public void setmDepAirportCode(String mDepAirportCode) {
        this.mDepAirportCode = mDepAirportCode;
    }

    public String getmArrAirportCode() {
        return mArrAirportCode;
    }

    public void setmArrAirportCode(String mArrAirportCode) {
        this.mArrAirportCode = mArrAirportCode;
    }

    public Date getmDepTime() {
        return mDepTime;
    }

    public void setmDepTime(Date mDepTime) {
        this.mDepTime = mDepTime;
    }

    public Date getmArrTime() {
        return mArrTime;
    }

    public void setmArrTime(Date mArrTime) {
        this.mArrTime = mArrTime;
    }

    public boolean isValid() {
        return true;
    }

    /**
     * Compare two airports based on 3 character code
     *
     * This implementation delegates to the case insensitive version of string compareTo
     * @return results of String.compareToIgnoreCase
     */
    public int compareTo(Flight other) {
        return this.mFlightCode.compareTo(other.mFlightCode);
    }

    /**
     * Compare two airports for sorting, ordering
     *
     * Delegates to airport1.compareTo for ordering by 3 character code
     *
     * @param flight1 the first airport for comparison
     * @param flight2 the second / other airport for comparison
     * @return -1 if airport ordered before airport2, +1 of airport1 after airport2, zero if no different in order
     */
    public int compare(Flight flight1, Flight  flight2) {
        return flight1.compareTo(flight2);
    }
}
