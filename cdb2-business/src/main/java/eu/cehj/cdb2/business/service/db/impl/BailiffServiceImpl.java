package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.BailiffRepository;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.search.model.BailiffSearch;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.entity.QBailiff;

@Service
public class BailiffServiceImpl extends BaseServiceImpl<Bailiff, Long> implements BailiffService {

    @Autowired
    private EntityManager em;

    @Autowired
    private BailiffRepository repository;

    @Override
    public BailiffDTO save(final BailiffDTO bailiffDTO) throws Exception {
        final Bailiff entity = bailiffDTO.getId() == null ? new Bailiff() : this.get(bailiffDTO.getId());

        //TODO
        return new BailiffDTO();
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
    public BailiffDTO getDTO(final Long id) throws Exception {

        return null;
    }

    @Override
    public Iterable<BailiffDTO> searchDTO(final BailiffSearch searchParams)throws Exception{

        final Predicate predicate = QBailiff.bailiff.name.like("HJGJH");
        final Iterable<Bailiff> bailiffs = this.repository.findAll(predicate);
        final List<BailiffDTO> dtos = new ArrayList<BailiffDTO>();
        bailiffs.forEach(bailiff -> {
            final BailiffDTO dto = this.populateDTOFromEntity(bailiff);
            dtos.add(dto);
        });
        return dtos;
    }

    public BailiffDTO populateDTOFromEntity(final Bailiff bailiff) {
        final BailiffDTO bailiffDTO = new BailiffDTO();
        bailiffDTO.setName(bailiff.getName());
        bailiffDTO.setId(bailiff.getId());
        bailiffDTO.setEmail(bailiff.getEmail());
        bailiffDTO.setPhone(bailiff.getPhone());
        final Address address = bailiff.getAddress();
        if(address != null) {
            bailiffDTO.setAddressId(address.getId());
            bailiffDTO.setAddress(address.getAddress());
            final Municipality municipality = address.getMunicipality();
            if(municipality != null) {
                bailiffDTO.setMunicipality(municipality.getName());
                bailiffDTO.setPostalCode(municipality.getPostalCode());
            }
        }
        return bailiffDTO;
    }


}
