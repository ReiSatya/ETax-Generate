package id.etax.utils;

import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

import id.etax.model.Database;
import id.etax.model.Param;
import id.etax.utils.Config;

public class Config {
	private static Logger log = Logger.getLogger(Config.class.getName());
	
	public static boolean init() {
        Properties prop = Files.getProperties("config.properties");
        if (prop.isEmpty()) {
            return false;
        }
        Log.configure("config.properties");
        Function.printStatus("START PROGRAM");
        log.info(Function.printStatus("[OK]", new Object[] { "File", "config.properties" }));

        Database.configure(prop);

        Map<String, String[]> commonParam = SQLData.getCommonParam(new String[] {
                "START DATE", "FINISH DATE", "START TIME", "FINISH TIME", "pdf.path.report", "pdf.source.file" }, false);
        setCommonParam(commonParam);
        prop.clear();
        return true;
    }

    private static void setCommonParam(Map<String, String[]> commonParam) {
        Param.configure(((String[]) commonParam.get("START DATE"))[0],
                ((String[]) commonParam.get("FINISH DATE"))[0],
                ((String[]) commonParam.get("START TIME"))[0],
                ((String[]) commonParam.get("FINISH TIME"))[0],
                ((String[]) commonParam.get("pdf.path.report"))[0],
                ((String[]) commonParam.get("pdf.source.file"))[0]);
    }
}
