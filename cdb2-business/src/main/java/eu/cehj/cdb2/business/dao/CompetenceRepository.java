package eu.cehj.cdb2.business.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Competence;


@RepositoryRestResource
public interface CompetenceRepository extends JpaRepository<Competence, Long>, QueryDslPredicateExecutor<Competence> {

	public List<Competence> findAllByInstrumentId(Long instrumentId);
}
