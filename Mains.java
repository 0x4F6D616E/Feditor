

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class Mains {
	//create the base menu
	public JMenuBar menuBar = new JMenuBar();
	public static boolean hidestatus = false;
	private static String old_txt = "";
	public Mains(){
		//set the sub menus 
		JMenu fileMenu = new JMenu((Main.language== 0) ? "File":"ملف");
		JMenu editMenu = new JMenu((Main.language== 0) ? "Edit":"تعديل");
		JMenu windowMenu = new JMenu((Main.language== 0) ? "Window":"نافذة");
		JMenu helpMenu = new JMenu((Main.language== 0) ? "Help":"مساعدة");
		//File menu's items 
		//JMenuItem openItem = new JMenuItem("Open",new ImageIcon(icoDir+"open.png"));
		JMenuItem openItem = new JMenuItem((Main.language== 0) ? "Open":"فتح",createImageIcon("resources/open.png"));
		JMenuItem saveItem = new JMenuItem((Main.language== 0) ? "Save":"حفظ",createImageIcon("resources/save.png"));
		JMenuItem saveAsItem = new JMenuItem((Main.language== 0) ? "Save as":"حفظ بإسم",createImageIcon("resources/saveas.png"));
		JMenuItem exitItem = new JMenuItem((Main.language== 0) ? "Exit":"خروج",createImageIcon("resources/exit.png"));
		// Help menu's items
		JMenuItem updateItem = new JMenuItem((Main.language== 0) ? "Update":"تحديث",createImageIcon("resources/update.png"));
		JMenuItem optionsItem = new JMenuItem((Main.language == 0) ? "Options":"الإعدادات",createImageIcon("resources/options.png"));
		JMenuItem aboutItem = new JMenuItem((Main.language== 0) ? "About":"عن المبرمج",createImageIcon("resources/about.png"));
		// Window menu's items
		JMenuItem newWindowItem = new JMenuItem((Main.language== 0) ? "New window":"نافذة جديدة",createImageIcon("resources/new-window.png"));
		// Edit menu's items
		JMenuItem insertDate	= new JMenuItem((Main.language== 0) ? "Insert date":"إدخال التاريخ",createImageIcon("resources/date.png"));
		JMenuItem find 			= new JMenuItem((Main.language== 0) ? "Find":"بحث",createImageIcon("resources/find.png"));
		JMenuItem findReplace 	= new JMenuItem((Main.language== 0) ? "Find/Replace":"بحث وإستبدال",createImageIcon("resources/find-replace.png"));
		JMenuItem hide 			= new JMenuItem((Main.language== 0) ? "Hide text":"إخفاء النص",createImageIcon("resources/hide.png"));
		// Shortcuts 
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O , Event.CTRL_MASK));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , Event.CTRL_MASK));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , Event.SHIFT_MASK));
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F , Event.CTRL_MASK));
		findReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J , Event.CTRL_MASK));
		insertDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5 , 0));
		hide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R , Event.CTRL_MASK));
		newWindowItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N , Event.CTRL_MASK));
		// works 
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(exitItem);
		editMenu.add(insertDate);
		editMenu.add(find);
		editMenu.add(findReplace);
		editMenu.add(hide);
		windowMenu.add(newWindowItem);
		helpMenu.add(updateItem);
		helpMenu.add(optionsItem);
		helpMenu.add(aboutItem);
		// add the sub to the base.
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);
		// set it visible.
		menuBar.setVisible(true);
		/**************            This behave of File->Open 				***************/
		openItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Main.textArea.getHighlighter().removeAllHighlights();
				JFileChooser finput = new JFileChooser();
				int result = finput.showOpenDialog(openItem);
				if (result == JFileChooser.APPROVE_OPTION){
					if(Main.textArea.getText() != "" || Main.textArea.getText() != null){
						Main.textArea.setText("");
					}
					File ffile = finput.getSelectedFile();
					String currline;
					File file;
					try {
						Main.openedFile = ffile.getAbsolutePath();
						file = new File(Main.openedFile);
					
					} catch (Exception e) {
						// Here if you want to add exception for opening file (Access denied or something like that ).
						JFrame a = new JFrame("ERROR");
						a.setVisible(true);
						Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					    int x = (int) ((dimension.getWidth() - a.getWidth()) / 2);
					    int y = (int) ((dimension.getHeight() - a.getHeight()) / 2);
					    a.setLocation(x, y);
						JOptionPane.showMessageDialog(a,
							    "Error has ouccr when opening the file.",
							    "Error !",
							    JOptionPane.ERROR_MESSAGE);
						a.setVisible(false);
						return;
					}
	
					try {
						 String encode = new InputStreamReader(
			                      new FileInputStream(file)).getEncoding();
						 System.out.println(encode);
						BufferedReader in = new BufferedReader(
								   new InputStreamReader(
						                      new FileInputStream(file), "UTF8"));
						while ((currline = in.readLine()) != null) {
							Main.textArea.append(currline+"\n");
							}
					} catch (Exception e) {
						JFrame a = new JFrame("ERROR");
						a.setVisible(true);
						Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					    int x = (int) ((dimension.getWidth() - a.getWidth()) / 2);
					    int y = (int) ((dimension.getHeight() - a.getHeight()) / 2);
					    a.setLocation(x, y);
					    a.setVisible(false);
						JOptionPane.showMessageDialog(a,
								(Main.language== 0) ? "Error has ouccr when opening the file.":"عفواً , لقد حدث خطأ أثناء فتح الملف",
								(Main.language== 0) ? "Error !":"خطأ",
							    JOptionPane.ERROR_MESSAGE);
						
						return;
					}
				}
				
			}
			
		});
		/***************				FILE->SAVE					************/
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(Main.openedFile != null && Main.openedFile.length() != 0){
					File openend = new File(Main.openedFile);
					try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(openend.getAbsolutePath()), "utf-8"))) {
						Main.textArea.write(writer);
					}catch(Exception e){
						JFrame a = new JFrame("ERROR");
						a.setVisible(true);
						Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					    int x = (int) ((dimension.getWidth() - a.getWidth()) / 2);
					    int y = (int) ((dimension.getHeight() - a.getHeight()) / 2);
					    a.setLocation(x, y);
					    a.setVisible(false);
						JOptionPane.showMessageDialog(a,
							    (Main.language== 0) ? "Error has ouccr when saving the file.":"عفواً , لقد حدث خطأ أثناء حفظ الملف",
							    (Main.language== 0) ? "Error !":"خطأ",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
				}else{
					JFileChooser finput = new JFileChooser();
					int result = finput.showSaveDialog(saveItem);
					if(result == JFileChooser.APPROVE_OPTION ){
						File ffile = finput.getSelectedFile();
						try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ffile.getAbsolutePath()), "utf-8"))) {
							Main.textArea.write(writer);
						}catch(Exception e){
							JFrame a = new JFrame("ERROR");
							a.setVisible(true);
							Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
						    int x = (int) ((dimension.getWidth() - a.getWidth()) / 2);
						    int y = (int) ((dimension.getHeight() - a.getHeight()) / 2);
						    a.setLocation(x, y);
						    a.setVisible(false);
							JOptionPane.showMessageDialog(a,
								    (Main.language== 0) ? "Error has ouccr when saving the file.":"عفواً , لقد حدث خطأ أثناء حفظ الملف",
								    (Main.language== 0) ? "Error !":"خطأ",
								    JOptionPane.ERROR_MESSAGE);
							return;
							}
				}
				}
			}
		});
		/***************				FILE->SAVE AS				************/
		saveAsItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser finput = new JFileChooser();
				int result = finput.showSaveDialog(saveAsItem);
				if(result == JFileChooser.APPROVE_OPTION ){
					File ffile = finput.getSelectedFile();
					try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ffile.getAbsolutePath()), "utf-8"))) {
						Main.textArea.write(writer);
					}catch(Exception e){
						JFrame a = new JFrame("ERROR");
						a.setVisible(true);
						Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					    int x = (int) ((dimension.getWidth() - a.getWidth()) / 2);
					    int y = (int) ((dimension.getHeight() - a.getHeight()) / 2);
					    a.setLocation(x, y);
					    a.setVisible(false);
						JOptionPane.showMessageDialog(a,
							    (Main.language== 0) ? "Error has ouccr when saving the file.":"عفواً , لقد حدث خطأ أثناء حفظ الملف",
							    (Main.language== 0) ? "Error !":"خطأ",
							    JOptionPane.ERROR_MESSAGE);
						return;
						}
			}
			}
			
		});
		/***************				FILE->EXIT					************/
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				int nsure = JOptionPane.showOptionDialog(null, 
						(Main.language== 0) ? "All window will be closed\nDo you want to continue ?":"سوف يتم إغلاق جميع النوافذ , هل تريد المتابعة ؟", 
								(Main.language== 0) ? "Warning":"تحذير", 
		    	        JOptionPane.OK_CANCEL_OPTION, 
		    	        JOptionPane.WARNING_MESSAGE, 
		    	        null, 
		    	        new String[]{(Main.language== 0) ? "Yes":"نعم", (Main.language== 0) ? "No":"لا"}, // this is the array
		    	        "default");
		    	if ( nsure == JOptionPane.YES_OPTION){
		    		System.exit(0);
		    	   }
		        else{
		        }
			}
		});
		/***************				EDIT-> INSERT DATE			************/
		insertDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				Main.textArea.append(dateFormat.format(date));
			}
		});
		/***************				EDIT-> FIND					************/
		find.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Main.textArea.getHighlighter().removeAllHighlights(); 
				JTextField wordField = new JTextField(20);
			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("Find what : "));
			      myPanel.add(wordField);

			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Find",JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			    	  Highlighter.HighlightPainter painter = 
			    			    new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
			    	  if(wordField.getText().length() == 0){
			    		  JOptionPane.showMessageDialog(new JFrame(),
			    				    (Main.language == 0) ? "You must fill up the box":"الرجاء إكمال الخانة",
			    				    		(Main.language == 0) ? "Error":"خطأ",
			    				    JOptionPane.WARNING_MESSAGE);
			    		  return;
			    	  }
			    	  int offset = Main.textArea.getText().indexOf(wordField.getText());
			    	  int length = wordField.getDocument().getLength();
			    	  boolean found = false;
			    	  while ( offset != -1)
			    	  {
			    	      try
			    	      {
			    	    	  found = true;
			    	          Main.textArea.getHighlighter().addHighlight(offset, offset+length, painter);
			    	          offset = Main.textArea.getText().indexOf(wordField.getText(), offset+1);
			    	      }
			    	      catch(BadLocationException ble) { System.out.println(ble); }
			    	  }
			    	  if(offset == -1 && found == true){
			    		  JOptionPane.showMessageDialog(new JFrame(),
			    				    (Main.language == 0 ) ? "Results have been found successfully":"تم إيجاد النتائج بنجاح",(Main.language == 0) ? "Search":"البحث",
			    				    	    JOptionPane.INFORMATION_MESSAGE);
			    	  }
			    	  if(offset == -1 && found == false){
			    		  JOptionPane.showMessageDialog(new JFrame(),
			    				    (Main.language == 0 ) ? "No results have been found":"تعذر الحصول على النتائج",(Main.language == 0) ? "Search":"البحث",
			    				    	    JOptionPane.WARNING_MESSAGE);
			    	  }
			      }
				
		
		}
			});
		/***************				EDIT->FIND/REPLACE			************/
	      findReplace.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent arg0){
							Main.textArea.getHighlighter().removeAllHighlights();
							JTextField findWord = new JTextField(15);
						      JTextField replaceWord = new JTextField(15);

						      JPanel myPanel = new JPanel();
						      myPanel.add(new JLabel("Find what :"));
						      myPanel.add(findWord);
						      myPanel.add(Box.createHorizontalStrut(20)); // a spacer
						      myPanel.add(new JLabel("Replace with :"));
						      myPanel.add(replaceWord);

						      int result = JOptionPane.showConfirmDialog(null, myPanel, 
						               "Find / Replace", JOptionPane.OK_CANCEL_OPTION);
						      if (result == JOptionPane.OK_OPTION) {
						    	  if(findWord.getText().length() == 0 || replaceWord.getText().length() == 0){
						    		  JOptionPane.showMessageDialog(new JFrame(),
						    				    (Main.language == 0) ? "You must fill up the boxes":"الرجاء إكمال الخانات",
						    				    		(Main.language == 0) ? "Error":"خطأ",
						    				    JOptionPane.WARNING_MESSAGE);
						    		  return;
						    	  }
						         Main.textArea.setText(Main.textArea.getText().replace(findWord.getText(), replaceWord.getText()));
						         Highlighter.HighlightPainter painter = 
						    			    new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
						    	  int offset = Main.textArea.getText().indexOf(replaceWord.getText());
						    	  int length = replaceWord.getDocument().getLength();
						    	  boolean found = false;
						    	  while ( offset != -1)
						    	  {
						    	      try
						    	      {
						    	    	  found = true;
						    	          Main.textArea.getHighlighter().addHighlight(offset, offset+length, painter);
						    	          offset = Main.textArea.getText().indexOf(replaceWord.getText(), offset+1);
						    	      }
						    	      catch(BadLocationException ble) { 
						    	    	  System.out.println(ble);
						    	    	  }
						    	  }
						      }
						      
						}
					});
		/*************** 				EDIT->HIDE text 			************/
		hide.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				if(hidestatus == true){
					hidestatus = false;
				}else{
					hidestatus = true;
				}
				if(hidestatus == true){
					Mains.old_txt = Main.textArea.getText();
					Main.textArea.setText("");
				}else{
					if(Main.textArea.getText().length() > 0 ){
						String[] options = new String[2];
						options[0] = new String((Main.language == 0) ? "Append the hidden text":"إضافة النص المخفي للنص الحالي");
						options[1] = new String((Main.language == 0) ? "Do not append , just set the hidden text":"قم بإزالة النص الحالي وأضف النص المخفي");
						int n = JOptionPane.showOptionDialog(new JFrame(),(Main.language == 0) ? "Would you like to append the text hidden text or set it and remove the current?":"هل تريد إضافة النص المخفي للنص الحالي أم حذف النص الحالي وإضافة النص المخفي؟",(Main.language == 0) ? "Question":"سؤال", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);		
						if(n == JOptionPane.YES_OPTION){
							Main.textArea.append("\n"+old_txt);
						}else{
							Main.textArea.setText(old_txt);
						}
					}else{
						Main.textArea.setText(old_txt);
					}
				}
				}
		});
		/*****************				Window -> New window 	*************/
		newWindowItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				Runnable newWindow = new Runnable(){
					public void run(){
						Player.newWindow();

					}
				};
				Thread newWindowThread = new Thread(newWindow,"test0");
				newWindowThread.start(); 
		}
			});
		
		/***************** 			Help -> Options				*************/
		optionsItem.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				Runnable optionWindow = new Runnable(){
					public void run(){
						Main.textArea.getHighlighter().removeAllHighlights();
						JFrame optionsFrame = new JFrame();
						optionsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						optionsFrame.setTitle((Main.language == 0 ) ? "Options":"الإعدادات");
						optionsFrame.setSize(new Dimension(400,300));
				        JTabbedPane jtp = new JTabbedPane();
				        optionsFrame.getContentPane().add(jtp);
				        /*		Panel1		*/
				        JPanel jp1 = new JPanel();
				        jp1.setBorder((Main.language == 0 ) ? new TitledBorder("Options"):new TitledBorder("الإعدادات"));
				        JLabel label1 = new JLabel();
				        label1.setText((Main.language == 0 ) ? "Language: ":":اللغة");
				        String [] langs = {(Main.language == 0 ) ? "English":"الإنجليزية",(Main.language == 0 ) ? "Arabic":"العربية"};
				        JComboBox langlist = new JComboBox(langs);
				        langlist.setSelectedIndex((Main.language == 0 ) ? 0:1);
				        langlist.addActionListener(new ActionListener(){
				        	@Override
				        	public void actionPerformed(ActionEvent e){
				        		String lng = (String) langlist.getSelectedItem();
				        		switch(lng){
				        		case "English":
				        		case "الإنجليزية":
					        			Main.language = 0;
					        			Path path = Paths.get(Main.curdir+"/options/language.ini");
					        			Charset charset = StandardCharsets.UTF_8;
					        			byte[] bytes = null;
					        			try{
					        				bytes = Files.readAllBytes(path);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content = new String(bytes, charset);
					        			content = content.replaceAll("1", "0");
					        			try {
					        				Files.write(path, content.getBytes(charset));
					        			} catch (IOException e1) {
					        				e1.printStackTrace();
					        			}
					        			Object[] options = {"OK"};
					        		    JOptionPane.showOptionDialog(new JFrame(),
					        		                   "You have to restart the program to change settings completly ","Message",
					        		                   JOptionPane.PLAIN_MESSAGE,
					        		                   JOptionPane.QUESTION_MESSAGE,
					        		                   null,
					        		                   options,
					        		                   options[0]);
					        			break;
				        		case "Arabic":
				        		case "العربية":
					        			Main.language = 1;
					        			Path path2 = Paths.get(Main.curdir+"/options/language.ini");
					        			Charset charset2 = StandardCharsets.UTF_8;
					        			byte[] bytes2 = null;
					        			try{
					        				bytes2 = Files.readAllBytes(path2);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content2 = new String(bytes2, charset2);
					        			content2 = content2.replaceAll("0", "1");
					        			try {
					        				Files.write(path2, content2.getBytes(charset2));
					        			} catch (IOException e1) {
					        				e1.printStackTrace();
					        			}
					        			Object[] options2 = {"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        	                   "لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ","رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options2,
					        	                   options2[0]);
					        			break;
					        	
				        		}
				        	}
				        });
				        JLabel labelColor = new JLabel();
				        labelColor.setText((Main.language == 0 ) ? "Text area background: ":":لون خلفية النص");
				        String [] txtbgColor = {(Main.language == 0 ) ? "Gray":"رصاصي",
				        		(Main.language == 0 ) ? "Light gray":"رصاصي فاتح",
		        				(Main.language == 0 ) ? "White":"ابيض",
		        				(Main.language == 0 ) ? "Cyan":"سماوي",
			        			(Main.language == 0 ) ? "Magenta":"أرجواني",
			        			(Main.language == 0 ) ? "Blue":"أزرق",
			        			(Main.language == 0 ) ? "Black":"أسود",
				        		(Main.language == 0 ) ? "Orange":"برتقالي",
				        		(Main.language == 0 ) ? "Green":"أخضر",
				        		(Main.language == 0 ) ? "Dark gray":"رصاصي غامق"};
				        JComboBox txtbgColorList = new JComboBox(txtbgColor);
				        txtbgColorList.addActionListener(new ActionListener(){
				        	@Override
				        	public void actionPerformed(ActionEvent e){
				        		String lng = (String) txtbgColorList.getSelectedItem();
				        		switch(lng){
					        		case "Gray":
					        		case "رصاصي":
					        			Main.setBackgroundColor(Main.textArea, "Gray");
					        			break;
					        		case "Light gray":
					        		case "رصاصي فاتح":
					        			Main.setBackgroundColor(Main.textArea, "Light gray");
					        			break;
					        		case "White":
					        		case "ابيض":
					        			Main.setBackgroundColor(Main.textArea, "White");
					        			break;
					        		case "Cyan":
					        		case "سماوي":
					        			Main.setBackgroundColor(Main.textArea, "Cyan");
					        			break;
					        		case "Magenta":
					        		case "أرجواني":
					        			Main.setBackgroundColor(Main.textArea, "Magenta");
					        			break;
					        		case "Blue":
					        		case "أزرق":
					        			Main.setBackgroundColor(Main.textArea, "Blue");
					        			break;
					        		case "Black":
					        		case "أسود":
					        			Main.setBackgroundColor(Main.textArea, "Black");
					        			break;
					        		case "Orange":
					        		case "برتقالي":
					        			Main.setBackgroundColor(Main.textArea, "Orange");
					        			break;
					        		case "Green":
					        		case "أخضر":
					        			Main.setBackgroundColor(Main.textArea, "Green");
					        			break;
					        		case "Dark gray":
					        		case "رصاصي غامق":
					        			Main.setBackgroundColor(Main.textArea, "Dark gray");
					        			break;
					        			
				        		}
				        	}
				        });
				        jp1.add(label1);
				        jp1.add(langlist);
				        jp1.add(new JLabel("                              "));
				        jp1.add(labelColor);
				        jp1.add(txtbgColorList);
				        jtp.addTab((Main.language == 0 ) ? "General":"عام", jp1);
				        /*		Panel2		*/
				        JPanel jp2 = new JPanel();
				        jp2.setBorder((Main.language == 0 ) ? new TitledBorder("Fonts"):new TitledBorder("الخطوط"));
				        JLabel label2 = new JLabel();
				        label2.setText((Main.language == 0 ) ? "Font:":":الخط");
				        String [] fonts = {
				        		"Georgia",
				        		"Impact",
				        		"Segoe Script",
				        		"Segoe Print",
				        		"Simplified Arabic Fixed",
				        		"Times New Roman",
				        		"Traditional Arabic",
				        		"Roman",
				        		"Gabriola",
				        		"Courier New",
				        		"Comic Sans MS",
				        		"Arabic Typesetting",
				        		"Arial"};
				        JComboBox fontList = new JComboBox(fonts);
				        fontList.setSelectedIndex(9);
				        fontList.addActionListener(new ActionListener(){
				        	@Override
				        	public void actionPerformed(ActionEvent e){
				        		String ft = (String) fontList.getSelectedItem();
				        		switch(ft){
					        		case "Georgia":
					        			String f1 = "Georgia";
					        			Main.defaultFont = f1;
					        			Main.setFont(f1, 0);
					        			break;
					        		case "Impact":
					        			String f2 = "Impact";
					        			Main.defaultFont = f2;
					        			Main.setFont(f2, 0);
					        			break;
					        		case "Segoe Script":
					        			String f3 = "Segoe Script";
					        			Main.defaultFont = f3;
					        			Main.setFont(f3, 0);
					        			break;
					        		case "Segoe Print":
					        			String f4 = "Segoe Print";
					        			Main.defaultFont = f4;
					        			Main.setFont(f4, 0);
					        			break;
					        		case "Simplified Arabic Fixed":
					        			String f5 = "Simplified Arabic Fixed";
					        			Main.defaultFont = f5;
					        			Main.setFont(f5, 0);
					        			break;
					        		case "Times New Roman":
					        			String f6 = "Times New Roman";
					        			Main.defaultFont = f6;
					        			Main.setFont(f6, 0);
					        			break;
					        		case "Traditional Arabic":
					        			String f7 = "Traditional Arabic";
					        			Main.defaultFont = f7;
					        			Main.setFont(f7, 0);
					        			break;
					        		case "Roman":
					        			String f8 = "Roman";
					        			Main.defaultFont = f8;
					        			Main.setFont(f8, 0);
					        			break;
					        		case "Gabriola":
					        			String f9 = "Gabriola";
					        			Main.defaultFont = f9;
					        			Main.setFont(f9, 0);
					        			break;
					        		case "Courier New":
					        			String f10 = "Courier New";
					        			Main.defaultFont = f10;
					        			Main.setFont(f10, 0);
					        			break;
					        		case "Comic Sans MS":
					        			String f11 = "Comic Sans MS";
					        			Main.defaultFont = f11;
					        			Main.setFont(f11, 0);
					        			break;
					        		case "Arabic Typesetting":
					        			String f12 = "Arabic Typesetting";
					        			Main.defaultFont = f12;
					        			Main.setFont(f12, 0);
					        			break;
					        		case "Arial":
					        			String f13 = "Arial";
					        			Main.defaultFont = f13;
					        			Main.setFont(f13, 0);
					        			break;
				        		}
				        	}
				        });
				        // fonts size list
				        JLabel fontSizeLabel = new JLabel();
				        fontSizeLabel.setText((Main.language == 0) ? "Font size : ":": حجم الخط");
				        String [] fontSize = {
				        		"10",
				        		"11",
				        		"12",
				        		"13",
				        		"14",
				        		"16",
				        		"18",
				        		"20",
				        		"22",
				        		"24",
				        		"28",
				        		"36",
				        		"48",
				        		"50"
				        };
				        JComboBox fontSizeList = new JComboBox(fontSize);
				        fontSizeList.setSelectedIndex(6);
				        fontSizeList.addActionListener(new ActionListener(){
				        	@Override
				        	public void actionPerformed(ActionEvent e){
				        		String fs = (String) fontSizeList.getSelectedItem();
				        		switch(fs){
					        		case "10":
					        			Main.defaultFontSize = 10;
					        			Main.setFont("no", 10);
					        			break;
					        		case "11":
					        			Main.defaultFontSize = 11;
					        			Main.setFont("no", 11);
					        			break;
					        		case "12":
					        			Main.defaultFontSize = 12;
					        			Main.setFont("no", 12);
					        			break;
					        		case "13":
					        			Main.defaultFontSize = 13;
					        			Main.setFont("no", 13);
					        			break;
					        		case "14":
					        			Main.defaultFontSize = 14;
					        			Main.setFont("no", 14);
					        			break;
					        		case "16":
					        			Main.defaultFontSize = 16;
					        			Main.setFont("no", 16);
					        			break;
					        		case "18":
					        			Main.defaultFontSize = 18;
					        			Main.setFont("no", 18);
					        			break;
					        		case "20":
					        			Main.defaultFontSize = 20;
					        			Main.setFont("no", 20);
					        			break;
					        		case "22":
					        			Main.defaultFontSize = 22;
					        			Main.setFont("no", 22);
					        			break;
					        		case "24":
					        			Main.defaultFontSize = 24;
					        			Main.setFont("no", 24);
					        			break;
					        		case "28":
					        			Main.defaultFontSize = 28;
					        			Main.setFont("no", 28);
					        			break;
					        		case "36":
					        			Main.defaultFontSize = 36;
					        			Main.setFont("no", 36);
					        			break;
					        		case "48":
					        			Main.defaultFontSize = 48;
					        			Main.setFont("no", 48);
					        			break;
					        		case "50":
					        			Main.defaultFontSize = 50;
					        			Main.setFont("no", 50);
					        			break;
				        		}
				        	}
				        });
				        JLabel fontColorLabel = new JLabel();
				        fontColorLabel.setText((Main.language == 0) ? "Font Color : ":": لون الخط");
				        String [] fontColor = {
				        		(Main.language == 0 ) ? "Red":"أحمر",
				        		(Main.language == 0 ) ? "Blue":"أزرق",
				        		(Main.language == 0 ) ? "Black":"أسود",
				        		(Main.language == 0 ) ? "White":"أبيض",
				        		(Main.language == 0 ) ? "Orange":"برتقالي",
				        		(Main.language == 0 ) ? "Cyan":"سماوي",
				        		(Main.language == 0 ) ? "Green":"أخضر"
				        };
				        JComboBox fontColorList = new JComboBox(fontColor);
				        fontColorList.addActionListener(new ActionListener(){
				        	@Override
				        	public void actionPerformed(ActionEvent e){
				        		String fc = (String) fontColorList.getSelectedItem();
				        		switch(fc){
				        		case "Red":
				        		case "أحمر":
				        			Main.textArea.setForeground(Color.red);
				        			break;
				        		case "Blue":
				        		case "أزرق":
				        			Main.textArea.setForeground(Color.blue);
				        			break;
				        		case "Black":
				        		case "أسود":
				        			Main.textArea.setForeground(Color.black);
				        			break;
				        		case "White":
				        		case "أبيض":
				        			Main.textArea.setForeground(Color.white);
				        			break;
				        		case "Orange":
				        		case "برتقالي":
				        			Main.textArea.setForeground(Color.orange);
				        			break;
				        		case "Cyan":
				        		case "سماوي":
				        			Main.textArea.setForeground(Color.cyan);
				        			break;
				        		case "Green":
				        		case "أخضر":
				        			Main.textArea.setForeground(Color.green);
				        			break;
				        			
					        		
				        		}
				        	}
				        });
				        jp2.add(fontSizeLabel);
				        jp2.add(fontSizeList);	
				        jp2.add(label2);
				        jp2.add(fontList);
				        jp2.add(new JLabel("                    "));
				        jp2.add(fontColorLabel);
				        jp2.add(fontColorList);
				        jtp.addTab((Main.language == 0) ? "Fonts":"الخطوط", jp2);
				        JPanel jp3 = new JPanel();
				        jp3.setBorder((Main.language == 0 ) ? new TitledBorder("Resolution Option"):new TitledBorder("إعدادات الأبعاد"));
				        JLabel label3 = new JLabel();
				        label3.setText((Main.language == 0) ? "Resoultion : ":":الأبعاد");
				        jp3.add(label3);
				        jtp.addTab((Main.language == 0) ? "Resolution":"الأبعاد", jp3);
				        String [] reses = {(Main.language == 0) ? "Default":"الإفتراضي","640x460","800x600","1024x724","1280x600","1336x768","Full screen"};
				        JComboBox resesList = new JComboBox(reses);
				        resesList.setSelectedIndex(0);
				        resesList.addActionListener(new ActionListener(){
				        	@Override
				        	public void actionPerformed(ActionEvent e){
				        		String rn = (String) resesList.getSelectedItem();
				        		switch(rn){
					        		case "640x460" :
					        			Path path3 = Paths.get(Main.curdir+"/options/resolution.ini");
					        			Charset charset3 = StandardCharsets.UTF_8;
					        			byte[] bytes3 = null;
					        			try{
					        				bytes3 = Files.readAllBytes(path3);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content3 = new String(bytes3, charset3);
					        			content3 = content3.replaceAll(Main.curRes[0]+"x"+Main.curRes[1], "640x460");
					        			try {
					        				Files.write(path3, content3.getBytes(charset3));
					        			} catch (IOException e3) {
					        				e3.printStackTrace();
					        			}
					        			Object[] options2 = {(Main.language == 0 ) ?  "OK":"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        					(Main.language == 0 ) ?  "You have to restart the program to change settings completly":"لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ",(Main.language == 0 ) ?  "Message":"رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options2,
					        	                   options2[0]);
					        			break;
					        		case "800x600" :
					        			Path path4 = Paths.get(Main.curdir+"/options/resolution.ini");
					        			Charset charset4 = StandardCharsets.UTF_8;
					        			byte[] bytes4 = null;
					        			try{
					        				bytes4 = Files.readAllBytes(path4);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content4 = new String(bytes4, charset4);
					        			content4 = content4.replaceAll(Main.curRes[0]+"x"+Main.curRes[1], "800x600");
					        			try {
					        				Files.write(path4, content4.getBytes(charset4));
					        			} catch (IOException e4) {
					        				e4.printStackTrace();
					        			}
					        			Object[] options4 = {(Main.language == 0 ) ?  "OK":"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        					(Main.language == 0 ) ?  "You have to restart the program to change settings completly":"لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ",(Main.language == 0 ) ?  "Message":"رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options4,
					        	                   options4[0]);
					        			break;
					        		case "1024x724" :
					        			Path path5 = Paths.get(Main.curdir+"/options/resolution.ini");
					        			Charset charset5 = StandardCharsets.UTF_8;
					        			byte[] bytes5 = null;
					        			try{
					        				bytes5 = Files.readAllBytes(path5);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content5 = new String(bytes5, charset5);
					        			content5 = content5.replaceAll(Main.curRes[0]+"x"+Main.curRes[1], "1024x724");
					        			try {
					        				Files.write(path5, content5.getBytes(charset5));
					        			} catch (IOException e5) {
					        				e5.printStackTrace();
					        			}
					        			Object[] options5 = {(Main.language == 0 ) ?  "OK":"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        					(Main.language == 0 ) ?  "You have to restart the program to change settings completly":"لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ",(Main.language == 0 ) ?  "Message":"رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options5,
					        	                   options5[0]);
					        			break;
					        		case "1280x600" :
					        			Path path6 = Paths.get(Main.curdir+"/options/resolution.ini");
					        			Charset charset6 = StandardCharsets.UTF_8;
					        			byte[] bytes6 = null;
					        			try{
					        				bytes6 = Files.readAllBytes(path6);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content6 = new String(bytes6, charset6);
					        			content6 = content6.replaceAll(Main.curRes[0]+"x"+Main.curRes[1], "1280x600");
					        			try {
					        				Files.write(path6, content6.getBytes(charset6));
					        			} catch (IOException e6) {
					        				e6.printStackTrace();
					        			}
					        			Object[] options6 = {(Main.language == 0 ) ?  "OK":"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        					(Main.language == 0 ) ?  "You have to restart the program to change settings completly":"لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ",(Main.language == 0 ) ?  "Message":"رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options6,
					        	                   options6[0]);
					        			break;
					        		case "1336x768" :
					        			Path path7 = Paths.get(Main.curdir+"/options/resolution.ini");
					        			Charset charset7 = StandardCharsets.UTF_8;
					        			byte[] bytes7 = null;
					        			try{
					        				bytes7 = Files.readAllBytes(path7);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content7 = new String(bytes7, charset7);
					        			content7 = content7.replaceAll(Main.curRes[0]+"x"+Main.curRes[1], "1336x768");
					        			try {
					        				Files.write(path7, content7.getBytes(charset7));
					        			} catch (IOException e7) {
					        				e7.printStackTrace();
					        			}
					        			Object[] options7 = {(Main.language == 0 ) ?  "OK":"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        					(Main.language == 0 ) ?  "You have to restart the program to change settings completly":"لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ",(Main.language == 0 ) ?  "Message":"رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options7,
					        	                   options7[0]);
					        			break;
					        		case "Full screen" :
					        			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					        			double width = screenSize.getWidth();
					        			double height = screenSize.getHeight();
					        			System.out.println("w="+width+"\nheight="+height);
					        			Path path8 = Paths.get(Main.curdir+"/options/resolution.ini");
					        			Charset charset8 = StandardCharsets.UTF_8;
					        			byte[] bytes8 = null;
					        			try{
					        				bytes8 = Files.readAllBytes(path8);
					        			}catch(Exception e1){
					        				System.out.println(e1.getMessage());
					        			}
					        			String content8 = new String(bytes8, charset8);
					        			content8 = content8.replaceAll(Main.curRes[0]+"x"+Main.curRes[1], (int)width+"x"+(int)height);
					        			try {
					        				Files.write(path8, content8.getBytes(charset8));
					        			} catch (IOException e7) {
					        				e7.printStackTrace();
					        			}
					        			Object[] options8 = {(Main.language == 0 ) ?  "OK":"حسناً"};
					        			JOptionPane.showOptionDialog(new JFrame(),
					        					(Main.language == 0 ) ?  "You have to restart the program to change settings completly":"لقد تم تغيير الإعدادات , يجب عليك إعادة تشغيل البرنامج, لكي تتغير الإعدادات بشكل كلي ",(Main.language == 0 ) ?  "Message":"رسالة",
					        	                   JOptionPane.PLAIN_MESSAGE,
					        	                   JOptionPane.QUESTION_MESSAGE,
					        	                   null,
					        	                   options8,
					        	                   options8[0]);
					        			break;
				        			
				        		}
				        	}
				        });
				        jp3.add(resesList);
				        optionsFrame.setVisible(true);
				        
						Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					    int x = (int) ((dimension.getWidth() - optionsFrame.getWidth()) / 2);
					    int y = (int) ((dimension.getHeight() - optionsFrame.getHeight()) / 2);
					    optionsFrame.setLocation(x, y);
					    optionsFrame.setVisible(true);
					}
				};
				Thread optionsWindowThread = new Thread(optionWindow,"test2");
				optionsWindowThread.start();
				}
		});
		/*****************			Help -> Update 				*************/
		updateItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Runnable updateWindow = new Runnable(){
					public void run(){
						try{
							Main.textArea.getHighlighter().removeAllHighlights();
							URL chkVersion = new URL("http://bip.weizmann.ac.il/javascript/update/check.txt");
							URLConnection con = chkVersion.openConnection();
							InputStream is =con.getInputStream();
							BufferedReader br = new BufferedReader(new InputStreamReader(is));
							String vline = null;
							vline = br.readLine();
							float ver = Float.parseFloat(vline);
							JProgressBar updateProgressBar = new JProgressBar(0,100);
							updateProgressBar.setIndeterminate(true);
							updateProgressBar.setVisible(true);
							updateProgressBar.setStringPainted(true);
							updateProgressBar.setString((Main.language == 0) ? "Checking for update..":"جاري التحقق من وجود تحديث ..");
							JFrame updateFrame = new JFrame();
							updateFrame.setTitle((Main.language == 0) ? "Update":"التحديث");
							updateFrame.add(updateProgressBar);
							updateFrame.setSize(400,70);
							Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
							int x = (int) ((dimension.getWidth() - updateFrame.getWidth()) / 2);
						    int y = (int) ((dimension.getHeight() - updateFrame.getHeight()) / 2);
						    updateFrame.setLocation(x, y);
						    updateFrame.setVisible(true);
							if(ver > Main.version){
								Thread.sleep(7000);
								updateProgressBar.setString((Main.language == 0 ) ? "Update found ! ":"تم إيجاد تحديث !");
								Thread.sleep(2000);
								updateProgressBar.setString((Main.language == 0 ) ? "Downloading the update ..":"جاري تحميل التحديث ..");
								try{
									String updateURL = "http://bip.weizmann.ac.il/javascript/update/Feditor.jar";
									URL updateLnk = new URL(updateURL);
									ReadableByteChannel rbc = Channels.newChannel(updateLnk.openStream());
									FileOutputStream fos = new FileOutputStream("Feditor-v"+ver+".jar");
									fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
								}catch(Exception e){
									System.out.println(e.getMessage());
								}
								Thread.sleep(5000);
								updateFrame.setVisible(false);
								JOptionPane.showMessageDialog(null,
										 (Main.language == 0) ? "Update has been downloaded successfully !":"تم تحميل التحديث بنجاح !",
									        (Main.language == 0) ? "updated successfully":"تم التحديث بنجاح",
									        JOptionPane.INFORMATION_MESSAGE);
							}else{
								updateFrame.setVisible(false);
								JOptionPane.showMessageDialog(null,
										 (Main.language == 0) ? "You already have the latest version of this app.":"أنت تملك الإصدار الأخير من هذا البرنامج.",
									        (Main.language == 0) ? "Update":"التحديث",
									        JOptionPane.INFORMATION_MESSAGE);
							}
						}catch(IOException e){
							JOptionPane.showMessageDialog(null,
									 (Main.language == 0) ? "In rule to update this app you need to have Connection to the internet ":"لتحديث هذا البرنامج يجب أن يكون لديك إتصال بالإنترنت.",
								        (Main.language == 0) ? "Update faild":"فشل التحديث",
								        JOptionPane.WARNING_MESSAGE);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				Thread updateWindowThread = new Thread(updateWindow,"test2");
				updateWindowThread.start();
				}
		});
		/***********			Help->about				********/
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				JFrame aboutFrame = new JFrame((Main.language == 0 ) ? "About":"عن");
				JTabbedPane jtp = new JTabbedPane();
				aboutFrame.getContentPane().add(jtp);
				JPanel jp1 = new JPanel();
				BufferedImage wPic;
		        jp1.setBorder((Main.language == 0 ) ? new TitledBorder("About programmer"):new TitledBorder("عن المبرمج"));
				jp1.add(new JLabel(Mains.createImageIcon("resources/Cowboy-Man.jpg")));
				JPanel jp2 = new JPanel();
		        JTextArea tst = new JTextArea();
		        jtp.addTab((Main.language == 0 ) ? "Programmer":"المبرمج", jp1);
				String enAboutTextProjects = ""
						+ "1-Programing articles script ."
						+ "\n"
						+ "\n"
						+ "2-Programming security exploits which support GUI(Graphical user interface)."
						+ "\n"
						+ "\n"
						+ "3-Find exploits in large programming projects like 'Immunity debugger'."
						+ "\n"
						+ "\n"
						+ "4-Programming some tools which helps pentetration tester ."
						+ "\n"
						+ "\n"
						+ "5-Programming an exploits to attack specific systems ."
						+ "\n"
						+ "\n"
						+ "6-Programming with different languages like php,python,shell script(bash),java,c,c++ .."
						+ "\n"
						+ "\n"
						+ "7-Helping some organisations to secure their website ."
						+ "\n"
						+ "\n";
				String arAboutTextProjects = ""
						+ "1-برمجة سكربت مقالات ."
						+ "\n"
						+ "\n"
						+ "2-برمجة إستغلالات لثغرات أمنية تدعم الواجهة الرسومية GUI."
						+ "\n"
						+ "\n"
						+ "3-إكتشاف ثغرات في بعض البرمجيات الكبيرة كـ Immunity debugger."
						+ "\n"
						+ "\n"
						+ "4-برمجة بعض الأدوات المساعدة للشخص المهتم في أمن المعلومات."
						+ "\n"
						+ "\n"
						+ "5-برمجة إستغلالات لثغرات معينة في الأنظمة."
						+ "\n"
						+ "\n"
						+ "6-البرمجة بعدة لغات كـ php,python,shell script(bash),java,c,c plus plus.. "
						+ "\n"
						+ "\n"
						+ "7-مساعدة بعض الجهات في الكشف عن بعض الثغرات في المواقع التابعة لها."
						+ "\n"
						+ "\n";
				tst.setText((Main.language == 0 ) ? enAboutTextProjects:arAboutTextProjects);
				tst.setEnabled(false);
				tst.setRows(15);
				if(Main.language == 0){
					tst.setFont(new Font("Courier New",Font.CENTER_BASELINE,18));
				}else{
					tst.setFont(new Font("Courier New",Font.CENTER_BASELINE,20));
				}
				if(Main.language == 1 )
					tst.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				tst.setBackground(Color.black);
				jp2.setBorder((Main.language == 0 ) ? new TitledBorder("Contributions"):new TitledBorder("المساهمات"));
				jp2.add(new JScrollPane(tst));
		        jtp.addTab((Main.language == 0 ) ? "Contributions & Projects":"المساهمات وبعض المشاريع", jp2);
				aboutFrame.setSize(1000, 630);
				aboutFrame.setResizable(false);
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			    int x = (int) ((dimension.getWidth() - aboutFrame.getWidth()) / 2);
			    int y = (int) ((dimension.getHeight() - aboutFrame.getHeight()) / 2);
			    aboutFrame.setLocation(x, y);
				aboutFrame.setVisible(true);
			}
		});
	}
	
	public static ImageIcon createImageIcon(String path) {
        URL imgURL = Main.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}

