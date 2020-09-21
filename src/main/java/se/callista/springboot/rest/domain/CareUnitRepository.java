package se.callista.springboot.rest.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareUnitRepository extends CrudRepository<CareUnitJPA, Long> {
}
