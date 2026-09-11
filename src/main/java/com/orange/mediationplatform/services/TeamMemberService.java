package com.MyProject.mediationplatform.services;
import com.MyProject.mediationplatform.model.Opportunity;
import com.MyProject.mediationplatform.model.OpportunityTeam;
import com.MyProject.mediationplatform.model.Owner;
import com.MyProject.mediationplatform.model.User;
import com.MyProject.mediationplatform.model.exceptions.CustomerLinksException;
import com.MyProject.mediationplatform.common.error.ApiCallException;
import com.MyProject.mediationplatform.common.model.customerlinks.OpportunityTeamMember;
import com.MyProject.mediationplatform.common.model.customerlinks.OpportunityTeamMemberRecords;
import com.MyProject.mediationplatform.common.service.remote.customerlinks.OpportunityTeamMemberService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMemberService {

    private OpportunityTeamMemberService service;

    public TeamMemberService(OpportunityTeamMemberService service) {
        this.service = service;
    }

    public Mono<Opportunity> getOpportunity(String id) {
        return service.get(id)
                .flatMap(opportunityTeamMember -> Mono.just(transformIntoOpportunity(opportunityTeamMember)))
                .doOnError(ApiCallException.class, e -> { throw new CustomerLinksException(e.getMessage()); });
    }

    private Opportunity transformIntoOpportunity(OpportunityTeamMember opportunityTeamMember) {
        List<OpportunityTeam> team = createOpportunityTeam(opportunityTeamMember);
        OpportunityTeamMemberRecords firstRecord;
        if(opportunityTeamMember.getRecords().isEmpty()) {
            firstRecord = null;
        } else {
            firstRecord = opportunityTeamMember.getRecords().get(0);
        }

        return new Opportunity(
                firstRecord == null ? null : firstRecord.getOpportunity().getName(),
                new Owner(firstRecord == null ? null : firstRecord.getOpportunity().getOpportunityOwner().getName()),
                team
        );
    }

    private List<OpportunityTeam> createOpportunityTeam(OpportunityTeamMember opportunityTeamMember) {
        List<OpportunityTeam> team = new ArrayList<>(0);

        opportunityTeamMember.getRecords()
                .stream()
                .forEach(opportunityTeamMemberRecords -> team.add(createOpportunityTeam(opportunityTeamMemberRecords)));
        return team;
    }

    private OpportunityTeam createOpportunityTeam(OpportunityTeamMemberRecords record) {
        return new OpportunityTeam(
                new User(
                        record.getUser().getName(),
                        record.getUser().getEmail(),
                        record.getUser().getMobilePhone(),
                        record.getUser().getPhone(),
                        record.getUser().getCuid()),
                record.getTeamMemberRole()
        );
    }

}
