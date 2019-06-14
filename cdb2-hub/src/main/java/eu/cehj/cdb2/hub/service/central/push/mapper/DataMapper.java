package eu.cehj.cdb2.hub.service.central.push.mapper;

import java.util.List;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.chj.cdb2.common.Data;

public interface DataMapper {

    public Data map(CountryOfSync cos, List<BailiffDTO> dtos);
}
