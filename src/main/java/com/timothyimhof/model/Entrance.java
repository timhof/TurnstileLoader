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
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.timothyimhof.mtaload.geo.json.Location;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
@Entity
@Table(name = "entrances")
public class Entrance implements Comparable<Entrance>, Serializable, IHasRoutes, IHasGeoCoordinates
{
    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToMany
    @JoinTable(name = "entrance_route", joinColumns = @JoinColumn(name = "entrance_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "ID"))
    private Set<Route> routes;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "station_id")
    private Station station;

    private boolean ada;
    private String adadNotes;
    private boolean freeCrossover;
    private String entranceType;
    private boolean exit_Only;
    private String entranceStaffing;
    private String northSouthStreet;
    private String eastWestStreet;
    private String Corner;
    private double latitude;
    private double longitude;

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

    /**
     * @return Returns the line.
     */
    public Line getLine()
    {
        return line;
    }

    /**
     * @param line The line to set.
     */
    public void setLine(Line line)
    {
        this.line = line;
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

    /**
     * @return Returns the ada.
     */
    public boolean isAda()
    {
        return ada;
    }

    /**
     * @param ada The ada to set.
     */
    public void setAda(boolean ada)
    {
        this.ada = ada;
    }

    /**
     * @return Returns the adadNotes.
     */
    public String getAdadNotes()
    {
        return adadNotes;
    }

    /**
     * @param adadNotes The adadNotes to set.
     */
    public void setAdadNotes(String adadNotes)
    {
        this.adadNotes = adadNotes;
    }

    /**
     * @return Returns the freeCrossover.
     */
    public boolean isFreeCrossover()
    {
        return freeCrossover;
    }

    /**
     * @param freeCrossover The freeCrossover to set.
     */
    public void setFreeCrossover(boolean freeCrossover)
    {
        this.freeCrossover = freeCrossover;
    }

    /**
     * @return Returns the entranceType.
     */
    public String getEntranceType()
    {
        return entranceType;
    }

    /**
     * @param entranceType The entranceType to set.
     */
    public void setEntranceType(String entranceType)
    {
        this.entranceType = entranceType;
    }

    /**
     * @return Returns the exit_Only.
     */
    public boolean isExit_Only()
    {
        return exit_Only;
    }

    /**
     * @param exit_Only The exit_Only to set.
     */
    public void setExit_Only(boolean exit_Only)
    {
        this.exit_Only = exit_Only;
    }

    /**
     * @return Returns the entranceStaffing.
     */
    public String getEntranceStaffing()
    {
        return entranceStaffing;
    }

    /**
     * @param entranceStaffing The entranceStaffing to set.
     */
    public void setEntranceStaffing(String entranceStaffing)
    {
        this.entranceStaffing = entranceStaffing;
    }

    /**
     * @return Returns the northSouthStreet.
     */
    public String getNorthSouthStreet()
    {
        return northSouthStreet;
    }

    /**
     * @param northSouthStreet The northSouthStreet to set.
     */
    public void setNorthSouthStreet(String northSouthStreet)
    {
        this.northSouthStreet = northSouthStreet;
    }

    /**
     * @return Returns the eastWestStreet.
     */
    public String getEastWestStreet()
    {
        return eastWestStreet;
    }

    /**
     * @param eastWestStreet The eastWestStreet to set.
     */
    public void setEastWestStreet(String eastWestStreet)
    {
        this.eastWestStreet = eastWestStreet;
    }

    /**
     * @return Returns the corner.
     */
    public String getCorner()
    {
        return Corner;
    }

    /**
     * @param corner The corner to set.
     */
    public void setCorner(String corner)
    {
        Corner = corner;
    }

    /**
     * @return Returns the latitude.
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * @param latitude The latitude to set.
     */
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    /**
     * @return Returns the longitude.
     */
    public double getLongitude()
    {
        return longitude;
    }

    /**
     * @param longitude The longitude to set.
     */
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
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

    public Entrance()
    {

    }

    public Entrance(Station station, Line line, Division division)
    {
        this.station = station;
        this.line = line;
        this.division = division;
    }

    private String getRouteString()
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
        Entrance entrance = (Entrance) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.station, entrance.station);
        builder.append(this.line, entrance.line);
        builder.append(this.division, entrance.division);
        builder.append(this.latitude, entrance.latitude);
        builder.append(this.longitude, entrance.longitude);
        return builder.isEquals();
    }

    @Override
    public int compareTo(Entrance entrance)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.station, entrance.station);
        builder.append(this.line, entrance.line);
        builder.append(this.division, entrance.division);
        builder.append(this.latitude, entrance.latitude);
        builder.append(this.longitude, entrance.longitude);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String
.format("%s, %s, %s", station.getName(), division.getName(), line.getName());
    }

    /**
     * @return
     * @see com.timothyimhof.model.IHasGeoCoordinates#getGeoCoordinates()
     */
    @Override
    public List<Location> getGeoCoordinates()
    {
        List<Location> geoCoordinateList = new ArrayList<Location>();
        Location location = new Location();
        location.setLat(this.latitude);
        location.setLng(this.longitude);
        geoCoordinateList.add(location);

        return geoCoordinateList;
    }

}
