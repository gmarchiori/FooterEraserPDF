package application;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class GUI {
	
	//percorso dove è presente il file
	protected static String path = "D:/ZZZ/";
	//destinazione del file modificato
	protected static String finalPath = "modified/";
	//nome del file (utilizzato pure al momento del salvataggio)
	protected static String fileName = "test.pdf";
	//immagine da usare come footer
	protected static String imgName = "blank.png";
	
	//pagina di partenza e arrivo per la cancellazione del footer
	protected static int fromPage;
	protected static int toPage;

	protected GUI() {

		//JFrame contenente tutto
		JFrame frame = new JFrame("FooterEraserPDF");
		//JPanel principale
		JPanel mainPanel = new JPanel();
		//JPanel dove sono presenti i parametri da inserire
		JPanel parametersPanel = new JPanel();
		
		//label che tiene traccia dello stato del programma
		final JLabel statusLabel = new JLabel("Footer Eraser PDF", SwingConstants.CENTER);
		//campi di inserimento numeri pagina
		final JTextField fromPageNum = new JTextField(20);
		final JTextField toPageNum = new JTextField(20);
		//JButton che avvia l'esecuzione dell'operazione
		JButton eraseButton = new JButton("Cancella footer");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setSize(500, 500);
		
		//come layout utilizzo un GridLayout
		mainPanel.setLayout(new GridLayout(0,2));
		mainPanel.setPreferredSize(new Dimension(500, 500));

		statusLabel.setLocation(150, 50);
		statusLabel.setSize(statusLabel.getPreferredSize());
		
//		JTextArea textArea = new JTextArea();
//		textArea.setEditable(false);
//		textArea.setLineWrap(true);
//		textArea.setOpaque(false);
//		textArea.setBorder(BorderFactory.createEmptyBorder());
//		add(textArea, BorderLayout.CENTER);
		
		//dimensioni del pulsante, non attiva con GridLayout
//		eraseButton.setBounds(300, 50, 140, 50);
		
		eraseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				//blocco try con tutte le operazioni al suo interno
				mainTry: try {
					
					PdfReader pdfReader = new PdfReader(path + fileName);
		
					Image image = Image.getInstance("img/" + imgName);
					
					fromPage = 1;
					toPage = pdfReader.getNumberOfPages();
					
					//blocco try relativo all'assegnazione dei parametri
					//fermo tutte le operazioni nel caso siano presenti errori, e lo comunico a video
					try {
						
						fromPage = Integer.parseInt(fromPageNum.getText());
						toPage = Integer.parseInt(toPageNum.getText());
						
						if(toPage < fromPage){
							
							statusLabel.setText("fromPage can't be greater than toPage");
							
							break mainTry;
							
						} else if(fromPage < 0){
							
							statusLabel.setText("fromPage must be at least 1");
							
							break mainTry;
							
							
						} else if(toPage > pdfReader.getNumberOfPages()){
							
							statusLabel.setText("toPage can't be greater than document's max length");
							
							break mainTry;
							
							
						}
						
					} catch(NumberFormatException e) {
						
						//nel caso sia presente del testo nei JTextField dei parametri pagine, blocco l'operazione
						statusLabel.setText("You must insert a number");
						
						e.printStackTrace();
						
						break mainTry;
						
					}
					
					//directory e stamper vengono definiti qui in modo che, in caso di errori,
					//non viene creato il percorso/file di destinazione
					File dir = new File(path + finalPath);
					dir.mkdir();
					PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(path + finalPath + fileName));
					
					//applico l'immagine per la cancellazione del footer
					for(int i = fromPage; i <= toPage; i++){
		
						PdfContentByte content = pdfStamper.getOverContent(i);
		
						image.setAbsolutePosition(0f, 20f);
		
						content.addImage(image);
					}
		
					pdfStamper.close();
					
					statusLabel.setText("Success! " + "Footer deleted from " + fromPage + " to " + toPage);
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
		});
		
		//aggiungo i componenti utilizzati come parametri al relativo JPanel
		parametersPanel.add(fromPageNum);
		parametersPanel.add(toPageNum);
		
		//aggiungo i componenti restanti (e eventuali altri JPanel) al JPanel principale
		mainPanel.add(statusLabel);
		mainPanel.add(eraseButton);
		mainPanel.add(parametersPanel);

		//aggiungo il tutto al JFrame, lo renderizzo e lo visualizzo (tenere in questo ordine le operazioni)
		frame.add(mainPanel);
		frame.pack();
		frame.setVisible(true);
		
	}

}
