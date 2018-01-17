package eu.cehj.cdb2.entity;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        // TODO replace with user retrieved by Spring Security when implemented
        return "Landry Soules";
    }
}