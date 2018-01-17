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

        final List<Language> languages = (List<Language>) this.repository.findAll();
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

}
