package eu.cehj.cdb2.business.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.querydsl.core.types.dsl.StringPath;

import eu.cehj.cdb2.business.dao.custom.MunicipalityRepositoryCustom;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QMunicipality;

@RepositoryRestResource
public interface MunicipalityRepository extends JpaRepository<Municipality, Long>, QueryDslPredicateExecutor<Municipality>, QuerydslBinderCustomizer<QMunicipality>, MunicipalityRepositoryCustom {

	public Municipality getByPostalCodeAndName(String postalCode, String name);

	public List<Municipality> findAllByOrderByPostalCode();

	public Municipality getByName(String name);

	@Override
	default public void customize(final QuerydslBindings bindings, final QMunicipality root) {
		bindings.bind(String.class).first((final StringPath path, final String value) -> path.containsIgnoreCase(value));
	}
}
