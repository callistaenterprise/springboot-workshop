package se.callista.springboot.rest.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;
import se.callista.springboot.rest.api.v1.Hospital;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.domain.HospitalRepository;
import se.callista.springboot.rest.service.HospitalMapper;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HospitalIntegrationTest {

    @Autowired
    public TestRestTemplate template;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    HospitalMapper hospitalMapper;

    @BeforeEach
    public void removeAnyLoadedData(){
        hospitalRepository.deleteAll();
    }

    @Test
    public void testGetAll() {
        // Set db values
        HospitalJPA hospital1 = insertOneHospital();

        // Set values and make call
        ParameterizedTypeReference<List<Hospital>> ptr =
                new ParameterizedTypeReference<>() { };
        URI targetUrl = UriComponentsBuilder.fromUriString("/api/v1/hospital")
                .build()
                .toUri();
        ResponseEntity<List<Hospital>> hospitals = template.exchange(targetUrl, HttpMethod.GET, null, ptr);

        // Assert
        assertEquals(HttpStatus.OK.value(), hospitals.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON, hospitals.getHeaders().getContentType());
        assertEquals("SU", hospitals.getBody().get(0).getName());
    }

    @Test
    public void testCreateOne() {
        // Create hospital values
        Hospital hospital1 = new Hospital("SÄS", "Vänersborg");

        // Set values and make call
        URI targetUrl = UriComponentsBuilder.fromUriString("/api/v1/hospital")
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Hospital> entity = new HttpEntity<>(hospital1, headers);
        ResponseEntity<Hospital> response = template.exchange(targetUrl, HttpMethod.POST, entity, Hospital.class);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertNotNull(response.getBody().getId());
        assertEquals("SÄS", response.getBody().getName());
    }

    @Test
    public void testUpdateOne() {
        // Set db values
        HospitalJPA hospital1 = insertOneHospital();

        // Change  values and make call
        URI targetUrl = UriComponentsBuilder.fromUriString("/api/v1/hospital")
                .path("/" + hospital1.getId())
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create updated hospital object
        Hospital hospitalToUpdate = hospitalMapper.toDTO(hospital1);
        hospitalToUpdate.setName("Another");

        HttpEntity<Hospital> entity = new HttpEntity<>(hospitalToUpdate, headers);
        ResponseEntity response = template.exchange(targetUrl, HttpMethod.PUT, entity, Hospital.class);

        // Assert OK
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

        // Assert change
        HospitalJPA updatedHospital = hospitalRepository.findById(hospital1.getId()).orElse(null);
        assertNotNull(updatedHospital);
        assertEquals("Another", updatedHospital.getName());
    }

    @Test
    public void testDeleteOne() {
        // Set db values
        HospitalJPA hospital1 = insertOneHospital();

        // Change  values and make call
        URI targetUrl = UriComponentsBuilder.fromUriString("/api/v1/hospital")
                .path("/" + hospital1.getId())
                .build()
                .toUri();

        ResponseEntity response = template.exchange(targetUrl, HttpMethod.DELETE, null, Hospital.class);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

        // Assert change
        HospitalJPA deletedHospital = hospitalRepository.findById(hospital1.getId()).orElse(null);
        assertNull(deletedHospital);
    }

    private HospitalJPA insertOneHospital() {
        HospitalJPA hospital = new HospitalJPA("SU", "Gbg");
        return hospitalRepository.save(hospital);
    }
}

