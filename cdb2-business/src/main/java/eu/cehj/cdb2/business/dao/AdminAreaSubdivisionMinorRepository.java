package eu.cehj.cdb2.business.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.AdminAreaSubdivisionMinor;


@RepositoryRestResource
public interface AdminAreaSubdivisionMinorRepository extends JpaRepository<AdminAreaSubdivisionMinor, Long>, QueryDslPredicateExecutor<AdminAreaSubdivisionMinor> {
    public AdminAreaSubdivisionMinor getByCode(String code);
}
