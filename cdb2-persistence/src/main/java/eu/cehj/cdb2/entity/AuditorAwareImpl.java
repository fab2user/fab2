package eu.cehj.cdb2.entity;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Long getCurrentAuditor() {
        // TODO replace with user retrieved by Spring Security when implemented
        return 1L;
    }
}