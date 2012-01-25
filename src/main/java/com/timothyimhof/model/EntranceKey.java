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
 * SubSystem: com.timothyimhof.mta.model
 * FileName: TurnstileRegister.java
 *************************************************************************/
package com.timothyimhof.model;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class EntranceKey implements Comparable<EntranceKey>
{
    private final Station station;
    private final Line line;
    private final Division division;
    private final String latitude;
    private final String longitude;

    public EntranceKey(Station station, Line line, Division division, String latitude, String longitude)
    {
        this.station = station;
        this.line = line;
        this.division = division;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.station);
        builder.append(this.line);
        builder.append(this.division);
        builder.append(this.latitude);
        builder.append(this.longitude);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        EntranceKey entranceKey = (EntranceKey) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.station, entranceKey.station);
        builder.append(this.line, entranceKey.line);
        builder.append(this.division, entranceKey.division);
        builder.append(this.latitude, entranceKey.latitude);
        builder.append(this.longitude, entranceKey.longitude);
        return builder.isEquals();
    }

    @Override
    public int compareTo(EntranceKey entranceKey)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.station, entranceKey.station);
        builder.append(this.line, entranceKey.line);
        builder.append(this.division, entranceKey.division);
        builder.append(this.latitude, entranceKey.latitude);
        builder.append(this.longitude, entranceKey.longitude);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s [%f, %f]", station.toString(), line.toString(), division.toString(), latitude, longitude);
    }
}
