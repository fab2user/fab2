package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.AddressRepository;
import eu.cehj.cdb2.business.dao.BailiffRepository;
import eu.cehj.cdb2.business.dao.MunicipalityRepository;
import eu.cehj.cdb2.business.exception.CDBException;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.Municipality;

@Service
public class BailiffServiceImpl extends BaseServiceImpl<Bailiff, Long> implements BailiffService {

    @Autowired
    private EntityManager em;

    @Autowired
    private BailiffRepository repository;

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    MunicipalityRepository municipalityRepository;

    @Override
    public BailiffDTO save(final BailiffDTO dto) throws Exception {
        final Bailiff entity = dto.getId() == null ? new Bailiff() : this.get(dto.getId());
        final Bailiff bailiff = this.populateEntityFromDTO(entity, dto);
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
    public BailiffDTO getDTO(final Long id) throws Exception {
        final Bailiff entity = this.get(id);
        if (entity != null) {
            return this.populateDTOFromEntity(entity);
        }
        return new BailiffDTO();
    }

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

    public Bailiff populateEntityFromDTO(final Bailiff entity, final BailiffDTO dto) throws Exception {
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
        Address address = null;
        if (dto.getAddressId() != null) {
            address = this.addressRepository.getOne(dto.getAddressId());
            if (address == null) {
                throw new CDBException("Address with id '" + dto.getAddressId() + "' can not be found");
            }
        } else {
            address = new Address();
            address.setAddress(dto.getAddress());
        }
        address.setMunicipality(municipality);
        this.addressRepository.save(address);
        entity.setAddress(address);

        return entity;
    }

    @Override
    public Page<BailiffDTO> findAll(final Predicate predicate, final Pageable pageable) throws Exception {
        final Page<Bailiff> entities = this.repository.findAll(predicate, pageable);
        final List<BailiffDTO> dtos = new ArrayList<>();
        final Iterator<Bailiff> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(this.populateDTOFromEntity(it.next()));
        }
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

}
