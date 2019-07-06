package quartz;

import org.quartz.*;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
public class SchedulerConfiguration {

    private int repeatCount = 3;

    public static void main(String[] args) throws Exception{
        SchedulerConfiguration quartzSchedulerExample = new SchedulerConfiguration();
        Scheduler scheduler = quartzSchedulerExample.createAndStartScheduler();
        quartzSchedulerExample.fireJob(scheduler, Job1.class);
        quartzSchedulerExample.fireJob(scheduler, Job2.class);
        quartzSchedulerExample.fireJob(scheduler, Job3.class);
    }

    public Scheduler createAndStartScheduler() throws SchedulerException {
        SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedFact.getScheduler();
        System.out
                .println("Scheduler name is: " + scheduler.getSchedulerName());
        System.out.println("Scheduler instance ID is: "
                + scheduler.getSchedulerInstanceId());
        System.out.println("Scheduler context's value for key QuartzTopic is "
                + scheduler.getContext().getString("QuartzTopic"));
        scheduler.start();
        return scheduler;
    }

    public <T extends Job> void fireJob(Scheduler scheduler, Class<T> jobClass) throws SchedulerException {

        // define the job and tie it to our HelloJob class
        JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
        JobDataMap data = new JobDataMap();
        data.put("latch", this);

        JobDetail jobDetail = jobBuilder
                .usingJobData("example", "quartz.SchedulerConfigueration")
                .usingJobData(data).build();

        // Trigger the job to run now, and then every 40 seconds
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .startNow()
                .withSchedule(dailyAtHourAndMinute(7, 21))
                .withDescription("MyTrigger").build();

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
