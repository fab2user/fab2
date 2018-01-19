package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.GeoAreaRepository;
import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.GeoArea;

@Service
public class GeoAreaServiceImpl extends BaseServiceImpl<GeoArea, Long> implements GeoAreaService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GeoAreaRepository repository;

    @Autowired
    private MunicipalityService municipalityService;

    @Override
    public List<GeoAreaDTO> getAllDTO() throws Exception {

        final List<GeoArea> areas = this.repository.findAll();

        this.logger.warn("areas found: " + areas.size());
        return areas.stream().map(area -> this.populateDTOFromEntity(area)).collect(Collectors.toList());
    }

    public GeoAreaDTO populateDTOFromEntity(final GeoArea area) {
        final GeoAreaDTO dto = new GeoAreaDTO();
        dto.setId(area.getId());
        dto.setName(area.getName());
        final List<MunicipalityDTO> municipalityDTOs = area.getMunicipalities().stream().map(municipality -> {
            dto.addZipCode(municipality.getPostalCode());
            return this.municipalityService.populateDTOFromEntity(municipality);
        }).collect(Collectors.toList());
        dto.setMunicipalities(municipalityDTOs);
        return dto;
    }

}
