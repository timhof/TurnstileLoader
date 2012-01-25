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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "fare_registers")
public class FareRegister implements Serializable, Comparable<FareRegister>
{

    private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fareType_id")
    private FareType fareType;

    private int value;
    /**
     * @return Returns the startDate.
     */
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @return Returns the endDate.
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return Returns the fareType.
     */
    public FareType getFareType()
    {
        return fareType;
    }

    /**
     * @param fareType The fareType to set.
     */
    public void setFareType(FareType fareType)
    {
        this.fareType = fareType;
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

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    public int compareTo(FareRegister fareRegister)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.startDate, fareRegister.startDate);
        builder.append(this.endDate, fareRegister.endDate);
        builder.append(this.fareType, fareRegister.fareType);
        builder.append(this.station, fareRegister.station);
        return builder.toComparison();
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.startDate);
        builder.append(this.endDate);
        builder.append(this.fareType);
        builder.append(this.station);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        FareRegister fareRegister = (FareRegister) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.startDate, fareRegister.startDate);
        builder.append(this.endDate, fareRegister.endDate);
        builder.append(this.fareType, fareRegister.fareType);
        builder.append(this.station, fareRegister.station);
        return builder.isEquals();
    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s, %s", formatter.format(this.startDate), formatter.format(this.endDate), this.fareType, this.station);
    }

    /**
     * @return Returns the value.
     */
    public int getValue()
    {
        return value;
    }

    /**
     * @param value The value to set.
     */
    public void setValue(int value)
    {
        this.value = value;
    }

    /**
     * @return Returns the station.
     */
    public Station getStation()
    {
        return station;
    }

    /**
     * @param station The station to set.
     */
    public void setStation(Station station)
    {
        this.station = station;
    }
}
