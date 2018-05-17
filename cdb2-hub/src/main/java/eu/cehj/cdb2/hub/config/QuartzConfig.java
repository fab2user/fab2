package eu.cehj.cdb2.hub.config;

import static org.quartz.JobBuilder.*;

import java.io.IOException;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import eu.cehj.cdb2.hub.service.central.push.schedule.AutoWiringSpringBeanJobFactory;
import eu.cehj.cdb2.hub.service.central.push.schedule.CDBJob;

/**
 * <p>
 * Processes execution of scheduled CDB syncs. As users are able to change the time of execution, we use Quartz to manage the tasks. A simple @Scheduled method is in charge of reading the database and identify tasks that sheduled time changed. For those, the associated trigger is removed and replaced by a new one containing the correct time.</p>
 * Largely inspired by this <a href="http://www.baeldung.com/spring-quartz-schedule">article</a>.
 */
@Configuration
public class QuartzConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzConfig.class);

    @Value("${cdb.job.key}")
    private String cdbJobKey;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        final AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        LOGGER.debug("Configuring Job factory");
        jobFactory.setApplicationContext(this.applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(final JobDetail job) throws SchedulerException, IOException {

        final StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize(new ClassPathResource("quartz.properties").getInputStream());

        LOGGER.debug("Getting a handle to the Scheduler");
        final Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(this.springBeanJobFactory());
        // CDB job is immutable, but we'll create dynamically as much triggers as countries for which we need sync
        scheduler.addJob(job, true);
        LOGGER.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }

    /**
     * This job is the only one in charge of CDB sync, but triggers allow us to execute it multiple times, with different parameters and different scheduling.
     */
    @Bean
    public JobDetail jobDetail() {

        return newJob()
                .ofType(CDBJob.class)
                .storeDurably()
                .withIdentity(JobKey.jobKey(this.cdbJobKey))
                .withDescription("Job in charge to send xml data to CDB.")
                .build();
    }

}
