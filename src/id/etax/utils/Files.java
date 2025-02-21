package id.etax.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Files {
	//buat ambil config filename properties
	public static Properties getProperties(String configFilename) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(configFilename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
}
