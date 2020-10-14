package eu.cehj.cdb2.business.dao.custom;

import java.util.List;

import eu.cehj.cdb2.entity.Municipality;

public interface MunicipalityRepositoryCustom {

	List<Municipality> searchMunicipalitySetGeoNameIdForName(String name, final String adminMinorCode);

}
