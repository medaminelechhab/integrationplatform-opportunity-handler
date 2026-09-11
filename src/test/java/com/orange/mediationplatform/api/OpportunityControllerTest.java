package com.MyProject.mediationplatform.api;

import com.MyProject.mediationplatform.model.Opportunity;
import com.MyProject.mediationplatform.model.Owner;
import com.MyProject.mediationplatform.services.TeamMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "teamMember", "clinks"})
class OpportunityControllerTest {

    @MockitoBean
    private TeamMemberService teamMemberService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When trying to call api with bad application name, returns a forbidden exception")
    public void callWithBadName() throws Exception {
        String interfaceName = "non-authorized";
        mockMvc.perform(post("/customerlinks/v1/opportunityTeamMembers")
                    .header("X-InterfaceBasicat", interfaceName)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"Public_Opportunity_ID\":\"11111\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("Unknown application: ".concat(interfaceName)));
    }

    @Test
    @DisplayName("Call and get response")
    public void callAndGetResponse() throws Exception {
        Mockito.when(teamMemberService.getOpportunity("70072490"))
                        .thenReturn(Mono.just(new Opportunity("Test", new Owner("Toto"), Collections.emptyList())));

        mockMvc.perform(post("/customerlinks/v1/opportunityTeamMembers")
                .header("X-InterfaceBasicat", "concerto")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"Public_Opportunity_ID\":\"70072490\"}"))
                .andExpect(status().isOk());
    }
}