package eu.cehj.cdb2.business.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.CountryOfSync;


@RepositoryRestResource
public interface CountryOfSyncRepository extends JpaRepository<CountryOfSync, Long>, QueryDslPredicateExecutor<CountryOfSync> {

    public CountryOfSync getByName(String countryName);

    public CountryOfSync getByCountryCode(String countryCode);
}
