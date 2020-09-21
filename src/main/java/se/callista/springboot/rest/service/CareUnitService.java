package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.api.v1.Bed;
import se.callista.springboot.rest.api.v1.CareUnit;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.domain.CareUnitRepository;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CareUnitService {

    @Autowired
    CareUnitRepository careUnitRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    CareUnitMapper careUnitMapper;

    public List<CareUnit> findAll() {
        List<CareUnitJPA> careunits = StreamSupport.stream(careUnitRepository.findAll().spliterator(),false).collect(Collectors.toList());

        //Transform to DTO's
        List<CareUnit> returnHospitals = careUnitMapper.toListDTOs(careunits);

        return returnHospitals;
    }

    public CareUnit findOne(long id) {
        CareUnitJPA careunit = careUnitRepository.findById(id).orElse(null);
        return careUnitMapper.toDTO(careunit);
    }

    public CareUnit save(Long hospitalId, CareUnit careunit) {
        // Transform from DTO
        CareUnitJPA careunitJPA = careUnitMapper.fromDTO(careunit);

        HospitalJPA hospital = hospitalRepository.findById(hospitalId).orElse(null);
        careunitJPA.setHospital(hospital);

        CareUnitJPA savedCareunitJPA = careUnitRepository.save(careunitJPA);

        //Transform to DTO
        return careUnitMapper.toDTO(savedCareunitJPA);
    }

    public void update(CareUnit careunit) {
        // Transform from DTO
        CareUnitJPA careunitJPA = careUnitMapper.fromDTO(careunit);

        careUnitRepository.save(careunitJPA);
    }

    public void delete(Long id) {
        careUnitRepository.deleteById(id);
    }
}
