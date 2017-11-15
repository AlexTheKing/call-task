package com.intexsoft.call.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue
    private long id;

    public long callId;
    public String fromNumber;
    public String toNumber;
    public long callTime;
}
