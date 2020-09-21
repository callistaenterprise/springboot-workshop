package se.callista.springboot.rest.domain;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends PagingAndSortingRepository<HospitalJPA, Long>, JpaSpecificationExecutor<HospitalJPA> {
}
