package eu.cehj.cdb2.hub.service.central.push.schedule;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.cehj.cdb2.hub.service.central.push.PushDataServiceLauncher;

@Component
public class CDBJob implements Job{

    @Autowired
    private PushDataServiceLauncher pushDataLauncher;

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException{
        final JobDataMap dataMap = context.getMergedJobDataMap();
        try {
            this.pushDataLauncher.process(dataMap.getString("countryCode"));
        } catch (final Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
