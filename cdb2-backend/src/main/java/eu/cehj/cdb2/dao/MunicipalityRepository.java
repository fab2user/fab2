package eu.cehj.cdb2.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.db.Municipality;


@RepositoryRestResource
public interface MunicipalityRepository extends CrudRepository<Municipality, Long> {

}
