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
@Table(name = "turnstile_registers")
public class TurnstileRegister implements Serializable, Comparable<TurnstileRegister>
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

    @Column(name = "description")
    private String description;

    @Column(name = "entriesCum")
    private int entriesCum;

    @Column(name = "exitsCum")
    private int exitsCum;

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
    @JoinColumn(name = "turnstile_id")
    private Turnstile turnstile;
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
        long timeInMillis = cal.getTimeInMillis();
        if (cal.get(Calendar.MINUTE) == 0)
        {
            timeInMillis -= 60000;
        }
        this.timestamp = new Date(timeInMillis);
        this.weekdayIndex = cal.get(Calendar.DAY_OF_WEEK);
        if (weekdayIndex == Calendar.SATURDAY)
        {
            this.weekdayIndex = 0;
        }

        this.hour = cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @return Returns the description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return Returns the entriesCum.
     */
    public int getEntriesCum()
    {
        return entriesCum;
    }

    /**
     * @param entriesCum The entriesCum to set.
     */
    public void setEntriesCum(int entriesCum)
    {
        this.entriesCum = entriesCum;
    }

    /**
     * @return Returns the exitsCum.
     */
    public int getExitsCum()
    {
        return exitsCum;
    }

    /**
     * @param exitsCum The exitsCum to set.
     */
    public void setExitsCum(int exitsCum)
    {
        this.exitsCum = exitsCum;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    /**
     * @return Returns the turnstile.
     */
    public Turnstile getTurnstile()
    {
        return turnstile;
    }

    /**
     * @param turnstile The turnstile to set.
     */
    public void setTurnstile(Turnstile turnstile)
    {
        this.turnstile = turnstile;
    }

    public int compareTo(TurnstileRegister turnstileRegister)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.timestamp, turnstileRegister.timestamp);
        return builder.toComparison();
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.timestamp);
        builder.append(this.description);
        builder.append(this.entriesCum);
        builder.append(this.exitsCum);
        builder.append(this.entriesDelta);
        builder.append(this.exitsDelta);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        TurnstileRegister turnstileRegister = (TurnstileRegister) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.timestamp, turnstileRegister.timestamp);
        builder.append(this.description, turnstileRegister.description);
        builder.append(this.entriesCum, turnstileRegister.entriesCum);
        builder.append(this.exitsCum, turnstileRegister.exitsCum);
        builder.append(this.entriesDelta, turnstileRegister.entriesDelta);
        builder.append(this.exitsDelta, turnstileRegister.exitsDelta);
        return builder.isEquals();
    }

    public void setPreviousEntries(int previousEntries)
    {
        this.entriesDelta = 0;
        if (previousEntries >= 0)
        {
            entriesDelta = this.entriesCum - previousEntries;
        }
    }

    public void setPreviousExits(int previousExits)
    {
        this.exitsDelta = 0;
        if (previousExits >= 0)
        {
            exitsDelta = this.exitsCum - previousExits;
        }
    }

    @Override
    public String toString()
    {
        return String.format("%s\t%s\t%d\t%d\t%s\t%s", formatter.format(this.timestamp), this.description, this.entriesCum, this.exitsCum,
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
