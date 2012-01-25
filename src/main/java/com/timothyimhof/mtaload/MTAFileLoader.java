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
package com.timothyimhof.mtaload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import com.timothyimhof.mtaload.turnstile.EnterExitLoader;
import com.timothyimhof.mtaload.turnstile.EntranceLoader;
import com.timothyimhof.mtaload.turnstile.FareLoader;
import com.timothyimhof.mtaload.turnstile.RemoteLoader;
import com.timothyimhof.mtaload.turnstile.StationAlternateNameLoader;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class MTAFileLoader
{
    public static void main(String[] args)
    {
        MTAFileLoader fileLoader = new MTAFileLoader();
        try
        {
            fileLoader.load();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParseException e)
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

    public void load() throws ParseException, IOException
    {
        load(-1);
    }

    public void load(int maxLinesPerFile) throws ParseException, IOException
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
    }
}
