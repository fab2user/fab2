package eu.cehj.cdb2.business.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringPath;

import eu.cehj.cdb2.entity.QSynchronization;
import eu.cehj.cdb2.entity.Synchronization;


@RepositoryRestResource
public interface SynchronizationRepository extends JpaRepository<Synchronization, Long>, QueryDslPredicateExecutor<Synchronization>, QuerydslBinderCustomizer<QSynchronization> {

    public Synchronization getByCountryOrderByEndDateDesc(Long countryId) throws Exception;

    @Query(value = "SELECT s1.*\n" +
            "FROM sync s1\n" +
            "LEFT JOIN sync s2\n" +
            "ON (s1.country = s2.country AND s1.id < s2.id)\n" +
            "WHERE s2.id IS NULL", nativeQuery = true)
    public List<Synchronization> getLastForEachCountry()throws Exception;

    @Override
    default public void customize(final QuerydslBindings bindings, final QSynchronization root) {
        bindings.bind(String.class).first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
        bindings.bind(QSynchronization.synchronization.endDate).as("dateAfter").first((final DateTimePath<Date> path, final Date value) -> path.after(value));
        //            // Allow use of alias city instead of ugly address.municipality.name
        //            bindings.bind(QBailiff.bailiff.address.municipality.name).as("city").first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
        //            //FIXME: Filter by competence works only when when alone or associated with city, but breaks with bailiff.name...
        //            bindings.bind(QBailiff.bailiff.bailiffCompetenceAreas.any().competence.code).as("competence").first((final StringPath path, final String value) -> path.containsIgnoreCase(value));

    }
}
