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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.timothyimhof.interval.util.DateIntervalHelper;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
@Entity
@Table(name = "remote_stations")
public class Remote implements Comparable<Remote>, Serializable, IHasRoutes
{

    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "remote")
    private String remote = "UNSET";

    @Column(name = "booth")
    private String booth = "UNSET";

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToMany
    @JoinTable(name = "remote_booth_route", joinColumns = @JoinColumn(name = "remote_booth_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "ID"))
    private Set<Route> routes;

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

    /**
     * @return Returns the routes.
     */
    public List<Route> getRoutes()
    {
        return new ArrayList<Route>(routes);
    }

    /**
     * @param routes The routes to set.
     */
    public void addRoute(Route route)
    {
        if (this.routes == null)
        {
            this.routes = new HashSet<Route>();
        }
        this.routes.add(route);
    }

    @OneToMany(mappedBy = "remoteBooth", cascade = CascadeType.ALL)
    private Set<Turnstile> turnstiles;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "borough_id")
    private Borough borough;

    /**
     * @return Returns the borough.
     */
    public Borough getBorough()
    {
        return borough;
    }

    /**
     * @param borough The borough to set.
     */
    public void setBorough(Borough borough)
    {
        this.borough = borough;
    }

    /**
     * @return Returns the turnstiles.
     */
    public List<Turnstile> getTurnstiles()
    {
        return new ArrayList<Turnstile>(this.turnstiles);
    }

    /**
     * @param turnstiles The turnstiles to set.
     */
    public void setTurnstiles(Set<Turnstile> turnstiles)
    {
        this.turnstiles = turnstiles;
    }

    public Remote()
    {

    }

    public Remote(String remote, String booth)
    {
        this.remote = remote;
        this.booth = booth;
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
     * @return Returns the remote.
     */
    public String getRemote()
    {
        return remote;
    }

    /**
     * @param remote The remote to set.
     */
    public void setRemote(String remote)
    {
        this.remote = remote;
    }

    /**
     * @return Returns the booth.
     */
    public String getBooth()
    {
        return booth;
    }

    /**
     * @param booth The booth to set.
     */
    public void setBooth(String booth)
    {
        this.booth = booth;
    }

    /**
     * @return Returns the division.
     */
    public Division getDivision()
    {
        return division;
    }

    /**
     * @param division The division to set.
     */
    public void setDivision(Division division)
    {
        this.division = division;
    }

    public int[] getTotalEntryExits()
    {
        int totalEntrys = 0;
        int totalExits = 0;
        for (Turnstile turnstile : this.turnstiles)
        {
            int[] totalEntryExits = turnstile.getTotalEntryExits();
            totalEntrys += totalEntryExits[0];
            totalExits += totalEntryExits[1];
        }
        return new int[]{totalEntrys, totalExits};
    }

    public void calculateIntervalEntries(DateIntervalHelper helper, double[][] intervalEntryExits)
    {
        for (Turnstile turnstile : this.turnstiles)
        {
            turnstile.calculateIntervalEntries(helper, intervalEntryExits);
        }
    }

    public List<TurnstileRegister> setRegisterDeltas()
    {
        List<TurnstileRegister> dummyRegisterList = new ArrayList<TurnstileRegister>();
        for (Turnstile turnstile : this.turnstiles)
        {
            dummyRegisterList.addAll(turnstile.setRegisterDeltas());
        }
        return dummyRegisterList;
    }

    public void addTurnstile(Turnstile turnstile)
    {
        if (this.turnstiles == null)
        {
            this.turnstiles = new HashSet<Turnstile>();
        }
        this.turnstiles.add(turnstile);
    }

    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.remote);
        builder.append(this.booth);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        Remote remoteBoothStation = (Remote) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.remote, remoteBoothStation.remote);
        builder.append(this.booth, remoteBoothStation.booth);
        return builder.isEquals();
    }

    @Override
    public int compareTo(Remote remoteBoothStation)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.remote, remoteBoothStation.remote);
        builder.append(this.booth, remoteBoothStation.booth);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s", remote, this.booth);
    }

    public boolean hasStation()
    {
        return this.station != null;
    }

    public boolean hasEntrance()
    {
        return this.station != null && this.station.hasEntrance();
    }
}
