package com.intexsoft.call.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Class {@code Call} implements call entity.</p>
 * Implements {@code Persistable<T>} interface.
 * Can be persisted in database in table with name "calls"
 */
@Entity
@Table(name = "calls")
public class Call implements Persistable<Long> {

    @Id
    @GeneratedValue
    private long id;

    /**
     * An ID of call
     */
    public long callId;

    /**
     * Number of person who made a call
     */
    public String fromNumber;

    /**
     * Number of person who received a call
     */
    public String toNumber;

    /**
     * Time when call was made
     */
    public long callTime;

    /**
     * Implemented method from {@code Persistable<T>}
     * Returns database ID of the instance
     *
     * @return Long - database ID of the persisted instance
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Implemented method from {@code Persistable<T>}
     * Returns boolean which indicates whether an object is new or was already persisted
     *
     * @return boolean - shows if the instance is new and has not been persisted yet.
     */
    @Override
    public boolean isNew() {
        return id == 0;
    }
}
