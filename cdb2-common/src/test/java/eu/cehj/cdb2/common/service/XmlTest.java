package eu.cehj.cdb2.common.service;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.cehj.cdb2.common.dto.BailiffDTO;

public class XmlTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    //    @Before
    public void setUp() throws Exception {
    }

    //    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {


        final JAXBContext context = JAXBContext.newInstance(BailiffDTO.class);
        final Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        final DummyBailiffRepository repo = new DummyBailiffRepository();
        final List<BailiffDTO> dtos = repo.getAllDTO();
        for(final BailiffDTO dto: dtos) {
            m.marshal(dto, System.out);
        }

    }

}
