package se.callista.springboot.rest.service;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import se.callista.springboot.rest.api.v1.Bed;
import se.callista.springboot.rest.domain.BedJPA;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BedMapper {
    BedMapper INSTANCE = Mappers.getMapper(BedMapper.class);
    BedJPA fromDTO(Bed s);
    Bed toDTO(BedJPA s);
    List<Bed> toListDTOs(List<BedJPA> s);
}

