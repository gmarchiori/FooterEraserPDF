package application;

import java.io.FileOutputStream;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Main {
	
	public static void main(String[] args) {
		try{
			PdfReader pdfReader = new PdfReader("D://ZZZ//test.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("D://ZZZ//test_MODIFIED.pdf"));

			Image image = Image.getInstance("D://ZZZ/blank.png");

			for(int i = 1; i <= pdfReader.getNumberOfPages(); i++){

				PdfContentByte content = pdfStamper.getOverContent(i);

				image.setAbsolutePosition(275f, 35f);

				content.addImage(image);
			}

			pdfStamper.close();

			System.out.println("Success!");
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}