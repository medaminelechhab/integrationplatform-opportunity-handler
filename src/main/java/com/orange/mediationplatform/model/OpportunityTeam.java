package com.MyProject.mediationplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpportunityTeam {

    @JsonProperty(value = "User")
    private User user;
    @JsonProperty(value = "TeamMemberRole")
    private String teamMemberRole;

    public OpportunityTeam(User user, String teamMemberRole) {
        this.user = user;
        this.teamMemberRole = teamMemberRole;
    }

    public User getUser() {
        return user;
    }

    public String getTeamMemberRole() {
        return teamMemberRole;
    }
}
