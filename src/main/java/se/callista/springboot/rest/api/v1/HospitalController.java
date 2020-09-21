package se.callista.springboot.rest.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.domain.HospitalJPA;
import se.callista.springboot.rest.service.CareUnitService;
import se.callista.springboot.rest.service.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @RequestMapping(value = "/hospital", method = RequestMethod.GET)
    public ResponseEntity<List<HospitalJPA>> listHospitals()
    {
        List<HospitalJPA> hospitals = hospitalService.findAll();
        return new ResponseEntity<>(hospitals, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.GET)
    public ResponseEntity<HospitalJPA> getOneHospital(@PathVariable( "id" ) Long id) {
        HospitalJPA hospital = hospitalService.findOne(id);
        return new ResponseEntity<>(hospital, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital", method = RequestMethod.POST)
    public ResponseEntity<HospitalJPA> createHospital(@RequestBody HospitalJPA hospital) {
        HospitalJPA savedHospital = hospitalService.save(hospital);
        return new ResponseEntity<>(savedHospital, HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateHospital(@PathVariable( "id" ) Long id,
                                         @RequestBody HospitalJPA hospital) {
        hospitalService.update(hospital);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/hospital/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteHospital(@PathVariable( "id" ) Long id) {
        hospitalService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
