package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.MunicipalityRepository;
import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;

@Service
public class MunicipalityServiceImpl extends BaseServiceImpl<Municipality, Long> implements MunicipalityService {

    @Autowired
    private EntityManager em;

    @Autowired
    private MunicipalityRepository repository;

    @Override
    public MunicipalityDTO save(final MunicipalityDTO municipalityDTO) throws Exception {
        Municipality entity = municipalityDTO.getId() == null ? new Municipality() : this.get(municipalityDTO.getId());

        BeanUtils.copyProperties(municipalityDTO, entity);

        entity = this.save(entity);

        return new MunicipalityDTO(entity);
    }

    @Override
    public List<MunicipalityDTO> getAllDTO() throws Exception {

        final List<Municipality> municipalities = this.repository.findAllByOrderByPostalCode();
        final List<MunicipalityDTO> municipalityDTOs = new ArrayList<>(municipalities.size());
        municipalities.forEach(m -> {
            final MunicipalityDTO dto = new MunicipalityDTO();
            dto.setId(m.getId());
            dto.setName(m.getName());
            dto.setPostalCode(m.getPostalCode());
            dto.setAdminAreaSubdivisionMajor(m.getAdminAreaSubdivisionMajor().getName());
            dto.setAdminAreaSubdivisionMiddle(m.getAdminAreaSubdivisionMiddle().getName());
            dto.setAdminAreaSubdivisionMinor(m.getAdminAreaSubdivisionMinor().getName());
            municipalityDTOs.add(dto);
        });
        return municipalityDTOs;
    }

    @Override
    public MunicipalityDTO getDTO(final Long id) throws Exception {

        return null;
    }

    @Override
    public void updateAreaFromStructure(final GeoDataStructure structure, final RecordBuilderHelper helper) {
        Municipality area = this.repository.getByPostalCode(structure.getZipCode());
        if (area == null) {
            area = this.populateEntity(structure, helper);
            this.repository.save(area);
        }
        helper.setMunicipality(area);

    }

    @Override
    public Municipality populateEntity(final GeoDataStructure structure, final RecordBuilderHelper helper) {
        final Municipality area = new Municipality();
        area.setPostalCode(structure.getZipCode());
        area.setName(structure.getCityName());
        area.setLatitude(structure.getxPos());
        area.setLongitude(structure.getyPos());
        area.setAdminAreaSubdivisionMajor(helper.getMajorArea());
        area.setAdminAreaSubdivisionMiddle(helper.getMiddleArea());
        area.setAdminAreaSubdivisionMinor(helper.getMinorArea());
        return area;
    }

    @Override
    public MunicipalityDTO populateDTOFromEntity(final Municipality municipality) {
        final MunicipalityDTO dto = new MunicipalityDTO();
        dto.setId(municipality.getId());
        dto.setName(municipality.getName());
        dto.setPostalCode(municipality.getPostalCode());
        if (municipality.getAdminAreaSubdivisionMajor() != null) {
            dto.setAdminAreaSubdivisionMajor(municipality.getAdminAreaSubdivisionMajor().getName());
        }
        if (municipality.getAdminAreaSubdivisionMiddle() != null) {
            dto.setAdminAreaSubdivisionMiddle(municipality.getAdminAreaSubdivisionMiddle().getName());
        }
        if (municipality.getAdminAreaSubdivisionMinor() != null) {
            dto.setAdminAreaSubdivisionMinor(municipality.getAdminAreaSubdivisionMinor().getName());
        }
        return dto;
    }

}
