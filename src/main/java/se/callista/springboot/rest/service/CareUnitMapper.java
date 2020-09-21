package se.callista.springboot.rest.service;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import se.callista.springboot.rest.api.v1.CareUnit;
import se.callista.springboot.rest.domain.CareUnitJPA;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CareUnitMapper {
    CareUnitMapper INSTANCE = Mappers.getMapper(CareUnitMapper.class);
    CareUnitJPA fromDTO(CareUnit s);
    CareUnit toDTO(CareUnitJPA s);
    List<CareUnit> toListDTOs(List<CareUnitJPA> s);
}

