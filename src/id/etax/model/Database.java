package id.etax.model;

import java.io.Serializable;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Database implements Serializable {
	private static final long serialVersionUID = 1L;	
	private static Logger log = Logger.getLogger(Database.class.getName());	
	private static String driver = null;
	private static String url = null;
	private static String name = null;
	private static String user = null;
	private static String pwd = null;
	private static String schema = null;
	
	public static void configure(Properties properties) {
		log.info("configure DB [OK]");
		setUrl(properties.getProperty("db.url").trim());
		setUser(properties.getProperty("db.user").trim());
		setSchema(properties.getProperty("db.schema").trim());
		setPwd(properties.getProperty("db.pwd").trim());
	}
	
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		Database.url = url;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Database.name = name;
	}
	public static String getUser() {
		return user;
	}
	public static void setUser(String user) {
		Database.user = user;
	}
	public static String getPwd() {
		return pwd;
	}
	public static void setPwd(String pwd) {
		Database.pwd = pwd;
	}
	public static String getSchema() {
		return schema;
	}
	public static void setSchema(String schema) {
		Database.schema = schema;
	}
}

