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
 * SubSystem: com.timothyimhof.mta.load
 * FileName: EnterExitLoader.java
 *************************************************************************/
package com.timothyimhof.mtaload.turnstile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.timothyimhof.model.Borough;
import com.timothyimhof.model.Division;
import com.timothyimhof.model.Entrance;
import com.timothyimhof.model.EntranceKey;
import com.timothyimhof.model.Line;
import com.timothyimhof.model.Remote;
import com.timothyimhof.model.Route;
import com.timothyimhof.model.Station;
import com.timothyimhof.model.StationAlternateName;
import com.timothyimhof.model.Turnstile;
import com.timothyimhof.model.TurnstileKey;
import com.timothyimhof.model.Week;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 * 
 * @author timothyi
 * @since Version 1.0, Nov 29, 2011
 */
public class MTALoadCache
{

    private Map<String, Integer> boroughKeyIdCache = new HashMap<String, Integer>();
    private Map<TurnstileKey, Integer> turnstileKeyIdCache = new HashMap<TurnstileKey, Integer>();
    private Map<String, Integer> remoteBoothKeyIdCache = new HashMap<String, Integer>();
    private Map<String, Integer> stationNameIdMap = new HashMap<String, Integer>();
    private Map<String, Integer> divisionNameIdMap = new HashMap<String, Integer>();
    private Map<String, Integer> lineNameIdMap = new HashMap<String, Integer>();
    private Map<String, Integer> boroughNameIdMap = new HashMap<String, Integer>();
    private Map<String, Integer> routeNameIdMap = new HashMap<String, Integer>();
    private Map<EntranceKey, Integer> entranceKeyIdMap = new HashMap<EntranceKey, Integer>();
    private Map<String, String> alternateStationNameMap = new HashMap<String, String>();
    private Map<String, Integer> yymmddWeekIdMap = new HashMap<String, Integer>();
    public static MTALoadCache mtaLoadCache;

    public static MTALoadCache getInstance()
    {
        if (mtaLoadCache == null)
        {
            mtaLoadCache = new MTALoadCache();
        }
        return mtaLoadCache;
    }

    private MTALoadCache()
    {
        initCache();
    }

    private void initCache()
    {
        initBoroughCache();
        initRemoteBoothCache();
        initStationAlternateNamesCache();
    }

    private void initBoroughCache()
    {
        OrmService ormService = new OrmService();
        Collection<Borough> boroughList = ormService.getAll(Borough.class);
        for (Borough borough : boroughList)
        {
            String boroughKey = borough.getName();
            this.boroughKeyIdCache.put(boroughKey, borough.getId());
        }
    }

    private void initRemoteBoothCache()
    {
        OrmService ormService = new OrmService();
        Collection<Remote> remoteBoothStationList = ormService.getAll(Remote.class);
        for (Remote remoteBoothStation : remoteBoothStationList)
        {
            this.remoteBoothKeyIdCache.put(remoteBoothStation.getRemote(), remoteBoothStation.getId());
        }
    }

    private void initStationAlternateNamesCache()
    {
        OrmService ormService = new OrmService();
        Collection<StationAlternateName> stationAlternateNameList = ormService.getAll(StationAlternateName.class);
        for (StationAlternateName stationAlternateName : stationAlternateNameList)
        {
            this.alternateStationNameMap.put(stationAlternateName.getName(), stationAlternateName.getName());
            for (String alternateName : stationAlternateName.getAlternateNames())
            {
                this.alternateStationNameMap.put(alternateName, stationAlternateName.getName());
            }
        }
    }

    public Turnstile getTurnstile(OrmService ormService, String remote, String booth, String scpAddress)
    {
        Turnstile turnstile = null;
        TurnstileKey turnstileKey = new TurnstileKey(remote, booth, scpAddress);

        Integer turnstileId = turnstileKeyIdCache.get(turnstileKey);
        if (turnstileId == null)
        {
            turnstile = new Turnstile();
            turnstile.setScpAddress(scpAddress);
            ormService.persist(turnstile);

            turnstileKeyIdCache.put(turnstileKey, turnstile.getId());
        }
        else
        {
            turnstile = ormService.findById(Turnstile.class, turnstileId);
        }
        return turnstile;
    }

    public Remote getRemote(OrmService ormService, String remote, String booth)
    {
        Remote remoteBooth = null;
        Integer remoteBoothStationId = remoteBoothKeyIdCache.get(remote);
        if (remoteBoothStationId == null)
        {
            remoteBooth = new Remote(remote, booth);
            ormService.getEntityManager().persist(remoteBooth);
            remoteBoothKeyIdCache.put(remote, remoteBooth.getId());
        }
        else
        {
            remoteBooth = ormService.findById(Remote.class, remoteBoothStationId);
        }
        return remoteBooth;
    }

