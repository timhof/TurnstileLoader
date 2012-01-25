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
 * FileName: BoroughLoaderTest.java
 *************************************************************************/
package com.timothyimhof.mtaload;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.timothyimhof.model.Borough;
import com.timothyimhof.mtaload.turnstile.BoroughLoader;
import com.timothyimhof.persistence.OrmService;


/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 9, 2011
 */
public class BoroughLoaderTest
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
     * {@link com.timothyimhof.mta.load.turnstile.BoroughLoader#loadFile(java.io.File, com.timothyimhof.mta.persistence.OrmService)}
     * .
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testLoadFile() throws ParseException, IOException
    {
        BoroughLoader loader = new BoroughLoader();
        loader.loadFiles();

        OrmService ormService = new OrmService();

        Collection<Borough> boroughList = (Collection<Borough>) ormService.getAll(Borough.class);

        assertTrue(boroughList.size() > 0);

        System.out.format("NUMBER OF BOROUGHS: %d\n", boroughList.size());

        for (Borough borough : boroughList)
        {
            System.out.format("%s\n", borough.toString());
        }
    }

}
