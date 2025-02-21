package id.etax.service;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import id.etax.utils.Function;

public class SchedulerBuilder {
	private static Logger log = Logger.getLogger(SchedulerBuilder.class.getName());

    public SchedulerBuilder(Class<? extends Job> jobClass, String startTime, String finishTime, String groupName) {
        Function.printStatus("SCHEDULER: Running");
        log.info(Function.printStatus("[OK]", new Object[] { "Class ", jobClass.getName(), "Start ", startTime, "Finish", finishTime, "Group ", groupName }));
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity("job1", groupName).build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("trigger1", groupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(Function.expressionDaily(startTime)))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);

            if (finishTime != null) {
                jobDetail = JobBuilder.newJob(jobClass).withIdentity("job2", groupName).build();
                trigger = TriggerBuilder
                        .newTrigger()
                        .withIdentity("trigger2", groupName)
                        .withSchedule(CronScheduleBuilder.cronSchedule(Function.expressionDaily(finishTime)))
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }

            scheduler.start();
        } catch (SchedulerException e) {
            log.error("Exception!!!", e);
        }
    }
}