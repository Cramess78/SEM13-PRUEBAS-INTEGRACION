package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface VisitMapper {

    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "visitDate", target = "visitDate", qualifiedByName = "localDateToString")
    VisitDTO mapToDto(Visit visit);

    @Mapping(source = "visitDate", target = "visitDate", qualifiedByName = "stringToLocalDate")
    @Mapping(source = "petId", target = "pet", ignore = true)
    Visit mapToEntity(VisitDTO visitDTO);

    List<VisitDTO> mapToDtoList(List<Visit> visitList);
    List<Visit> mapToEntityList(List<VisitDTO> visitDTOList);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Named("localDateToString")
    default String localDateToString(LocalDate date) {
        return (date != null)
                ? date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
    }
}