package eu.cehj.cdb2.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eu.cehj.cdb2.business.service.data.DataImportService;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;

@RestController
@RequestMapping("api/update")
/**
 * Updates municipalities database.
 */
public class CDBUpdateController extends BaseController {

    @Autowired
    private DataImportService importService;


    @RequestMapping(method = { POST })
    @ResponseStatus(value = HttpStatus.OK)
    //    @Async
    public CompletableFuture<List<MunicipalityDTO>> upload(@RequestParam("file") final MultipartFile file) throws Exception{
        try (final InputStream is = file.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));) {
            this.importService.importData(reader);
        } catch (final Throwable e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }



    //    @RequestMapping(method = { GET })
    //    @ResponseStatus(value = CREATED)
    //    @Async
    //    public CompletableFuture<List<MunicipalityDTO>> launchUpdate() throws Exception {
    //
    //        return null;
    //    }

}