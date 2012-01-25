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
 * FileName: EnterExitLoader.java
 *************************************************************************/
package com.timothyimhof.mtaload.turnstile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import au.com.bytecode.opencsv.CSVReader;

import com.timothyimhof.model.Borough;
import com.timothyimhof.mtaload.AbstractFileLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class BoroughLoader extends AbstractFileLoader
{

    private Map<String, Integer> boroughKeyIdCache = new HashMap<String, Integer>();

    public static void main(String[] args)
    {
        BoroughLoader loader = new BoroughLoader();
        try
        {
            loader.loadFiles();
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public BoroughLoader()
    {
        loadProperties();

        initCache();
    }

    private void initCache()
    {
        OrmService ormService = new OrmService();
        Collection<Borough> boroughList = ormService.getAll(Borough.class);
        for (Borough borough : boroughList)
        {
            String boroughKey = borough.getName();
            this.boroughKeyIdCache.put(boroughKey, borough.getId());
        }
        System.out.format("%d BOROUGHS\n", this.boroughKeyIdCache.size());
    }

    @Override
    public void loadFile(File file, OrmService ormService) throws ParseException, IOException
    {
        EntityManager em = ormService.getEntityManager();

        CSVReader parser = new CSVReader(new FileReader(file));

        ormService.beginTransaction();

        boolean skipLine = true;
        String[] record;
        while ((record = parser.readNext()) != null)
        {
            if (skipLine)
            {
                skipLine = false;
                continue;
            }

            record = upperCase(record);
            Borough rbs = getBorough(ormService, record[0]);

            em.persist(rbs);
        }

        ormService.tryCommitTransactionOrRollback();
    }

    public Borough getBorough(OrmService ormService, String boroughName)
    {
        Borough borough = null;
        Integer boroughId = boroughKeyIdCache.get(boroughName);
        if (boroughId == null)
        {

            borough = new Borough(boroughName);
            ormService.getEntityManager().persist(borough);
            // System.out.println("New RemoteBoothStation: " +
            // remoteBoothStation.getId() + "(" +
            // remoteBoothStationKey.toString() + ")");
            boroughKeyIdCache.put(boroughName, borough.getId());
        }
        else
        {
            borough = ormService.findById(Borough.class, boroughId);
        }
        return borough;
    }

    /**
     * @return
     * @see com.timothyimhof.mta.load.AbstractFileLoader#getLoadDirectory()
     */
    @Override
    protected String getLoadDirectory()
    {
        return "borough_load_directory";
    }

}
