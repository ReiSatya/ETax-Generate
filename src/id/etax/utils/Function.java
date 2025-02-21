package id.etax.utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

public class Function {
	private static Logger log = Logger.getLogger(Function.class.getName());
	private static DateFormat dateFormat = new SimpleDateFormat(
			"dd-MMM-yy hh.mm.ss.SSS");
	private static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	private static StringBuilder stringBuilder = new StringBuilder();
	private static Date date = new Date();

	// public static String printStatus(String str1, String str2) {
	// return "\n> " + str1 + "\t: " + str2;
	// }

	public static boolean isSchedule(String startDate, String finishDate) {
		int dateNow = dateNow();
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy");
        SimpleDateFormat sf3 = new SimpleDateFormat("MM");
        Date date = new Date();
        int years = Integer.valueOf(sf2.format(date));
        int month = Integer.valueOf(sf3.format(date));
        System.out.println("> Month and Year: " + month + " / " + years);
        YearMonth ym = YearMonth.of(years, month);
        int y = ym.lengthOfMonth();
        if ((dateNow < Integer.parseInt(startDate)) || (dateNow > Integer.parseInt(finishDate)) || Integer.parseInt(finishDate) > y) {
            log.warn(printStatus("Not schedule!", new Object[] { "Date now", Integer.valueOf(dateNow), "Start date", startDate, "Finish date", finishDate, "Last Date This month", y }));
            exit();
        }
        return true;
    }

	public static String split(String[] param) {
		if (param != null) {
			StringBuffer sb = new StringBuffer();
			String[] arrayOfString = param;
			int j = param.length;
			for (int i = 0; i < j; i++) {
				String s = arrayOfString[i];
				sb.append("[").append(s).append("]");
			}
			return sb.toString();
		}
		return null;
	}

	public static String sqlReplace(String sql) {
		return sql.replace("'", "''''");
	}

	public static String getDate() {
		return dateFormat.format(Long.valueOf(date.getTime()));
	}

	public static String getDate_yyyyMMdd() {
		return yyyyMMdd.format(Long.valueOf(date.getTime()));
	}

	public static String getDate_yyyyMMdd(Date date) {
		return yyyyMMdd.format(date);
	}

	public static String getDate(Date date) {
		return dateFormat.format(date);
	}

	public static String replaceDateFormat(Date date) {
		return dateFormat.format(date);
	}

	public static void printList(List<String[]> dataList) {
		stringBuilder.setLength(0);
		stringBuilder.append("Print data");
		for (int i = 0; i < dataList.size(); i++) {
			stringBuilder.append("\n" + (i + 1) + ". ");
			for (int j = 0; j < ((String[]) dataList.get(i)).length; j++) {
				stringBuilder.append("|")
						.append(((String[]) dataList.get(i))[j]);
			}
		}
		log.info(stringBuilder);
	}

    public static String expressionDaily(String time) {
        String result = splitBack(time) + " * * ?";
        return result;
    }

    public static String splitBack(String time) {
        String[] arrStr = time.split(":");
        if (arrStr.length != 3) {
            throw new IllegalArgumentException("Time must be in the format HH:mm:ss");
        }
        String result = "00 " + arrStr[1] + " " + arrStr[0];
        return result;
    }

	public static String expDaily(String time) {
		String result = time + " * * ?";
		return result;
	}

	public static String expMonthly(String time, String startDate,
			String endDate) {
		String result = time + startDate + "-" + endDate + " * ?";
		return result;
	}

	public static void showPerformance(long startTime) {
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		String resultPerformance = "[Use memory: " + memory / 1048576L
				+ " megabytes.]";
		log.info(resultPerformance);

		long stopTime = System.currentTimeMillis();
		long elapsedTime = (stopTime - startTime) / 1000L;
		resultPerformance = "[Times: " + elapsedTime / 60L + " minutes.]";
		log.info(resultPerformance);
	}

	public static String getErrMsg(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	public static String errMsg(Exception e) {
		if (e.getCause() == null) {
			String s = e.toString();
			return s.substring(s.indexOf(":") + 2);
		}
		return e.getCause().getLocalizedMessage();
	}

	public static String param(int length) {
		StringBuilder result = new StringBuilder();
		result.append("(");
		for (int i = 1; i < length; i++) {
			result.append("?,");
		}
		return "?)";
	}

	public static String getLastMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(2, -1);
		return new SimpleDateFormat("MMyyyy").format(c.getTime());
	}

	public static int dateNow() {
		return Integer.parseInt(new SimpleDateFormat("dd").format(new Date()));
	}

	public static void exit() {
		// MailDebug.report();
		// MailChecker.report();
		printStatus("END PROGRAM");
		log.info("Bye...");
		System.exit(0);
	}

	public static String search(String input, String key1, String key2) {
		if (input.lastIndexOf(key1) == -1) {
			return "|unknown|";
		}
		if (input.lastIndexOf(key2) == -1) {
			return "|unknown|";
		}
		return input.substring(input.lastIndexOf(key1) + key1.length(),
				input.lastIndexOf(key2)).trim();
	}

	public static String search(String input) {
		String[] splitStr = input.split(";");
		if (splitStr.length == 1) {
			return splitStr[0];
		}
		if (splitStr.length == 2) {
			return splitStr[1];
		}
		return "?";
	}

