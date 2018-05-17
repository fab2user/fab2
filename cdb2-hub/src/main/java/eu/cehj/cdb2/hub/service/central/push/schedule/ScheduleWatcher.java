package eu.cehj.cdb2.hub.service.central.push.schedule;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.entity.CountryOfSync;

@Service
public class ScheduleWatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleWatcher.class);

    @Value("${cdb.job.key}")
    private String cdbJobKey;

    @Value("${cdb.job.group}")
    private String cdbJobGroup;

    @Autowired
    CountryOfSyncService cosService;

    @Autowired
    Scheduler scheduler;

    @Scheduled(fixedDelayString = "${cdb.job.watcher.schedule.millis}")
    public void watch() throws SchedulerException{
        this.showSchedulerDetails();
        final List<CountryOfSync> coss = this.cosService.getAll();
        for (final CountryOfSync cos : coss) {
            LOGGER.debug(cos.getCountryCode() + " -> " + cos.getDaysOfWeek() + " | " + cos.getFrequency());
            this.compareWithScheduled(cos) ;

        }

    }

    private void compareWithScheduled(final CountryOfSync cos) throws SchedulerException{
        final boolean triggerPresent = this.scheduler.checkExists(this.generateTriggerKey(cos));
        if (!triggerPresent) {
            LOGGER.debug("Trigger unknown for country " + cos.getName());
            if(cos.isActive()) {
                LOGGER.debug("Country is active: create relevant trigger !");
                final Trigger trigger = this.createTrigger(cos);
                this.scheduler.scheduleJob(trigger);
            }
        } else {
            final Trigger trigger = this.scheduler.getTrigger(this.generateTriggerKey(cos));
            if (!trigger.getDescription().equals(cos.getFrequency()) || !cos.isActive()) {
                this.scheduler.unscheduleJob(trigger.getKey());
                this.compareWithScheduled(cos);
            }

        }
    }

    private TriggerKey generateTriggerKey(final CountryOfSync cos) {
        return TriggerKey.triggerKey(cos.getCountryCode() + "-" + cos.getId(), this.cdbJobGroup);
    }

    private Trigger createTrigger(final CountryOfSync cos) {

        final Trigger trigger = newTrigger()
                .forJob(this.cdbJobKey)
                .usingJobData("countryCode", cos.getCountryCode())
                .withSchedule(cronSchedule(this.buildCronExpression(cos)))
                .withIdentity(this.generateTriggerKey(cos))
                .withDescription(cos.getFrequency())
                .build();

        LOGGER.debug("Trigger created, with key: " + trigger.getKey().toString());
        return trigger;
    }

    private String buildCronExpression(final CountryOfSync cos) {
        final String[] hm = cos.getFrequency().split(":");
        // Remove leading 0 if present
        final String h = Long.toString(Long.parseLong(hm[0]));
        final String m = Long.toString(Long.parseLong(hm[1]));
        final String d = cos.getDaysOfWeek();
        return String.format("0 %s %s ? * %s", m, h, d);
    }

    @SuppressWarnings("unchecked")
    private void showSchedulerDetails() throws SchedulerException{
        for (final String groupName : this.scheduler.getJobGroupNames()) {

            for (final JobKey jobKey : this.scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                final String jobName = jobKey.getName();
                final String jobGroup = jobKey.getGroup();
                LOGGER.debug("[jobName] : {} - [groupName] : {}", jobName, jobGroup);
                //get job's triggers
                final List<Trigger> triggers = (List<Trigger>) this.scheduler.getTriggersOfJob(jobKey);
                for (final Trigger trigger : triggers) {
                    LOGGER.debug("{} -> {}",trigger.getKey(), trigger.getNextFireTime());
                }

            }

        }
        LOGGER.debug("==============================================================================");
    }
}
