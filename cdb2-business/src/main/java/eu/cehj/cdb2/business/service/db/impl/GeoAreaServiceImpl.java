package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAUpdateClause;

import eu.cehj.cdb2.business.dao.GeoAreaRepository;
import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.GeoAreaSimpleDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;
import eu.cehj.cdb2.entity.GeoArea;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QMunicipality;

@Service
public class GeoAreaServiceImpl extends BaseServiceImpl<GeoArea, GeoAreaDTO, Long, GeoAreaRepository> implements GeoAreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoAreaServiceImpl.class);

    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private BailiffCompetenceAreaService bailiffCompetenceAreaService;

    @Override
    public List<GeoAreaDTO> getAllDTO() {

        final List<GeoArea> areas = this.repository.findAll();

        LOGGER.debug("areas found: {}", areas.size());
        return areas.stream().map(this::populateDTOFromEntity).collect(Collectors.toList());
    }

    @Override
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
        if (dto.getId() != null) {
            area = this.repository.findOne(dto.getId());
        } else {
            area = new GeoArea();
            this.populateAreaFromDTO(area, dto);
        }
        // We first remove all municipalities related to area, in order to only bind the ones transmitted by dto
        for(final Municipality city: area.getMunicipalities()) {
            city.setGeoArea(null);
            this.municipalityService.save(city);
        }

        area.setMunicipalities(new ArrayList<Municipality>());
        this.repository.save(area);
        for(final MunicipalityDTO cityDTO: dto.getMunicipalities()) {
            final Municipality municipality = this.municipalityService.get(cityDTO.getId());
            if (municipality != null) {
                municipality.setGeoArea(area);
                this.municipalityService.save(municipality);
            }
        }
        return dto;
    }

    @Override
    public GeoAreaDTO saveDTO(final GeoAreaDTO dto) {
        return this.populateEntity(dto);
    }

    public void populateAreaFromDTO(final GeoArea area, final GeoAreaDTO dto) {
        area.setName(dto.getName());
    }

    @Override
    @Transactional
    public void delete(final Long id) {

        //We first have to check that this area isn't bound to a bailiff
        final Iterable<BailiffCompetenceArea> bcas = this.bailiffCompetenceAreaService.findAllForGeoArea(this.get(id));
        if(bcas.iterator().hasNext()) {
            final BailiffCompetenceArea bca = bcas.iterator().next();
            throw new CDBException("Deletion impossible: geo area is bound to bailiff with ID " + bca.getBailiff().getId());
        }
        super.delete(id);
        final QMunicipality municipality = QMunicipality.municipality;
        new JPAUpdateClause(this.em, municipality)
        .where(municipality.geoArea.id.eq(id))
        .setNull(municipality.geoArea)
        .execute();
    }

    @Override
    public GeoAreaSimpleDTO getSimpleDTO(final Long id) {
        final GeoArea entity = this.get(id);
        return this.populateSimpleDTOFromEntity(entity);
    }

    public GeoAreaSimpleDTO populateSimpleDTOFromEntity(final GeoArea entity) {
        final GeoAreaSimpleDTO dto = new GeoAreaSimpleDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public Page<GeoAreaDTO> findAll(final Predicate predicate, final Pageable pageable)  {
        final Page<GeoArea> entities = this.repository.findAll(predicate, pageable);
        final List<GeoAreaDTO> dtos = new ArrayList<>();
        final Iterator<GeoArea> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(this.populateDTOFromEntity(it.next()));
        }
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

    @Override
    public GeoArea populateEntityFromDTO(final GeoAreaDTO dto) {
        return null;
    }

    @Override
    public List<GeoAreaSimpleDTO> getAllSimpleDTO() {
        return this.getAll().stream().map(
                this::populateSimpleDTOFromEntity
                ).collect(Collectors.toList());
    }

}
