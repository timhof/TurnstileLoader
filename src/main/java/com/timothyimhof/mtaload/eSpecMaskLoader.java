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
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class eSpecMaskLoader
{

    private String upcFile;
    private String clpsefile;

    public static void main(String[] args)
    {
        String upcFile = "C:\\ACNielsen\\PFW\\pfact\\data\\12798_upcs.txt";
        String clpsefile = "C:\\ACNielsen\\PFW\\pfact\\data\\12798_upc-clpse.txt";
        eSpecMaskLoader loader = new eSpecMaskLoader(upcFile, clpsefile);
        try
        {
            loader.loadFiles();
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

    public eSpecMaskLoader(String upcFile, String clpsefile)
    {
        this.upcFile = upcFile;
        this.clpsefile = clpsefile;
    }

    public void loadFiles() throws IOException
    {
        Set<String> maskSet = new HashSet<String>();
        CSVReader parser = new CSVReader(new FileReader(this.clpsefile), '|');
//        Iterator<String[]> iterator = parser.iterator();
        int count = 0;
        String [] record;
        while ((record = parser.readNext()) != null)
        {
//            String[] record = iterator.next();
            if (count > 0)
            {
                String trnalUPC = record[1];
                String rptblUPC = record[2];
                maskSet.add(rptblUPC);
            }
            count++;
        }

        parser = new CSVReader(new FileReader(this.clpsefile), '|');
        count = 0;
        int foundCount = 0;
        int missingCount = 0;

        Set<String> uniquePrdcCodeSet = new HashSet<String>();

        while ((record = parser.readNext()) != null) 
        {
//            String[] record = iterator.next();
            if (count > 0)
            {
                String prdcCode = record[3];
                uniquePrdcCodeSet.add(prdcCode);
                if (maskSet.contains(prdcCode))
                {
                    foundCount++;
                }
                else
                {
                    missingCount++;
                }
            }
            count++;
            if (count % 100 == 0)
            {
                System.out.format("%d\n", count);
            }
        }

        System.out.format("UNIQUE PRDC CODES:     %d\n", uniquePrdcCodeSet.size());
        System.out.format("TOTAL UPC ROWS:        %d\n", count - 1);
        System.out.format("ROWS WITH MAPPINGS:    %d\n", foundCount);
        System.out.format("ROWS WITHOUT MAPPINGS: %d\n", missingCount);
    }

}
