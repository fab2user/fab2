package eu.cehj.cdb2.business.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Language;


@RepositoryRestResource
public interface LanguageRepository extends CrudRepository<Language, Long>, QueryDslPredicateExecutor<Language> {

}
