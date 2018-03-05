package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.LanguageRepository;
import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.common.dto.LanguageDTO;
import eu.cehj.cdb2.entity.Language;

@Service
public class LanguageServiceImpl extends BaseServiceImpl<Language, LanguageDTO, Long, LanguageRepository> implements LanguageService {

    @Override
    public LanguageDTO populateDTOFromEntity(final Language language) {
        final LanguageDTO dto = new LanguageDTO();
        dto.setId(language.getId());
        dto.setCode(language.getCode());
        dto.setLanguage(language.getLanguage());
        return dto;
    }

    @Override
    public Language populateEntityFromDTO(final LanguageDTO dto) throws Exception {
        final Language entity = dto.getId() == null ? new Language() : this.get(dto.getId());
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setLanguage(dto.getLanguage());
        return entity;
    }

}
