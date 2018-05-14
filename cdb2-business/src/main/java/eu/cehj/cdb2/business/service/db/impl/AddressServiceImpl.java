package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.AddressRepository;
import eu.cehj.cdb2.business.service.db.AddressService;
import eu.cehj.cdb2.common.dto.AddressDTO;
import eu.cehj.cdb2.entity.Address;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, AddressDTO, Long, AddressRepository> implements AddressService {

    @Override
    public Address populateEntityFromDTO(final AddressDTO dto) {
        // We don't want to return anything here
        return null;
    }

    @Override
    public AddressDTO populateDTOFromEntity(final Address entity) {
        // We don't want to return anything here
        return null;
    }


}
