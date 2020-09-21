package se.callista.springboot.rest.domain;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareUnitRepository extends PagingAndSortingRepository<CareUnitJPA, Long>, QuerydslPredicateExecutor<CareUnitJPA> {
}
