package eu.cehj.cdb2.business.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.cehj.cdb2.entity.Synchronization;


@RepositoryRestResource
public interface SynchronizationRepository extends JpaRepository<Synchronization, Long>, QueryDslPredicateExecutor<Synchronization> {

    public Synchronization getByCountryOrderByEndDateDesc(Long countryId) throws Exception;

    @Query(value = "SELECT s1.*\n" +
            "FROM sync s1\n" +
            "LEFT JOIN sync s2\n" +
            "ON (s1.country = s2.country AND s1.id < s2.id)\n" +
            "WHERE s2.id IS NULL", nativeQuery = true)
    public List<Synchronization> getLastForEachCountry()throws Exception;
}
