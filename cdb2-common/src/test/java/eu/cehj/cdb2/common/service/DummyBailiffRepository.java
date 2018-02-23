package eu.cehj.cdb2.common.service;

import java.util.ArrayList;
import java.util.List;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Country;
import eu.cehj.cdb2.entity.Municipality;

public class DummyBailiffRepository {

    public List<BailiffDTO>getAllDTO(){
        final List<BailiffDTO> dtos = new ArrayList<BailiffDTO>();
        final Country country = new Country();
        country.setCode("FR");
        country.setName("France");
        final Municipality municipality = new Municipality();
        municipality.setId(1L);
        municipality.setName("Paris");
        municipality.setPostalCode("75000");
        final Address address = new Address();
        address.setId(1L);
        address.setCountry(country);
        address.setAddress("2 rue de la soif");
        address.setMunicipality(municipality);


        final BailiffDTO dto = new BailiffDTO();
        dto.setId(1L);
        dto.setAddress(address.getAddress());
        dto.setCity(address.getMunicipality().getName());
        dto.setEmail("roger@federer.com");
        dto.setName("Jean Keszky");
        dtos.add(dto);
        return dtos;
    }

}
