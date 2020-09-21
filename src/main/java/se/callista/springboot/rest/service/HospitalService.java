package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.api.v1.Bed;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;
import se.callista.springboot.rest.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    HospitalMapper hospitalMapper;

    public List<Hospital> findAll() {
        List<HospitalJPA> hospitals = StreamSupport.stream(hospitalRepository.findAll().spliterator(),false).collect(Collectors.toList());

        //Transform to DTO's
        List<Hospital> returnHospitals = hospitalMapper.toListDTOs(hospitals);

        return returnHospitals;
    }

    public Hospital findOne(long id) {
        HospitalJPA hospital = hospitalRepository.findById(id).orElseThrow(() -> new NotFoundException("Hospital", Long.toString(id)));
        return hospitalMapper.toDTO(hospital);
    }

    public Hospital save(Hospital hospital) {
        // Transform from DTO
        HospitalJPA hospitalJPA = hospitalMapper.fromDTO(hospital);

        HospitalJPA savedHospitalJPA = hospitalRepository.save(hospitalJPA);

        //Transform to DTO
        return hospitalMapper.toDTO(savedHospitalJPA);
    }

    public void update(Hospital hospital) {
        // Transform from DTO
        HospitalJPA hospitalJPA = hospitalMapper.fromDTO(hospital);

        // Check that this hospital exist. We may or may not check this depending on strategy on updates that becomes saves...
        hospitalRepository.findById(hospitalJPA.getId()).orElseThrow(() -> new NotFoundException("Hospital", Long.toString(hospitalJPA.getId())));

        hospitalRepository.save(hospitalJPA);
    }

    public void delete(Long id) {
        // Check if the record exist, if not throw NotFoundException
        hospitalRepository.findById(id).orElseThrow(() -> new NotFoundException("Hospital", Long.toString(id)));

        hospitalRepository.deleteById(id);
    }
}
