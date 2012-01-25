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
 * SubSystem: com.timothyimhof.mta.persistence
 * FileName: OrmService.java
 *************************************************************************/
package com.timothyimhof.persistence;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 
 *
 * @author  timothyi
 * @since 	Version 1.0, Nov 30, 2011
 */
public class OrmService
{
    private static EntityManagerFactory emf;

    private EntityManager em;

    public OrmService()
    {
        if (emf == null)
        {
            emf = Persistence.createEntityManagerFactory("default");
        }
        this.em = emf.createEntityManager();
    }

    public EntityManager getEntityManager()
    {
        return this.em;
    }

    public void persist(Object o)
    {
        em.persist(o);
    }

    public void persistAll(Collection<? extends Object> objectList)
    {
        for (Object o : objectList)
            em.persist(o);
    }

    public <T> List<T> getAll(Class<T> type)
    {
        String query = "select x from " + type.getSimpleName() + " x"; // something
                                                                       // like
                                                                       // "select x from Order x";
        // Execute the query and return results.
        @SuppressWarnings("unchecked")
        List<T> results = (List<T>) executeQuery(query);
        return results;
    }

    public Collection<?> executeQuery(String query)
    {
        javax.persistence.Query ormQuery = this.em.createQuery(query);
        List<?> result = ormQuery.getResultList();
        return result;
    }

    /**
     * Begins a transaction within the entity manager
     * 
     * 
     * @author MarkS
     * @since Version 0.07, Sep 23, 2008
     */
    public void beginTransaction()
    {
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.begin();
    }

    /**
     * Tries to commit the pending transaction, and rolls back if unsuccessful
     * 
     * @param transaction
     * @author MarkS
     * @since Version 0.07, Sep 17, 2008
     */
    public void tryCommitTransactionOrRollback()
    {
        EntityTransaction transaction = getEntityManager().getTransaction();
        if (transaction.getRollbackOnly())
            transaction.rollback();
        else
            transaction.commit();
    }

    public void rollbackTransaction()
    {
        EntityTransaction transaction = getEntityManager().getTransaction();
        transaction.rollback();
    }

    public <T> T findById(Class<T> type, Object id)
    {
        EntityManager manager = getEntityManager();
        T toReturn = manager.find(type, id);
        return toReturn;
    }
}
