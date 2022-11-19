package com.magaease.easeagent.demo.dubbo.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class Message implements Serializable {
   private static final AtomicLong sequenceIdGenerator = new AtomicLong(0);
   private Long sequenceId = sequenceIdGenerator.getAndIncrement();

   private String data;

    public Long getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Long sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
