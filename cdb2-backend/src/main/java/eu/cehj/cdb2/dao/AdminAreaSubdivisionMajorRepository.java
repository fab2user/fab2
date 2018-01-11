package eu.cehj.cdb2.dao;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.AdminAreaSubdivisionMajor;


@RepositoryRestResource
public interface AdminAreaSubdivisionMajorRepository extends CrudRepository<AdminAreaSubdivisionMajor, Long>, QueryDslPredicateExecutor<AdminAreaSubdivisionMajor> {

    public AdminAreaSubdivisionMajor getByCode(String code);
}
