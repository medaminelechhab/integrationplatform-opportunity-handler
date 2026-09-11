package com.MyProject.mediationplatform.api;

import com.MyProject.mediationplatform.model.Opportunity;
import com.MyProject.mediationplatform.model.OpportunityRequestBody;
import com.MyProject.mediationplatform.services.AuthenticationService;
import com.MyProject.mediationplatform.services.TeamMemberService;
import io.swagger.annotations.ApiParam;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customerlinks/v1")
public class OpportunityController {

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private AuthenticationService authenticationService;

    @ApiParam(value = "Get team members of an opportunity")
    @PostMapping("/opportunityTeamMembers")
    public Publisher<Opportunity> getOpportunityById(
            @RequestHeader("X-InterfaceBasicat") String clInterface,
            @RequestBody() OpportunityRequestBody opportunityRequestBody
    ) {
        authenticationService.authenticate(clInterface);
        return teamMemberService.getOpportunity(opportunityRequestBody.getPublicOpportunityId());
    }

}
