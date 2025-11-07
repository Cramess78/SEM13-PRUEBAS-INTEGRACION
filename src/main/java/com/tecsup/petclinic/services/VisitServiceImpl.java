package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final PetRepository petRepository;
    // Constructor
    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper, PetRepository petRepository) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
        this.petRepository = petRepository;
    }
    // crear
    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit visit = visitMapper.mapToEntity(visitDTO);
        setPetIfExists(visitDTO, visit);
        Visit newVisit = visitRepository.save(visit);
        return visitMapper.mapToDto(newVisit);
    }
    // actualizar
    @Override
    public VisitDTO update(VisitDTO visitDTO) {
        Visit visit = visitMapper.mapToEntity(visitDTO);
        setPetIfExists(visitDTO, visit);
        Visit updatedVisit = visitRepository.save(visit);
        return visitMapper.mapToDto(updatedVisit);
    }
    // eliminar
    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        VisitDTO visitDTO = findById(id);
        visitRepository.delete(visitMapper.mapToEntity(visitDTO));
    }
    // verifica si  la mascota existe
    private void setPetIfExists(VisitDTO visitDTO, Visit visit) {
        if (visitDTO.getPetId() != null) {
            petRepository.findById(visitDTO.getPetId())
                    .ifPresent(visit::setPet);
        }
    }
    //  se busca  la visita por el ID
    private VisitDTO findById(Integer id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("no hay visita:" + id));
        return visitMapper.mapToDto(visit);
    }
}
