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
 * FileName: EnterExitLoaderTest.java
 *************************************************************************/
package com.timothyimhof.mtaload;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 30, 2011
 */
public class DatabaseHelper
{
    public static final String JDBC_URL = "jdbc:sqlite:C:/Users/timothyi/mta/database/development.sqlite3";
    
    public static void deleteDatabase() throws SQLException
    {
        if ("sqlite".equals(JDBC_URL.substring(5, 11)))
        {
            deleteDatabaseSQLITE();
        }
        else
        {
            deleteDatabaseH2();
        }
    }

    public static void deleteDatabaseH2() throws SQLException
    {
        Connection conn = DriverManager.getConnection(JDBC_URL, "sa", "");

        Statement stmt = conn.createStatement();

        try
        {
            stmt.execute("DROP ALL OBJECTS DELETE FILES");
            // stmt.execute("SHUTDOWN"); TODO: db files get removed if this line
            // is uncommented
        }
        finally
        {
            stmt.close();
            conn.close();
        }
    }

    public static void deleteDatabaseSQLITE() throws SQLException
    {
        File f = new File(JDBC_URL.substring(12));
       f.delete();
    }
}
