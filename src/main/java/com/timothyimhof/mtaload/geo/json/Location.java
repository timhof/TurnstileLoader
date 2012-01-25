/**************************************************************************
 * SBIR Data Rights (DFARS 252.227-7018)
 * Contract No.: W31P4Q-07-C-0022 
 * Contractor Name: Applied Visions, Inc.
 * Address: 6 Bayview Ave, Northport, NY 11768
 * Expiration of SBIR Rights Period: April 14, 2015 or 5 years after 
 * contract termination, whichever is later. 
 *
 * The Government�s rights to use, modify, reproduce, release, perform,
 * display or disclose technical data or computer software marked with
 * this legend are restricted during the period shown as provided in 
 * paragraph (b)(4) of the Rights in Noncommercial Technical Data and 
 * Computer Software � Small Business Innovation Research (SBIR) Program 
 * clause in the above identified contract. No restrictions apply after 
 * the expiration date shown above. Any reproduction of technical data, 
 * computer software, or portions thereof marked with this legend must 
 * also reproduce the markings.
 *
 * Copyright (c) 2009 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - timothyi
 * Project: MeerCAT
 * SubSystem: com.timothyimhof.mta.load.geo.json
 * FileName: GeoResult.java
 *************************************************************************/
package com.timothyimhof.mtaload.geo.json;


/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 14, 2011
 */
public class Location
    {
    private double lat;

    /**
     * @return Returns the lat.
     */
    public double getLat()
    {
        return lat;
    }

    /**
     * @param lat The lat to set.
     */
    public void setLat(double lat)
    {
        this.lat = lat;
    }

    /**
     * @return Returns the lng.
     */
    public double getLng()
    {
        return lng;
    }

    /**
     * @param lng The lng to set.
     */
    public void setLng(double lng)
    {
        this.lng = lng;
    }

    private double lng;

    public String toString()
    {
        return String.format("[%f, %f]", lat, lng);
    }
}
