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

import com.timothyimhof.model.Division;
import com.timothyimhof.model.Remote;
import com.timothyimhof.model.Route;
import com.timothyimhof.model.Station;
import com.timothyimhof.mtaload.AbstractFileLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class RemoteLoader extends AbstractFileLoader
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

    public RemoteLoader()
    {
        loadProperties();
    }

    @Override
    public void loadFile(File file, OrmService ormService) throws ParseException, IOException
    {
        CSVReader parser = new CSVReader(new FileReader(file));

        ormService.beginTransaction();

        boolean skipLine = true;
        String[] record;
        while ((record = parser.readNext()) != null)
        {
            record = upperCase(record);
            if (skipLine)
            {
                skipLine = false;
                continue;
            }
            Remote rbs = MTALoadCache.getInstance().getRemote(ormService, record[0], record[1]);
            Station station = MTALoadCache.getInstance().getStation(ormService, record[2], "", record[4]);
            Division division = MTALoadCache.getInstance().getDivision(ormService, record[4]);

            rbs.setStation(station);
            station.setDivision(division);
            rbs.setDivision(division);
            station.addRemoteBooth(rbs);
            for (int i = 0; i < record[3].length(); i++)
            {
                Route route = MTALoadCache.getInstance().getRoute(ormService, record[3].substring(i, i + 1));

                    // Add route to remoteBooth
                    route.addRemoteBooth(rbs);
                    rbs.addRoute(route);

                // Add route to station
                route.addStation(station);
                station.addRoute(route);
            }
        }

        ormService.tryCommitTransactionOrRollback();
    }

    /**
     * @return
     * @see com.timothyimhof.mta.load.AbstractFileLoader#getLoadDirectory()
     */
    @Override
    protected String getLoadDirectory()
    {
        return "remote_booth_station_load_directory";
    }

}
