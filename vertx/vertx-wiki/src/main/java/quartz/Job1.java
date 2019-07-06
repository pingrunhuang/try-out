package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job1 implements Job {
    private static int count;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Job1 start: " + jobExecutionContext.getFireTime());
        count++;
        System.out.println("Job count " + count);
        System.out.println("Job1 next scheduled time: " + jobExecutionContext.getNextFireTime());
        System.out.println("Job's thread name is: " + Thread.currentThread().getName());
        System.out.println("Job end");
        System.out.println("--------------------------------------------------------------------");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
