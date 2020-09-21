package se.callista.springboot.rest.api.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.callista.springboot.rest.exception.RestClientException;
import se.callista.springboot.rest.service.HospitalService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/hospital", method = RequestMethod.GET)
    public ResponseEntity<List<Hospital>> listHospitals()
    {
        List<Hospital> hospitals = hospitalService.findAll();
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.GET)
    public ResponseEntity<Hospital> getOneHospital(@PathVariable( "id" ) Long id) {
        Hospital hospital = hospitalService.findOne(id);
        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital", method = RequestMethod.POST)
    public ResponseEntity<Hospital> createHospital(@Valid @RequestBody Hospital hospital) {

        // Log the incoming payload
        log.debug("New Hospital: {}", hospital.toString());

        Hospital savedHospital = hospitalService.save(hospital);
        return new ResponseEntity<>(savedHospital, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateHospital(@PathVariable( "id" ) Long id,
                                         @Valid @RequestBody Hospital hospital) {

        // Check id consistency
        if (!id.equals(hospital.getId())) {
            throw new RestClientException(messageSource.getMessage("hospital.update.inconsistent.ids", new String[]{Long.toString(id), Long.toString(hospital.getId())}, null));
        }

        hospitalService.update(hospital);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteHospital(@PathVariable( "id" ) Long id) {
        hospitalService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
