package id.etax.utils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	private static String defaultPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}.%M:%L - %m%n";
	private static ConsoleAppender a;
	private static DailyRollingFileAppender b;

	public static void changeLayout(String pattern) {
		a.setLayout(new PatternLayout(pattern));
		b.setLayout(new PatternLayout(pattern));
	}

	public static void defaultLayout() {
		a.setLayout(new PatternLayout(defaultPattern));
		b.setLayout(new PatternLayout(defaultPattern));
	}

	private static void initAppender() {
		a = (ConsoleAppender) Logger.getRootLogger().getAppender("stdout");
		b = (DailyRollingFileAppender) Logger.getRootLogger().getAppender(
				"file");
	}

	static void configure(String file) {
		PropertyConfigurator.configure(file);
		initAppender();
	}
}
