package id.etax.service;

import id.etax.api.service.GetAPIData;
import id.etax.conn.DBEngine;
import id.etax.model.Param;
import id.etax.utils.Function;
import id.etax.utils.SQLData;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.log4j.Logger;

public class PDFEngine {
  List<String> total;
  
  List<String> failed;
  
  List<String> succes;
  
  HashMap<String, Integer> duplicate;
  
  public PDFEngine() {
    this.total = new ArrayList<>();
    this.failed = new ArrayList<>();
    this.succes = new ArrayList<>();
    this.duplicate = new HashMap<>();
  }
  
  private static Logger log = Logger.getLogger(PDFEngine.class.getName());
  
  public void engine() {
    int availableThread = Runtime.getRuntime().availableProcessors();
    ThreadPoolExecutor executor = new ThreadPoolExecutor(1, availableThread, 
        0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    executor.prestartAllCoreThreads();
    GetAPIData gap = new GetAPIData();
    HashMap<String, String[]> nasabahName = gap.GetNasabahDataAll();
    Set<String> no = nasabahName.keySet();
    myCellAble callable = null;
    Future<String> test = null;
    List<Future<String>> lF = new ArrayList<>();
    log.info("GENERATE START");
    String period = Function.getPeriod();
    log.info("PERIOD: " + period);
    for (String n : no) {
      String[] nasabah = nasabahName.get(n);
      System.out.println("sudah nasabah ke " + n);
      System.out.println(nasabah[1]);
      System.out.println(nasabah[2]);
      HashMap<String, Object[]> getPajak = gap.getStatement(nasabah[0]);
      Set<String> noIn = getPajak.keySet();
      for (String nIn : noIn) {
        System.out.println("getPajak ke " + nIn);
        if (!nIn.equals("kosong")) {
          Object[] invo = getPajak.get(nIn);
          String tes1 = String.valueOf(invo[1]);
          String tes2 = String.valueOf(invo[2]);
          String tes3 = String.valueOf(invo[3]);
          String tes4 = String.valueOf(invo[4]);
          String tes5 = String.valueOf(invo[5]);
          String tes6 = String.valueOf(invo[6]);
          String tes7 = String.valueOf(invo[7]);
          String tes8 = String.valueOf(invo[8]);
          String tes9 = String.valueOf(invo[9]);
          log.info(String.valueOf(tes1) + " : " + tes2 + " : " + tes3 + " : " + 
              tes4 + 
              " : " + tes5 + " : " + tes6 + " : " + tes7 + " : " + 
              tes8 + " : " + tes9 + " : " + Function.getPeriod());
          if (!SQLData.checkInvoice(tes1, period))
            SQLData.insertSTGInvoice(tes1, tes2, tes3, tes4, 
                tes5, tes6, tes7, tes8, tes9, Function.getPeriod()); 
        } 
      } 
      callable = new myCellAble(nasabah[2], period);
      test = executor.submit(callable);
    } 
    try {
      while (!test.isDone() && !test.isCancelled()) {
        Thread.sleep(200L);
        System.out.println("Waiting for task completion...");
      } 
      String result123 = test.get();
      Date date = new Date();
      SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM");
      SimpleDateFormat sf2 = new SimpleDateFormat("YYYY");
      log.info("Retrieved result from the task - " + result123);
      String start = Param.getStartDate();
      String end = Param.getFinishDate();
      String my = sf1.format(date);
      String years = sf2.format(date);
      String sDate = String.valueOf(my) + "-" + start;
      String fDate = String.valueOf(my) + "-" + end;
      int to = this.total.size();
      int su = this.succes.size();
      int fa = this.failed.size();
      SQLData.InsertLogSCH(sDate, fDate, to, su, fa, "PDF");
      Function.printStatus("FINISH GENERATE E-TAX STATEMENT");
      executor.shutdown();
    } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
      log.info("executor catch : " + e.getMessage());
      e.printStackTrace();
    } 
  }
  
  class myCellAble implements Callable<String> {
    private String name1;
    
    private String period;
    
    public myCellAble(String name1, String period) {
      this.name1 = name1;
      this.period = period;
    }
    
    public String call() throws Exception {
      String result = PDFEngine.this.getData(this.name1, this.period);
      PDFEngine.log.info("dari call: " + result);
      return result;
    }
  }
  
