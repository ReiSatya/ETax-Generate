package id.etax.service;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import id.etax.utils.Function;

public class JobMontly implements Job {
	private static Logger log = Logger.getLogger(JobMontly.class.getName());
    public static boolean isRunning = false;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (isRunning) {
            try {
                Function.printStatus("SCHEDULER: Timeout!");
                isRunning = false;
                Function.exit();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(Function.getErrMsg(e));
            }
        } else {
            Function.printStatus("SCHEDULER: Starting");
            isRunning = true;
            Function.printStatus("Starting Generate E-TAX Statement");
            PDFEngine pdf = new PDFEngine();
    		pdf.engine();
    		Function.wait(5);
    		System.out.println("mau masuk summary");
    		SummaryEngine spdf = new SummaryEngine();
    		spdf.engine();	
    		Function.printStatus("FINISH GENERATE ALL PDF");
        }
    }
}