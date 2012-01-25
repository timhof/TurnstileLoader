/**************************************************************************
 * SBIR Data Rights (DFARS 252.227-7018)
 * Contract No.: W31P4Q-07-C-0022 
 * Contractor Name: Applied Visions, Inc.
 * Address: 6 Bayview Ave, Northport, NY 11768
 * Expiration of SBIR Rights Period: April 14, 2015 or 5 years after 
 * contract termination, whichever is later. 
 *
 * The Government’s rights to use, modify, reproduce, release, perform,
 * display or disclose technical data or computer software marked with
 * this legend are restricted during the period shown as provided in 
 * paragraph (b)(4) of the Rights in Noncommercial Technical Data and 
 * Computer Software – Small Business Innovation Research (SBIR) Program 
 * clause in the above identified contract. No restrictions apply after 
 * the expiration date shown above. Any reproduction of technical data, 
 * computer software, or portions thereof marked with this legend must 
 * also reproduce the markings.
 *
 * Copyright (c) 2009 Applied Visions, Inc. All Rights Reserved.
 * Author: Applied Visions, Inc. - timothyi
 * Project: MeerCAT
 * SubSystem: com.timothyimhof.mta.load.geo.util
 * FileName: GeoUtil.java
 *************************************************************************/
package com.timothyimhof.mtaload.geo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timothyimhof.mtaload.geo.json.AddressComponent;
import com.timothyimhof.mtaload.geo.json.GeoResult;
import com.timothyimhof.mtaload.geo.json.GeoResultContainer;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 14, 2011
 */
public class GeoUtil
{

    public static GeoResultContainer getGeoResultContainer(double lat, double lon) throws MalformedURLException, IOException
    {
        String response = getURLResponse(String.format("http://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&sensor=false", lat, lon));

        GeoResultContainer geoResultContainer = null;

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Gson gson = gsonBuilder.create();
            geoResultContainer = gson.fromJson(response, GeoResultContainer.class);

        return geoResultContainer;

    }

    public static Collection<GeoResult> getGeoResults(double lat, double lon) throws MalformedURLException, IOException
    {
        GeoResultContainer geoResultContainer = getGeoResultContainer(lat, lon);
        return geoResultContainer.getResults();
    }

    public static Collection<AddressComponent> getAddressComponents(double lat, double lon, Set<String> resultTypes,
 Set<String> addressComponentTypes)
            throws MalformedURLException,
    IOException
    {
        Collection<AddressComponent> addressComponents = new HashSet<AddressComponent>();

        GeoResultContainer geoResultContainer = getGeoResultContainer(lat, lon);

        for (GeoResult result : geoResultContainer.getResults())
        {
            if(result.hasTypeMatch(resultTypes))
            {
                for (AddressComponent addressComponent : result.getAddressComponents())
                {
                    if(addressComponent.hasTypeMatch(addressComponentTypes))
                        addressComponents.add(addressComponent);
                }
            }
        }
        return addressComponents;
    }

    public static String getURLResponse(String url) throws MalformedURLException, IOException
    {
        StringBuilder response = new StringBuilder();
        InputStream is = new URL(url).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            
            int cp;
            while ((cp = rd.read()) != -1)
            {
                response.append((char) cp);
            }

        return response.toString();
    }
}
