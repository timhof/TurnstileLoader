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
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.timothyimhof.model.Remote;
import com.timothyimhof.model.Station;
import com.timothyimhof.model.Turnstile;
import com.timothyimhof.model.TurnstileRegister;
import com.timothyimhof.model.Week;
import com.timothyimhof.mtaload.AbstractFileLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class EnterExitLoader extends AbstractFileLoader
{
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy HH:mm:ss");

    public EnterExitLoader()
    {
        loadProperties();
    }

    @Override
    public void loadFile(File file, OrmService ormService) throws ParseException, IOException
    {
        CSVReader parser = new CSVReader(new FileReader(file));

        String filename = file.getName();
        int indexOf_ = filename.indexOf('_');
        String yymmdd = filename.substring(indexOf_ + 1, filename.length() - 4);
        Week week = MTALoadCache.getInstance().getWeek(ormService, yymmdd);

        ormService.beginTransaction();

        Calendar cal = new GregorianCalendar();

        int lineCount = 0;
        String[] record;
        while ((record = parser.readNext()) != null)
        {
            record = upperCase(record);
            Turnstile turnstile = MTALoadCache.getInstance().getTurnstile(ormService, record[1], record[0], record[2]);

            int readingsPerRow = (record.length - 3) / 5;
            for (int i = 0; i < readingsPerRow; i++)
            {
                TurnstileRegister register = getRegister(record, 3 + (i * 5), week, cal);
                ormService.persist(register);
                register.setTurnstile(turnstile);
                turnstile.addRegister(register);
            }

            Remote remoteBooth = MTALoadCache.getInstance().getRemote(ormService, record[1], record[2]);
            remoteBooth.addTurnstile(turnstile);
            turnstile.setRemoteBooth(remoteBooth);

            lineCount++;
            if (lineCount % 1000 == 0)
            {
                System.out.format("\tPROCESSED %d LINES\n", lineCount);
                ormService.tryCommitTransactionOrRollback();
                ormService.beginTransaction();
            }
            if (maxLinesPerFile >= 0 && lineCount >= maxLinesPerFile)
            {
                break;
            }
        }

        ormService.tryCommitTransactionOrRollback();
    }

    @Override
    protected void postLoad(OrmService ormService)
    {

        System.out.println("SETTING TURNSTILE REGISTER DELTAS");
        List<Remote> remoteBoothList = ormService.getAll(Remote.class);

        int remoteCount = 0;
        int numRemotes = remoteBoothList.size();

        for (Remote remoteBooth : remoteBoothList)
        {
            remoteCount++;
            System.out.format("\tPROCESSING REMOTE: %d out of %d\n", remoteCount, numRemotes);
            List<Turnstile> turnstileList = remoteBooth.getTurnstiles();
            Collections.sort(turnstileList);
            int turnstileCount = 0;
            int numTurnstiles = turnstileList.size();
            System.out.println("\t\t" + turnstileList.size() + " TURNSTILES\n");

            for (Turnstile turnstile : turnstileList)
            {
                turnstileCount++;
                System.out.format("\t\tPROCESSING TURNSTILE: %s (%d out of %d)\n", turnstile.toString(), turnstileCount, numTurnstiles);
                ormService.beginTransaction();
                List<TurnstileRegister> registers = turnstile.setRegisterDeltas();
                ormService.persistAll(registers);
                ormService.tryCommitTransactionOrRollback();

                ormService.getEntityManager().refresh(turnstile);
            }
        }

        setStationRegisters();
    }

    public void setStationRegisters()
    {
        OrmService ormService = new OrmService();
        System.out.println("SETTING STATION REGISTER DELTAS");
        List<Station> stationList = ormService.getAll(Station.class);
        int stationCount = stationList.size();
        int count = 0;
        for (Station station : stationList)
        {
            ormService.beginTransaction();
            station.clearRegisters();
            ormService.tryCommitTransactionOrRollback();

            ormService.beginTransaction();
            station.addRegisters();
            ormService.tryCommitTransactionOrRollback();

            System.out.format("\tPROCESSED %d out of %d STATIONS\n", ++count, stationCount);
        }
    }
    private TurnstileRegister getRegister(String[] record, int startIndex, Week week, Calendar cal) throws ParseException
    {
        TurnstileRegister register = new TurnstileRegister();
        register.setDescription(record[startIndex + 2]);
        register.setEntriesCum(Integer.parseInt(record[startIndex + 3]));
        register.setExitsCum(Integer.parseInt(record[startIndex + 4]));
        cal.setTime(formatter.parse(record[startIndex] + " " + record[startIndex + 1]));
        register.setTimestamp(cal);
        // register.setWeek(week);
        return register;
    }

    /**
     * @return
     * @see com.timothyimhof.mta.load.AbstractFileLoader#getLoadDirectory()
     */
    @Override
    protected String getLoadDirectory()
    {
        return "turnstile_load_directory";
    }

}
