package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.LanguageRepository;
import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.common.dto.LanguageDTO;
import eu.cehj.cdb2.entity.Language;

@Service
// We just need to declare empty class, since everything is done in generic parent class !
public class LanguageServiceImpl extends BaseServiceImpl<Language, LanguageDTO, Long, LanguageRepository> implements LanguageService {

}
