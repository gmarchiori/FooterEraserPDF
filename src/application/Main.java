package application;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) {
		
		//TODO: xxx
		//FIXME: aaa
		//XXX: asd
		
		System.out.println("AAA");
		
		JFrame frame = new JFrame("FrameDemo");
		frame.setSize(500, 500);
		frame.add(new JLabel("Hello, World!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
	}

}
