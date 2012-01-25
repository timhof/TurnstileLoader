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
 * SubSystem: gson
 * FileName: GSONTest.java
 *************************************************************************/
package gson;

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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timothyimhof.mtaload.geo.json.AddressComponent;
import com.timothyimhof.mtaload.geo.json.GeoResult;
import com.timothyimhof.mtaload.geo.json.GeoResultContainer;
import com.timothyimhof.mtaload.geo.json.Geometry;
import com.timothyimhof.mtaload.geo.json.Viewport;
import com.timothyimhof.mtaload.geo.util.GeoType;
import com.timothyimhof.mtaload.geo.util.GeoUtil;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 14, 2011
 */
public class GSONTest
{

    /**
     * 
     *
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    /**
     * 
     *
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    /**
     * 
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * 
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void test() throws MalformedURLException, IOException
    {
        InputStream is = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=40.8328,-73.3821&sensor=false").openStream();

        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1)
            {
                sb.append((char) cp);
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            Gson gson = gsonBuilder.create();
            GeoResultContainer json = gson.fromJson(sb.toString(), GeoResultContainer.class);
            System.out.println(json.getResults().size());
            for (GeoResult result : json.getResults())
            {
                System.out.format("\n\n");

                String address = result.getFormattedAddress();
                System.out.format("ADDRESS: %s\n", address);

                Geometry geometry = result.getGeometry();
                System.out.format("LOCATION TYPE: %s\n", (geometry.getLocationType() == null ? "NA" : geometry.getLocationType().toString()));
                System.out.format("LOCATION: %s\n", geometry.getLocation().toString());

                Viewport viewport = geometry.getViewport();
                System.out.format("VIEWPORT\n");
                System.out.format("\tNORTHEAST: %s\n", viewport.getNortheast().toString());
                System.out.format("\tSOUTHWEST: %s\n", viewport.getSouthwest().toString());

                for (AddressComponent addressComponent : result.getAddressComponents())
                {
                    System.out.format("\tADDRESS COMPONENT: %s\n", addressComponent.toString());
                }

                StringBuilder typeStr = new StringBuilder();
                for (String type : result.getTypes())
                {
                    typeStr.append(type);
                    typeStr.append(", ");
                }
                typeStr.replace(typeStr.length() - 2, typeStr.length(), "");
                System.out.format("\tTYPES: %s\n", typeStr.toString());
                }
        }
        finally
        {
            is.close();
        }
    }
    
    @Test
    public void addressComponentsForTypesTest() throws MalformedURLException, IOException
    {
        Set<String> types = new HashSet<String>();
        types.add(GeoType.administrative_area_level_2.toString());

        double[][] latlons = { { 40.71547788029149, -73.96005841970849 }, { 40.8175392, -73.4081268 }, { 40.8328, -73.3821 } };
        for (double[] latlon : latlons)
        {
            System.out.format("\n\n");
            Collection<AddressComponent> addressComponents = GeoUtil.getAddressComponents(latlon[0], latlon[1], types, types);

            for (AddressComponent addressComponent : addressComponents)
            {
                System.out.format("\tADDRESS COMPONENT: %s\n", addressComponent.toString());
            }
        }
    }

}
