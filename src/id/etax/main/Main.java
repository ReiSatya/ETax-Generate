package id.etax.main;

import org.apache.log4j.Logger;

import id.etax.model.Param;
import id.etax.service.JobMontly;
import id.etax.service.PDFEngine;
import id.etax.service.SchedulerBuilder;
import id.etax.service.SummaryEngine;
import id.etax.utils.Config;
import id.etax.utils.Function;

public class Main {
	public static void main(String[] args) {
//		if (Config.init()) {
//            if (Function.isSchedule(Param.getStartDate(), Param.getFinishDate())) {
//                new SchedulerBuilder(JobMontly.class, Param.getStartTime(), Param.getFinishTime(), "groupMonthly");
//            }
//        } else {
//            System.err.println("Error configuration!");
//            System.exit(0);
//        }
		
		Config.init();
		PDFEngine pdf = new PDFEngine();
		pdf.engine();
		Function.wait(5);
		System.out.println("mau masuk summary");
		SummaryEngine spdf = new SummaryEngine();
		spdf.engine();	
		Function.printStatus("FINISH GENERATE ALL PDF");
	}
}
