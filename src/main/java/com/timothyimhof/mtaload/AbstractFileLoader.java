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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;

import com.timothyimhof.model.MTAFile;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public abstract class AbstractFileLoader
{
    private Properties properties;
    // For testing purposes
    protected int maxLinesPerFile = -1;

    /**
     * @return Returns the maxLinesPerFile.
     */
    public int getMaxLinesPerFile()
    {
        return maxLinesPerFile;
    }

    /**
     * @param maxLinesPerFile The maxLinesPerFile to set.
     */
    public void setMaxLinesPerFile(int maxLinesPerFile)
    {
        this.maxLinesPerFile = maxLinesPerFile;
    }

    public void loadProperties()
    {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("loading.properties");

        this.properties = new Properties();
        try
        {
            properties.load(inputStream);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadFiles() throws ParseException, IOException
    {
        OrmService ormService = new OrmService();

        File directory = new File(this.properties.getProperty(getLoadDirectory()));
        File[] fileList = directory.listFiles();

        for (File file : fileList)
        {
            System.out.println("LOADING: " + file.getName());
            loadFile(file, ormService);

            ormService.beginTransaction();

            MTAFile turnstileFile = new MTAFile();
            turnstileFile.setDatetime(Calendar.getInstance().getTime());
            turnstileFile.setFilename(file.getName());
            ormService.getEntityManager().persist(turnstileFile);

            ormService.tryCommitTransactionOrRollback();
        }

        postLoad(ormService);

    }

    protected String[] upperCase(String[] values)
    {
        String[] upper = new String[values.length];
        for (int i = 0; i < values.length; i++)
        {
            upper[i] = values[i].toUpperCase().trim();
        }
        return upper;
    }

    protected abstract String getLoadDirectory();

    public abstract void loadFile(File file, OrmService ormService) throws FileNotFoundException, ParseException, IOException;

    protected void postLoad(OrmService ormService)
    {
        
    }
}
