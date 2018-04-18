package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.AddressRepository;
import eu.cehj.cdb2.business.dao.BailiffRepository;
import eu.cehj.cdb2.business.dao.LanguageRepository;
import eu.cehj.cdb2.business.dao.MunicipalityRepository;
import eu.cehj.cdb2.business.exception.CDBException;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.dto.CompetenceExportDTO;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;
import eu.cehj.cdb2.entity.GeoArea;
import eu.cehj.cdb2.entity.Language;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QLanguage;

@Service
public class BailiffServiceImpl extends BaseServiceImpl<Bailiff, BailiffDTO, Long, BailiffRepository> implements BailiffService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MunicipalityRepository municipalityRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public BailiffDTO save(final BailiffDTO dto) throws Exception {
        final Bailiff bailiff = this.populateEntityFromDTO(dto);
        this.repository.save(bailiff);
        return this.populateDTOFromEntity(bailiff);
    }

    @Override
    public List<BailiffDTO> getAllDTO() throws Exception {
        final List<Bailiff> bailiffs = this.repository.findAll();
        final List<BailiffDTO> dtos = new ArrayList<BailiffDTO>(bailiffs.size());
        bailiffs.forEach(bailiff -> {
            final BailiffDTO bailiffDTO = this.populateDTOFromEntity(bailiff);
            dtos.add(bailiffDTO);
        });
        return dtos;
    }

    @Override
    public BailiffDTO populateDTOFromEntity(final Bailiff entity) {
        final BailiffDTO dto = new BailiffDTO();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setWebSite(entity.getWebSite());
        dto.setFax(entity.getFax());
        entity.getLanguages().forEach(lang -> dto.getLanguages().add(lang.getId()));
        if (entity.getLangOfDetails().size() > 0) {
            dto.setLangOfDetails(entity.getLangOfDetails().get(0).getId());
        }

        final Address address = entity.getAddress();
        if (address != null) {
            dto.setAddressId(address.getId());
            dto.setAddress1(address.getAddress1());
            dto.setAddress2(address.getAddress2());
            final Municipality municipality = address.getMunicipality();
            if (municipality != null) {
                dto.setCity(municipality.getName());
                dto.setPostalCode(municipality.getPostalCode());
                dto.setMunicipalityId(municipality.getId());
            }
        }
        return dto;
    }

    @Override
    public Bailiff populateEntityFromDTO(final BailiffDTO dto) throws Exception {
        final Bailiff entity = dto.getId() == null ? new Bailiff() : this.get(dto.getId());
        if (dto.getMunicipalityId() == null) {
            throw new CDBException("Municipality can not be null");
        }
        final Municipality municipality = this.municipalityRepository.getOne(dto.getMunicipalityId());
        if (municipality == null) {
            throw new CDBException("Municipality with id '" + dto.getMunicipalityId() + "' can not be found");
        }

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.getLanguages().clear();
        dto.getLanguages().forEach(lang -> {
            final Language language = this.languageRepository.getOne(lang);
            entity.getLanguages().add(language);
        });
        //        final Long idLangOfDetails = dto.getLangOfDetails();
        // Small cheat since I assumed we can have several lang of details TODO: If it's confirmed there can be only one, then modify entity accordingly
        entity.getLangOfDetails().clear();
        //        if (idLangOfDetails != null) {
        //            final Language lang = this.languageRepository.getOne(idLangOfDetails);
        //            entity.getLangOfDetails().add(lang);
        //        }
        // Set default lang of details to EN, as requested by client
        final Language enLang =  this.languageRepository.findOne(QLanguage.language1.code.eq("EN"));
        if(enLang != null) {
            entity.getLangOfDetails().add(enLang);
        }
        Address address = null;
        if (dto.getAddressId() != null) {
            address = this.addressRepository.getOne(dto.getAddressId());
            if (address == null) {
                throw new CDBException("Address with id '" + dto.getAddressId() + "' can not be found");
            }
        } else {
            address = new Address();
        }
        address.setAddress1(dto.getAddress1());
        address.setAddress2(dto.getAddress2());
        address.setMunicipality(municipality);
        this.addressRepository.save(address);
        entity.setAddress(address);
        entity.setWebSite(dto.getWebSite());

        return entity;
    }

    @Override
    public List<BailiffDTO> findAll(final Predicate predicate, final Pageable pageable) throws Exception {
        //        final Page<Bailiff> entities = this.repository.findAll(predicate, pageable);
        //@formatter:off
        return this.repository.findAll(predicate, pageable).getContent()
                .stream()
                .map(b -> this.populateDTOFromEntity(b))
                .collect(Collectors.toList());
        //@formatter:on
        //        final Page<BailiffDTO> page = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        //        return page;
    }

    @Override
    public List<BailiffDTO> getAllEvenDeletedDTO() throws Exception {
        final List<Bailiff> entities = this.repository.getAllBailiffsEvenDeleted();
        final List<BailiffDTO> dtos = new ArrayList<BailiffDTO>(entities.size());
        entities.forEach(entity -> {
            final BailiffDTO dto = this.populateDTOFromEntity(entity);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<BailiffExportDTO> getAllForExport() throws Exception {
        final List<Bailiff> entities = this.repository.findAll();
        final List<BailiffExportDTO> dtos = new ArrayList<>(entities.size());
        for (final Bailiff entity : entities) {
            dtos.add(this.populateExportDTOFromEntity(entity));
        }
        return dtos;
    }

    @Override
    public BailiffExportDTO populateExportDTOFromEntity(final Bailiff entity) throws Exception {
        final BailiffExportDTO dto = new BailiffExportDTO();
        if (entity.getAddress() != null) {
            dto.setAddress1(entity.getAddress().getAddress1());
            dto.setAddress2(entity.getAddress().getAddress2());
            if (entity.getAddress().getMunicipality() != null) {
                dto.setMunicipality(entity.getAddress().getMunicipality().getName());
                dto.setPostalCode(entity.getAddress().getMunicipality().getPostalCode());
            }
        }
        dto.setFax(entity.getFax());
        if (entity.getLanguages().size() > 0) {
            dto.setLang(entity.getLanguages().get(0).getLanguage());
        }

        dto.setName(entity.getName());

        dto.setTel(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setVideoConference(entity.isVideoConference());
        final List<CompetenceExportDTO> competences = new ArrayList<>();
        for (final BailiffCompetenceArea bca : entity.getBailiffCompetenceAreas()) {
            for (final GeoArea area : bca.getAreas()) {
                final CompetenceExportDTO competence = new CompetenceExportDTO();
                competence.setGeoAreaId(Long.toString(area.getId())); // TODO: Check if we need to use Area name instead (seems like according to example xml file)
                competence.setInstrument(bca.getCompetence().getInstrument().getCode());
                competence.setType(bca.getCompetence().getCode());
                competences.add(competence);
            }
        }
        dto.setCompetences(competences);
        return dto;
    }

}
