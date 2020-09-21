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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.callista.springboot.rest.domain.BedJPA;
import se.callista.springboot.rest.domain.CareUnitJPA;
import se.callista.springboot.rest.exception.RestClientException;
import se.callista.springboot.rest.service.BedService;
import se.callista.springboot.rest.service.CareUnitService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CareUnitController {

    @Autowired
    CareUnitService careUnitService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/careunit", method = RequestMethod.GET)
    public ResponseEntity<List<CareUnit>> listCareunits()
    {
        List<CareUnit> careunits = careUnitService.findAll();
        return new ResponseEntity<>(careunits, HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit/{id}", method = RequestMethod.GET)
    public ResponseEntity<CareUnit> getOneCareunit(@PathVariable( "id" ) Long id) {
        CareUnit careunit = careUnitService.findOne(id);
        return new ResponseEntity<>(careunit, HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit", method = RequestMethod.POST)
    public ResponseEntity<CareUnit> createCareunit(@Valid @RequestBody CareUnit careunit,
                                                   @RequestParam(value = "hospitalId", required = true) Long hospitalId) {
        // Log the incoming payload
        log.debug("New Care unit: {}, connect to hospital {}", careunit.toString(), hospitalId);

        CareUnit savedCareunit = careUnitService.save(hospitalId, careunit);
        return new ResponseEntity<>(savedCareunit, HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCareunit(@PathVariable( "id" ) Long id,
                                         @Valid @RequestBody CareUnit careunit) {
        // Check id consistency
        if (!id.equals(careunit.getId())) {
            throw new RestClientException(messageSource.getMessage("careunit.update.inconsistent.ids", new String[]{Long.toString(id), Long.toString(careunit.getId())}, null));
        }

        careUnitService.update(careunit);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/careunit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCareunit(@PathVariable( "id" ) Long id) {
        careUnitService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
