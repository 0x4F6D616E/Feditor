

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class Main{
		public static float version = 0.3f;
		public static String curdir = System.getProperty("user.dir");
		public static String [] curRes = getCurRes();
		public static int language = getLang(); 
		public static int objCounter = 0;
		public static Toolbar toolbar;
		public static JTextArea textArea;
		public static String defaultFont = "Courier New";
		public static int    defaultFontSize = 18;
		public static String openedFile = null;

		/*
		0 : English
		1 : Arabic
		*/
	public Main(){
		objCounter++;
		 JFrame frame = new JFrame((Main.language== 0) ? "Feditor v0.3":"فيديتور v0.3");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		frame.setSize(Integer.parseInt(curRes[0]),Integer.parseInt(curRes[1]));
		// definitions  
		textArea = new JTextArea();
		TextLineNumber tln = new TextLineNumber(textArea);
		JScrollPane scrollPane = new JScrollPane(textArea);
		toolbar = new Toolbar();

		// if you close without saving
		
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	String message 	= (Main.language== 0) ? "Are you sure to close this window ?":"هل أنت متأكد من أنك تريد إغلاق النافذة ؟";
		    	String title   	= (Main.language== 0) ? "Don't forget to save your file":"لاتنسى حفظ الملف الخاص بك";
		    	int nsure = JOptionPane.showOptionDialog(null, 
		    	        message, 
		    	        title, 
		    	        JOptionPane.OK_CANCEL_OPTION, 
		    	        JOptionPane.INFORMATION_MESSAGE, 
		    	        null, 
		    	        new String[]{(Main.language== 0) ? "Yes":"نعم", (Main.language== 0) ? "No":"لا"}, // this is the array
		    	        "default");
		    	if ( nsure == JOptionPane.YES_OPTION){
		    		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		    		if(objCounter == 1){
		    			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    		}
		    		objCounter--;
		    	   }
		        else{
		        	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		        }
		    }
		});
		
		// properties:
		frame.setLayout(new BorderLayout());
		textArea.setBackground(Color.lightGray);
		textArea.setTabSize(4);
		textArea.setFont(new Font(defaultFont, Font.PLAIN, defaultFontSize));
		tln.setFont(new Font("Roman", Font.BOLD, 12));
		tln.setForeground(Color.gray);
		scrollPane.setRowHeaderView( tln );
		// add to window
		frame.add(scrollPane);
		frame.add(toolbar,BorderLayout.SOUTH);
		frame.add(new Mains().menuBar,BorderLayout.NORTH);
		// Center window
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	    frame.setVisible(true);
	}
	protected static int getLang(){
		try  {
		    String text = null;
		    BufferedReader brTest = new BufferedReader(new FileReader(curdir+"/options/language.ini"));
		    text = brTest.readLine();
	    	int langID = Integer.parseInt(text.split("=")[1]);
	    	return langID;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return 0;
	}
	protected static  String[] getCurRes(){
		try{
		 String text = null;
		    BufferedReader brTest = new BufferedReader(new FileReader(curdir+"/options/resolution.ini"));
		 	text = brTest.readLine();
	    	String [] curResolution = text.split("=")[1].split("x");
	    	return curResolution;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return new String[2];
	}
	public static void setFont(String font,int size){
		if(size == 0 ){
			size = defaultFontSize;
		}
		if(font == "no"){
			font = Main.defaultFont;
		}
		Main.textArea.setFont(new Font(font, Font.PLAIN, size));
	}
	public static void setBackgroundColor(JTextArea textArea,String color){
		switch(color){
			case "Gray":
				textArea.setBackground(Color.gray);
				textArea.setForeground(Color.black);
				break;
			case "Light gray":
				textArea.setBackground(Color.lightGray);
				textArea.setForeground(Color.black);
				break;
			case "White":
				textArea.setBackground(Color.white);
				textArea.setForeground(Color.black);
				break;
			case "Cyan":
				textArea.setBackground(Color.cyan);
				textArea.setForeground(Color.black);
				break;
			case "Magenta":
				textArea.setBackground(Color.magenta);
				break;
			case "Blue":
				textArea.setBackground(Color.blue);
				textArea.setForeground(Color.white);
				break;
			case "Black":
				textArea.setBackground(Color.black);
				textArea.setForeground(Color.white);
				break;
			case "Orange":
				textArea.setBackground(Color.orange);
				textArea.setForeground(Color.black);
				break;
			case "Green":
				textArea.setBackground(Color.green);
				textArea.setForeground(Color.black);
				break;
			case "Dark gray":
				textArea.setBackground(Color.darkGray);
				textArea.setForeground(Color.white);
				break;
		
		}
	}

}
