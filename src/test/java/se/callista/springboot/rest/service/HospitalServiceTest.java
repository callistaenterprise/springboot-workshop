package se.callista.springboot.rest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.HospitalCriteria;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;
import se.callista.springboot.rest.exception.NotFoundException;


import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Transactional
public class HospitalServiceTest {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    HospitalMapper hospitalMapper;

    private static String NAME_ONE = "SU";
    private static String NAME_TWO = "SÄS";
    private static String ADDRESS_ONE = "Gbg";

    @BeforeEach
    public void emptyRepository() {
        hospitalRepository.deleteAll();
    }

    @Test
    public void insertOne_findAll() {
        HospitalJPA initHospital = insertOneHospital();
        Page<Hospital> readHospitals = hospitalService.findAll(new HospitalCriteria(), PageRequest.of(0,20));

        assertNotNull(readHospitals.getContent());
        assertEquals(1, readHospitals.getContent().size());
        assertEquals(NAME_ONE, readHospitals.getContent().get(0).getName());
    }

    @Test
    public void insertOne_findOne() {
        HospitalJPA initHospital = insertOneHospital();

        Hospital readHospital = hospitalService.findOne(initHospital.getId());

        assertEquals(NAME_ONE, readHospital.getName());
    }


    @Test
    public void insertOne_deleteIt() {
        HospitalJPA initHospital = insertOneHospital();

        hospitalService.delete(initHospital.getId());

        Assertions.assertThrows(NotFoundException.class, () -> {
            hospitalService.findOne(initHospital.getId());
        });
     }

    @Test
    public void insertOne_deleteNonExistent() {
        HospitalJPA initHospital = insertOneHospital();
        Assertions.assertThrows(NotFoundException.class, () -> {
            hospitalService.delete(initHospital.getId() + 100);
        });
    }

    @Test
    public void insertOne_updateIt() {
        HospitalJPA initHospital = insertOneHospital();
        initHospital.setName(NAME_TWO);

        hospitalService.update(hospitalMapper.toDTO(initHospital));

        Hospital readHospital = hospitalService.findOne(initHospital.getId());

        assertEquals(NAME_TWO, readHospital.getName());
    }

    /**
     * Helper method
     * @return
     */
    private HospitalJPA insertOneHospital() {
        HospitalJPA hospital = new HospitalJPA(NAME_ONE, ADDRESS_ONE);
        hospital.setCareunits(null);
        return hospitalRepository.save(hospital);
    }

}
