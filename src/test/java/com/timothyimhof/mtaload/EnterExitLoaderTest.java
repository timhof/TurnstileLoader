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
 * FileName: EnterExitLoaderTest.java
 *************************************************************************/
package com.timothyimhof.mtaload;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timothyimhof.model.Division;
import com.timothyimhof.model.Remote;
import com.timothyimhof.model.Station;
import com.timothyimhof.mtaload.turnstile.EnterExitLoader;
import com.timothyimhof.mtaload.turnstile.EntranceLoader;
import com.timothyimhof.mtaload.turnstile.FareLoader;
import com.timothyimhof.mtaload.turnstile.RemoteLoader;
import com.timothyimhof.mtaload.turnstile.StationAlternateNameLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 30, 2011
 */
public class EnterExitLoaderTest
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
        // DatabaseHelper.deleteDatabase();
    }

    /**
     * Test method for
     * {@link com.timothyimhof.mta.load.turnstile.EnterExitLoader#loadFiles(javax.persistence.EntityManager)}
     * .
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testLoadTurnstileFiles() throws ParseException, IOException
    {
        EnterExitLoader loader = new EnterExitLoader();
        loader.loadFiles();
    }

    /**
     * Test method for
     * {@link com.timothyimhof.mta.load.turnstile.EnterExitLoader#loadFiles(javax.persistence.EntityManager)}
     * .
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testLoadFiles() throws ParseException, IOException
    {
        StationAlternateNameLoader stationAlternateNameLoader = new StationAlternateNameLoader();
        stationAlternateNameLoader.loadFiles();

        EntranceLoader entranceLoader = new EntranceLoader();
        entranceLoader.loadFiles();

        RemoteLoader remoteBoothStationLoader = new RemoteLoader();
        remoteBoothStationLoader.loadFiles();
        //
        EnterExitLoader loader = new EnterExitLoader();
        // loader.setMaxLinesPerFile(1000);
        loader.loadFiles();

        FareLoader fareLoader = new FareLoader();
        fareLoader.loadFiles();
        //
        OrmService ormService = new OrmService();
        //
        // Collection<RemoteBooth> remoteBoothList = (Collection<RemoteBooth>)
        // ormService.getAll(RemoteBooth.class);
        //
        // assertTrue(remoteBoothList.size() > 0);
        //
        // System.out.format("NUMBER OF REMOTEBOOTHS: %d\n",
        // remoteBoothList.size());
        //
        // Collection<Turnstile> turnstileList = (Collection<Turnstile>)
        // ormService.getAll(Turnstile.class);
        //
        // assertTrue(turnstileList.size() > 0);
        //
        // System.out.format("NUMBER OF TURNSTILES: %d\n",
        // turnstileList.size());

        // printStationTotals(ormService);
        // printIncompleteStations(ormService);
        printIncompleteStationsByRoute(ormService);
        // printRemoteBoothTotals(ormService);

    }

    private void printIncompleteStations(OrmService ormService)
    {
        List<Station> stationList = (List<Station>) ormService.getAll(Station.class);
        Collections.sort(stationList);

        for (Station station : stationList)
        {
            if (station.hasEntrance() && !station.hasRemoteBooth())
            {
                System.out.format(" E: %s\n", station.toString());
            }
        }
        
        for (Station station : stationList)
        {
            if (!station.hasEntrance() && station.hasRemoteBooth()
                    && !"PTH".equals(station.getRemoteBooths().iterator().next().getDivision().getName())
                    && !"SRT".equals(station.getRemoteBooths().iterator().next().getDivision().getName()))
            {
                System.out.format("RB: %s\n", station.toString());
            }
        }
    }

    private void printIncompleteStationsByRoute(OrmService ormService)
    {
        List<Station> stationList = (List<Station>) ormService.getAll(Station.class);
        Collections.sort(stationList);

        List<Division> divisionList = (List<Division>) ormService.getAll(Division.class);

        for (Division division : divisionList)
        {
            System.out.println(division);
            for (Station station : stationList)
            {
                if (station.hasEntrance() && !station.hasRemoteBooth() && station.getDivision() != null && station.getDivision().equals(division))
                {
                    System.out.format("\t E: %s\n", station.toString());
                }
            }

            for (Station station : stationList)
            {
                if (!station.hasEntrance() && station.hasRemoteBooth()
                        && !"PTH".equals(station.getRemoteBooths().iterator().next().getDivision().getName())
                        && !"SRT".equals(station.getRemoteBooths().iterator().next().getDivision().getName())
                        && station.getRemoteBooths().iterator().next().getDivision().equals(division))
                {
                    System.out.format("\tRB: %s\n", station.toString());
                }
            }
            for (Station station : stationList)
            {
                if (station.hasEntrance() && station.hasRemoteBooth() && station.getDivision() != null && station.getDivision().equals(division))
                {
                    System.out.format("\tBOTH: %s\n", station.toString());
                }
            }
        }
    }

    private void printStationTotals(OrmService ormService)
    {
        List<Station> stationList = (List<Station>) ormService.getAll(Station.class);
        Collections.sort(stationList);

        assertTrue(stationList.size() > 0);

        System.out.format("NUMBER OF STATIONS: %d\n", stationList.size());

        for (Station station : stationList)
        {
            System.out.format("%s\n", station.toString());
            for (Remote remoteBooth : station.getRemoteBooths())
            {
                int[] totalEntryExits = remoteBooth.getTotalEntryExits();
                if (totalEntryExits[0] > 0)
                {
                    System.out.format("\t%s\n", remoteBooth.toString());
                    System.out.format("\t\tENTRIES: %d\n\t\tEXITS: %d\n", totalEntryExits[0], totalEntryExits[1]);
                }
            }
        }
    }

    private void printRemoteBoothTotals(OrmService ormService)
    {
        List<Remote> remoteBoothList = (List<Remote>) ormService.getAll(Remote.class);

        assertTrue(remoteBoothList.size() > 0);

        System.out.format("NUMBER OF REMOTEBOOTHS: %d\n", remoteBoothList.size());
        Collections.sort(remoteBoothList);
        for (Remote remoteBooth : remoteBoothList)
        {

            System.out.format("%s\n", remoteBooth.toString());
                int[] totalEntryExits = remoteBooth.getTotalEntryExits();
                if (totalEntryExits[0] > 0)
                {
                    System.out.format("\t\tENTRIES: %d\n\t\tEXITS: %d\n", totalEntryExits[0], totalEntryExits[1]);
                }
        }
    }

    @Test
    public void testIdGeneration()
    {
        Remote rbs = new Remote();
        OrmService ormService = new OrmService();

        ormService.beginTransaction();
        ormService.getEntityManager().persist(rbs);
        ormService.tryCommitTransactionOrRollback();

        System.out.println("FDF");

    }

    @Test
    public void testStationRegister()
    {
        EnterExitLoader enterExitLoader = new EnterExitLoader();
        enterExitLoader.setStationRegisters();
    }
}
