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
 * SubSystem: com.timothyimhof.mta.load
 * FileName: EntranceLoaderTest.java
 *************************************************************************/
package com.timothyimhof.mtaload;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timothyimhof.model.Station;
import com.timothyimhof.mtaload.turnstile.EntranceLoader;
import com.timothyimhof.persistence.OrmService;


/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 9, 2011
 */
public class EntranceLoaderTest
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
        DatabaseHelper.deleteDatabase();
    }

    /**
     * 
     *
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        DatabaseHelper.deleteDatabase();
    }

    /**
     * Test method for
     * {@link com.timothyimhof.mta.load.turnstile.EntranceLoader#loadFile(java.io.File, com.timothyimhof.mta.persistence.OrmService)}
     * .
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testLoadFile() throws ParseException, IOException
    {
        OrmService ormService = new OrmService();
        EntranceLoader loader = new EntranceLoader();
        loader.loadFiles();

        // List<Entrance> entranceList = (List<Entrance>)
        // ormService.getAll(Entrance.class);
        //
        // assertTrue(entranceList.size() > 0);

        // System.out.format("NUMBER OF ENTRANCES: %d\n", entranceList.size());
        //
        // Collections.sort(entranceList);
        // for (Entrance entrance : entranceList)
        // {
        // System.out.format("%s\n", entrance.toString());
        // }

        // List<Station> stationList = (List<Station>)
        // ormService.getAll(Station.class);
        //
        // assertTrue(stationList.size() > 0);

        // System.out.format("NUMBER OF STATIONS: %d\n", stationList.size());
        //
        // Collections.sort(stationList);
        // for (Station station : stationList)
        // {
        // System.out.format("%s\n", station.toString());
        // for (Entrance entrance : station.getEntrances())
        // {
        // System.out.format("\t%s\n", entrance.toString());
        // }
        // }

        printStations(ormService);
    }

    private void printStations(OrmService ormService)
    {
        List<Station> stationList = (List<Station>) ormService.getAll(Station.class);

        Collections.sort(stationList);
        assertTrue(stationList.size() > 0);

        System.out.format("NUMBER OF STATIONS: %d\n", stationList.size());

        Map<String, Integer> stationNameCountMap = new HashMap<String, Integer>();
        for (Station station : stationList)
        {
            System.out.format("%s\n", station.toString());
            // for (RemoteBooth remoteBooth : station.getRemoteBooths())
            // {
            // int[] totalEntryExits = remoteBooth.getTotalEntryExits();
            // if (totalEntryExits[0] > 0)
            // {
            // System.out.format("\t%s\n", remoteBooth.toString());
            // }
            // }
            Integer countInt = stationNameCountMap.get(station.getName());
            if(countInt == null)
            {
                countInt = new Integer(1);
            }
            else
            {
                countInt = new Integer(countInt.intValue() + 1);
            }
            stationNameCountMap.put(station.getName(), countInt);
        }

        int totalDuplicates = 0;
        for (Map.Entry<String, Integer> entry : stationNameCountMap.entrySet())
        {
            if(entry.getValue().intValue() > 1)
            {
                System.out.format("%d entries for: %s\n", entry.getValue().intValue(), entry.getKey());
                totalDuplicates += entry.getValue().intValue();
            }
        }
        System.out.format("%d duplicates\n", totalDuplicates);
    }

}
