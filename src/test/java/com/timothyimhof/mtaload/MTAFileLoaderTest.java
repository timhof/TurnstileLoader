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
 * FileName: MTAFileLoaderTest.java
 *************************************************************************/
package com.timothyimhof.mtaload;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timothyimhof.model.Remote;
import com.timothyimhof.model.Turnstile;
import com.timothyimhof.model.TurnstileRegister;
import com.timothyimhof.persistence.OrmService;


/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 1, 2011
 */
public class MTAFileLoaderTest
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
     * Test method for {@link com.timothyimhof.mta.load.MTAFileLoader#load()}.
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testLoad() throws ParseException, IOException
    {
        MTAFileLoader loader = new MTAFileLoader();
        loader.load(1000);

        OrmService ormService = new OrmService();

        Collection<Remote> remoteBoothList = (Collection<Remote>) ormService.getAll(Remote.class);

        assertTrue(remoteBoothList.size() > 0);

        System.out.format("NUMBER OF REMOTEBOOTHS: %d\n", remoteBoothList.size());

        for (Remote remoteBooth : remoteBoothList)
        {
            System.out.format("%s\n", remoteBooth.toString());

        }

        Collection<Turnstile> turnstileList = (Collection<Turnstile>) ormService.getAll(Turnstile.class);

        assertTrue(turnstileList.size() > 0);

        System.out.format("NUMBER OF TURNSTILES: %d\n", turnstileList.size());

        for (Turnstile turnstile : turnstileList)
        {
            System.out.format("%s\n", turnstile.toString());
            List<TurnstileRegister> registerList = turnstile.getRegisters();
            Collections.sort(registerList);
            for (TurnstileRegister register : registerList)
            {
                // System.out.format("\t%s\n", register.toString());
            }
        }
    }

}
