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

import au.com.bytecode.opencsv.CSVReader;

import com.timothyimhof.model.Entrance;
import com.timothyimhof.model.Route;
import com.timothyimhof.mtaload.AbstractFileLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class EntranceLoader extends AbstractFileLoader
{
    public static void main(String[] args)
    {
        RemoteLoader loader = new RemoteLoader();
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

    public EntranceLoader()
    {
        loadProperties();
    }

    @Override
    public void loadFile(File file, OrmService ormService) throws ParseException, NumberFormatException, IOException
    {
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
            Entrance entrance = MTALoadCache.getInstance().getEntrance(ormService, record[2], record[1], record[0], record[24], record[25]);

            entrance.setAda(getBooleanValue(record[14]));
            entrance.setAdadNotes(record[15]);
            entrance.setFreeCrossover(getBooleanValue(record[16]));
            entrance.setEntranceStaffing(record[17]);
            entrance.setEntranceType(record[18]);
            entrance.setExit_Only(getBooleanValue(record[19]));
            entrance.setEntranceStaffing(record[20]);
            entrance.setNorthSouthStreet(record[21]);
            entrance.setEastWestStreet(record[22]);
            entrance.setCorner(record[23]);
            entrance.setLatitude(Double.parseDouble(record[24]) / 1000000.0);
            entrance.setLongitude(Double.parseDouble(record[25]) / 1000000.0);

            for (int i = 3; i < 14; i++)
            {
                if (record[i].length() > 0)
                {
                    Route route = MTALoadCache.getInstance().getRoute(ormService, record[i]);

                    // Add route to entrance
                    route.addEntrance(entrance);
                    entrance.addRoute(route);

                    // Add route to station
                    route.addStation(entrance.getStation());
                    entrance.getStation().addRoute(route);
                }
            }
        }

        ormService.tryCommitTransactionOrRollback();
    }

    private boolean getBooleanValue(String value)
    {
        return "TRUE".equals(value) || "YES".equals(value);
    }
    /**
     * @return
     * @see com.timothyimhof.mta.load.AbstractFileLoader#getLoadDirectory()
     */
    @Override
    protected String getLoadDirectory()
    {
        return "entrance_load_directory";
    }

}
