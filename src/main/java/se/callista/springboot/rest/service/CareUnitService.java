package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<CareUnitJPA> findAll() {
        List<CareUnitJPA> careunits = StreamSupport.stream(careUnitRepository.findAll().spliterator(),false).collect(Collectors.toList());
        return careunits;
    }

    public CareUnitJPA findOne(long id) {
        CareUnitJPA careunit = careUnitRepository.findById(id).orElse(null);
        return careunit;
    }

    public CareUnitJPA save(Long hospitalId, CareUnitJPA careunit) {
        HospitalJPA hospital = hospitalRepository.findById(hospitalId).orElse(null);
        careunit.setHospital(hospital);
        return careUnitRepository.save(careunit);
    }

    public void update(CareUnitJPA careunit) {
        careUnitRepository.save(careunit);
    }

    public void delete(Long id) {
        careUnitRepository.deleteById(id);
    }
}
