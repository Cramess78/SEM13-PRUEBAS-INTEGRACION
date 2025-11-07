package com.tecsup.petclinic.services;
import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import java.util.List;

/**
 *
 * @author jgomezm
 *
 */
public interface VisitService {

    public VisitDTO create(VisitDTO visitDTO);
    VisitDTO update(VisitDTO visit);
    void delete(Integer id) throws VisitNotFoundException;

}
