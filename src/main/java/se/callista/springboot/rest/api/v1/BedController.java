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
import se.callista.springboot.rest.exception.RestClientException;
import se.callista.springboot.rest.service.BedService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BedController {

    @Autowired
    BedService bedService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/bed", method = RequestMethod.GET)
    public ResponseEntity<List<Bed>> listBeds()
    {
        List<Bed> devices = bedService.findAll();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @RequestMapping(value = "/bed/{id}", method = RequestMethod.GET)
    public ResponseEntity<Bed> getOneBed(@PathVariable( "id" ) Long id) {
        Bed bed = bedService.findOne(id);
        return new ResponseEntity<>(bed, HttpStatus.OK);
    }

    @RequestMapping(value = "/bed", method = RequestMethod.POST)
    public ResponseEntity<Bed> createBed(@Valid @RequestBody Bed bed,
                                         @RequestParam(value = "careunitId", required = true) Long careunitId) {

        // Log the incoming payload
        log.debug("New Bed: {}, connect to careunit Id {}", bed.toString(), careunitId);

        Bed savedBed = bedService.save(careunitId, bed);
        return new ResponseEntity<>(savedBed, HttpStatus.OK);
    }

    @RequestMapping(value = "/bed/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateBed(@PathVariable( "id" ) Long id,
                                    @Valid @RequestBody Bed bed) {

        // Check id consistency
        if (!id.equals(bed.getId())) {
            throw new RestClientException(messageSource.getMessage("bed.update.inconsistent.ids", new String[]{Long.toString(id), Long.toString(bed.getId())}, null));
        }

        bedService.update(bed);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/bed/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBed(@PathVariable( "id" ) Long id) {
        bedService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
