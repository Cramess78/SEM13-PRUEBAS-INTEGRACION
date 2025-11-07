package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;
import com.tecsup.petclinic.services.VisitServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class VisitControllerTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VisitMapper visitMapper;

    @InjectMocks
    private VisitServiceImpl visitService;

    private VisitDTO visitDTO;
    private Visit visit;
    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        visitDTO = new VisitDTO();
        visitDTO.setId(1);
        visitDTO.setDescription("Consulta inicial");
        visitDTO.setVisitDate("2025-11-07");
        visitDTO.setPetId(10);

        pet = new Pet();
        pet.setId(Math.toIntExact(10));
        pet.setName("Firulais");

        visit = new Visit();
        visit.setDescription("Consulta inicial");
        visit.setVisitDate(LocalDate.parse("2025-11-07"));
        visit.setPet(pet);
    }

    /**
     * 🧪 Prueba: crear una visita
     */
    @Test
    void testCreateVisit() {
        when(visitMapper.mapToEntity(any(VisitDTO.class))).thenReturn(visit);
        when(petRepository.findById(anyInt())).thenReturn(Optional.of(pet));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(visitMapper.mapToDto(any(Visit.class))).thenReturn(visitDTO);

        VisitDTO result = visitService.create(visitDTO);

        assertNotNull(result);
        assertEquals("Consulta inicial", result.getDescription());
        verify(visitRepository, times(1)).save(any(Visit.class));
    }

    /**
     * 🧪 Prueba: actualizar una visita
     */
    @Test
    void testUpdateVisit() {
        visitDTO.setDescription("Consulta de seguimiento");
        visit.setDescription("Consulta de seguimiento");

        when(visitMapper.mapToEntity(any(VisitDTO.class))).thenReturn(visit);
        when(petRepository.findById(anyInt())).thenReturn(Optional.of(pet));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        when(visitMapper.mapToDto(any(Visit.class))).thenReturn(visitDTO);

        VisitDTO updated = visitService.update(visitDTO);

        assertNotNull(updated);
        assertEquals("Consulta de seguimiento", updated.getDescription());
        verify(visitRepository, times(1)).save(any(Visit.class));
    }

    /**
     * 🧪 Prueba: eliminar una visita
     */
    @Test
    void testDeleteVisit() throws VisitNotFoundException {
        when(visitRepository.findById(anyInt())).thenReturn(Optional.of(visit));
        when(visitMapper.mapToDto(any(Visit.class))).thenReturn(visitDTO);

        visitService.delete(1);

        verify(visitRepository, times(1)).delete(any(Visit.class));
    }
}


