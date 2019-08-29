package eu.cehj.cdb2.business.service.db.impl.special;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.common.dto.CompetenceForSelectDTO;
import eu.cehj.cdb2.entity.Competence;
import eu.cehj.cdb2.entity.Instrument;

@Service
public class CompetenceForSelectService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompetenceForSelectService.class);

	@Autowired
	CompetenceService competenceService;

	public List<CompetenceForSelectDTO> getCompetencesForSelect(){
		final List<Competence> competences = this.competenceService.getAll();
		return this.toDTOList(competences);
	}

	public List<CompetenceForSelectDTO> toDTOList(final List<Competence> entities)  {
		return entities.stream().map(entity ->{
			try {
				return this.toDTO(entity);
			} catch (final Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			return null;
		}).collect(Collectors.toList());
	}

	public CompetenceForSelectDTO toDTO(final Competence entity){
		final CompetenceForSelectDTO dto = new CompetenceForSelectDTO();
		if(entity != null) {
			dto.setCode(entity.getCode());
			dto.setDescription(entity.getDescription());
			dto.setId(entity.getId());
			if(entity.getInstrument() != null) {
				final Instrument instrument = entity.getInstrument();
				dto.setInstrumentCode(instrument.getCode());
				dto.setInstrumentDescription(instrument.getDescription() + " (" + entity.getDescription() + ")");
				dto.setInstrumentId(instrument.getId());
			}
		}
		return dto;
	}
}
