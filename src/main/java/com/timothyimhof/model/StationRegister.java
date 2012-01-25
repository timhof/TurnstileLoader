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
import java.util.Calendar;
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
@Table(name = "station_registers")
public class StationRegister implements Serializable, Comparable<StationRegister>
{

    private static SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    // @ManyToOne(cascade = CascadeType.PERSIST)
    // @JoinColumn(name = "week_id")
    // private Week week;

    private int weekdayIndex;// 0-6

    private int hour;// 0-23

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "entriesDelta")
    private int entriesDelta = -1;

    @Column(name = "exitsDelta")
    private int exitsDelta = -1;
    
    /**
     * @return Returns the entriesDelta.
     */
    public int getEntriesDelta()
    {
        return entriesDelta;
    }

    /**
     * @param entriesDelta The entriesDelta to set.
     */
    public void setEntriesDelta(int entriesDelta)
    {
        this.entriesDelta = entriesDelta;
    }

    /**
     * @return Returns the exitsDelta.
     */
    public int getExitsDelta()
    {
        return exitsDelta;
    }

    /**
     * @param exitsDelta The exitsDelta to set.
     */
    public void setExitsDelta(int exitsDelta)
    {
        this.exitsDelta = exitsDelta;
    }


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "station_id")
    private Station station;
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
     * @return Returns the timestamp.
     */
    public Date getTimestamp()
    {
        return timestamp;
    }

    /**
     * @param datetime The timestampatetime to set.
     */
    public void setTimestamp(Calendar cal)
    {
        this.timestamp = new Date(cal.getTimeInMillis());

        this.weekdayIndex = cal.get(Calendar.DAY_OF_WEEK);
        if (weekdayIndex == Calendar.SATURDAY)
        {
            this.weekdayIndex = 0;
        }

        this.hour = cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    /**
     * @return Returns the station.
     */
    public Station getStation()
    {
        return station;
    }

    /**
     * @param turnstile The station to set.
     */
    public void setStation(Station station)
    {
        this.station = station;
    }

    public int compareTo(StationRegister stationRegister)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.timestamp, stationRegister.timestamp);
        return builder.toComparison();
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.timestamp);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        StationRegister stationRegister = (StationRegister) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.timestamp, stationRegister.timestamp);
        return builder.isEquals();
    }


    @Override
    public String toString()
    {
        return String.format("%s\t%s\t%s", formatter.format(this.timestamp),
                this.entriesDelta >= 0 ? String.valueOf(this.entriesDelta) : "N/A", this.exitsDelta >= 0 ? String.valueOf(this.exitsDelta)
                        : "N/A");
    }

    // /**
    // * @return Returns the week.
    // */
    // public Week getWeek()
    // {
    // return week;
    // }
    //
    // /**
    // * @param week The week to set.
    // */
    // public void setWeek(Week week)
    // {
    // this.week = week;
    // }

    /**
     * @return Returns the weekdayIndex.
     */
    public int getWeekdayIndex()
    {
        return weekdayIndex;
    }

    /**
     * @param weekdayIndex The weekdayIndex to set.
     */
    public void setWeekdayIndex(int weekdayIndex)
    {
        this.weekdayIndex = weekdayIndex;
    }

    /**
     * @return Returns the hour.
     */
    public int getHour()
    {
        return hour;
    }

    /**
     * @param hour The hour to set.
     */
    public void setHour(int hour)
    {
        this.hour = hour;
    }
}
