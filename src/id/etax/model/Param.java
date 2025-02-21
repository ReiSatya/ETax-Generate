package id.etax.model;

import java.io.Serializable;

import org.apache.log4j.Logger;

import id.etax.utils.Function;


public class Param implements Serializable{
	private static Logger log = Logger.getLogger(Param.class.getName());
	private static final long serialVersionUID = 1L;
	private static String startDate = null;
	private static String finishDate = null;
	private static String startTime = null;
	private static String finishTime = null;
	private static String pdfPath = null;
	private static String pdfSource = null;
	
	public static void configure(String startDate, String finishDate,String startTime, String finishTime, String pdfPath,
			String pdfSource) {

		setStartDate(startDate);
		setFinishDate(finishDate);
		setStartTime(startTime);
		setFinishTime(finishTime);
		setPdfPath(pdfPath);
		setPdfSource(pdfSource);
		log.info(Function.printStatus("[OK]", new Object[] { "Start date",
				getStartDate(), "Finish date", getFinishDate(), "Start time",
				getStartTime(), "Finish time", getFinishTime(), "Pdf path",
				getPdfPath(), "Pdf source", getPdfSource()}));
	}
	
	public static String getStartDate() {
		return startDate;
	}
	public static void setStartDate(String startDate) {
		Param.startDate = startDate;
	}
	public static String getFinishDate() {
		return finishDate;
	}
	public static void setFinishDate(String finishDate) {
		Param.finishDate = finishDate;
	}
	public static String getStartTime() {
		return startTime;
	}
	public static void setStartTime(String startTime) {
		Param.startTime = startTime;
	}
	public static String getFinishTime() {
		return finishTime;
	}
	public static void setFinishTime(String finishTime) {
		Param.finishTime = finishTime;
	}
	public static String getPdfPath() {
		return pdfPath;
	}
	public static void setPdfPath(String pdfPath) {
		Param.pdfPath = pdfPath;
	}
	public static String getPdfSource() {
		return pdfSource;
	}
	public static void setPdfSource(String pdfSource) {
		Param.pdfSource = pdfSource;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}

