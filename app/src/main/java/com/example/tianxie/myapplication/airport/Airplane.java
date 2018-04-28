package com.example.tianxie.myapplication.airport;

import java.util.Comparator;

/**
 * Created by tian xie on 3/28/2018.
 */

public class Airplane implements Comparable<Airplane>, Comparator<Airplane> {

    private Integer mNumFirst;
    private Integer mNumCoach;
    private String mManufacturer;
    private String mModel;

    public Airplane () {
        mNumFirst = 0;
        mNumCoach = 0;
        mManufacturer = "";
        mModel = "";
    }

    public Airplane (Integer mNumFirst, Integer mNumCoach, String mManufacturer, String mModel) {
        if (!isValidNumFirst(mNumFirst))
            throw new IllegalArgumentException("" + mNumFirst);
        if (!isValidNumCoach(mNumCoach))
            throw new IllegalArgumentException("" + mNumCoach);
        if (!isValidManufacturer(mManufacturer))
            throw new IllegalArgumentException(mManufacturer);
        if (!isValidModel(mModel))
            throw new IllegalArgumentException(mModel);

        this.mNumFirst = mNumFirst;
        this.mNumCoach = mNumCoach;
        this.mManufacturer = mManufacturer;
        this.mModel = mModel;
    }

    public Integer getmNumFirst() {
        return mNumFirst;
    }

    public void setmNumFirst(Integer mNumFirst) {
        this.mNumFirst = mNumFirst;
    }

    public Integer getmNumCoach() {
        return mNumCoach;
    }

    public void setmNumCoach(Integer mNumCoach) {
        this.mNumCoach = mNumCoach;
    }

    public String getmManufacturer() {
        return mManufacturer;
    }

    public void setmManufacturer(String mManufacturer) {
        this.mManufacturer = mManufacturer;
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel;
    }

    public boolean isValid() {

        // If the name isn't valid, the object isn't valid
        if ((mManufacturer == null) || (mManufacturer == ""))
            return false;

        // If we don't have a 3 character code, object isn't valid
        if ((mModel == null) || (mModel.length() != 3))
            return false;

        return true;
    }


    public Boolean isValidNumFirst (Integer mNumFirst) {
        return true;
    }

    public Boolean isValidNumCoach (Integer mNumCoach) {
        return true;
    }

    public Boolean isValidManufacturer (String mManufacturer) {
        return true;
    }

    public Boolean isValidModel (String mModel) {
        return true;
    }

    /**
     * Compare two airports based on 3 character code
     *
     * This implementation delegates to the case insensitive version of string compareTo
     * @return results of String.compareToIgnoreCase
     */
    public int compareTo(Airplane other) {
        return this.mModel.compareToIgnoreCase(other.mModel);
    }

    /**
     * Compare two airports for sorting, ordering
     *
     * Delegates to airport1.compareTo for ordering by 3 character code
     *
     * @param airplane1 the first airport for comparison
     * @param airplane2 the second / other airport for comparison
     * @return -1 if airport ordered before airport2, +1 of airport1 after airport2, zero if no different in order
     */
    public int compare(Airplane airplane1, Airplane airplane2) {
        return airplane1.compareTo(airplane2);
    }
}
