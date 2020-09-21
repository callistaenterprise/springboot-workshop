package se.callista.springboot.rest.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedRepository extends CrudRepository<BedJPA, Long> {
}
