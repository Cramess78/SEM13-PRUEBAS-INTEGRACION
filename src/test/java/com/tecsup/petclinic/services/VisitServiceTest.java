package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
 class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    void testActualizarVisit() {

        VisitDTO visitDTO = new VisitDTO();
        visitDTO.setDescription("Consulta inicial");
        visitDTO.setPetId(1);
        visitDTO.setVisitDate(LocalDate.now().toString());

        VisitDTO created = visitService.create(visitDTO);
        log.info("Visit creada: " + created);

        created.setDescription("Consulta de seguimiento");

        VisitDTO updated = visitService.update(created);
        log.info("Visit actualizada: " + updated);

        assertEquals("Consulta de seguimiento", updated.getDescription());
    }

}