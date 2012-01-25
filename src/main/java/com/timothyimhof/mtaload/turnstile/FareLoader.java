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
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import au.com.bytecode.opencsv.CSVReader;

import com.timothyimhof.model.FareRegister;
import com.timothyimhof.model.FareType;
import com.timothyimhof.model.Remote;
import com.timothyimhof.model.Station;
import com.timothyimhof.mtaload.AbstractFileLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class FareLoader extends AbstractFileLoader
{
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public FareLoader()
    {
        loadProperties();
    }

    @Override
    public void loadFile(File file, OrmService ormService) throws ParseException, IOException
    {
        CSVReader parser = new CSVReader(new FileReader(file));
        System.out.println("LOADING FARES");

        ormService.beginTransaction();

        int lineCount = 0;
        String[] record;
        Date startDate = null;
        Date endDate = null;
        FareType[] fareTypes = null;
        while ((record = parser.readNext()) != null)
        {
            record = upperCase(record);
            if(record[record.length-1].equals(""))
            {
                record = Arrays.copyOf(record, record.length - 1);
            }
            if(lineCount == 0)
            {

            }
            else if(lineCount == 1)
            {
                startDate = formatter.parse(record[1].substring(0, record[1].indexOf('-')));
                endDate = formatter.parse(record[1].substring(record[1].indexOf('-') + 1));
            }
            else if (lineCount == 2)
            {
                fareTypes = new FareType[record.length];
                for (int i = 2; i < record.length; i++)
                {
                    fareTypes[i] = new FareType();
                    fareTypes[i].setColumnName(record[i]);
                }
            }
            else
            {
                Remote remote = MTALoadCache.getInstance().getRemote(ormService, record[0], record[0]);
                Station station = remote.getStation();

                if (station == null)
                {
                    System.out.println("MISSING REMOTE: " + record[0] + ":" + record[1]);
                    continue;
                }
                for (int i = 2; i < record.length; i++)
                {
                    FareRegister fareRegister = new FareRegister();
                    fareRegister.setFareType(fareTypes[i]);
                    fareRegister.setValue(Integer.parseInt(record[i]));
                    fareRegister.setStartDate(startDate);
                    fareRegister.setEndDate(endDate);
                    station.addFareRegister(fareRegister);
                    fareRegister.setStation(station);
                    ormService.persist(fareRegister);
                }
            }
            lineCount++;
            if (lineCount % 100 == 0)
            {
                System.out.format("PROCESSED %d LINES\n", lineCount);
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
        return "fare_load_directory";
    }

}
