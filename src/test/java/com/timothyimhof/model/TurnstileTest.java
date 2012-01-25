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
 * SubSystem: com.timothyimhof.model
 * FileName: TurnstileTest.java
 *************************************************************************/
package com.timothyimhof.model;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Jan 14, 2012
 */
public class TurnstileTest
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

    /**
     * Test method for {@link com.timothyimhof.model.Turnstile#setRegisterDeltas()}.
     */
    @Test
    public void testSetRegisterDeltasInitialSparseWithDuplicates()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Turnstile turnstile = new Turnstile();
        Week week = new Week();
        week.setId(8);
        int cumEntries = 11;
        int cumExits = 12;
        for (int hour = 0; hour < 24; hour++)
        {
            if (hour % 4 != 0)
                continue;
            int minute = 0;
            while (minute < 60)
            {
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                TurnstileRegister register = new TurnstileRegister();
                register.setTimestamp(cal);
                register.setEntriesCum(cumEntries);
                register.setExitsCum(cumExits);
                // register.setWeek(week);
                System.out.println("ADDING " + register.toString());
                turnstile.addRegister(register);

                cumEntries += (int) (Math.random() * 30);
                cumExits += (int) (Math.random() * 30);

                minute += (int) (Math.random() * 60);
            }
        }

        List<TurnstileRegister> newRegisterList = turnstile.setRegisterDeltas();

        testResult(turnstile, newRegisterList);
    }

    /**
     * Test method for
     * {@link com.timothyimhof.model.Turnstile#setRegisterDeltas()}.
     */
    @Test
    public void testSetRegisterDeltasInitialSparse()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Turnstile turnstile = new Turnstile();
        Week week = new Week();
        week.setId(8);
        int cumEntries = 11;
        int cumExits = 12;
        for (int hour = 0; hour < 24; hour++)
        {
            if (hour % 4 != 0)
                continue;
            cal.set(Calendar.HOUR_OF_DAY, hour);
            TurnstileRegister register = new TurnstileRegister();
            register.setTimestamp(cal);
            register.setEntriesCum(cumEntries);
            register.setExitsCum(cumExits);
            // register.setWeek(week);
            System.out.println("ADDING " + register.toString());
            turnstile.addRegister(register);

            cumEntries += (int) (Math.random() * 30);
            cumExits += (int) (Math.random() * 30);

        }

        List<TurnstileRegister> newRegisterList = turnstile.setRegisterDeltas();

        testResult(turnstile, newRegisterList);
    }

    /**
     * Test method for
     * {@link com.timothyimhof.model.Turnstile#setRegisterDeltas()}.
     */
    @Test
    public void testSetRegisterDeltasInitialSparseTwice()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Turnstile turnstile = new Turnstile();
        Week week = new Week();
        week.setId(8);
        int cumEntries = 11;
        int cumExits = 12;
        for (int hour = 0; hour < 24; hour++)
        {
            if (hour % 4 != 0)
                continue;
            cal.set(Calendar.HOUR_OF_DAY, hour);
            TurnstileRegister register = new TurnstileRegister();
            register.setTimestamp(cal);
            register.setEntriesCum(cumEntries);
            register.setExitsCum(cumExits);
            // register.setWeek(week);
            System.out.println("ADDING " + register.toString());
            turnstile.addRegister(register);

            cumEntries += (int) (Math.random() * 30);
            cumExits += (int) (Math.random() * 30);

        }

        List<TurnstileRegister> newRegisterList = turnstile.setRegisterDeltas();

        testResult(turnstile, newRegisterList);
        
        turnstile.setRegisters(newRegisterList);
        List<TurnstileRegister> anotherNewRegistertList = turnstile.setRegisterDeltas();

        testResult(turnstile, anotherNewRegistertList);
    }

    private void testResult(Turnstile turnstile, List<TurnstileRegister> newRegisterList)
    {

        List<TurnstileRegister> oldRegisterList = turnstile.getRegisters();
        assertTrue(newRegisterList.size() >= oldRegisterList.size());

        int enterDeltaTotal = 0;
        int exitDeltaTotal = 0;
        int initialEnterCum = newRegisterList.get(0).getEntriesCum();
        int initialExitCum = newRegisterList.get(0).getExitsCum();
        TurnstileRegister previousRegister = null;
        for (TurnstileRegister register : newRegisterList)
        {
            if (register.getEntriesDelta() >= 0)
            {
                enterDeltaTotal += register.getEntriesDelta();
                exitDeltaTotal += register.getExitsDelta();

                assertTrue(String.format("%d + %d != %d", enterDeltaTotal, initialEnterCum, register.getEntriesCum()),
                        register.getEntriesCum() == enterDeltaTotal + initialEnterCum);
                assertTrue(String.format("%d + %d != %d", exitDeltaTotal, initialExitCum, register.getExitsCum()),
                        register.getExitsCum() == exitDeltaTotal + initialExitCum);

                // /If same hour twice both must be non-dummy
                assertTrue(previousRegister.getHour() != register.getHour() || !"DUMMY".equals(previousRegister.getDescription())
                        && !"DUMMY".equals(register.getDescription()));

                assertTrue(String.format("PREVIOUS: %d, CURRENT: %d", previousRegister.getHour(), register.getHour()), register.getHour()
                        - previousRegister.getHour() < 2);
            }
            previousRegister = register;
        }
    }
    /**
     * Test method for
     * {@link com.timothyimhof.model.Turnstile#setRegisterDeltas()}.
     */
    @Test
    public void testSetRegisterDeltasInitialComplete()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Turnstile turnstile = new Turnstile();
        Week week = new Week();
        week.setId(8);
        int cumEntries = 11;
        int cumExits = 12;
        for (int hour = 0; hour < 24; hour++)
        {
            cal.set(Calendar.HOUR_OF_DAY, hour);
            TurnstileRegister register = new TurnstileRegister();
            register.setTimestamp(cal);
            register.setEntriesCum(cumEntries);
            register.setExitsCum(cumExits);
            // register.setWeek(week);
            System.out.println("ADDING " + register.toString());
            turnstile.addRegister(register);

            cumEntries += (int) (Math.random() * 30);
            cumExits += (int) (Math.random() * 30);

        }

        List<TurnstileRegister> newRegisterList = turnstile.setRegisterDeltas();

        List<TurnstileRegister> oldRegisterList = turnstile.getRegisters();

        assertTrue(newRegisterList.size() == oldRegisterList.size());

        for (int i = 0; i < oldRegisterList.size(); i++)
        {
            assertTrue(newRegisterList.get(i).equals(oldRegisterList.get(i)));
        }

        testResult(turnstile, newRegisterList);
    }

}
