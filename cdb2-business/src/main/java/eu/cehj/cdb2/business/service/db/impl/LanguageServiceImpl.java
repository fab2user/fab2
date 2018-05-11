package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.LanguageRepository;
import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.common.dto.LanguageDTO;
import eu.cehj.cdb2.entity.Language;
import eu.cehj.cdb2.entity.QLanguage;

@Service
public class LanguageServiceImpl extends BaseServiceImpl<Language, LanguageDTO, Long, LanguageRepository> implements LanguageService {

    @Override
    public Language getLangByCode(final String code) {
        return this.repository.findOne(QLanguage.language1.code.eq(code));
    }

}
