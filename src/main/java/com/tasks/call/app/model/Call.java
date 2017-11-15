package com.tasks.call.app.model;

import javax.persistence.*;

@Entity
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "callId")
    private long callId;
    @Column(name = "fromNumber")
    private String fromNumber;
    @Column(name = "toNumber")
    private String toNumber;
    @Column(name = "callTime")
    private long callTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCallId() {
        return callId;
    }

    public void setCallId(long callId) {
        this.callId = callId;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }
}
