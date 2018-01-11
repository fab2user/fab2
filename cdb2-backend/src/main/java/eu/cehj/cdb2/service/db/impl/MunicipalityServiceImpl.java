package eu.cehj.cdb2.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQuery;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QMunicipality;
import eu.cehj.cdb2.service.db.MunicipalityService;

@Service
public class MunicipalityServiceImpl extends BaseServiceImpl<Municipality, Long> implements MunicipalityService {

    @Autowired
    private EntityManager em;

    @Override
    public MunicipalityDTO save(final MunicipalityDTO municipalityDTO) throws Exception {
        Municipality entity = municipalityDTO.getId() == null ? new Municipality() : this.get(municipalityDTO.getId());

        BeanUtils.copyProperties(municipalityDTO, entity);

        entity = this.save(entity);

        return new MunicipalityDTO(entity);
    }

    @Override
    public List<MunicipalityDTO> getAllDTO() throws Exception {

        final QMunicipality municipality = QMunicipality.municipality;

        final List<Municipality> municipalities = new JPAQuery<Municipality>(this.em)
                .from(municipality)
                .fetch();
        final List<MunicipalityDTO> municipalityDTOs = new ArrayList<>(municipalities.size());
        municipalities.forEach(m -> {
            municipalityDTOs.add(new MunicipalityDTO(m));
        });
        return municipalityDTOs;
    }

    @Override
    public MunicipalityDTO getDTO(final Long id) throws Exception {

        return null;
    }

    @Override
    public Municipality populateEntity(final GeoDataStructure structure) {
        // TODO Auto-generated method stub
        return null;
    }
}
