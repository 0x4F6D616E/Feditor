

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Toolbar extends JPanel {
	private JButton saveButton;
	private JButton openButton;
	private JOptionPane error;
	public Toolbar(){
		saveButton = new JButton((Main.language== 0) ? "Save as":"حفظ بإسم");
		openButton = new JButton((Main.language== 0) ? "Open":"فتح");
		/*   Set the layout     */
		setLayout(new FlowLayout());
		/*   Set the button options(OPEN Button)   */
		openButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser finput = new JFileChooser();
				int result = finput.showOpenDialog(openButton);
				if (result == JFileChooser.APPROVE_OPTION){
					if(Main.textArea.getText() != "" || Main.textArea.getText() != null){
						Main.textArea.setText("");
					}
					File file;
					String currline;
					try {
						File ffile = finput.getSelectedFile();
						file = new File(ffile.getAbsolutePath());
					} catch (Exception e) {
						// Here if you want to add exception for opening file (Access denied or something like that ).
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
	
					try {
						BufferedReader in = new BufferedReader(
								   new InputStreamReader(
						                      new FileInputStream(file), "UTF8"));

						while ((currline = in.readLine()) != null) {
							Main.textArea.append(currline+"\n");
							}
					} catch (IOException e) {
						// Here if you want to add exception for opening file (Access denied or something like that ).
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
		/*    Set button options (SAVE BUTTON)        */
		
		saveButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser finput = new JFileChooser();
				int result = finput.showSaveDialog(saveButton);
				if(result == JFileChooser.APPROVE_OPTION ){
					File ffile = finput.getSelectedFile();
					try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ffile.getAbsolutePath()), "utf-8"))) {
						/*	Old Lines 
							String ln = System.getProperty("line.separator");
							String text = Main.textArea.getText() ;
							String as = text.replaceAll("\n", ln);
							writer.write(as);
						*/
						Main.textArea.write(writer);
						//	}
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
		
		
		/*    Add to the panel       */
		add(saveButton);
		add(openButton);
	}
}
