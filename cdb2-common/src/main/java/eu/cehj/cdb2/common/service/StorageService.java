package eu.cehj.cdb2.common.service;

import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {

    //    @Value("${server.port}")
    //    private String uploadStorageDirectory;

    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("uploads");

    public void store(final MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream()) {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        }catch(final FileAlreadyExistsException e) {
            this.deleteFile(file.getOriginalFilename());
            throw e;
        }
    }

    public Resource loadFile(final String filename) throws Exception {
        final Path file = this.rootLocation.resolve(filename);
        final Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        }
        return null;
    }

    public void deleteFile(final String fileName) throws Exception{
        Files.deleteIfExists(this.rootLocation.resolve(fileName));
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
    }

    @PostConstruct
    public void init() throws Exception {
        Files.createDirectories(this.rootLocation);
    }
}
