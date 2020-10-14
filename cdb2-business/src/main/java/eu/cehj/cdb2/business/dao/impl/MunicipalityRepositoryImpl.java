package eu.cehj.cdb2.business.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.jpa.impl.JPAQuery;

import eu.cehj.cdb2.business.dao.custom.MunicipalityRepositoryCustom;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QAdminAreaSubdivisionMinor;
import eu.cehj.cdb2.entity.QMunicipality;

public class MunicipalityRepositoryImpl implements MunicipalityRepositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public List<Municipality> searchMunicipalitySetGeoNameIdForName(final String name, final String adminMinorCode) {

		final QMunicipality qMunicipality = QMunicipality.municipality;
		final QAdminAreaSubdivisionMinor qAdminAreaSubdivisionMinor = QAdminAreaSubdivisionMinor.adminAreaSubdivisionMinor;

		final JPAQuery<Municipality> jpaQuery = new JPAQuery(this.em);

		jpaQuery.from(qMunicipality);
		jpaQuery.innerJoin(qMunicipality.adminAreaSubdivisionMinor, qAdminAreaSubdivisionMinor);
		jpaQuery.where(qMunicipality.name.eq(name));
		jpaQuery.where(qAdminAreaSubdivisionMinor.code.eq(adminMinorCode));

		final List<Municipality>  municipalities = jpaQuery.fetch();

		return municipalities;
	}
}
