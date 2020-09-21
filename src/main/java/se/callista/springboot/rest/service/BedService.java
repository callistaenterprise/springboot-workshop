package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.BedRepository;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.domain.CareUnitRepository;
import se.callista.springboot.rest.domain.HospitalJPA;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BedService {

    @Autowired
    BedRepository bedRepository;

    @Autowired
    CareUnitRepository careUnitRepository;

    public List<BedJPA> findAll() {
        List<BedJPA> beds = StreamSupport.stream(bedRepository.findAll().spliterator(),false).collect(Collectors.toList());
        return beds;
    }

    public BedJPA findOne(long id) {
        BedJPA bed = bedRepository.findById(id).orElse(null);
        return bed;
    }

    public BedJPA save(Long careunitId, BedJPA bed) {
        CareUnitJPA careunit = careUnitRepository.findById(careunitId).orElse(null);
        bed.setCareunit(careunit);

        return bedRepository.save(bed);
    }

    public void update(BedJPA bed) {
        bedRepository.save(bed);
    }

    public void delete(Long id) {
        bedRepository.deleteById(id);
    }


}
