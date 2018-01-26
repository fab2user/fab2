package eu.cehj.cdb2.business.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.BailiffCompetenceArea;


@RepositoryRestResource
public interface BailiffCompetenceAreaRepository extends JpaRepository<BailiffCompetenceArea, Long>, QueryDslPredicateExecutor<BailiffCompetenceArea> {

}
