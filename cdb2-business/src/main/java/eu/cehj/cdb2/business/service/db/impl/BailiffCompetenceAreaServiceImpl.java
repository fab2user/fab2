package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.BailiffCompetenceAreaRepository;
import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;
import eu.cehj.cdb2.entity.Municipality;

@Service
public class BailiffCompetenceAreaServiceImpl extends BaseServiceImpl<BailiffCompetenceArea, Long> implements BailiffCompetenceAreaService {

    @Autowired
    private EntityManager em;

    @Autowired
    private BailiffCompetenceAreaRepository repository;


    public BailiffDTO populateDTOFromEntity(final Bailiff bailiff) {
        final BailiffDTO bailiffDTO = new BailiffDTO();
        bailiffDTO.setName(bailiff.getName());
        bailiffDTO.setId(bailiff.getId());
        bailiffDTO.setEmail(bailiff.getEmail());
        bailiffDTO.setPhone(bailiff.getPhone());
        final Address address = bailiff.getAddress();
        if (address != null) {
            bailiffDTO.setAddressId(address.getId());
            bailiffDTO.setAddress(address.getAddress());
            final Municipality municipality = address.getMunicipality();
            if (municipality != null) {
                bailiffDTO.setCity(municipality.getName());
                bailiffDTO.setPostalCode(municipality.getPostalCode());
                bailiffDTO.setMunicipalityId(municipality.getId());
            }
        }
        return bailiffDTO;
    }


    @Override
    public BailiffCompetenceAreaDTO save(final BailiffCompetenceAreaDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<BailiffCompetenceAreaDTO> getAllDTO() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public BailiffCompetenceAreaDTO getDTO(final Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
