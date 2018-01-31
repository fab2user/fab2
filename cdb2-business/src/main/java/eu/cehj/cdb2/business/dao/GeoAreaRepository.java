package eu.cehj.cdb2.business.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

import eu.cehj.cdb2.entity.GeoArea;
import eu.cehj.cdb2.entity.QGeoArea;

@RepositoryRestResource
public interface GeoAreaRepository extends JpaRepository<GeoArea, Long>, QueryDslPredicateExecutor<GeoArea>, QuerydslBinderCustomizer<QGeoArea> {

    @Override
    default void customize(final QuerydslBindings bindings, final QGeoArea root) {
        bindings.bind(String.class).first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
    }
}
