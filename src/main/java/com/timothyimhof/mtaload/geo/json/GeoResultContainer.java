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
 * SubSystem: com.timothyimhof.mta.load.geo.json
 * FileName: GeoResult.java
 *************************************************************************/
package com.timothyimhof.mtaload.geo.json;

import java.util.Collection;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 14, 2011
 */
public class GeoResultContainer
{

    private Collection<GeoResult> results;

    /**
     * @return Returns the results.
     */
    public Collection<GeoResult> getResults()
    {
        return results;
    }

    /**
     * @param results The results to set.
     */
    public void setResults(Collection<GeoResult> results)
    {
        this.results = results;
    }

}
