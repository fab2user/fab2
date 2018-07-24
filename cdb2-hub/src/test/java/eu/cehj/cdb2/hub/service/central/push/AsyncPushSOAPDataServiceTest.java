package eu.cehj.cdb2.hub.service.central.push;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Court;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.ObjectFactory;

public class AsyncPushSOAPDataServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPushSOAPDataServiceTest.class);

    private final List<BailiffDTO> bailiffDTOs = new ArrayList<>();
    private final BailiffDTO dto1 = new BailiffDTO();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {

        final CompetenceDTO competenceDTO1 = new CompetenceDTO();
        competenceDTO1.setCode("C1");
        competenceDTO1.setDescription("description competence 1");
        competenceDTO1.setId(1L);
        final InstrumentDTO instrumentDTO1 = new InstrumentDTO();
        instrumentDTO1.setId(1L);
        instrumentDTO1.setCode("I1");
        instrumentDTO1.setDescription("description instrument 1");
        final List<CompetenceDTO> competences1 = new ArrayList<>();
        competences1.add(competenceDTO1);
        instrumentDTO1.setCompetences(competences1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGenerateValidDocument() {

        //        this.toXml(this.data);
    }

    public Court dtoToData(final BailiffDTO bailiffDTO) {
        final Court court = new Court();
        //court.set
        final Detail detail = new Detail();
        detail.setAddress(String.format("%s %s", bailiffDTO.getAddress1(), bailiffDTO.getAddress2()));
        detail.setPostalCode(bailiffDTO.getPostalCode());
        detail.setMunicipality(bailiffDTO.getCity());
        final Details details = new Details();
        //        details.getDetail().add(detail);
        court.setDetails(details);
        //        data.getCourtOrPhysicalPerson().add(court);
        final Competences competences = new Competences();
        for(final CompetenceDTO competenceDTO : bailiffDTO.getCompetences()) {
            final Competence competence = new Competence();
            competence.setType(competenceDTO.getDescription());
            competence.setInstrument(competenceDTO.getInstrument().getDescription());
            //            competences.getCompetence().add(competence);
        }
        court.setCompetences(competences);
        return court;
    }

    public void toXml(final Data data) {
        try {
            for (final CompetenceDTO cmp : this.dto1.getCompetences()) {
                final InstrumentDTO instDTO = cmp.getInstrument();

            }
            final JAXBContext ctx = JAXBContext.newInstance(Data.class);
            final Marshaller marshaller = ctx.createMarshaller();
            final ObjectFactory of = new ObjectFactory();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //            final JAXBElement<Data> wData = of.createData(data);
            //            marshaller.marshal(wData, System.out);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
