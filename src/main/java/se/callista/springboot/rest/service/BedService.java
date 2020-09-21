package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.api.v1.Bed;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.BedRepository;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.domain.CareUnitRepository;
import se.callista.springboot.rest.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BedService {

    @Autowired
    BedRepository bedRepository;

    @Autowired
    CareUnitRepository careUnitRepository;

    @Autowired
    private BedMapper bedMapper;

    public List<Bed> findAll() {
        List<BedJPA> beds = StreamSupport.stream(bedRepository.findAll().spliterator(),false).collect(Collectors.toList());

        //Transform to DTO's
        List<Bed> returnBeds = bedMapper.toListDTOs(beds);

        return returnBeds;
    }

    public Bed findOne(long id) {
        BedJPA bed = bedRepository.findById(id).orElseThrow(() -> new NotFoundException("Bed", Long.toString(id)));
        return bedMapper.toDTO(bed);
    }

    public Bed save(Long careunitId, Bed bed) {
        // Transform from DTO
        BedJPA bedJPA = bedMapper.fromDTO(bed);

        CareUnitJPA careunit = careUnitRepository.findById(careunitId).orElseThrow(() -> new NotFoundException("Careunit", Long.toString(careunitId)));
        bedJPA.setCareunit(careunit);

        BedJPA savedBedJPA = bedRepository.save(bedJPA);

        //Transform to DTO
        return bedMapper.toDTO(savedBedJPA);
    }

    public void update(Bed bed) {
        // Transform from DTO
        BedJPA bedJPA = bedMapper.fromDTO(bed);

        bedRepository.save(bedJPA);
    }

    public void delete(Long id) {
        bedRepository.deleteById(id);
    }

}
