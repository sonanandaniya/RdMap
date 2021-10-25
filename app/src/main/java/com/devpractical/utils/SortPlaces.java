package com.devpractical.utils;

import com.devpractical.room.CityData;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;

import java.util.Comparator;

public class SortPlaces implements Comparator<CityData> {
    LatLng currentLoc;

    public SortPlaces(LatLng current) {
        currentLoc = current;
    }


    @Override
    public int compare(final CityData place1, final CityData place2) {

        return Double.compare(place1.getDistance(), place2.getDistance());

    }

    public static double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(deltaLat / 2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon / 2), 2)));
        return (radius * angle)/1000;
    }
}