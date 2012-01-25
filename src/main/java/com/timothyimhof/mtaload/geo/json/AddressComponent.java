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
 * SubSystem: com.timothyimhof.mta.load.geo.json
 * FileName: GeoResult.java
 *************************************************************************/
package com.timothyimhof.mtaload.geo.json;

import java.util.Set;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 14, 2011
 */
public class AddressComponent implements Comparable<AddressComponent>
    {

    private String longName;
    private String shortName;
    private String[] types;
    /**
     * @return Returns the longName.
     */
    public String getLongName()
    {
        return longName;
    }

    /**
     * @param longName The longName to set.
     */
    public void setLongName(String longName)
    {
        this.longName = longName;
    }

    /**
     * @return Returns the shortName.
     */
    public String getShortName()
    {
        return shortName;
    }

    /**
     * @param shortName The shortName to set.
     */
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    /**
     * @return Returns the types.
     */
    public String[] getTypes()
    {
        return types;
    }

    /**
     * @param types The types to set.
     */
    public void setTypes(String[] types)
    {
        this.types = types;
    }


    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.longName);
        builder.append(this.shortName);
        builder.append(this.types);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        AddressComponent addressComponent = (AddressComponent) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.longName, addressComponent.longName);
        builder.append(this.shortName, addressComponent.shortName);
        builder.append(this.types, addressComponent.types);
        return builder.isEquals();
    }

    @Override
    public int compareTo(AddressComponent addressComponent)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.longName, addressComponent.longName);
        builder.append(this.shortName, addressComponent.shortName);
        builder.append(this.types, addressComponent.types);
        return builder.toComparison();
    }

    public String toString()
    {
        StringBuilder typeStr = new StringBuilder();
        for (String type : types)
        {
            typeStr.append(type);
            typeStr.append(", ");
        }
        typeStr.replace(typeStr.length() - 2, typeStr.length(), "");
        return String.format("%s (%s) [%s]", this.longName, this.shortName, typeStr.toString());
    }

    public boolean hasTypeMatch(Set<String> searchTypes)
    {
        for (String type : this.types)
        {
            if (searchTypes.contains(type))
            {
                return true;
            }
        }
        return false;
    }
}
