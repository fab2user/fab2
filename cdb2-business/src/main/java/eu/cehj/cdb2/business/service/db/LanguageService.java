package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.LanguageDTO;
import eu.cehj.cdb2.entity.Language;

@Service
public interface LanguageService extends BaseService<Language, LanguageDTO, Long>{

    public Language getLangByCode(String code)throws Exception;

}