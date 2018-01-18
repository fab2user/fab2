package eu.cehj.cdb2.business.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Municipality;


@RepositoryRestResource
public interface MunicipalityRepository extends JpaRepository<Municipality, Long>, QueryDslPredicateExecutor<Municipality> {

    public Municipality getByPostalCode(String postalCode);

    public List<Municipality> findAllByOrderByPostalCode();
}
