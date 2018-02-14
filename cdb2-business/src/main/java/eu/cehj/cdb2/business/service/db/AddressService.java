package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.AddressDTO;
import eu.cehj.cdb2.entity.Address;

@Service
public interface AddressService extends BaseService<Address, AddressDTO, Long> {

}