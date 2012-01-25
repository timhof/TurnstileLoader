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
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.persistence.annotations.BatchFetch;
import org.eclipse.persistence.annotations.BatchFetchType;

import com.timothyimhof.interval.util.DateIntervalHelper;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 29, 2011
 */
@Entity
@Table(name = "turnstiles")
public class Turnstile implements Serializable, Comparable<Turnstile>
{

    private static final long serialVersionUID = 3641782810208641704L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "remoteBooth_id")
    private Remote remoteBooth;

    /**
     * @param remoteBooth The remoteBooth to set.
     */
    public void setRemoteBooth(Remote remoteBooth)
    {
        this.remoteBooth = remoteBooth;
    }

    /**
     * @return Returns the remoteBooth.
     */
    public Remote getRemoteBooth()
    {
        return remoteBooth;
    }

    @Column(name = "scpAddress")
    private String scpAddress;// Subunit Channel Position represents an specific
    // address for a device (02-00-00)

    @OneToMany(mappedBy = "turnstile", cascade = CascadeType.ALL)
    @BatchFetch(BatchFetchType.JOIN)
    private List<TurnstileRegister> registers;

    /**
     * @param registers The registers to set.
     */
    public void setRegisters(List<TurnstileRegister> registers)
    {
        this.registers = registers;
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
     * @return Returns the scpAddress.
     */
    public String getScpAddress()
    {
        return scpAddress;
    }

    /**
     * @param scpAddress The scpAddress to set.
     */
    public void setScpAddress(String scpAddress)
    {
        this.scpAddress = scpAddress;
    }

    public void addRegister(TurnstileRegister register)
    {
        if (this.registers == null)
        {
            this.registers = new ArrayList<TurnstileRegister>();
        }
        this.registers.add(register);
    }
    /**
     * @return Returns the registers.
     */
    public List<TurnstileRegister> getRegisters()
    {
        return registers;
    }

    /**
     * @return Returns the serialversionuid.
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    public Turnstile()
    {

    }

    public int[] getTotalEntryExits()
    {
        return new int[] { calculateTotalEntries(), calculateTotalExits() };
    }

    private int calculateTotalEntries()
    {
        int totalEntries = 0;
        for (TurnstileRegister register : this.registers)
        {
            if (register.getEntriesDelta() > 0)
            {
                totalEntries += register.getEntriesDelta();
            }
        }
        return totalEntries;
    }

    private int calculateTotalExits()
    {
        int totalExits = 0;
        for (TurnstileRegister register : this.registers)
        {
            if (register.getExitsDelta() > 0)
            {
                totalExits += register.getExitsDelta();
            }
        }
        return totalExits;
    }

    public void calculateIntervalEntries(DateIntervalHelper helper, double[][] intervalEntryExits)
    {
        for (TurnstileRegister register : this.registers)
        {
            if (helper.isDateInRange(register.getTimestamp()))
            {
                int intervalIndex = helper.getIntervalIndex(register.getTimestamp());
                if (register.getEntriesDelta() > 0)
                    intervalEntryExits[0][intervalIndex] += register.getEntriesDelta();
                if (register.getExitsDelta() > 0)
                    intervalEntryExits[1][intervalIndex] += register.getExitsDelta();
            }
        }
    }

    public List<TurnstileRegister> setRegisterDeltas()
    {

        List<TurnstileRegister> registerList = getRegisters();
        Collections.sort(registerList);
        System.out.println("ORIGINAL LIST");
        for (TurnstileRegister register : registerList)
        {
            System.out.format("\t%s\n", register.toString());
        }

        RegisterDeltaState state = new RegisterDeltaState();

        for (TurnstileRegister register : registerList)
        {
            if (state.previousRegister != null)
            {
                state.registerTime.setTime(register.getTimestamp());

                //This register belongs to a different hour than previous
                if (state.previousRegisterTime.get(Calendar.YEAR) < state.registerTime.get(Calendar.YEAR)
                        || state.previousRegisterTime.get(Calendar.DAY_OF_YEAR) < state.registerTime.get(Calendar.DAY_OF_YEAR)
                        || state.previousRegisterTime.get(Calendar.HOUR_OF_DAY) < state.registerTime.get(Calendar.HOUR_OF_DAY))
                {
                    // fill in missing hour marks for all hours between previous
                    // hour, register hour exclusive
                    state.hourMarkRegisterTime.setTime(state.previousRegister.getTimestamp());
                    state.hourMarkRegisterTime.add(Calendar.HOUR, 1);
                    state.hourMarkRegisterTime.set(Calendar.MINUTE, 0);

                    //Insert registers for all skipped hours
                    while (state.hourMarkRegisterTime.get(Calendar.YEAR) < state.registerTime.get(Calendar.YEAR)
                            || state.hourMarkRegisterTime.get(Calendar.DAY_OF_YEAR) < state.registerTime.get(Calendar.DAY_OF_YEAR)
                            || state.hourMarkRegisterTime.get(Calendar.HOUR_OF_DAY) < state.registerTime.get(Calendar.HOUR_OF_DAY))
                    {
                        // Need dummy registers for:
                        // hour marks previous hour+1 through register.gethour()
                        // - 1
                        insertDummyRegister(state.hourMarkRegisterTime, register, state, "SKIPPED");

                        state.hourMarkRegisterTime.add(Calendar.HOUR, 1);

                    }

                //Insert hour mark if this register is not on hour
                if (state.registerTime.get(Calendar.MINUTE) > 0)
                {
                    // Need dummy registers for:
                    // hour mark = register.gethour()
                        state.hourMarkRegisterTime.setTime(register.getTimestamp());
                        state.hourMarkRegisterTime.set(Calendar.MINUTE, 0);

                        insertDummyRegister(state.hourMarkRegisterTime, register, state, "MARK");

                }
                }

                register.setPreviousEntries(state.previousRegister.getEntriesCum());
                register.setPreviousExits(state.previousRegister.getExitsCum());
            }

            state.newRegisterList.add(register);
            state.previousRegister = register;
            state.previousRegisterTime.setTime(register.getTimestamp());
        }

        System.out.println("NEW LIST");
        for (TurnstileRegister register : state.newRegisterList)
        {
            System.out.format("\t%s\n", register.toString());
        }
        return state.newRegisterList;
    }

    private void insertDummyRegister(Calendar cal, TurnstileRegister nextRegister, RegisterDeltaState state, String description)
    {

        int totalTimeDiffInMillis = (int) (nextRegister.getTimestamp().getTime() - state.previousRegister.getTimestamp().getTime());
        int totalTimeDiffInMinutes = totalTimeDiffInMillis / 60000;

        int entriesCumDiff = nextRegister.getEntriesCum() - state.previousRegister.getEntriesCum();
        int exitsCumDiff = nextRegister.getExitsCum() - state.previousRegister.getExitsCum();

        double entriesPerMinute = entriesCumDiff / (double) totalTimeDiffInMinutes;
        double exitsPerMinute = exitsCumDiff / (double) totalTimeDiffInMinutes;

        int segmentTimeDiffInMillis = (int) (cal.getTimeInMillis() - state.previousRegister.getTimestamp().getTime());
        int segmentTimeDiffInMinutes = segmentTimeDiffInMillis / 60000;

        TurnstileRegister dummyRegister = new TurnstileRegister();
        dummyRegister.setTurnstile(this);
        dummyRegister.setDescription(description);
        dummyRegister.setTimestamp(cal);

        // Week week = dummyRegister.getWeekdayIndex() <
        // state.previousRegister.getWeekdayIndex() ? nextRegister.getWeek() :
        // state.previousRegister
        // .getWeek();
        // dummyRegister.setWeek(week);

        dummyRegister.setEntriesCum(state.previousRegister.getEntriesCum() + (int) (entriesPerMinute * segmentTimeDiffInMinutes));
        dummyRegister.setExitsCum(state.previousRegister.getExitsCum() + (int) (exitsPerMinute * segmentTimeDiffInMinutes));

        dummyRegister.setPreviousEntries(state.previousRegister.getEntriesCum());
        dummyRegister.setPreviousExits(state.previousRegister.getExitsCum());

        state.newRegisterList.add(dummyRegister);

        state.previousRegister = dummyRegister;
        state.previousRegisterTime.setTime(dummyRegister.getTimestamp());
    }

    @Override
    public String toString()
    {
        return String.format("%s\t%s, Turnstile[%d]", this.remoteBooth.toString(), this.scpAddress, this.id);
    }
    
    @Override
    public int hashCode()
    {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(this.id);
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        Turnstile turnstile = (Turnstile) o;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(this.id, turnstile.id);
        return builder.isEquals();
    }

    @Override
    public int compareTo(Turnstile turnstile)
    {
        CompareToBuilder builder = new CompareToBuilder();
        builder.append(this.remoteBooth, turnstile.remoteBooth);
        builder.append(this.scpAddress, turnstile.scpAddress);
        return builder.toComparison();
    }

    private class RegisterDeltaState
    {
        TurnstileRegister previousRegister;
        List<TurnstileRegister> newRegisterList = new ArrayList<TurnstileRegister>();
        Calendar registerTime = Calendar.getInstance();
        Calendar previousRegisterTime = Calendar.getInstance();
        Calendar hourMarkRegisterTime = Calendar.getInstance();
    }
}
