package com.example.tianxie.myapplication;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tianxie.myapplication.DashBoardFragment.SeatClass;
import com.example.tianxie.myapplication.DashBoardFragment.StopOvers;
import com.example.tianxie.myapplication.DashBoardFragment.TripType;
import com.example.tianxie.myapplication.airport.Flights;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tian xie on 3/29/2018.
 */

public class SearchAdapter extends BaseAdapter{
    private Flights mDataset;
    private SeatClass mSeatClass;
    private TripType mTripType;
    private StopOvers mStopOvers;
    private Integer mNumber;

    public SearchAdapter (Flights mDataset, SeatClass mSeatClass, TripType mTripType, StopOvers mStopOvers, Integer mNumber) {
        this.mDataset = mDataset;
        this.mSeatClass = mSeatClass;
        this.mTripType = mTripType;
        this.mStopOvers = mStopOvers;
        this.mNumber = mNumber;
}

    public void setDateset(Flights mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataset.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if ( convertView == null ) {
        /* There is no view at this position, we create a new one.
           In this case by inflating an xml layout */
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_view, parent, false);
            TextView mFlightTime = (TextView) convertView.findViewById(R.id.item_flight_time);
            TextView mPrice = (TextView) convertView.findViewById(R.id.item_price);
            TextView mDepAirportCode = (TextView) convertView.findViewById(R.id.item_dep_airport);
            TextView mArrAirportCode = (TextView) convertView.findViewById(R.id.item_arr_airport);
            TextView mDepTime = (TextView) convertView.findViewById(R.id.item_dep_date);
            TextView mArrTime = (TextView) convertView.findViewById(R.id.item_arr_date);

            holder = new ViewHolder(convertView);
            holder.mFlightTime = mFlightTime;
            holder.mPrice = mPrice;
            holder.mDepAirportCode = mDepAirportCode;
            holder.mArrAirportCode = mArrAirportCode;
            holder.mDepTime = mDepTime;
            holder.mArrTime = mArrTime;
            convertView.setTag (holder);
        }
        else {
        /* We recycle a View that already exists */
            holder = (ViewHolder) convertView.getTag ();
        }

        // Once we have a reference to the View we are returning, we set its values.
        // Here is where you should set the ToggleButton value for this item!!!

        holder.mDepAirportCode.setText(mDataset.get(position).getmDepAirportCode() + "");
        holder.mArrAirportCode.setText(mDataset.get(position).getmArrAirportCode() + "");
        DateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        holder.mDepTime.setText(dateFormat.format(mDataset.get(position).getmDepTime()) + "");
        holder.mArrTime.setText(dateFormat.format(mDataset.get(position).getmArrTime()) + "");
        holder.mFlightTime.setText(mDataset.get(position).getmFlightTime() + " min");

        DecimalFormat df2 = new DecimalFormat(".##");
        if (mSeatClass == SeatClass.COACHCLASS) {
            holder.mPrice.setText("$ " + df2.format(mDataset.get(position).getmCoachPrice() * mNumber));
        } else {
            holder.mPrice.setText("$ " + df2.format(mDataset.get(position).getmFirstPrice() * mNumber));
        }

        return convertView;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder {
        // each data item is just a string in this case

        public View view;
        public TextView mFlightTime;
        public TextView mPrice;
        public TextView mDepAirportCode;
        public TextView mArrAirportCode;
        public TextView mDepTime;
        public TextView mArrTime;

        public ViewHolder(View v) {
            view = v;
        }
    }
}
