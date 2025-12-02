package com.realtime.repository;

/**
 * Projection interface for CargoLocation latitude and longitude.
 * 
 * Used to fetch only the essential data (latitude and longitude)
 * from CargoLocation entity instead of loading the full entity.
 */
public interface LatLonSummary {

    /**
     * Get the latitude of the cargo location.
     *
     * @return latitude as Double
     */
    Double getLatitude();

    /**
     * Get the longitude of the cargo location.
     *
     * @return longitude as Double
     */
    Double getLongitude();
}
