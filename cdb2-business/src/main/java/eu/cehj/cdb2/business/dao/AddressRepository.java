package eu.cehj.cdb2.business.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Address;


@RepositoryRestResource
public interface AddressRepository extends CrudRepository<Address, Long>, QueryDslPredicateExecutor<Address> {

}
