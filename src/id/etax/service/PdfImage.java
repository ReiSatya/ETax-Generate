package id.etax.service;

import java.awt.image.BufferedImage;
import java.io.File;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import id.etax.model.Param;
import id.etax.utils.Function;


public class PdfImage {
	public String[] convert(String path, String name) {
		String result = "";
		String path1 = "";
		try {
		
			PDDocument doc = PDDocument.load(new File(path));
			PDFRenderer pdfRender = new PDFRenderer(doc);
//			if(doc.isEncrypted()) {
//				StandardDecryptionMaterial sdm = new StandardDecryptionMaterial("29101999");
//		        doc.;
//			}
//			int page = doc.getNumberOfPages();
			for(int page =0; page < doc.getNumberOfPages(); ++page) {
				 path1 = Param.getPdfPath()+Function.getPeriod()+"/"+name+".png";
				BufferedImage bim = pdfRender.renderImageWithDPI(page, 300, ImageType.RGB);
				ImageIOUtil.writeImage(bim, String.format(path1), 300);
//				SQLData.insertHistory(Function.getDate(), email, noKontrak, period, path1);
				System.out.println(path1);
				
			}
			result = "success";
			
			doc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.getMessage();
		}
		String[] result1 = {result, path1};
		return result1;
	}
}
