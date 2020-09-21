package se.callista.springboot.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.HospitalCriteria;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;
import se.callista.springboot.rest.exception.NotFoundException;

import static se.callista.springboot.rest.domain.HospitalJPA.addressContains;
import static se.callista.springboot.rest.domain.HospitalJPA.nameContains;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    HospitalMapper hospitalMapper;

    public Page<Hospital> findAll(HospitalCriteria criteria, Pageable pageable) {
        Specification<HospitalJPA> spec = createHotelSearchSpec( criteria );

        Page<HospitalJPA> hospitals = hospitalRepository.findAll(spec, pageable);

        //Transform to DTO's
        return new PageImpl<Hospital>(hospitalMapper.toListDTOs(hospitals.getContent()), pageable, hospitals.getTotalElements());
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

    private Specification<HospitalJPA> createHotelSearchSpec(HospitalCriteria hospitalCriteria) {
        if ( hospitalCriteria.getName() == null && hospitalCriteria.getAddress() == null )
            return null;

        return Specification
                .where( nameContains( hospitalCriteria.getName() ) )
                .and( addressContains( hospitalCriteria.getAddress() ) );
    }
}
