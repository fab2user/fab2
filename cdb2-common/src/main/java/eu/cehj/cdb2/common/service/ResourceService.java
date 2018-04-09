package eu.cehj.cdb2.common.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Properties loadProperties(final String classPathPath, final String filePath) throws Exception {
        final Properties properties = new Properties();
        final ClassPathResource jarResource = new ClassPathResource(classPathPath);
        final FileSystemResource fileSystemResource = new FileSystemResource(filePath);
        if(jarResource != null) {
            try (final InputStream is = jarResource.getInputStream()) {
                properties.load(is);
            }catch(final FileNotFoundException err) {
                this.logger.warn(err.getMessage(), err);
            }
        }
        if(fileSystemResource != null) {
            try (InputStream is = fileSystemResource.getInputStream()) {
                properties.load(is);
            }catch(final FileNotFoundException err) {
                this.logger.warn(err.getMessage());
            }
        }
        return properties;
    }
}
