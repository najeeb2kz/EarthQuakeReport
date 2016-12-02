package com.chaudhry.najeeb.quakereport;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> eq) {
        super(context, 0, eq);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //getItem(position) is defined in super class ArrayAdapter.  This method returns item in the list at given idex position
        //Find the earthquake at the given position in the list of earthquakes
        Earthquake currentEarthquake = getItem(position);

        //++++++++++  magnitudeTextView  ++++++++++
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitudeTextView);
        magnitudeTextView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        //++++++++++  magnitudeTextView  ++++++++++


        //++++++++++  location TextViews  ++++++++++
        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String locationOffset;

        //Split method splits using a delimiter into string array but delimiter is not included in any part of string array
        //so in this case I have to add "off" back in the first node of string array
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView offSetLocationTextView = (TextView) listItemView.findViewById(R.id.offSetLocationTextView);
        offSetLocationTextView.setText(locationOffset);

        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primaryLocationTextView);
        primaryLocationTextView.setText(primaryLocation);
        //++++++++++  locationTextView  ++++++++++


        //++++++++++  timeTextView  ++++++++++
        //So far time is in milliseconds.  Convert into good looking date and time format.  Create date object
        Date dateObject = new Date(currentEarthquake.getDate());

        // Find the TextView with view ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.dateTextView);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateTextView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.timeTextView);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeTextView.setText(formattedTime);
        //++++++++++  timeTextView  ++++++++++

        return listItemView;
    }



    //Return the formatted date string (i.e. "Mar 3, 1984") from a Date object
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }



    //Return the formatted date string (i.e. "4:30 PM") from a Date object
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


    //Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
    //from a decimal magnitude value
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


    //Return appropriate background color based on the current earthquake magnitude
    private int getMagnitudeColor(double magnitude) {
        int mMagnitude = (int)magnitude;
        int magnitudeColorResourceId;
        switch (mMagnitude) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        //Now convert into an actual color value. Remember that color resource IDs just point to the
        // resource defined, but not the value of the color.
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
