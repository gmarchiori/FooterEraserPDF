package application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class GUI {
	
	protected static String path = "D:/ZZZ/";
	protected static String finalPath = "modified/";
	protected static String fileName = "test.pdf";
	protected static String imgName = "blank.png";
	
	protected static int fromPage = 1;
	protected static int toPage;

	protected GUI() {

		JFrame frame = new JFrame("FooterEraserPDF");
		JPanel statusPanel = new JPanel();
		JButton eraseButton = new JButton("Cancella footer");
		final JLabel statusLabel = new JLabel("Footer Eraser PDF");
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setSize(500, 500);
		
		statusPanel.setPreferredSize(new Dimension(500, 500));
		statusPanel.setLayout(null);

		statusLabel.setLocation(150, 50);
		statusLabel.setSize(statusLabel.getPreferredSize());
		
		eraseButton.setBounds(300, 50, 140, 50);
		eraseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					File dir = new File(path + finalPath);
					dir.mkdir();
					
					PdfReader pdfReader = new PdfReader(path + fileName);
		
					PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(path + finalPath + fileName));
		
					Image image = Image.getInstance("img/" + imgName);
					
					toPage = pdfReader.getNumberOfPages();
		
					for(int i = fromPage; i <= toPage; i++){
		
						PdfContentByte content = pdfStamper.getOverContent(i);
		
						image.setAbsolutePosition(0f, 20f);
		
						content.addImage(image);
					}
		
					pdfStamper.close();
					
					statusLabel.setText("Success!");
					
				} catch (Exception ex) {
					
					ex.printStackTrace();
					
					statusLabel.setText(ex.toString());
					
				}
			}
		});
		
		statusPanel.add(statusLabel);
		statusPanel.add(eraseButton);

		frame.add(statusPanel);
		frame.pack();
		
	}

}