	private static String status(String status) {
		stringBuilder.setLength(0);
		int mod = 50 - status.length();
		for (int i = 0; i < mod; i++) {
			stringBuilder.append("*");
			if (mod / 2 == i) {
				stringBuilder.append(" " + status + " ");
			}
		}
		return stringBuilder.toString();
	}

	public static void printStatus(String status) {
		Log.changeLayout("%m%n");
		log.info(status(status));
		Log.defaultLayout();
	}

	public static String printStatus(String str1, String str2) {
		return "\n> " + str1 + "\t: " + str2;
	}

	public static String printStatus(String info, Object[] arr1) {
		stringBuilder.setLength(0);
		stringBuilder.append(info);
		for (int i = 0; i < arr1.length; i += 2) {
			stringBuilder.append("\n> " + arr1[i] + "\t: " + arr1[(i + 1)]);
		}
		return stringBuilder.toString();
	}

	public static String replacePeriod(String content, String period) {
		return content.replace("${period}", getPeriodSubject(period));
	}

	public static String replacePeriodEng(String content, String period) {
		return content.replace("${periodEng}", getPeriodSubjectEng(period));
	}

	public static String replaceUsername(String content, String username) {
		return content.replace("${username}", username);
	}

	public static String replacePassword(String content, String password) {
		return content.replace("${password}", password);
	}

	public static String whereSQL(String field, String[] key) {
		stringBuilder.setLength(0);
		for (int i = 0; i < key.length; i++) {
			stringBuilder.append(field).append("=").append("'" + key[i] + "'");
			if (i != key.length - 1) {
				stringBuilder.append(" OR ");
			}
		}
		return stringBuilder.toString();
	}
	
	public static String periodFormat(String period) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMyyyy");
        LocalDate date = LocalDate.parse("01" + period, DateTimeFormatter.ofPattern("ddMMyyyy"));

        Locale indonesianLocale = new Locale("id", "ID");
        String currentMonthName = date.getMonth().getDisplayName(TextStyle.FULL, indonesianLocale);
        int currentYear = date.getYear();

        LocalDate nextMonthDate = date.plusMonths(1);
        String nextMonthName = nextMonthDate.getMonth().getDisplayName(TextStyle.FULL, indonesianLocale);
        int nextYear = nextMonthDate.getYear();

        return String.format("%s %d - %s %d", currentMonthName, currentYear, nextMonthName, nextYear);
    }

	public static String getPeriod() {
		String result = "";
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		if (month == 0) {
			result = "12" + (year - 1);
		} else if (month == 1 || month == 2 || month == 3 || month == 4
				|| month == 5 || month == 6 || month == 7 || month == 8
				|| month == 9) {
			result = "0" + month + year;
		} else if (month == 10 || month == 11) {
			result = String.valueOf(month) + String.valueOf(year);
		}
		return result;
	}

	public static String getPeriodSubject(String period) {
		String result = "";
		String month = period.substring(0, 2);
		String year = period.substring(2);
		if (month == "01" || month.equals("01")) {
			result = "Januari " + (year);
		} else if (month == "02" || month.equals("02")) {
			result = "Februari " + (year);
		} else if (month == "03" || month.equals("03")) {
			result = "Maret " + (year);
		} else if (month == "04" || month.equals("04")) {
			result = "April " + (year);
		} else if (month == "05" || month.equals("05")) {
			result = "Mei " + (year);
		} else if (month == "06" || month.equals("06")) {
			result = "Juni " + (year);
		} else if (month == "07" || month.equals("07")) {
			result = "Juli " + (year);
		} else if (month == "08" || month.equals("08")) {
			result = "Agustus " + (year);
		} else if (month == "09" || month.equals("09")) {
			result = "September " + (year);
		} else if (month == "10" || month.equals("10")) {
			result = "Oktober " + (year);
		} else if (month == "11" || month.equals("11")) {
			result = "November " + (year);
		} else {
			result = "Desember " + (year);
		}
		return result;
	}

	public static String getPeriodSubjectEng(String period) {
		String result = "";
		String month = period.substring(0, 2);
		String year = period.substring(2);
		if (month == "01" || month.equals("01")) {
			result = "January " + (year);
		} else if (month == "02" || month.equals("02")) {
			result = "February " + (year);
		} else if (month == "03" || month.equals("03")) {
			result = "March " + (year);
		} else if (month == "04" || month.equals("04")) {
			result = "April " + (year);
		} else if (month == "05" || month.equals("05")) {
			result = "May " + (year);
		} else if (month == "06" || month.equals("06")) {
			result = "June " + (year);
		} else if (month == "07" || month.equals("07")) {
			result = "July " + (year);
		} else if (month == "08" || month.equals("08")) {
			result = "August " + (year);
		} else if (month == "09" || month.equals("09")) {
			result = "September " + (year);
		} else if (month == "10" || month.equals("10")) {
			result = "October " + (year);
		} else if (month == "11" || month.equals("11")) {
			result = "November " + (year);
		} else {
			result = "December " + (year);
		}
		return result;
	}
	
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted, failed to complete operation");
        }
    }

}
