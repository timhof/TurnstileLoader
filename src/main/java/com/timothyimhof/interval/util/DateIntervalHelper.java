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
 * SubSystem: com.timothyimhof.interval.util
 * FileName: DateIntervalHelper.java
 *************************************************************************/
package com.timothyimhof.interval.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Dec 29, 2011
 */
public class DateIntervalHelper
{

    public static final int MILLISECONDS_PER_HOUR = 3600000;
    public static final int MILLISECONDS_PER_4_HOURS = MILLISECONDS_PER_HOUR * 4;
    public static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");

    private Date starttime;
    private Date endtime;

    /**
     * @return Returns the endtime.
     */
    public Date getEndtime()
    {
        return endtime;
    }

    /**
     * @param endtime The endtime to set.
     */
    public void setEndtime(Date endtime)
    {
        this.endtime = endtime;
    }

    public DateIntervalHelper(Date starttime, Date endtime)
    {
        this.starttime = starttime;
        this.endtime = endtime;

    }

    public int getNumIntervals()
    {
        int millisecondsInTimeRange = (int) (endtime.getTime() - starttime.getTime());
        int intervals = (millisecondsInTimeRange / MILLISECONDS_PER_4_HOURS) + 1;
        return intervals;
    }

    public int getIntervalIndex(Date date)
    {
        int milliSecondsFromStart = (int) (date.getTime() - starttime.getTime());
        int intervalIndex = milliSecondsFromStart / MILLISECONDS_PER_4_HOURS;
        return intervalIndex;
    }

    public boolean isDateInRange(Date date)
    {
        return !(date.before(starttime) || date.after(endtime));
    }

    public String[] getDateLabels()
    {
        String[] xAxisLabels = new String[getNumIntervals()];
        Calendar cal = Calendar.getInstance();
        cal.setTime(starttime);
        for (int i = 0; i < xAxisLabels.length; i++)
        {

            if (cal.get(Calendar.HOUR_OF_DAY) == 0)
                xAxisLabels[i] = formatter.format(cal.getTime());
            cal.add(Calendar.MILLISECOND, MILLISECONDS_PER_4_HOURS);
        }
        return xAxisLabels;
    }

    /**
     * @return Returns the starttime.
     */
    public Date getStarttime()
    {
        return starttime;
    }

    /**
     * @param starttime The starttime to set.
     */
    public void setStarttime(Date starttime)
    {
        this.starttime = starttime;
    }

}
