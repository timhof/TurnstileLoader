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

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
@Entity
@Table(name = "station_alternate_names")
public class StationAlternateName implements Comparable<StationAlternateName>, Serializable
{
    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    private Set<String> alternateNames;

    /**
     * @return Returns the alternateNames.
     */
    public Set<String> getAlternateNames()
    {
        return alternateNames;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    public StationAlternateName()
    {
        this.alternateNames = new HashSet<String>();
    }

    public StationAlternateName(String name, String alternateName)
    {
        this.name = name;
        this.alternateNames = new HashSet<String>();
        this.alternateNames.add(alternateName);
    }

    public StationAlternateName(String name, String[] alternateNames)
    {
        this.name = name;
        this.alternateNames = new HashSet<String>();
        this.alternateNames.addAll(Arrays.asList(alternateNames));
    }

    /**
     * @return Returns the id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(int id)
    {
        this.id = id;
    }


    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.name);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        StationAlternateName station = (StationAlternateName) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.name, station.name);
        return builder.isEquals();
    }

    @Override
    public int compareTo(StationAlternateName station)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.name, station.name);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String.format("%s -> [%s]", name, getAlternateNameString());
    }

    private String getAlternateNameString()
    {
        StringBuilder alternateNameString = new StringBuilder();
        if (alternateNames != null)
        {
            for (String alternateName : alternateNames)
            {
                alternateNameString.append(alternateName + ", ");
            }
        }
        return alternateNameString.toString();
    }
}
