package eu.cehj.cdb2.business.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.QBailiff;


@RepositoryRestResource
public interface BailiffRepository extends JpaRepository<Bailiff, Long>, QueryDslPredicateExecutor<Bailiff>, QuerydslBinderCustomizer<QBailiff> {

    @Override
    default public void customize(final QuerydslBindings bindings, final QBailiff root) {
        bindings.bind(String.class).first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
        // Allow use of alias city instead of ugly address.municipality.name
        bindings.bind(QBailiff.bailiff.address.municipality.name).as("city").first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
        bindings.bind(QBailiff.bailiff.address.municipality.postalCode).as("postalCode").first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
        //FIXME: Filter by competence works only when when alone or associated with city, but breaks with bailiff.name...
        bindings.bind(QBailiff.bailiff.bailiffCompetenceAreas.any().competence.code).as("competence").first((final StringPath path, final String value) -> path.containsIgnoreCase(value));

    }

    @Query("SELECT b FROM Bailiff b WHERE b.deleted = true or b.deleted = false or b.deleted is null")
    public List<Bailiff> getAllBailiffsEvenDeleted()throws Exception;

    @Override
    @Query("SELECT b FROM Bailiff b where b.deleted = false or b.deleted is null")
    public List<Bailiff> findAll();
}
