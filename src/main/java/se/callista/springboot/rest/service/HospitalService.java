package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.BedRepository;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    public List<HospitalJPA> findAll() {
        List<HospitalJPA> hospitals = StreamSupport.stream(hospitalRepository.findAll().spliterator(),false).collect(Collectors.toList());
        return hospitals;
    }

    public HospitalJPA findOne(long id) {
        HospitalJPA hospital = hospitalRepository.findById(id).orElse(null);
        return hospital;
    }

    public HospitalJPA save(HospitalJPA hospital) {
        return hospitalRepository.save(hospital);
    }

    public void update(HospitalJPA hospital) {
        hospitalRepository.save(hospital);
    }

    public void delete(Long id) {
        hospitalRepository.deleteById(id);
    }
}
