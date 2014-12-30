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
		//JPanel dove inserisco le pagine da elaborare
		JPanel pagesPanel = new JPanel();
		//JPanel dove inserisco la posizione del file di origine e la destinazione del file elaborato
		JPanel filePanel = new JPanel();
		
		//label che tiene traccia dello stato del programma
		//vengono usati i tag <html> in modo da far andare a capo la label se troppo lunga
		final JLabel statusLabel = new JLabel("<html>" + "Footer Eraser PDF" +  "</html>", JLabel.CENTER);
		//campi di inserimento numeri pagina e relative label
		JLabel fromPageLabel = new JLabel("Start page:", JLabel.CENTER);
		JLabel toPageLabel = new JLabel("End page:", JLabel.CENTER);
		final JTextField fromPageField = new JTextField(20);
		final JTextField toPageField = new JTextField(20);
		//campi di inserimento percorsi relativi ai file
		JLabel originFileLabel = new JLabel("Input file:", JLabel.CENTER);
		JLabel destinationFileLabel = new JLabel("Destination file:", JLabel.CENTER);
		final JTextField originFileField = new JTextField(20);
		final JTextField destinationFileField = new JTextField(20);
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
		
		//di default agisco tutte le pagine del pdf
		fromPageField.setText("" + 1);
		toPageField.setText("" + "end");
		
		//dimensioni del pulsante (funzione inutile con GridLayout)
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
						
						//fromPage è inizializzato a 1 di default, ma si aggiorna lo stesso
						//anche il field è inizializzato a 1 di default
						fromPage = Integer.parseInt(fromPageField.getText());
						//se il field contiene la strigna "end" vuol dire che si vuole arrivare fino all'ultima pagina
						//di default è impostato a "end". In caso fosse diverso, viene aggiornato
						toPage = toPageField.getText().equals("end") ? toPage : Integer.parseInt(toPageField.getText());
						
						if(toPage < fromPage){
							
							statusLabel.setText("<html>" + "Start can't be greater than end" +  "</html>");
							
							break mainTry;
							
						} else if(fromPage <= 0){
							
							statusLabel.setText("<html>" + "Start must be at least 1" +  "</html>");
							
							break mainTry;
							
							
						} else if(toPage > pdfReader.getNumberOfPages()){
							
							statusLabel.setText("<html>" + "End can't be greater than document's max length" +  "</html>");
							
							break mainTry;
							
							
						}
						
					} catch(NumberFormatException e) {
						
						//nel caso sia presente del testo nei JTextField dei parametri pagine, blocco l'operazione
						statusLabel.setText("<html>" + "You must insert a number" +  "</html>");
						
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
					
					statusLabel.setText("<html>" + "Success! " + "Footer deleted from page " + fromPage + " to page " + toPage +  "</html>");
					
				} catch (Exception e) {
					
					e.printStackTrace();
					
				}
			}
		});
		
		
		
		//aggiungo i componenti nel corrispettivo JPanel
		pagesPanel.add(fromPageLabel);
		pagesPanel.add(fromPageField);
		pagesPanel.add(toPageLabel);
		pagesPanel.add(toPageField);
		
		filePanel.add(originFileLabel);
		filePanel.add(originFileField);
		filePanel.add(destinationFileLabel);
		filePanel.add(destinationFileField);
		
		//aggiungo i componenti restanti (e eventuali altri JPanel) al JPanel principale
		mainPanel.add(statusLabel);
		mainPanel.add(eraseButton);
		mainPanel.add(pagesPanel);
		mainPanel.add(filePanel);

		//aggiungo il tutto al JFrame, lo renderizzo e lo visualizzo (tenere in questo ordine le operazioni)
		frame.add(mainPanel);
		frame.pack();
		frame.setVisible(true);

	}

}
