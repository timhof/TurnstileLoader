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
 * SubSystem: com.timothyimhof.mtaload
 * FileName: IntervalTest.java
 *************************************************************************/
package com.timothyimhof.mtaload;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 29, 2011
 */
public class IntervalTest
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
    public void testInterval()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2011);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 19);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        final Date starttime = new Date(cal.getTimeInMillis());

        cal.set(Calendar.DAY_OF_MONTH, 25);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        final Date endtime = new Date(cal.getTimeInMillis());

        int millisecondsInTimeRange = (int) (endtime.getTime() - starttime.getTime());
        int millisecondsPerHour = 3600000;
        int millisecondsPer4Hours = millisecondsPerHour * 4;
        int intervals = (millisecondsInTimeRange / millisecondsPer4Hours) + 1;
        final double[][] intervalEntryExits = new double[2][intervals];

        cal = Calendar.getInstance();
        cal.setTime(new Date(starttime.getTime()));
        Date time = cal.getTime();

        while (time.equals(endtime) || time.before(endtime))
        {
            int milliSecondsFromStart = (int) (time.getTime() - starttime.getTime());
            int interval = milliSecondsFromStart / millisecondsPer4Hours;

            if (interval >= intervalEntryExits[0].length)
            {
                System.out.println("fadf");
            }

            cal.add(Calendar.MINUTE, 1);
            time = cal.getTime();
        }
    }

}
