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
import se.callista.springboot.rest.service.BedService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BedController {

    @Autowired
    BedService bedService;

    @RequestMapping(value = "/bed", method = RequestMethod.GET)
    public ResponseEntity<List<BedJPA>> listBeds()
    {
        List<BedJPA> devices = bedService.findAll();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @RequestMapping(value = "/bed/{id}", method = RequestMethod.GET)
    public ResponseEntity<BedJPA> getOneBed(@PathVariable( "id" ) Long id) {
        BedJPA bed = bedService.findOne(id);
        return new ResponseEntity<>(bed, HttpStatus.OK);
    }

    @RequestMapping(value = "/bed", method = RequestMethod.POST)
    public ResponseEntity<BedJPA> createBed(@RequestBody BedJPA bed,
                                            @RequestParam(value = "careunitId", required = true) Long careunitId) {
        BedJPA savedBed = bedService.save(careunitId, bed);
        return new ResponseEntity<>(savedBed, HttpStatus.OK);
    }

    @RequestMapping(value = "/bed/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateBed(@PathVariable( "id" ) Long id,
                                       @RequestBody BedJPA bed) {
        bedService.update(bed);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/bed/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteBed(@PathVariable( "id" ) Long id) {
        bedService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
