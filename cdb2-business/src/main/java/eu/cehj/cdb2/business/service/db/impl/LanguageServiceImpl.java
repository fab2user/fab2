package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.LanguageRepository;
import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.common.dto.LanguageDTO;
import eu.cehj.cdb2.entity.Language;

@Service
public class LanguageServiceImpl extends BaseServiceImpl<Language, Long> implements LanguageService {

    @Autowired
    private LanguageRepository repository;


    @Override
    public List<LanguageDTO> getAllDTO() throws Exception {

        final List<Language> languages = this.repository.findAll();
        final List<LanguageDTO> dtos = new ArrayList<LanguageDTO>(languages.size());
        languages.forEach( language -> {
            final LanguageDTO dto = this.populateDTOFromEntity(language);
            dtos.add(dto);
        });
        return dtos;
    }

    public LanguageDTO populateDTOFromEntity(final Language language) {
        final LanguageDTO dto = new LanguageDTO();
        dto.setId(language.getId());
        dto.setCode(language.getCode());
        dto.setLanguage(language.getLanguage());
        return dto;
    }

    public Language populateEntityFromDTO(final LanguageDTO dto) throws Exception {
        final Language entity = dto.getId() == null ? new Language() : this.get(dto.getId());
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setLanguage(dto.getLanguage());
        return entity;
    }

    @Override
    public LanguageDTO save(final LanguageDTO dto) throws Exception {
        final Language entity = this.populateEntityFromDTO( dto);
        this.repository.save(entity);
        return this.populateDTOFromEntity(entity);
    }

}
