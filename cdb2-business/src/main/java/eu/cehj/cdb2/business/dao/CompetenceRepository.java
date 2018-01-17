package eu.cehj.cdb2.business.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Competence;


@RepositoryRestResource
public interface CompetenceRepository extends CrudRepository<Competence, Long>, QueryDslPredicateExecutor<Competence> {

}
