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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "routes")
public class Route implements Comparable<Route>, Serializable, IHasStations, IHasEntrances, IHasRemoteBooths, IHasGeoCoordinates
{
    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "route_station", joinColumns = @JoinColumn(name = "route_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "station_id", referencedColumnName = "ID"))
    private Set<Station> stations;

    @ManyToMany(mappedBy = "routes")
    private List<Entrance> entrances;

    @ManyToMany(mappedBy = "routes")
    private List<Remote> remoteBooths;

    private String imageURL;


    /**
     * @return Returns the imageURL.
     */
    public String getImageURL()
    {
        return imageURL;
    }

    /**
     * @param imageURL The imageURL to set.
     */
    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    /**
     * @return Returns the entrances.
     */
    public List<Entrance> getEntrances()
    {
        return entrances;
    }

    /**
     * @param entrances The entrances to set.
     */
    public void addEntrance(Entrance entrance)
    {
        if (this.entrances == null)
        {
            this.entrances = new ArrayList<Entrance>();
        }
        this.entrances.add(entrance);
    }

    /**
     * @return Returns the entrances.
     */
    public List<Remote> getRemoteBooths()
    {
        return remoteBooths;
    }

    /**
     * @param entrances The entrances to set.
     */
    public void addRemoteBooth(Remote remoteBooth)
    {
        if (this.remoteBooths == null)
        {
            this.remoteBooths = new ArrayList<Remote>();
        }
        this.remoteBooths.add(remoteBooth);
    }

    /**
     * @return Returns the stations.
     */
    public List<Station> getStations()
    {
        return new ArrayList<Station>(stations);
    }

    /**
     * @param stations The station to add.
     */
    public void addStation(Station station)
    {
        if (this.stations == null)
        {
            this.stations = new HashSet<Station>();
        }
        this.stations.add(station);
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }


    public Route()
    {

    }

    public Route(String name)
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
        Route route = (Route) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.name, route.name);
        return builder.isEquals();
    }

    @Override
    public int compareTo(Route route)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.name, route.name);
        return builder.toComparison();
    }

    @Override
    public String toString()
    {
        return String.format("%s [%d]", name, id);
    }

    /**
     * @return
     * @see com.timothyimhof.model.IHasGeoCoordinates#getGeoCoordinates()
     */
    @Override
    public List<Location> getGeoCoordinates()
    {
        List<Location> geoCoordinateList = new ArrayList<Location>();
        for (Entrance entrance : entrances)
        {
            Location location = new Location();
            location.setLat(entrance.getLatitude());
            location.setLng(entrance.getLongitude());
            geoCoordinateList.add(location);
        }

        return geoCoordinateList;
    }
}
