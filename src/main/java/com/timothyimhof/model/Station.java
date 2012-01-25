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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;

import com.timothyimhof.interval.util.DateIntervalHelper;
import com.timothyimhof.mtaload.geo.json.Location;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
@Entity
@Table(name = "stations")
public class Station implements Comparable<Station>, Serializable, IHasEntrances, IHasRemoteBooths, IHasRoutes, IHasGeoCoordinates
{
    private static final long serialVersionUID = 3641782810208641704L;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyddMMHH");

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private Set<Remote> remoteBooths;

    @ManyToMany(mappedBy = "stations")
    private Set<Route> routes;

    @OneToMany(mappedBy = "station")
    private List<FareRegister> fareRegisters;


    @OneToMany(mappedBy = "station")
    private Set<Entrance> entrances;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "borough_id")
    private Borough borough;

    @ManyToOne
    private Line line;

    @ManyToOne
    private Division division;

    @Transient
    Map<FareType, Integer> fareTypeTotalMap;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    @BatchFetch(BatchFetchType.JOIN)
    private List<StationRegister> registers;

    public void clearRegisters()
    {
        if (this.registers != null)
        {
            this.registers.clear();
        }
    }

    public void addRegisters()
    {
        Map<String, StationRegister> registerTimeKeyStationRegisterMap = new HashMap<String, StationRegister>();
     Calendar cal = new GregorianCalendar();
     for(Remote remote : this.remoteBooths)
        {
            for(Turnstile turnstile : remote.getTurnstiles())
            {
                for(TurnstileRegister register : turnstile.getRegisters())
                {
                    cal.setTime(register.getTimestamp());
                    String yyyymmddhh = formatter.format(register.getTimestamp());
                    StationRegister stationRegister = (StationRegister) registerTimeKeyStationRegisterMap.get(yyyymmddhh);
                    if(stationRegister == null)
                    {
                        stationRegister = new StationRegister();
                        stationRegister.setStation(this);
                        cal.setTime(register.getTimestamp());
                        stationRegister.setTimestamp(cal);
                        // stationRegister.setWeek(register.getWeek());
                        this.addRegister(stationRegister);
                        registerTimeKeyStationRegisterMap.put(yyyymmddhh, stationRegister);
                    }
                    stationRegister.setEntriesDelta(stationRegister.getEntriesDelta() + register.getEntriesDelta());
                    stationRegister.setExitsDelta(stationRegister.getExitsDelta() + register.getExitsDelta());
                }
            }
        }
    }

    public void addRegister(StationRegister register)
    {
        if (this.registers == null)
        {
            this.registers = new ArrayList<StationRegister>();
        }
        this.registers.add(register);
    }

    @Transient
    int[] totalEntryExits;

    /**
     * @return Returns the division.
     */
    public Division getDivision()
    {
        return division;
    }

    /**
     * @return Returns the remoteBooths.
     */
    public List<Remote> getRemoteBooths()
    {
        return new ArrayList<Remote>(remoteBooths);
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }



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

    public Station()
    {

    }

    public Station(String name)
    {
        this.name = name;
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
        Station station = (Station) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.name, station.name);
        return builder.isEquals();
    }

    @Override
    public int compareTo(Station station)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.name, station.name);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String.format("%s", getName());
    }

    /**
     * @return Returns the routes.
     */
    public List<Route> getRoutes()
    {
        return new ArrayList<Route>(routes);
    }

    public int[] getTotalEntryExits()
    {
        if (totalEntryExits == null)
        {
        int totalEntrys = 0;
        int totalExits = 0;
        for (Remote remoteBooth : this.remoteBooths)
        {
            int[] totalEntryExits = remoteBooth.getTotalEntryExits();
            totalEntrys += totalEntryExits[0];
            totalExits += totalEntryExits[1];
        }
            totalEntryExits = new int[] { totalEntrys, totalExits };
        }
        return totalEntryExits;
    }

    public double[][] getIntervalEntryExits(DateIntervalHelper helper)
    {
        double[][] intervalEntryExits = new double[2][helper.getNumIntervals()];

        intervalEntryExits = new double[2][helper.getNumIntervals()];
            for (Remote remoteBooth : this.remoteBooths)
            {
                remoteBooth.calculateIntervalEntries(helper, intervalEntryExits);
            }
        return intervalEntryExits;
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

    /**
     * @param routes The routes to set.
     */
    public void addFareRegister(FareRegister fareRegister)
    {
        if (this.fareRegisters == null)
        {
            this.fareRegisters = new ArrayList<FareRegister>();
        }
        this.fareRegisters.add(fareRegister);
    }

    /**
     * @return Returns the entrances.
     */
    public List<FareRegister> getFareRegisters()
    {
        return this.fareRegisters;
    }

    /**
     * @return Returns the entrances.
     */
    public List<Entrance> getEntrances()
    {
        return new ArrayList<Entrance>(entrances);
    }

    /**
     * @param entrances The entrances to set.
     */
    public void addEntrance(Entrance entrance)
    {
        if (this.entrances == null)
        {
            this.entrances = new HashSet<Entrance>();
        }
        this.entrances.add(entrance);
    }

    /**
     * @param entrances The entrances to set.
     */
    public void addRemoteBooth(Remote remoteBooth)
    {
        if (this.remoteBooths == null)
        {
            this.remoteBooths = new HashSet<Remote>();
        }
        this.remoteBooths.add(remoteBooth);
    }

    /**
     * @return Returns the line.
     */
    public Line getLine()
    {
        return line;
    }

    /**
     * @param entrances The line to add
     */
    public void setLine(Line line)
    {
        this.line = line;
    }

    /**
     * @param division The division to add
     */
    public void setDivision(Division division)
    {
        this.division = division;
    }

    public String getRouteString()
    {
        StringBuilder routeString = new StringBuilder();
        if (routes != null)
        {
            for (Route route : routes)
            {
                routeString.append(route.getName());
            }
        }
        return routeString.toString();
    }

    public boolean hasRemoteBooth()
    {
        return (this.remoteBooths != null && !this.remoteBooths.isEmpty());
    }

    public boolean hasEntrance()
    {
        return (this.entrances != null && !this.entrances.isEmpty());
    }

    public boolean hasRoute(Route route)
    {
        return (this.routes != null && this.routes.contains(route));
    }

    private String getReferenceString()
    {
        StringBuilder referernceString = new StringBuilder();
        if (this.remoteBooths != null && !this.remoteBooths.isEmpty())
        {
            referernceString.append(this.remoteBooths.iterator().next().toString());
            // Set<Route> routeSet = new HashSet<Route>();
            // for (RemoteBooth remoteBooth : this.remoteBooths)
            // {
            // routeSet.addAll(remoteBooth.getRoutes());
            // }
            // for (Route route : routeSet)
            // {
            // referernceString.append(route.getName());
            // }
            // referernceString.append("/");
        }
        if (this.entrances != null && !this.entrances.isEmpty())
        {
            referernceString.append(this.entrances.iterator().next().toString());
        }
        // referernceString.replace(referernceString.length() - 1,
        // referernceString.length(), ")");
        return referernceString.toString();
    }

    public List<Location> getGeoCoordinates()
    {
        List<Location> geoCoordinateList = new ArrayList<Location>();
        for (Entrance entrance : this.entrances)
        {
            Location location = new Location();
            location.setLat(entrance.getLatitude());
            location.setLng(entrance.getLongitude());
            geoCoordinateList.add(location);
        }

        return geoCoordinateList;
    }

    public Map<FareType, Integer> getFareTypeTotalsMap()
    {
        if (fareTypeTotalMap == null)
        {
            fareTypeTotalMap = new HashMap<FareType, Integer>();

        for (FareRegister register : fareRegisters)
        {
            Integer fareTypeTotal = fareTypeTotalMap.get(register.getFareType());
            int newValue = register.getValue();
            if (fareTypeTotal != null)
            {
                newValue += fareTypeTotal.intValue();
            }
            fareTypeTotalMap.put(register.getFareType(), newValue);
        }
        }

        return fareTypeTotalMap;
    }

    public int getFareTotal()
    {
        int total = 0;
        for (FareRegister register : fareRegisters)
        {
            total += register.getValue();
        }

        return total;
    }
}
