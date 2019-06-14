package eu.cehj.cdb2.business.service.db.impl;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.AddressRepository;
import eu.cehj.cdb2.business.dao.BailiffRepository;
import eu.cehj.cdb2.business.dao.LanguageRepository;
import eu.cehj.cdb2.business.dao.MunicipalityRepository;
import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.GeoAreaService;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.dto.CompetenceExportDTO;
import eu.cehj.cdb2.common.dto.GeoAreaSimpleDTO;
import eu.cehj.cdb2.common.dto.cdb.CDBResponse;
import eu.cehj.cdb2.common.dto.cdb.CompetentBodyDetail;
import eu.cehj.cdb2.common.exception.dto.CDBException;
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

	@Autowired
	BailiffCompetenceAreaService bailiffCompetenceAreaService;

	@Autowired
	CompetenceService competenceService;

	@Autowired
	GeoAreaService geoAreaService;

	@Value("${national.id.prefix}")
	String nationalIdPrefix;

	private static final Logger LOGGER = LoggerFactory.getLogger(BailiffServiceImpl.class);

	@Override
	public BailiffDTO save(final BailiffDTO dto) {
		final Bailiff bailiff = this.populateEntityFromDTO(dto);
		final Bailiff savedBailiff = this.repository.save(bailiff);
		dto.setId(savedBailiff.getId());
		if(dto.isToBeUpdated()) {
			this.saveCompetences(dto);
		}
		return this.populateDTOFromEntity(savedBailiff);
	}

	private void saveCompetences(final BailiffDTO dto){
		// First remove old data
		this.bailiffCompetenceAreaService.delete(this.bailiffCompetenceAreaService.findAllForBailiffId(dto.getId()));

		if(dto.getCompetences() != null) {
			for(final CompetenceDTO competenceDTO: dto.getCompetences()) {
				final BailiffCompetenceAreaDTO bca = new BailiffCompetenceAreaDTO();
				final GeoAreaSimpleDTO gas = new GeoAreaSimpleDTO();
				gas.setId(dto.getGeo().getId());
				bca.setAreas(Arrays.asList(gas));
				bca.setBailiff(dto);
				final CompetenceDTO comp = new CompetenceDTO();
				comp.setId(competenceDTO.getId());
				bca.setCompetence(comp);
				this.bailiffCompetenceAreaService.save(bca);
			}
		}
	}

	@Override
	public List<BailiffDTO> getAllDTO() {
		final List<Bailiff> bailiffs = this.repository.findAll();
		final List<BailiffDTO> dtos = new ArrayList<>(bailiffs.size());
		bailiffs.forEach(bailiff -> {
			BailiffDTO bailiffDTO = null;
			try {
				bailiffDTO = this.populateDTOFromEntity(bailiff);
			} catch (final Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
			dtos.add(bailiffDTO);
		});
		return dtos;
	}

	@Override
	public BailiffDTO populateDTOFromEntity(final Bailiff entity) {
		final BailiffDTO dto = new BailiffDTO();
		dto.setName(entity.getName());
		dto.setNationalIdPrefix(this.nationalIdPrefix);
		dto.setNationalId(this.removePrefixIfNeeded(entity.getNationalId()) );
		dto.setId(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setPhone(entity.getPhone());
		dto.setWebSite(entity.getWebSite());
		dto.setFax(entity.getFax());
		dto.setOpenHours(entity.getOpenHours());
		dto.setComments(entity.getComments());
		entity.getLanguages().forEach(lang -> dto.getLanguages().add(lang.getId()));
		if (!entity.getLangOfDetails().isEmpty()) {
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

		dto.setCompetences(entity.getBailiffCompetenceAreas().stream().map(bca -> {
			try {
				return this.competenceService.getDTO(bca.getCompetence().getId());
			} catch (final Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			return null;
		}).collect(Collectors.toList()));
		if(CollectionUtils.isNotEmpty(entity.getBailiffCompetenceAreas())){
			final GeoArea area = entity.getBailiffCompetenceAreas().get(0).getAreas().get(0);
			final GeoAreaSimpleDTO geoSimpleDTO = this.geoAreaService.getSimpleDTO(area.getId());
			dto.setGeo(geoSimpleDTO);
		}
		return dto;
	}

	@Override
	public Bailiff populateEntityFromDTO(final BailiffDTO dto) {
		final Bailiff entity = dto.getId() == null ? new Bailiff() : this.get(dto.getId());
		if (dto.getMunicipalityId() == null) {
			throw new CDBException("Municipality can not be null");
		}
		final Municipality municipality = this.municipalityRepository.getOne(dto.getMunicipalityId());
		if (municipality == null) {
			throw new CDBException("Municipality with id '" + dto.getMunicipalityId() + "' can not be found");
		}

		entity.setId(dto.getId());
		entity.setNationalId(this.addPrefixIfNeeded(dto.getNationalId()));
		entity.setName(dto.getName());
		entity.setPhone(dto.getPhone());
		entity.setEmail(dto.getEmail());
		entity.getLanguages().clear();
		dto.getLanguages().forEach(lang -> {
			final Language language = this.languageRepository.getOne(lang);
			entity.getLanguages().add(language);
		});
		// Small cheat since I assumed we can have several lang of details TODO: If it's confirmed there can be only one, then modify entity accordingly
		entity.getLangOfDetails().clear();
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
		entity.setOpenHours(dto.getOpenHours());
		entity.setComments(dto.getComments());
		return entity;
	}

	@Override
	public List<BailiffDTO> findAll(final Predicate predicate, final Pageable pageable) {
		//@formatter:off
		return this.repository.findAll(predicate, pageable).getContent()
				.stream()
				.map(b -> {
					try {
						return this.populateDTOFromEntity(b);
					} catch (final Exception e) {
						LOGGER.error(e.getMessage(),e);
					}
					return null;
				})
				.collect(Collectors.toList());
		//@formatter:on
	}

	@Override
	public List<BailiffDTO> getAllEvenDeletedDTO() {
		final List<Bailiff> entities = this.repository.getAllBailiffsEvenDeleted();
		final List<BailiffDTO> dtos = new ArrayList<>(entities.size());
		entities.forEach(entity -> {
			BailiffDTO dto = null;
			try {
				dto = this.populateDTOFromEntity(entity);
			} catch (final Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			dtos.add(dto);
		});
		return dtos;
	}

	@Override
	public List<BailiffExportDTO> getAllForExport() {
		final List<Bailiff> entities = this.repository.findAll();
		final List<BailiffExportDTO> dtos = new ArrayList<>(entities.size());
		for (final Bailiff entity : entities) {
			dtos.add(this.populateExportDTOFromEntity(entity));
		}
		return dtos;
	}

	@Override
	public BailiffExportDTO populateExportDTOFromEntity(final Bailiff entity){
		final BailiffExportDTO dto = new BailiffExportDTO();
		dto.setId(entity.getId());
		dto.setNationalId(entity.getNationalId());
		if (entity.getAddress() != null) {
			dto.setAddress1(entity.getAddress().getAddress1());
			dto.setAddress2(entity.getAddress().getAddress2());
			if (entity.getAddress().getMunicipality() != null) {
				dto.setMunicipality(entity.getAddress().getMunicipality().getName());
				dto.setPostalCode(entity.getAddress().getMunicipality().getPostalCode());
			}
		}
		dto.setFax(entity.getFax());

		if (!entity.getLanguages().isEmpty()) {
			dto.setAcceptedLanguages(new ArrayList<String>());
			for (final Language acceptedLanguage: entity.getLanguages()) {
				dto.getAcceptedLanguages().add(acceptedLanguage.getCode());
			}
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

	@Override
	public List<BailiffDTO> populateDTOsFromCDB(final CDBResponse cdbResponse){
		if((cdbResponse.getCompetentBodies() == null) || (cdbResponse.getCompetentBodies().isEmpty())) {
			return new ArrayList<>(0);
		}
		return cdbResponse.getCompetentBodies().stream().map(cb -> {
			final BailiffDTO dto = new BailiffDTO();
			if((cb.getDetails() != null) && (cb.getDetails().get(0)!=null)) {
				final CompetentBodyDetail detail = cb.getDetails().get(0);
				dto.setName( defaultString(detail.getName(),null));
				dto.setAddress1(defaultString(detail.getAddress(),null));
				dto.setPostalCode(defaultString(detail.getPostalCode(),null));
				dto.setCity(defaultString(detail.getMunicipality(),null));
				dto.setPhone(defaultString(detail.getTel(),null));
				dto.setFax(defaultString(detail.getFax(), null));
				dto.setLangDisplay(defaultString(detail.getLang(), null));
				final Boolean videoConference = detail.getVideoConference();
				if(videoConference != null) {
					dto.setVideoConferenceAvailable(videoConference);
				}
				return dto;
			}return null;
		}).
				filter(Objects::nonNull).
				collect(Collectors.toList());
	}

	private String addPrefixIfNeeded(final String nationalId) {
		if(isBlank(nationalId)) {
			return null;
		}
		if(isNotBlank(this.nationalIdPrefix) && !nationalId.startsWith(this.nationalIdPrefix)) {
			return this.nationalIdPrefix + nationalId;
		}
		return nationalId;
	}

	private String removePrefixIfNeeded(final String nationalId) {
		if(isBlank(nationalId)) {
			return null;
		}
		if(isNotBlank(this.nationalIdPrefix) && nationalId.startsWith(this.nationalIdPrefix)) {
			return nationalId.substring(this.nationalIdPrefix.length());
		}
		return nationalId;
	}
}
