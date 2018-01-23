package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAUpdateClause;

import eu.cehj.cdb2.business.dao.GeoAreaRepository;
import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.GeoArea;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QMunicipality;

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

    public GeoAreaDTO populateEntity(final GeoAreaDTO dto) {
        GeoArea area = null;
        if(dto.getId()!= null) {
            area = this.repository.findOne(dto.getId());
        }else {
            area = new GeoArea();
            this.populateAreaFromDTO(area, dto);
        }
        // We first remove all municipalities related to area, in order to only bind the ones transmitted by dto
        area.getMunicipalities().stream().forEach(city -> {
            city.setGeoArea(null);
            try {
                this.municipalityService.save(city);
            } catch (final Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        });

        area.setMunicipalities(new ArrayList<Municipality>());
        this.repository.save(area);
        final Iterator<MunicipalityDTO>it = dto.getMunicipalities().iterator();
        while(it.hasNext()) {
            try {
                final Municipality municipality =  this.municipalityService.get(it.next().getId());
                if(municipality != null) {
                    municipality.setGeoArea(area);
                    this.municipalityService.save(municipality);
                }
            } catch (final Exception e) {
                this.logger.error(e.getMessage(),e);
            }
        }

        return dto;
    }

    @Override
    public GeoAreaDTO saveDTO(final GeoAreaDTO dto) throws Exception {
        return this.populateEntity(dto);
    }

    public void populateAreaFromDTO(final GeoArea area, final GeoAreaDTO dto) {
        area.setName(dto.getName());
    }

    @Override
    @Transactional
    public void delete(final Long id) throws Exception {
        super.delete(id);
        final QMunicipality municipality = QMunicipality.municipality;
        new JPAUpdateClause(this.em, municipality)
        .where(municipality.geoArea.id.eq(id))
        .setNull(municipality.geoArea)
        .execute();
    }
}