    public Station getStation(OrmService ormService, String stationName, String lineName, String divisionName)
    {
        Station station = null;

        String stationKey = getStationKey(ormService, stationName, lineName, divisionName);
        Integer stationId = stationNameIdMap.get(stationKey);
        if (stationId == null)
        {
            station = new Station(stationName);
            ormService.getEntityManager().persist(station);
            stationNameIdMap.put(stationKey, station.getId());
        }
        else
        {
            station = ormService.findById(Station.class, stationId);
        }
        return station;
    }

    public String getStationKey(OrmService ormService, String stationName, String lineName, String divisionName)
    {
        String stationLineDivisionKey = String.format("%s, %s, %s", stationName, divisionName, lineName);

        String stationKeyForStationLineDivision = alternateStationNameMap.get(stationLineDivisionKey);
        if (alternateStationNameMap.get(stationKeyForStationLineDivision) == null)
        {
            alternateStationNameMap.put(stationLineDivisionKey, stationLineDivisionKey);
            stationKeyForStationLineDivision = stationLineDivisionKey;
        }
        String stationKey = String.format("%s, , ", stationName);
        if (alternateStationNameMap.get(stationKey) == null)
        {
            alternateStationNameMap.put(stationKey, stationLineDivisionKey);
        }
        String stationDivisionKey = String.format("%s, %s, ", stationName, divisionName);
        if (alternateStationNameMap.get(stationDivisionKey) == null)
        {
            alternateStationNameMap.put(stationDivisionKey, stationLineDivisionKey);
        }

        StationAlternateName stationAlternateName = new StationAlternateName(stationLineDivisionKey, new String[] { stationDivisionKey, stationKey });
        ormService.persist(stationAlternateName);

        return stationKeyForStationLineDivision;
    }

    public Division getDivision(OrmService ormService, String name)
    {
        Division division = null;
        Integer divisionId = divisionNameIdMap.get(name);
        if (divisionId == null)
        {
            division = new Division(name);
            ormService.getEntityManager().persist(division);
            divisionNameIdMap.put(name, division.getId());
        }
        else
        {
            division = ormService.findById(Division.class, divisionId);
        }
        return division;
    }

    public Line getLine(OrmService ormService, String name)
    {
        Line line = null;
        Integer lineId = lineNameIdMap.get(name);
        if (lineId == null)
        {
            line = new Line(name);
            ormService.getEntityManager().persist(line);
            lineNameIdMap.put(name, line.getId());
        }
        else
        {
            line = ormService.findById(Line.class, lineId);
        }
        return line;
    }

    public Week getWeek(OrmService ormService, String yymmdd)
    {
        Week week = null;
        Integer weekId = yymmddWeekIdMap.get(yymmdd);
        if (weekId == null)
        {
            week = new Week(yymmdd);
            ormService.getEntityManager().persist(week);
            yymmddWeekIdMap.put(yymmdd, week.getId());
        }
        else
        {
            week = ormService.findById(Week.class, weekId);
        }
        return week;
    }

    public Borough getBorough(OrmService ormService, String name)
    {
        Borough borough = null;
        Integer boroughId = boroughNameIdMap.get(name);
        if (boroughId == null)
        {
            borough = new Borough(name);
            ormService.getEntityManager().persist(borough);
            divisionNameIdMap.put(name, borough.getId());
        }
        else
        {
            borough = ormService.findById(Borough.class, boroughId);
        }
        return borough;
    }

    public Entrance getEntrance(OrmService ormService, String stationName, String lineName, String divisionName, String latitude, String longitude)
    {
        Entrance entrance = null;
        Line line = getLine(ormService, lineName);
        Division division = getDivision(ormService, divisionName);

        Station station = getStation(ormService, stationName, lineName, divisionName);

        EntranceKey entranceKey = new EntranceKey(station, line, division, latitude, longitude);
        Integer entranceId = entranceKeyIdMap.get(entranceKey);
        if (entranceId == null)
        {
            entrance = new Entrance(station, line, division);
            ormService.getEntityManager().persist(entrance);
            entranceKeyIdMap.put(entranceKey, entrance.getId());
        }
        else
        {
            entrance = ormService.findById(Entrance.class, entranceId);
        }
        station.addEntrance(entrance);
        station.setDivision(division);
        return entrance;
    }

    public Route getRoute(OrmService ormService, String name)
    {
        Route route = null;
        Integer routeId = routeNameIdMap.get(name);
        if (routeId == null)
        {
            route = new Route(name);
            ormService.getEntityManager().persist(route);
            routeNameIdMap.put(name, route.getId());
        }
        else
        {
            route = ormService.findById(Route.class, routeId);
        }
        return route;
    }
}
