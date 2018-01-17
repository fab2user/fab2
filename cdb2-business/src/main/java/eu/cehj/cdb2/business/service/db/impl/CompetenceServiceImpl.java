package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CompetenceRepository;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.entity.Competence;

@Service
public class CompetenceServiceImpl extends BaseServiceImpl<Competence, Long> implements CompetenceService {

    @Autowired
    private CompetenceRepository repository;


    @Override
    public List<CompetenceDTO> getAllDTO() throws Exception {

        final List<Competence> competences = (List<Competence>) this.repository.findAll();
        final List<CompetenceDTO> dtos = new ArrayList<CompetenceDTO>(competences.size());
        competences.forEach(competence -> {
            final CompetenceDTO dto = this.populateDTOFromEntity(competence);
            dtos.add(dto);
        });
        return dtos;
    }

    public CompetenceDTO populateDTOFromEntity(final Competence competence) {
        final CompetenceDTO dto = new CompetenceDTO();
        dto.setId(competence.getId());
        dto.setCode(competence.getCode());
        dto.setDescription(competence.getDescription());
        return dto;
    }

}
