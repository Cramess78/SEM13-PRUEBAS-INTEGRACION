package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VisitControllerTest {

    private static final String VISIT_API_URL = "/visits";
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitService visitService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllVisits_ShouldReturnListOfVisits() throws Exception {
        List<Visit> visits = Arrays.asList(
                createVisit(1L, "2023-01-01", "10:00", "Checkup", 1L, 1L),
                createVisit(2L, "2023-01-02", "11:00", "Follow-up", 1L, 2L)
        );
        when(visitService.findAll()).thenReturn(visits);

        mockMvc.perform(get(VISIT_API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("Checkup")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].description", is("Follow-up")));
    }

    @Test
    public void getVisitById_WhenExists_ShouldReturnVisit() throws Exception {
        Long visitId = 1L;
        Visit visit = createVisit(visitId, "2023-01-01", "10:00", "Checkup", 1L, 1L);
        when(visitService.findById(visitId)).thenReturn(visit);

        mockMvc.perform(get(VISIT_API_URL + "/{id}", visitId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(visitId.intValue()))
                .andExpect(jsonPath("$.description", is("Checkup")));
    }

    @Test
    public void getVisitById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        Long visitId = 99L;
        when(visitService.findById(visitId)).thenThrow(new RuntimeException("Visit not found"));

        mockMvc.perform(get(VISIT_API_URL + "/{id}", visitId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * SEGUNDO COMMIT - Pruebas para endpoints POST y PUT
     * 
     * @Test
     * public void createVisit_WithValidData_ShouldReturnCreated() { ... }
     * 
     * @Test
     * public void updateVisit_WithValidData_ShouldReturnUpdatedVisit() { ... }
     */

    /**
     * TERCER COMMIT - Pruebas para endpoint DELETE y casos de error
     * 
     * @Test
     * public void deleteVisit_WhenExists_ShouldReturnNoContent() { ... }
     * 
     * @Test
     * public void createVisit_WithInvalidData_ShouldReturnBadRequest() { ... }
     */

    // Helper method to create Visit objects
    private Visit createVisit(Long id, String date, String time, String description, Long petId, Long vetId) {
        Visit visit = new Visit();
        visit.setId(id);
        visit.setDate(date);
        visit.setTime(time);
        visit.setDescription(description);
        // Aquí deberías establecer las relaciones con Pet y Vet si es necesario
        return visit;
    }
}

