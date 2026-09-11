package com.MyProject.mediationplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpportunityRequestBody {

    @JsonProperty(value = "Public_Opportunity_ID")
    private String publicOpportunityId;

    public String getPublicOpportunityId() {
        return publicOpportunityId;
    }
}
