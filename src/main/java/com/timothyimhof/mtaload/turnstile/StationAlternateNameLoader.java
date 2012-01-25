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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.timothyimhof.model.StationAlternateName;
import com.timothyimhof.mtaload.AbstractFileLoader;
import com.timothyimhof.persistence.OrmService;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
public class StationAlternateNameLoader extends AbstractFileLoader
{
    public static void main(String[] args)
    {
        StationAlternateNameLoader loader = new StationAlternateNameLoader();
        try
        {
            loader.loadFiles();
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public StationAlternateNameLoader()
    {
        loadProperties();
    }

    @Override
    public void loadFile(File file, OrmService ormService) throws FileNotFoundException, ParseException
    {
        List<String[]> alternatesList = new ArrayList<String[]>();
        alternatesList.add(new String[] { "1 AV, BMT, CANARSIE", "1 AVE, BMT, " });
        alternatesList.add(new String[] { "103 ST-CORONA PLAZA, IRT, FLUSHING", "103 ST-CORONA, IRT, " });
        alternatesList.add(new String[] { "104 ST, BMT, JAMAICA", "104 STREET, BMT, " });
        alternatesList.add(new String[] { "116 ST-COLUMBIA UNIVERSITY, IRT, 7TH AVE-BWAY", "116 ST-COLUMBIA, IRT, " });
        alternatesList.add(new String[] { "135 ST, IRT, LENOX", "135 ST, IRT, " });
        alternatesList.add(new String[] { "137 ST-CITY COLLEGE, IRT, 7TH AVE-BWAY", "137 ST-CITY COL, IRT, " });
        alternatesList.add(new String[] { "138 ST-GRAND CONCOURSE, IRT, JEROME", "138 ST, IRT, " });
        alternatesList.add(new String[] { "149 ST-GRAND CONCOURSE, IRT, JEROME", "149 ST-GR CONC, IRT, ", "149 ST - 3 AVE, IRT, " });
        alternatesList.add(new String[] { "15 ST-PROSPECT PARK, IND, PROSPECT PARK", "15 ST-PROSPECT, IND, " });
        alternatesList.add(new String[] { "161 ST-YANKEE STADIUM, IRT, JEROME", "161 ST-YANKEE, IRT, " });
        alternatesList.add(new String[] { "163 ST-AMSTERDAM AV, IND, 8TH AVENUE", "163 ST-AMSTERDM, IND, " });
        alternatesList.add(new String[] { "168 ST, IRT, 7TH AVE-BWAY", "168 ST-BROADWAY, IRT, " });
        alternatesList.add(new String[] { "174-175 STS, IND, CONCOURSE", "174-175 ST, IND, " });
        alternatesList.add(new String[] { "182-183 STS, IND, CONCOURSE", "182-183 ST, IND, " });
        alternatesList.add(new String[] { "207 ST, IRT, 7TH AVE-BWAY", "207 STREET, IRT, " });
        alternatesList.add(new String[] { "21 ST, IND, CROSSTOWN", "21 ST, IND, " });
        alternatesList.add(new String[] { "215 ST, IRT, 7TH AVE-BWAY", "215 STREET, IRT, " });
        alternatesList.add(new String[] { "231 ST, IRT, 7TH AVE-BWAY", "231 STREET, IRT, " });
        alternatesList.add(new String[] { "3 AV-149 ST, IRT, WHITE PLAINS", "149 ST - 3 AVE, IRT, " });
        alternatesList.add(new String[] { "30 AV, BMT, ASTORIA", "GRAND-30 AV, BMT, " });
        alternatesList.add(new String[] { "33 ST-RAWSON ST, IRT, FLUSHING", "33 ST/RAWSON ST, IRT, " });
        alternatesList.add(new String[] { "34 ST-PENN STATION, IND, 8TH AVENUE", "34 ST-PENN STA, IND, " });
        alternatesList.add(new String[] { "34 ST-PENN STATION, IRT, 7TH AVE-BWAY", "34 ST-PENN STA, IRT, " });
        alternatesList.add(new String[] { "34 ST-HERALD SQ, IND, 6TH AVENUE", "34 STREET, IND, " });
        alternatesList.add(new String[] { "34 ST-HERALD SQ, BMT, BROADWAY", "34 STREET, BMT, " });
        alternatesList.add(new String[] { "36 AV, BMT, ASTORIA", "WASHINGTON-36 A, BMT, " });
        alternatesList.add(new String[] { "39 AV, BMT, ASTORIA", "BEEBE-39 AV, BMT, " });
        alternatesList.add(new String[] { "4 AV-9 ST, IND, PROSPECT PARK", "4 AV, IND, " });
        alternatesList.add(new String[] { "42 ST-PORT AUTHORITY BUS TERMINAL, IND, 8TH AVENUE", "42 STREET, IND, " });
        alternatesList.add(new String[] { "TIMES SQ-42 ST, IRT, 7TH AVE-BWAY", "42 ST -TIMES SQ, IRT, " });
        alternatesList.add(new String[] { "47-50 STS-ROCKEFELLER CTR, IND, 6TH AVENUE", "47-50 ST-ROCK, IND, " });
        alternatesList.add(new String[] { "49 ST, BMT, BROADWAY", "49 ST-7 AV, BMT, " });
        alternatesList.add(new String[] { "51 ST, IRT, LEXINGTON", "51 STREET, IRT, " });
        alternatesList.add(new String[] { "52 ST, IRT, FLUSHING", "52 ST-LINCOLN, IRT, " });
        alternatesList.add(new String[] { "59 ST-COLUMBUS CIRCLE, IND, 8TH AVENUE", "59 ST-COLUMBUS, IND, " });
        alternatesList.add(new String[] { "66 ST-LINCOLN CENTER, IRT, 7TH AVE-BWAY", "66 ST/LINCOLN, IRT, " });
        alternatesList.add(new String[] { "68 ST-HUNTER COLLEGE, IRT, LEXINGTON", "68ST-HUNTER COL, IRT, " });
        alternatesList.add(new String[] { "69 ST, IRT, FLUSHING", "69 ST-FISK AV, IRT, " });
        alternatesList.add(new String[] { "7 AV, IND, PROSPECT PARK", "7 AV-PARK SLOPE, IND, " });
        alternatesList.add(new String[] { "72 ST, IRT, 7TH AVE-BWAY", "72 ST, IRT, " });
        alternatesList.add(new String[] { "74 ST-BROADWAY, IRT, FLUSHING", "74 ST-BROADWAY, IND, " });
        alternatesList.add(new String[] { "75 ST-ELDERTS LN, BMT, JAMAICA", "ELDERTS LANE, BMT, " });
        alternatesList.add(new String[] { "8 ST-NYU, BMT, BROADWAY", "8 ST-BROADWAY, BMT, " });
        alternatesList.add(new String[] { "80 ST, IND, LIBERTY AVENUE", "HUDSON-80 ST, IND, " });
        alternatesList.add(new String[] { "82 ST-JACKSON HTS, IRT, FLUSHING", "82 ST-JACKSON H, IRT, " });
        alternatesList.add(new String[] { "85 ST-FOREST PKWY, BMT, JAMAICA", "FOREST PKWY, BMT, " });
        alternatesList.add(new String[] { "88 ST, IND, LIBERTY AVENUE", "BOYD-88 ST, IND, " });
        alternatesList.add(new String[] { "90 ST-ELMHURST AV, IRT, FLUSHING", "90 ST-ELMHURST, IRT, " });
        alternatesList.add(new String[] { "95 ST, BMT, 4TH AVENUE", "BAY RIDGE-95 ST, BMT, " });
        alternatesList.add(new String[] { "AQUEDUCT RACETRACK, IND, ROCKAWAY", "AQUEDUCT, IND, " });
        alternatesList.add(new String[] { "AQUEDUCT-NORTH CONDUIT AV, IND, ROCKAWAY", "AQUEDUCT-N CNDT, IND, " });
        alternatesList.add(new String[] { "ASTOR PL, IRT, LEXINGTON", "ASTOR PLACE, IRT, " });
        alternatesList.add(new String[] { "ASTORIA-DITMARS BLVD, BMT, ASTORIA", "DITMARS BL-31 S, BMT, " });
        alternatesList.add(new String[] { "ASTORIA BLVD, BMT, ASTORIA", "HOYT ST-ASTORIA, BMT, " });
        alternatesList.add(new String[] { "ATLANTIC AV, IRT, EASTERN PARKWAY", "ATLANTIC AVE, IRT, " });
        alternatesList.add(new String[] { "ATLANTIC AV-PACIFIC ST, BMT, 4TH AVENUE", "PACIFIC ST, BMT, " });
        alternatesList.add(new String[] { "AVENUE H, BMT, BRIGHTON", "AVE H, BMT, " });
        alternatesList.add(new String[] { "AVENUE I, IND, CULVER", "AV I, IND, " });
        alternatesList.add(new String[] { "AVENUE J, BMT, BRIGHTON", "AV J, BMT, " });
        alternatesList.add(new String[] { "AVENUE M, BMT, BRIGHTON", "AV M, BMT, " });
        alternatesList.add(new String[] { "AVENUE N, IND, CULVER", "AV N, IND, " });
        alternatesList.add(new String[] { "AVENUE P, IND, CULVER", "AVE P, IND, " });
        alternatesList.add(new String[] { "AVENUE U, BMT, BRIGHTON", "AV U, BMT, " });
        alternatesList.add(new String[] { "AVENUE X, IND, CULVER", "AVE X, IND, " });
        alternatesList.add(new String[] { "B'WAY-LAFAYETTE ST, IND, 6TH AVENUE", "BROADWAY/LAFAY, IND, " });
        alternatesList.add(new String[] { "BEACH 105 ST, IND, ROCKAWAY", "SEASIDE-B 105, IND, " });
        alternatesList.add(new String[] { "BEACH 25 ST, IND, FAR ROCKAWAY", "WAVECREST-B 25, IND, " });
        alternatesList.add(new String[] { "BEACH 36 ST, IND, FAR ROCKAWAY", "EDGMERE, IND, " });
        alternatesList.add(new String[] { "BEACH 44 ST, IND, FAR ROCKAWAY", "FRANK AV, IND, " });
        alternatesList.add(new String[] { "BEACH 60 ST, IND, FAR ROCKAWAY", "STRAITON AV, IND, " });
        alternatesList.add(new String[] { "BEACH 67 ST, IND, FAR ROCKAWAY", "GASTON AV, IND, " });
        alternatesList.add(new String[] { "BEACH 90 ST, IND, ROCKAWAY", "HOLLAND-B 90 ST, IND, " });
        alternatesList.add(new String[] { "BEACH 98 ST, IND, ROCKAWAY", "PLAYLAND-B 98, IND, " });
        alternatesList.add(new String[] { "BEDFORD PK BLVD-LEHMAN COLLEGE, IRT, JEROME", "BEDFORD PARK BL, IRT, " });
        alternatesList.add(new String[] { "BEDFORD-NOSTRAND AVS, IND, CROSSTOWN", "BEDFORD/NOSTREN, IND, " });
        alternatesList.add(new String[] { "BLEECKER ST, IRT, LEXINGTON, , ", "BLEEKER ST, IRT, " });
        alternatesList.add(new String[] { "BOROUGH HALL, IRT, CLARK STREET", "BOROUGH HALL/CT, IRT, " });
        alternatesList.add(new String[] { "BRIARWOOD-VAN WYCK BLVD, IND, QUEENS", "VAN WYCK BLVD, IND, " });
        alternatesList.add(new String[] { "BROADWAY JUNCTION, IND, FULTON STREET", "BROADWAY-ENY, IND, " });
        alternatesList.add(new String[] { "BROOKLYN BRIDGE-CITY HALL, IRT, LEXINGTON", "BROOKLYN BRIDGE, IRT, " });
        alternatesList.add(new String[] { "BUSHWICK AV-ABERDEEN ST, BMT, CANARSIE", "BUSHWICK AV, BMT, " });
        alternatesList.add(new String[] { "CATHEDRAL PKWY (110 ST), IND, 8TH AVENUE", "110 ST-CATHEDRL, IND, " });
        alternatesList.add(new String[] { "CHAMBERS ST, BMT, NASSAU LOOP", "CHAMBERS STREET, BMT, " });
        alternatesList.add(new String[] { "CHRISTOPHER ST-SHERIDAN SQ, IRT, 7TH AVE-BWAY", "CHRISTOPHER ST, IRT, " });
        alternatesList.add(new String[] { "CITY HALL, BMT, BROADWAY", "MURRAY ST-B'WAY, BMT, " });
        alternatesList.add(new String[] { "CLINTON-WASHINGTON AVS, IND, FULTON STREET", "CLINTON-WASH AV, IND, " });
        alternatesList.add(new String[] { "CONEY ISLAND-STILLWELL AV, BMT, CONEY ISLAND", "STILLWELL AV, BMT, " });
        alternatesList.add(new String[] { "COURT SQ-23 ST, IND, QUEENS", "23 ST-ELY AV, IND, " });
        alternatesList.add(new String[] { "CROWN HTS-UTICA AV, IRT, EASTERN PARKWAY", "UTICA AVE, IRT, " });
        alternatesList.add(new String[] { "CYPRESS HILLS, BMT, JAMAICA", "CYPRESS  HILLS, BMT, " });
        alternatesList.add(new String[] { "DELANCEY ST-ESSEX ST, IND, 6TH AVENUE", "DELANCEY ST, IND, " });
        alternatesList.add(new String[] { "DELANCEY ST-ESSEX ST, BMT, NASSAU LOOP", "DELANCEY ST, BMT, " });
        alternatesList.add(new String[] { "DYCKMAN ST, IND, 8TH AVENUE", "DYCKMAN-200 ST, IND, " });
        alternatesList.add(new String[] { "E 143 ST-ST MARY'S ST, IRT, PELHAM", "E 143 ST, IRT, " });
        alternatesList.add(new String[] { "EASTCHESTER-DYRE AV, IRT, DYRE AVENUE", "DYRE AV, IRT, " });
        alternatesList.add(new String[] { "EASTERN PKWY-BROOKLYN MUSEUM, IRT, EASTERN PARKWAY", "EASTERN PKWY, IRT, " });
        alternatesList.add(new String[] { "ELDER AV, IRT, PELHAM", "ELDER AVE, IRT, " });
        alternatesList.add(new String[] { "FAR ROCKAWAY-MOTT AV, IND, FAR ROCKAWAY", "FAR ROCKAWAY, IND, " });
        alternatesList
.add(new String[] { "FLATBUSH AV-BROOKLYN COLLEGE, IRT, NOSTRAND", "FLATBUSH AV, IRT, " });
        alternatesList.add(new String[] { "FLUSHING-MAIN ST, IRT, FLUSHING", "MAIN ST, IRT, " });
        alternatesList.add(new String[] { "FOREST HILLS-71 AV, IND, QUEENS", "71 ST-CONTINENT, IND, " });
        alternatesList.add(new String[] { "FORT HAMILTON PKWY, BMT, WEST END", "FT HAMILTON PKY, BMT, " });
        alternatesList.add(new String[] { "FULTON ST, IRT, CLARK STREET", "FULTON STREET, IRT, " });
        alternatesList.add(new String[] { "GRAND ARMY PLAZA, IRT, EASTERN PARKWAY", "GRAND ARMY PLAZ, IRT, " });
        alternatesList.add(new String[] { "GRAND AV-NEWTOWN, IND, QUEENS", "GRAND AV-NEWTON, IND, " });
        alternatesList.add(new String[] { "GRAND CENTRAL-42 ST, IRT, LEXINGTON", "GRAND CENTRAL-42 ST, IRT, SHUTTLE",
                "GRAND CENTRAL-42 ST, IRT, FLUSHING", "42 ST-GRD CNTRL, IRT, " });
        alternatesList.add(new String[] { "HARLEM-148 ST, IRT, LENOX", "148 ST-LENOX, IRT, " });
        alternatesList.add(new String[] { "HOWARD BEACH-JFK AIRPORT, IND, ROCKAWAY", "HOWARD BCH-JFK, IND, " });
        alternatesList.add(new String[] { "HOYT-SCHERMERHORN, IND, FULTON STREET", "HOYT/SCHERMER, IND, " });
        alternatesList.add(new String[] { "HUNTERS POINT AV, IRT, FLUSHING", "HUNTERS PT AV, IRT, " });
        alternatesList.add(new String[] { "INTERVALE AV, IRT, WHITE PLAINS", "INTERVALE-163, IRT, " });
        alternatesList.add(new String[] { "INWOOD-207 ST, IND, 8TH AVENUE", "207 ST/WASH HTS, IND, " });
        alternatesList.add(new String[] { "JACKSON HEIGHTS-ROOSEVELT AV, IND, QUEENS", "ROOSEVELT AVE, IND, " });
        alternatesList.add(new String[] { "JAMAICA CENTER-PARSONS/ARCHER, IND, ARCHER AVENUE", "JAMAICA CENTER, IND, " });
        alternatesList.add(new String[] { "JAMAICA-179 ST, IND, QUEENS", "179 STREET, IND, " });
        alternatesList.add(new String[] { "JAMAICA-VAN WYCK, IND, ARCHER AVENUE", "JAMAICA-VAN WYC, IND, " });
        alternatesList.add(new String[] { "JAY ST-METROTECH, BMT, BROADWAY", "LAWRENCE ST, BMT, " });
        alternatesList.add(new String[] { "KEW GARDENS-UNION TPKE, IND, QUEENS", "UNION TPK-KEW G, IND, " });
        alternatesList.add(new String[] { "KINGSTON-THROOP AVS, IND, FULTON STREET", "KINGSTON-THROOP, IND, " });
        alternatesList.add(new String[] { "KNICKERBOCKER AV, BMT, MYRTLE AVENUE", "KNICKERBOCKER, BMT, " });
        // alternatesList.add(new String[] {
        // "LEXINGTON AV/59 ST, BMT, BROADWAY", "LEXINGTON AVE, IRT, ",
        // "59 STREET,  IRT, " });
        alternatesList.add(new String[] { "MARBLE HILL-225 ST, IRT, 7TH AVE-BWAY", "225 STREET, IRT, " });
        alternatesList.add(new String[] { "METS-WILLETS POINT, IRT, FLUSHING", "WILLETS PT-SHEA, IRT, " });
        alternatesList.add(new String[] { "MOSHOLU PKWY, IRT, JEROME", "MOSHOLU PKY, IRT, " });
        alternatesList.add(new String[] { "MYRTLE-WILLOUGHBY AVS, IND, CROSSTOWN", "MYRTLE-WILLOUGH, IND, " });
        alternatesList.add(new String[] { "NORWOOD-205 ST, IND, CONCOURSE", "205 ST, IND, " });
        alternatesList.add(new String[] { "OZONE PARK-LEFFERTS BLVD, IND, LIBERTY AVENUE", "LEFFERTS BLVD, IND, " });
        alternatesList.add(new String[] { "PARKCHESTER, IRT, PELHAM", "E 177 ST-PARKCH, IRT, " });
        alternatesList.add(new String[] { "PELHAM PKWY, IRT, WHITE PLAINS", "PELHAM PKY, IRT, " });
        alternatesList.add(new String[] { "PRINCE ST, BMT, BROADWAY", "PRINCE ST-B'WAY, BMT, " });
        alternatesList.add(new String[] { "QUEENSBORO PLAZA, IRT, FLUSHING", "QUEENSBORO PLZ, IRT, " });
        alternatesList.add(new String[] { "ROCKAWAY PARK-BEACH 116 ST, IND, ROCKAWAY", "ROCKAWAY PK 116, IND, " });
        alternatesList.add(new String[] { "ROOSEVELT ISLAND, IND, 63RD STREET", "ROOSEVELT IS, IND, " });
        alternatesList.add(new String[] { "SARATOGA AV, IRT, NEW LOTS", "SARATOGA AVE, IRT, " });
        alternatesList.add(new String[] { "SMITH-9 STS, IND, PROSPECT PARK", "SMITH/9 ST, IND, " });
        alternatesList.add(new String[] { "SUTPHIN BLVD-ARCHER AV-JFK AIRPORT, IND, ARCHER AVENUE", "JFK JAMAICA CT1, IND, " });
        alternatesList.add(new String[] { "TIMES SQ-42 ST, BMT, BROADWAY", "42 ST-TIMES SQ, BMT, " });
        alternatesList.add(new String[] { "TIMES SQ-42 ST, IRT, 7TH AVE-BWAY", "42 ST - TIMES S, IRT, " });
        alternatesList.add(new String[] { "VAN CORTLANDT PARK-242 ST, IRT, 7TH AVE-BWAY", "242 ST, IRT, " });
        alternatesList.add(new String[] { "VAN SICLEN AV, IRT, NEW LOTS", "VAN SICLEN AVE, IRT, " });
        alternatesList.add(new String[] { "VERNON BLVD-JACKSON AV, IRT, FLUSHING", "VERNON/JACKSON, IRT, " });
        alternatesList.add(new String[] { "WAKEFIELD-241 ST, IRT, WHITE PLAINS", "241 ST, IRT, " });
        alternatesList.add(new String[] { "WEST 8 ST-NY AQUARIUM, BMT, CONEY ISLAND", "W 8 ST-AQUARIUM, BMT, " });
        alternatesList.add(new String[] { "WEST FARMS SQ-E TREMONT AV, IRT, WHITE PLAINS", "E TREMONT AV, IRT, " });
        alternatesList.add(new String[] { "WESTCHESTER SQ-EAST TREMONT AV, IRT, PELHAM", "WESTCHESTER SQ, IRT, " });
        alternatesList.add(new String[] { "WHITEHALL ST-SOUTH FERRY, BMT, BROADWAY", "SOUTH FERRY, BMT, ", "WHITEHALL, BMT, " });
        ;
        alternatesList.add(new String[] { "WOODLAWN, IRT, JEROME", "WOODLAWN ROAD, IRT, " });
        alternatesList.add(new String[] { "WOODSIDE-61 ST, IRT, FLUSHING", "61 ST/WOODSIDE, IRT, " });

        alternatesList.add(new String[] { "135 ST, IND, 8TH AVENUE", "135TH STREET, IND, " });
        alternatesList.add(new String[] { "161 ST-YANKEE STADIUM, IND, CONCOURSE", "161 ST-YANKEE, IND, " });
        alternatesList.add(new String[] { "168 ST, IND, 8TH AVENUE", "168 ST-BROADWAY, IND, " });
        alternatesList.add(new String[] { "23 ST, IND, 8TH AVENUE", "23RD STREET, IND, " });
        alternatesList.add(new String[] { "42 ST-BRYANT PK, IND, 6TH AVENUE", "42 ST, IND, " });
        alternatesList.add(new String[] { "5 AV/53 ST, IND, QUEENS", "FIFTH AVE, IND, " });
        alternatesList.add(new String[] { "81 ST-MUSEUM OF NATURAL HISTORY, IND, 8TH AVENUE", "81 ST-MUSEUM, IND, " });
        alternatesList.add(new String[] { "AVENUE U, IND, CULVER", "AVE U, IND, " });
        alternatesList.add(new String[] { "BEDFORD PARK BLVD, IND, CONCOURSE", "BEDFORD PARK BL, IND, " });
        alternatesList.add(new String[] { "FORT HAMILTON PKWY, IND, PROSPECT PARK", "FT HAMILTON PKY, IND, " });
        alternatesList.add(new String[] { "FULTON ST, IND, CROSSTOWN", "FULTON STREET, IND, " });
        alternatesList.add(new String[] { "JAY ST-METROTECH, IND, FULTON STREET", "JAY ST-BOROUGH, IND, " });

        alternatesList.add(new String[] { "23 ST, BMT, BROADWAY", "23 ST-5 AVE, BMT, " });
        alternatesList.add(new String[] { "5TH AV/59 ST, BMT, BROADWAY", "5 AV, BMT, " });
        alternatesList.add(new String[] { "BAY RIDGE AV, BMT, 4TH AVENUE", "BAY RIDGE AVE, BMT, " });
        alternatesList.add(new String[] { "BEVERLY RD, BMT, BRIGHTON", "BEVERLEY RD, BMT, " });
        alternatesList.add(new String[] { "FULTON ST, BMT, NASSAU LOOP", "FULTON STREET, BMT, " });
        alternatesList.add(new String[] { "KINGS HWY, BMT, SEA BEACH", "KINGS HIGHWAY, BMT, " });
        alternatesList.add(new String[] { "MIDDLE VILLAGE-METROPOLITAN AV, BMT, MYRTLE AVENUE", "METROPOLITAN AV, BMT, " });

        alternatesList.add(new String[] { "LEXINGTON AV/53 ST, IND, QUEENS", "LEXINGTON-3RD, IND, " });
        alternatesList.add(new String[] { "BLEECKER ST, IRT, LEXINGTON", "BLEEKER ST, IRT, " });
        alternatesList.add(new String[] { "5 AV, IRT, FLUSHING", "FIFTH AVE, IRT, " });
        alternatesList.add(new String[] { "BLEECKER ST, IRT, LEXINGTON", "BLEEKER ST, IRT, " });
        alternatesList.add(new String[] { "NEW LOTS AV, IRT, NEW LOTS", "NEW LOTS AVE, IRT, " });
        alternatesList.add(new String[] { "NOSTRAND AV, IRT, EASTERN PARKWAY", "NOSTRAND AVE, IRT, " });
        alternatesList.add(new String[] { "ROCKAWAY AV, IRT, NEW LOTS", "ROCKAWAY AVE, IRT, " });
        alternatesList.add(new String[] { "SUTTER AV-RUTLAND RD, IRT, NEW LOTS", "SUTTER AVE, IRT, " });

        alternatesList.add(new String[] { "103 ST, IRT, 7TH AVE-BWAY", "103 ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "110 ST, IRT, LEXINGTON", "110 ST-CATHEDRL, IRT, " });
        alternatesList.add(new String[] { "116 ST-COLUMBIA UNIVERSITY, IRT, 7TH AVE-BWAY", "116 ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "125 ST, IRT, 7TH AVE-BWAY", "125 ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "138 ST-GRAND CONCOURSE, IRT, JEROME", "138 ST-3 AV, IRT, " });
        alternatesList.add(new String[] { "149 ST-GRAND CONCOURSE, IRT, JEROME", "149 ST-GR CONC*, IRT, " });
        alternatesList.add(new String[] { "23 ST, IRT, 7TH AVE-BWAY", "23 ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "28 ST, IRT, 7TH AVE-BWAY", "28 ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "72 ST, IRT, 7TH AVE-BWAY", "72ND STREET, IRT, " });
        alternatesList.add(new String[] { "86 ST, IRT, 7TH AVE-BWAY", " 86 STREET, IRT, " });
        alternatesList.add(new String[] { "96 ST, IRT, 96 ST, IRT, 7TH AVE-BWAY", "96 ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "CANAL ST, IRT, 7TH AVE-BWAY", "CANAL ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "CHAMBERS ST, IRT, 7TH AVE-BWAY", "CHAMBERS STREET, IRT, " });
      
        alternatesList.add(new String[] { "WALL ST, IRT, CLARK STREET", "WALL ST, IRT, LEXINGTON" });
        alternatesList.add(new String[] { "9 AV, BMT, WEST END", "9 AVE, BMT, " });
        alternatesList.add(new String[] { "14 ST-UNION SQ, BMT, BROADWAY", "14 ST, BMT, " });
        alternatesList.add(new String[] { "18 AV, BMT, SEA BEACH", "18 AV, BMT, WEST END" });
        alternatesList.add(new String[] { "20 AV, BMT, SEA BEACH", "20 AV, BMT, WEST END" });
        alternatesList.add(new String[] { "8 AV, BMT, 8 AV, BMT, CANARSIE", "8 AV, BMT, SEA BEACH" });
        alternatesList.add(new String[] { "86 ST, BMT, 4TH AVENUE", "86 ST, BMT, SEA BEACH" });
        alternatesList.add(new String[] { "AVENUE U, BMT, BRIGHTON", "AVENUE U, BMT, SEA BEACH" });
        alternatesList.add(new String[] { "CANAL ST, BMT, BROADWAY", "CANAL ST, BMT, NASSAU LOOP" });

        alternatesList.add(new String[] { "28 ST, BMT, BROADWAY", "28 ST-BROADWAY, BMT, " });
        alternatesList.add(new String[] { "FORT HAMILTON PKWY, BMT, WEST END", "FORT HAMILTON PKWY, BMT, SEA BEACH" });

        alternatesList.add(new String[] { "DEKALB AV, BMT, 4TH AVENUE", "DEKALB AV, BMT, CANARSIE" });
        alternatesList.add(new String[] { "CLINTON-WASHINGTON AVS, IND, FULTON STREET", "CLINTON-WASHINGTON AVS, IND, CROSSTOWN" });

        alternatesList.add(new String[] { "COURT ST, BMT, BROADWAY", "BOROUGH HALL/CT, BMT, " });
        alternatesList.add(new String[] { "28 ST, BMT, BROADWAY", "28 ST-BROADWAY, BMT, " });

        alternatesList.add(new String[] { "59 ST-COLUMBUS CIRCLE, IRT, 7TH AVE-BWAY", "59 ST-COLUMBUS, IRT, " });
        alternatesList.add(new String[] { "86 ST, IRT, LEXINGTON", "86 STREET, IRT, " });
        alternatesList.add(new String[] { "COURT SQ, IRT, FLUSHING", "45 RD-COURT H S, IRT, " });
        alternatesList.add(new String[] { "59 ST, IRT, LEXINGTON", "LEXINGTON AVE, IRT, ", "59 STREET, IRT, " });
        alternatesList.add(new String[] { "PARK PLACE, IRT, CLARK STREET", "WORLD TRADE CTR, IRT, " });

        alternatesList.add(new String[] { "104 ST, IND, LIBERTY AVENUE", "OXFORD-104 ST, IND, " });
        alternatesList.add(new String[] { "81 ST-MUSEUM OF NATURAL HISTORY, IND, 8TH AVENUE", "81 ST-MUSEUM, IND, " });
        alternatesList.add(new String[] { "BAY PKWY, IND, CULVER", "22 AV-BAY PKY, IND, " });
        alternatesList.add(new String[] { "KINGS HWY, IND, CULVER", "KINGS HIGHWAY, IND, " });
        alternatesList.add(new String[] { "111 ST, IND, LIBERTY AVENUE", "GREENWOOD-111, IND, " });
        alternatesList.add(new String[] { "111 ST, IND, LIBERTY AVENUE", "GREENWOOD-111, IND, " });
        alternatesList.add(new String[] { "SMITH-9 STS, IND, PROSPECT PARK", "9 ST, IND, " });
        alternatesList.add(new String[] { "FULTON ST, IND, 8TH AVENUE", "BROADWAY-NASSAU, IND, " });
        alternatesList.add(new String[] { "HOWARD BEACH-JFK AIRPORT, IND, ROCKAWAY", "JFK HOWARD BCH, IND, " });
        alternatesList.add(new String[] { "JAMAICA CENTER-PARSONS/ARCHER, IND, ARCHER AVENUE", "JFK JAMAICA CT2, IND, " });

        alternatesList.add(new String[] { "CHAMBERS ST, BMT, NASSAU LOOP", "BROOKLYN BRIDGE, BMT, " });
        alternatesList.add(new String[] { "BAY PKWY, BMT, SEA BEACH", "BAY PKY, BMT, " });
        alternatesList.add(new String[] { "BAY PKWY, BMT, WEST END", "BAY PKY-22 AV, BMT, " });
        alternatesList.add(new String[] { "BROADWAY, BMT, ASTORIA", "BROADWAY-31 ST, BMT, " });
        alternatesList.add(new String[] { "CANARSIE-ROCKAWAY PKWY, BMT, CANARSIE", "ROCKAWAY PKY, BMT, " });
        alternatesList.add(new String[] { "DELANCEY ST-ESSEX ST, BMT, NASSAU LOOP", "ESSEX ST, BMT, " });
        // RemoteBoothLoader loader = new RemoteBoothLoader();
        // loader.loadFiles();
        //
        // List<RemoteBooth> remoteBoothList = (List<RemoteBooth>)
        // ormService.getAll(RemoteBooth.class);
        //
        // Collections.sort(remoteBoothList);
        //
        // Set<String> outputSet = new HashSet<String>();
        // for (RemoteBooth remoteBooth : remoteBoothList)
        // {
        // outputSet.add(remoteBooth.toString());
        // }

        ormService.beginTransaction();
        for (String[] alternates : alternatesList)
        {
            StationAlternateName stationAlternateName = new StationAlternateName(alternates[0], alternates);
            ormService.persist(stationAlternateName);
            //
            // System.out.format("alternatesList.add(new String[] {\"%s\"",
            // alternates[0]);
            // String[] split_0 = alternates[0].split(",");
            // for (int i = 1; i < alternates.length; i++)
            // {
            // String[] split_i = alternates[i].split(",");
            // // System.out.format(", \"%s, %s, \"", split_i[0].trim(),
            // // split_i[1].trim());
            //
            // if (split_0[2].length() == 0 &&
            // !outputSet.contains(String.format("%s, %s", split_i[0].trim(),
            // split_i[1].trim())))
            // {
            // System.out.println(alternates[i]);
            // }
            // }
            // System.out.format(" });\n");
        }

        ormService.tryCommitTransactionOrRollback();

    }


    /**
     * @return
     * @see com.timothyimhof.mta.load.AbstractFileLoader#getLoadDirectory()
     */
    @Override
    protected String getLoadDirectory()
    {
        return "entrance_load_directory";
    }

}
