package com.MyProject.mediationplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {

    @JsonProperty(value = "Name")
    private String name;

    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
