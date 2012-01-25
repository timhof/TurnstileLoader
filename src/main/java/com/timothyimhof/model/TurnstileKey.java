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
public class TurnstileKey implements Comparable<TurnstileKey>
{
    private final String booth;// Control Area (A002)
    private final String remote;// Remote Unit for a station (R051)
    private final String scpAddress;// Subunit Channel Position represents an
                                    // specific
                            // address for a device (02-00-00)

    public TurnstileKey(String remote, String booth, String scpAddress)
    {
        this.booth = booth;
        this.remote = remote;
        this.scpAddress = scpAddress;
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.booth);
        builder.append(this.remote);
        builder.append(this.scpAddress);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        TurnstileKey turnstileKey = (TurnstileKey) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.booth, turnstileKey.booth);
        builder.append(this.remote, turnstileKey.remote);
        builder.append(this.scpAddress, turnstileKey.scpAddress);
        return builder.isEquals();
    }

    @Override
    public int compareTo(TurnstileKey turnstileKey)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.booth, turnstileKey.booth);
        builder.append(this.remote, turnstileKey.remote);
        builder.append(this.scpAddress, turnstileKey.scpAddress);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s", booth, remote, scpAddress);
    }
}
