package eu.cehj.cdb2.business.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public Synchronization getByCountryOrderByEndDateDesc(Long countryId) ;

    @Query(value = "SELECT s1.*\n" +
            "FROM sync s1\n" +
            "LEFT JOIN sync s2\n" +
            "ON (s1.country = s2.country AND s1.id < s2.id)\n" +
            "WHERE s2.id IS NULL", nativeQuery = true)
    public List<Synchronization> getLastForEachCountry();

    @Query(value = "select s.* from sync s \n" +
            "inner join( select country, max(modified_on) as maxDate from  sync group by country) \n" +
            "s2 on s.country = ?1 and s.country = s2.country and s.modified_on = s2.maxDate;", nativeQuery = true)
    public Synchronization getLastForCountry(Long countryId);


    @Override
    default void customize(final QuerydslBindings bindings, final QSynchronization root) {
        bindings.bind(String.class).first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
        final Calendar calStart = new GregorianCalendar(1900,0,1);
        final Date startDate = new Date(calStart.getTimeInMillis());
        final Calendar calEnd = new GregorianCalendar(2030,0,1);
        final Date endDate = new Date(calEnd.getTimeInMillis());
        bindings.bind(QSynchronization.synchronization.startDate).as("dateBefore").first((final DateTimePath<Date> path, final Date value) -> path.between(startDate, value));
        bindings.bind(QSynchronization.synchronization.endDate).as("dateAfter").first((final DateTimePath<Date> path, final Date value) -> path.between(value, endDate));

    }
}
