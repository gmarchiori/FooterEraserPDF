package application;

import javax.swing.JFrame;
import javax.swing.JLabel;

import beans.prova1;

public class Main {
	
	/** 
	 * prova
	 * prova javadoc 
	 * @param args il parametro principale
	 * @return void
	 */

	public static void main(String[] args) {
		
		//TODO: xxx
		//FIXME: aaa
		//XXX: asd
		
		System.out.println("AAA");
		
		prova1 p = new prova1();
		p.setNum(22);
		
		JFrame frame = new JFrame("FrameDemo");
		frame.setSize(500, 500);
		frame.add(new JLabel("Hello, World!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
	}

}
