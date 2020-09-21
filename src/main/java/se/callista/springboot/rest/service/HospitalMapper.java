package se.callista.springboot.rest.service;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.HospitalJPA;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HospitalMapper {
    HospitalMapper INSTANCE = Mappers.getMapper(HospitalMapper.class);
    HospitalJPA fromDTO(Hospital s);
    Hospital toDTO(HospitalJPA s);
    List<Hospital> toListDTOs(List<HospitalJPA> s);
}

