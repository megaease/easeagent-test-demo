package com.megaease.easeagent.demo.motan.api.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
