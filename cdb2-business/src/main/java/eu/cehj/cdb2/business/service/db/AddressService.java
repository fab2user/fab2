package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.AddressDTO;
import eu.cehj.cdb2.entity.Address;

@Service
public interface AddressService extends BaseService<Address, Long> {

    public AddressDTO save(AddressDTO addressDTO) throws Exception;

    public List<AddressDTO> getAllDTO() throws Exception;

    public AddressDTO getDTO(Long id) throws Exception;

}