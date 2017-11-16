package com.intexsoft.call.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>Class {@code Call} implements call entity.</p>
 * Implements {@code Persistable<T>} interface.
 * Can be persisted in database in table with name "calls"
 */
@Entity
@Table(name = "calls")
public class Call extends AbstractPersistable<Long> {

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
    public long startTime;

    /**
     * Time when call was ended
     */
    public long endTime;
}
