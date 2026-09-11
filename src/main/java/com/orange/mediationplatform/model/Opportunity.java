package com.MyProject.mediationplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Opportunity {

    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Owner")
    private Owner owner;
    @JsonProperty(value = "OpportunityTeam")
    private List<OpportunityTeam> opportunityTeam;

    public Opportunity(String name, Owner owner, List<OpportunityTeam> opportunityTeam) {
        this.name = name;
        this.owner = owner;
        this.opportunityTeam = opportunityTeam;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public List<OpportunityTeam> getOpportunityTeam() {
        return opportunityTeam;
    }
}
