package eu.cehj.cdb2.business.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.BatchDataUpdate;


@RepositoryRestResource
public interface BatchDataUpdateRepository extends JpaRepository<BatchDataUpdate, Long>, QueryDslPredicateExecutor<BatchDataUpdate> {

    public List<BatchDataUpdate> getByCountryId(Long countryId);
}
