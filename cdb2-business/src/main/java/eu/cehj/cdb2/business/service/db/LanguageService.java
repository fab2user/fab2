package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.LanguageDTO;
import eu.cehj.cdb2.entity.Language;

@Service
public interface LanguageService extends BaseService<Language, Long>{

    public List<LanguageDTO> getAllDTO() throws Exception;

    public LanguageDTO save(LanguageDTO dto) throws Exception;

}