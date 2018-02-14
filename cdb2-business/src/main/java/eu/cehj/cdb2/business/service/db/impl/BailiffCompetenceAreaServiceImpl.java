package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.BailiffCompetenceAreaRepository;
import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.dto.GeoAreaSimpleDTO;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;
import eu.cehj.cdb2.entity.Competence;
import eu.cehj.cdb2.entity.GeoArea;
import eu.cehj.cdb2.entity.Instrument;
import eu.cehj.cdb2.entity.QBailiffCompetenceArea;

@Service
public class BailiffCompetenceAreaServiceImpl extends BaseServiceImpl<BailiffCompetenceArea, BailiffCompetenceAreaDTO, Long> implements BailiffCompetenceAreaService {

    @Autowired
    private EntityManager em;

    @Autowired
    private BailiffCompetenceAreaRepository repository;

    @Autowired
    private BailiffService bailiffService;

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private GeoAreaService areaService;

    @Override
    public BailiffCompetenceAreaDTO save(final BailiffCompetenceAreaDTO dto) throws Exception {
        final BailiffCompetenceArea entity = this.populateEntityFromDTO(dto);
        this.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public BailiffCompetenceArea populateEntityFromDTO(final BailiffCompetenceAreaDTO dto) throws Exception{
        BailiffCompetenceArea entity = new BailiffCompetenceArea();
        if(dto.getId()!=null) {
            entity = this.get(dto.getId());
        }
        final BailiffDTO bailiffDTO = dto.getBailiff();
        final Bailiff bailiff =  this.bailiffService.get(bailiffDTO.getId());

        final Competence competence = this.competenceService.get(dto.getCompetence().getId());
        final Instrument instrument = competence.getInstrument();
        final List<GeoArea> existingAreas = entity.getAreas();
        existingAreas.removeAll(existingAreas);
        final List<GeoAreaSimpleDTO> areas =  dto.getAreas();
        final Iterator<GeoAreaSimpleDTO> it = areas.iterator();
        while(it.hasNext()) {
            final GeoAreaSimpleDTO areaDTO = it.next();
            entity.getAreas().add(this.areaService.get(areaDTO.getId()));
        }
        entity.setBailiff(bailiff);
        entity.setCompetence(competence);
        return entity;
    }

    public BailiffCompetenceAreaDTO populateDTOFromEntity(final BailiffCompetenceArea entity)throws Exception{
        final BailiffCompetenceAreaDTO dto = new BailiffCompetenceAreaDTO();
        dto.setId(entity.getId());
        final BailiffDTO bailiffDTO = this.bailiffService.getDTO(entity.getBailiff().getId());
        dto.setBailiff(bailiffDTO);
        final CompetenceDTO competenceDTO = this.competenceService.getDTO(entity.getCompetence().getId());
        dto.setCompetence(competenceDTO);
        final List<GeoAreaSimpleDTO>areas = new ArrayList<GeoAreaSimpleDTO>();
        final Iterator<GeoArea> it = entity.getAreas().iterator();
        while(it.hasNext()) {
            final GeoAreaSimpleDTO areaDTO = this.areaService.getSimpleDTO(it.next().getId());
            areas.add(areaDTO);
        }
        dto.setAreas(areas);
        return dto;
    }

    @Override
    public List<BailiffCompetenceAreaDTO> getAllDTO() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BailiffCompetenceAreaDTO> getAllDTO(final Long bailiffId) throws Exception {
        final List<BailiffCompetenceAreaDTO> dtos = new ArrayList<>();
        final List<BailiffCompetenceArea> entities = this.repository.findByBailiffId(bailiffId);
        final Iterator<BailiffCompetenceArea> it = entities.iterator();
        while(it.hasNext()) {
            dtos.add(this.populateDTOFromEntity(it.next()));
        }

        return dtos;
    }

    @Override
    public BailiffCompetenceAreaDTO getDTO(final Long id) throws Exception {
        return null;
    }

    @Override
    public Iterable<BailiffCompetenceArea> findAllForGeoArea(final GeoArea geoArea)throws Exception{
        final QBailiffCompetenceArea bailiffCompetenceArea = QBailiffCompetenceArea.bailiffCompetenceArea;
        final Predicate query =
                bailiffCompetenceArea.areas.contains(geoArea)
                .and(bailiffCompetenceArea.bailiff.deleted.isFalse()
                        .or(bailiffCompetenceArea.bailiff.deleted.isNull()));
        return this.repository.findAll(query);
    }

}
