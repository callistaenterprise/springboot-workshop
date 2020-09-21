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
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.service.BedService;
import se.callista.springboot.rest.service.CareUnitService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CareUnitController {

    @Autowired
    CareUnitService careUnitService;

    @RequestMapping(value = "/careunit", method = RequestMethod.GET)
    public ResponseEntity<List<CareUnitJPA>> listCareunits()
    {
        List<CareUnitJPA> careunits = careUnitService.findAll();
        return new ResponseEntity<>(careunits, HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit/{id}", method = RequestMethod.GET)
    public ResponseEntity<CareUnitJPA> getOneCareunit(@PathVariable( "id" ) Long id) {
        CareUnitJPA careunit = careUnitService.findOne(id);
        return new ResponseEntity<>(careunit, HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit", method = RequestMethod.POST)
    public ResponseEntity<CareUnitJPA> createCareunit(@RequestBody CareUnitJPA careunit,
                                                      @RequestParam(value = "hospitalId", required = true) Long hospitalId) {
        CareUnitJPA savedCareunit = careUnitService.save(hospitalId, careunit);
        return new ResponseEntity<>(savedCareunit, HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCareunit(@PathVariable( "id" ) Long id,
                                        @RequestBody CareUnitJPA careunit) {
        careUnitService.update(careunit);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCareunit(@PathVariable( "id" ) Long id) {
        careUnitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
