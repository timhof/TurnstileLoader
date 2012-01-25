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
 * SubSystem: com.timothyimhof.mta.load.geo.util
 * FileName: GeoUtil.java
 *************************************************************************/
package com.timothyimhof.mtaload.geo.util;


/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 14, 2011
 */
public enum GeoType
{
    street_address, // indicates a precise street address.
    route, // indicates a named route (such as "US 101").
    intersection, // indicates a major intersection, usually of two major roads.
    political, // indicates a political entity. Usually, this type indicates a
               // polygon of some civil administration.
    country, // indicates the national political entity, and is typically the
             // highest order type returned by the Geocoder.
    administrative_area_level_1, // indicates a first-order civil entity below
                                 // the country level. Within the United States,
                                 // these administrative levels are states. Not
                                 // all nations exhibit these administrative
                                 // levels.
    administrative_area_level_2, // indicates a second-order civil entity below
                                 // the country level. Within the United States,
                                 // these administrative levels are counties.
                                 // Not all nations exhibit these administrative
                                 // levels.
    administrative_area_level_3, // indicates a third-order civil entity below
                                 // the country level. This type indicates a
                                 // minor civil division. Not all nations
                                 // exhibit these administrative levels.
    colloquial_area, // indicates a commonly-used alternative name for the
                     // entity.
    locality, // indicates an incorporated city or town political entity.
    sublocality, // indicates an first-order civil entity below a locality
    neighborhood, // indicates a named neighborhood
    premise, // indicates a named location, usually a building or collection of
             // buildings with a common name
    subpremise, // indicates a first-order entity below a named location,
                // usually a singular building within a collection of buildings
                // with a common name
    postal_code, // indicates a postal code as used to address postal mail
                 // within the country.
    natural_feature, // indicates a prominent natural feature.
    airport, // indicates an airport.
    park, // indicates a named park.
    point_of_interest, // indicates a named point of interest. Typically, these
                       // "POI"s are prominent local entities that don't easily
                       // fit in another category such as
                       // "Empire State Building" or "Statue of Liberty."
    post_box, // indicates a specific postal box.
    street_number, // indicates the precise street number.
    floor, // indicates the floor of a building address.
    room, // indicates the room of a building address.
}
