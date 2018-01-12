package eu.cehj.cdb2.business.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Municipality;


@RepositoryRestResource
public interface MunicipalityRepository extends CrudRepository<Municipality, Long>, QueryDslPredicateExecutor<Municipality> {

    public Municipality getByPostalCode(String postalCode);
}
