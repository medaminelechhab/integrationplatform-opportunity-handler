package com.MyProject.mediationplatform.services;

import com.MyProject.mediationplatform.common.model.customerlinks.*;
import com.MyProject.mediationplatform.common.service.remote.customerlinks.OpportunityTeamMemberService;
import com.MyProject.mediationplatform.model.OpportunityTeam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"test", "teamMember", "clinks"})
class TeamMemberServiceTest {

    @Autowired
    private Environment environment;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TeamMemberService teamMemberService;
    @MockitoBean
    private OpportunityTeamMemberService opportunityTeamMemberService;

    @Test
    @DisplayName("When no team members are found in CL, returns an empty response")
    public void verifyWhenThereIsNoResponseFromCL() {
        Mockito.when(opportunityTeamMemberService.get("test"))
                .thenReturn(Mono.just(createDefaultResponse(0)));

        StepVerifier.create(teamMemberService.getOpportunity("test"))
                .expectNextMatches(opportunity ->
                        opportunity.getOpportunityTeam().size() == 0
                                && opportunity.getName() == null
                                && (opportunity.getOwner() == null ||opportunity.getOwner().getName() == null))
                .verifyComplete();
    }

    @Test
    @DisplayName("When one team member is found in CL, returns a response")
    public void verifyTransformation() {
        Mockito.when(opportunityTeamMemberService.get("test"))
                .thenReturn(Mono.just(createDefaultResponse(1)));

        StepVerifier.create(teamMemberService.getOpportunity("test"))
                .expectNextMatches(opportunity -> verify(opportunity, 1))
                .verifyComplete();
    }

    @Test
    @DisplayName("When team members are found in CL, returns a response")
    public void verifyMultipleTransformation() {
        Mockito.when(opportunityTeamMemberService.get("test2"))
                .thenReturn(Mono.just(createDefaultResponse(5)));

        StepVerifier.create(teamMemberService.getOpportunity("test2"))
                .expectNextMatches(opportunity -> verify(opportunity, 5))
                .verifyComplete();
    }

    private boolean verify(com.MyProject.mediationplatform.model.Opportunity opportunity, int nb) {
        boolean isNotNull = opportunity.getOpportunityTeam() != null;
        boolean goodSize = opportunity.getOpportunityTeam().size() == nb;

        boolean goodName = "Opportunity Name".equals(opportunity.getName());
        boolean goodOwner = "Owner Chief".equals(opportunity.getOwner().getName());

        boolean goodUsername = true;
        boolean goodEmail = true;
        boolean goodMobilephone = true;
        boolean goodIdentity = true;
        for(int i = 0; i < nb; i++) {
            OpportunityTeam teamMember = opportunity.getOpportunityTeam().get(i);
            String name = "Toto Dupont_" + i;
            goodUsername = goodName == true ? name.equals(teamMember.getUser().getName()) : false;
            goodEmail = goodEmail == true ? "email@mail.com".equals(teamMember.getUser().getEmail()) : false;
            goodMobilephone = goodMobilephone == true ? "612345678".equals(teamMember.getUser().getMobilePhone()) : false;
            goodIdentity = goodIdentity == true ? "AAAA4567".equals(teamMember.getUser().getCuid()) : false;
        }

        return isNotNull && goodSize && goodName && goodOwner && goodUsername && goodEmail && goodIdentity && goodMobilephone;
    }

    private void verifyResponse(com.MyProject.mediationplatform.model.Opportunity opportunity, int nb) {
        Assertions.assertNotNull(opportunity.getOpportunityTeam());
        Assertions.assertEquals(nb, opportunity.getOpportunityTeam().size());

        Assertions.assertEquals("Opportunity Name", opportunity.getName());
        Assertions.assertEquals("Owner Chief", opportunity.getOwner().getName());

        for(int i = 0; i < nb; i++) {
            OpportunityTeam teamMember = opportunity.getOpportunityTeam().get(i);
            Assertions.assertEquals("Toto Dupont_" + i, teamMember.getUser().getName());
            Assertions.assertEquals("email@mail.com" + i, teamMember.getUser().getEmail());
            Assertions.assertEquals("612345678" + i, teamMember.getUser().getMobilePhone());
            Assertions.assertEquals("AAAA4567" + i, teamMember.getUser().getCuid());
        }
    }

    private OpportunityTeamMember createDefaultResponse(int numberOfUser) {
        List<OpportunityTeamMemberRecords> records = numberOfUser == 0 ? new ArrayList<>() : createRecords(numberOfUser);
        OpportunityTeamMember result = new OpportunityTeamMember();
        result.setRecords(records);
        return result;
    }

    private List<OpportunityTeamMemberRecords> createRecords(int numberOfUSers) {
        List<OpportunityTeamMemberRecords> records = new ArrayList<>();
        for(int i = 0; i < numberOfUSers; i++) {
            records.add(createRecord(i));
        }
        return records;
    }

    private OpportunityTeamMemberRecords createRecord(int position) {
        OpportunityTeamMemberRecords record = new OpportunityTeamMemberRecords();
        record.setTeamMemberRole("role");
        record.setOpportunity(createOpportunity());
        record.setUser(createUser(position));
        return record;
    }

    private OpportunityUser createUser(int position) {
        OpportunityUser user = new OpportunityUser();
        user.setName("Toto Dupont_" + position);
        user.setEmail("email@mail.com");
        user.setMobilePhone("612345678");
        user.setPhone(null);
        user.setCuid("AAAA4567");
        return user;
    }

    private Opportunity createOpportunity() {
        OpportunityOwner owner = new OpportunityOwner();
        owner.setName("Owner Chief");

        Opportunity opportunity = new Opportunity();
        opportunity.setName("Opportunity Name");
        opportunity.setOpportunityOwner(owner);
        return opportunity;
    }

}