  public String getData(String name1, String period) {
    System.out.println("masuk --getData-- apa kaga");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String result = "";
    try {
      String sourcePDF = String.valueOf(Param.getPdfSource()) + "tax_report.jasper";
      String pathReport = "";
      String query = "SELECT DISTINCT \"tax_nama\",\"cif\" ,\"tax_npwp\", \"tax_alamat\", \"tax_jenispenghasilan\", \"tax_jumlahbunga\", \"tax_tarif\", \"tax_pphpotong\", \"tax_dnln\" FROM \"tax_stg_etax\" WHERE \"tax_nama\" = ?";
      System.out.println(query);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      String date1 = sdf.format(date);
      conn = DBEngine.getConnection();
      ps = conn.prepareStatement(query);
      ps.setString(1, name1);
      rs = ps.executeQuery();
      System.out.println("sampe si ga sih");
      System.out.println("ini apa " + rs);
      if (rs.next()) {
        System.out.println("udh di dalem if");
        String id = rs.getString("cif");
        System.out.println("cif ini cif kan? " + id);
        System.out.println("ini apa aja isinya " + id + " : " + period + " : " + name1 + " : " + pathReport + " : " + sourcePDF);
        String[] result1 = generateFile(id, period, name1, pathReport, 
            sourcePDF);
        boolean check = SQLData.checkLog(id, period);
        System.out.println("masuk ke dalem rs");
        if (result1[0].equals("success")) {
          this.succes.add(id);
          this.total.add(id);
          log.info(String.valueOf(id) + " : " + date1 + " : " + "R" + " : " + 
              "Ready to Send" + " : " + result1[1] + " : " + 
              " : " + period);
          if (check) {
            System.out.println("masuk updateLog");
            SQLData.updateLog(id, date1, "R", "Ready to Send", 
                result1[1], period, result1[2]);
          } else {
            System.out.println("masuk inputLog");
            String res = SQLData.inputLOG(id, date1, "R", "Ready to Send", 
                result1[1], period, result1[2]);
            System.out.println(res);
          } 
        } else {
          this.total.add(id);
          this.failed.add(id);
          log.info(String.valueOf(id) + " : " + date1 + " : " + "N" + " : " + 
              result1[0] + " : " + " : " + " : " + 
              period);
          if (check) {
            System.out.println("masuk updateLog else");
            SQLData.updateLog(id, date1, "N", result1[0], "", 
                period, "");
          } else {
            System.out.println("masuk inputLog else");
            SQLData.inputLOG(id, date1, "N", result1[0], "", 
                period, "");
          } 
        } 
        result = String.valueOf(id) + " Generated";
      } else {
        log.info("no data");
        result = "no data";
      } 
      rs.close();
      ps.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("tes: " + e.getMessage());
      result = e.getMessage();
    } 
    return result;
  }
  
  public String[] generateFile(String id, String period, String name1, String pathReport, String sourcePDF) {
    String result = "";
    JasperReport jasperReport = null;
    Connection conn2 = null;
    String path = "";
    String idSurat = generate();
    try {
      String name = name1;
      if (name1.contains("/"))
        name = name1.replace("/", "-"); 
      System.out.println("masuk if di generateFile");
      File dir = new File(String.valueOf(Param.getPdfPath()) + period);
      dir.mkdir();
      pathReport = String.valueOf(Param.getPdfPath()) + period + "//";
      log.info("path report: " + pathReport);
      path = String.valueOf(pathReport) + name + ".pdf";
      if (SQLData.checkOutletFile(path, period)) {
        int no = 1;
        if (this.duplicate.containsKey(name1)) {
          no = ((Integer)this.duplicate.get(name1)).intValue() + 1;
          this.duplicate.put(name1, Integer.valueOf(no));
        } else {
          this.duplicate.put(name1, Integer.valueOf(no));
        } 
        path = String.valueOf(pathReport) + name + " " + no + ".pdf";
      } 
      log.info("path file: " + path);
      conn2 = DBEngine.getConnection();
      Map<String, Object> parameters = new HashMap<>();
      parameters.put("subreportData", name1);
      parameters.put("noSurat", idSurat);
      JRPdfExporter exporter = new JRPdfExporter();
      InputStream is = new FileInputStream(new File(sourcePDF));
      jasperReport = (JasperReport)JRLoader.loadObject(is);
      log.info("Masuk mau ngeprint jaspernya");
      System.out.println("isi jaspernya apaan " + jasperReport + " : " + parameters + " : " + conn2);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, 
          parameters, conn2);
      log.info("Masuk mau fill jaspernya");
      exporter.setParameter(JRExporterParameter.JASPER_PRINT, 
          jasperPrint);
      ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
      exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteOS);
      exporter.exportReport();
      FileOutputStream outputStream = new FileOutputStream(
          new File(path));
      byteOS.writeTo(outputStream);
      byteOS.flush();
      byteOS.close();
      outputStream.flush();
      outputStream.close();
      log.info(String.valueOf(id) + " " + period);
      SQLData.recordInvoice(id, period);
      SQLData.deleteSTG(id, period);
      conn2.close();
      result = "success";
    } catch (Exception e) {
      log.error(e.getMessage());
      result = e.getMessage();
    } 
    String[] result1 = { result, path, idSurat };
    return result1;
  }
  
  public String generate() {
    String result = "";
    try {
      Date date = new Date();
      SimpleDateFormat dtf = new SimpleDateFormat("MMYY");
      String dateFormat = dtf.format(date);
      String randomSt = generateRandom();
      String randomSc = generateRandom();
      String finalInvo = String.valueOf(dateFormat) + "/ETAX-STATEMENT/" + randomSt + "/" + 
        randomSc;
      if (SQLData.checkGenerateID(finalInvo))
        do {
          finalInvo = reGenerate();
        } while (!SQLData.checkGenerateID(finalInvo)); 
      result = finalInvo;
    } catch (Exception e) {
      log.error(e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String generateRandom() {
    String result = "";
    try {
      for (int i = 0; i < 4; i++) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10);
        String tes2 = String.valueOf(randomNum);
        if (!result.equals("")) {
          result = String.valueOf(result) + tes2;
        } else {
          result = tes2;
        } 
      } 
    } catch (Exception e) {
      log.info(e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String reGenerate() {
    String result = "";
    try {
      Date date = new Date();
      SimpleDateFormat dtf = new SimpleDateFormat("MMYY");
      String dateFormat = dtf.format(date);
      String randomSt = generateRandom();
      String randomSc = generateRandom();
      result = String.valueOf(dateFormat) + "/ETAX-STATEMENT/" + randomSt + "/" + randomSc;
    } catch (Exception e) {
      log.info(e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
}
