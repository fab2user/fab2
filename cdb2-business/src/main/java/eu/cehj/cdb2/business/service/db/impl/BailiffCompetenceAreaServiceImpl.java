package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.BailiffCompetenceAreaRepository;
import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaCustomDTO;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.dto.GeoAreaSimpleDTO;
import eu.cehj.cdb2.common.dto.SimpleCompetenceDTO;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;
import eu.cehj.cdb2.entity.Competence;
import eu.cehj.cdb2.entity.GeoArea;
import eu.cehj.cdb2.entity.QBailiffCompetenceArea;

@Service
public class BailiffCompetenceAreaServiceImpl extends BaseServiceImpl<BailiffCompetenceArea, BailiffCompetenceAreaDTO, Long, BailiffCompetenceAreaRepository> implements BailiffCompetenceAreaService {

	@Autowired
	private BailiffService bailiffService;

	@Autowired
	private CompetenceService competenceService;

	@Autowired
	private GeoAreaService areaService;

	private static final Logger LOGGER = LoggerFactory.getLogger(BailiffCompetenceAreaServiceImpl.class);

	@Override
	public BailiffCompetenceAreaDTO save(final BailiffCompetenceAreaDTO dto){
		final BailiffCompetenceArea entity = this.populateEntityFromDTO(dto);
		this.save(entity);
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public BailiffCompetenceArea populateEntityFromDTO(final BailiffCompetenceAreaDTO dto) {
		BailiffCompetenceArea entity = new BailiffCompetenceArea();
		if(dto.getId()!=null) {
			entity = this.get(dto.getId());
		}
		final BailiffDTO bailiffDTO = dto.getBailiff();
		final Bailiff bailiff =  this.bailiffService.get(bailiffDTO.getId());

		final Competence competence = this.competenceService.get(dto.getCompetence().getId());
		final List<GeoArea> existingAreas = entity.getAreas();
		existingAreas.clear();
		for( final GeoAreaSimpleDTO areaDTO: dto.getAreas()) {
			entity.getAreas().add(this.areaService.get(areaDTO.getId()));
		}
		entity.setBailiff(bailiff);
		entity.setCompetence(competence);
		entity.setDeleted(false);
		return entity;
	}

	@Override
	public BailiffCompetenceAreaDTO populateDTOFromEntity(final BailiffCompetenceArea entity){
		final BailiffCompetenceAreaDTO dto = new BailiffCompetenceAreaDTO();
		dto.setId(entity.getId());
		final BailiffDTO bailiffDTO = this.bailiffService.getDTO(entity.getBailiff().getId());
		dto.setBailiff(bailiffDTO);
		final CompetenceDTO competenceDTO = this.competenceService.getDTO(entity.getCompetence().getId());
		dto.setCompetence(competenceDTO);
		final List<GeoAreaSimpleDTO>areas = new ArrayList<>();
		for(final GeoArea area : entity.getAreas()) {
			final GeoAreaSimpleDTO areaDTO = this.areaService.getSimpleDTO(area.getId());
			areas.add(areaDTO);
		}
		dto.setAreas(areas);
		return dto;
	}

	public BailiffCompetenceAreaCustomDTO populateCustomDTOFromEntity(final BailiffCompetenceArea entity){
		final BailiffCompetenceAreaCustomDTO dto = new BailiffCompetenceAreaCustomDTO();
		dto.setId(entity.getId());
		final BailiffDTO bailiffDTO = this.bailiffService.getDTO(entity.getBailiff().getId());
		dto.setBailiff(bailiffDTO);
		final SimpleCompetenceDTO competenceDTO = this.competenceService.getSimpleDTO(entity.getCompetence().getId());
		dto.setCompetence(competenceDTO);
		final List<GeoAreaSimpleDTO>areas = new ArrayList<>();
		for(final GeoArea area : entity.getAreas()) {
			final GeoAreaSimpleDTO areaDTO = this.areaService.getSimpleDTO(area.getId());
			areas.add(areaDTO);
		}
		dto.setAreas(areas);
		return dto;
	}

	@Override
	public List<BailiffCompetenceAreaDTO> getAllDTO(final Long bailiffId){
		return this.repository.findByBailiffId(bailiffId).stream().map(entity -> {
			try {
				return this.populateDTOFromEntity(entity);
			} catch (final Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public List<BailiffCompetenceAreaCustomDTO> getAllSimpleDTO(final Long bailiffId){
		return this.repository.findByBailiffId(bailiffId).stream().map(entity -> {
			try {
				return this.populateCustomDTOFromEntity(entity);
			} catch (final Exception e) {
				LOGGER.error(e.getMessage(), e);
				return null;
			}
		}).collect(Collectors.toList());
	}

	@Override
	public Iterable<BailiffCompetenceArea> findAllForGeoArea(final GeoArea geoArea){
		final QBailiffCompetenceArea bailiffCompetenceArea = QBailiffCompetenceArea.bailiffCompetenceArea;
		final Predicate query =
				bailiffCompetenceArea.areas.contains(geoArea)
				.and(bailiffCompetenceArea.bailiff.deleted.isFalse()
						.or(bailiffCompetenceArea.bailiff.deleted.isNull()));
		return this.repository.findAll(query);
	}

	@Override
	public Iterable<BailiffCompetenceArea> findAllForBailiffId(final Long bailiffId) {
		// If bailiff in creation, we don't have id yet
		if(bailiffId == null) {
			return new ArrayList <>(0);
		}
		return this.repository.findAll(QBailiffCompetenceArea.bailiffCompetenceArea.bailiff.id.eq(bailiffId));
	}

}
