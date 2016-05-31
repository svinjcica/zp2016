package etf.bg.ac.rs.zp2016.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ExportView extends JFrame{
	private JButton open;
	private JFileChooser fc;
	private Label errorLabel = new Label("");
	private TextField txt, pass;
	private JButton exportB, exitB;
	private FirstWind fw;
	
	private String chooseFile(){
		open = new JButton();
		fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("my own home directory"));
		fc.setDialogTitle("Choose file");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PKCS #12", "p12", "pkcs12");
		fc.setFileFilter(filter);
		if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
		
		}
		System.out.println(fc.getSelectedFile().getAbsolutePath());
		
		return fc.getSelectedFile().getAbsolutePath();
	}
	
	private static String getFileExtension(String fileName) {
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return null;
	    }
	
	public Panel plateExportB(){
		Panel plate = new Panel(new GridLayout(1, 2));
		exportB = new JButton ("Export");
		exportB.setSize(2,2);
		exportB.setBackground(Color.yellow);
		exportB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(exportB);
		exportB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				
				if(txt.getText().length() == 0) {
					errorLabel.setText("You must choose file!!");
					errorLabel.setBackground(Color.RED);
				}
				else if(pass.getText().length() == 0){
					errorLabel.setText("You must input password!!");
					errorLabel.setBackground(Color.RED);
				}
				else{
					String extStr = getFileExtension(pass.getText());
					if( extStr == null){
						errorLabel.setText("You must choose file! ");
						errorLabel.setBackground(Color.RED);
					}
					else{
						if(!extStr.equals(".p12")) {
							errorLabel.setText("File extension must be .p12! ");
							errorLabel.setBackground(Color.RED);	
						}
					}
				}
				// fje za eksport
			}
		});
		
		exitB = new JButton("EXIT");
 		exitB.setSize(2,2);
 		exitB.setFont(new Font(null,Font.BOLD, 15));
 		exitB.setBackground(Color.ORANGE);
 		plate.add(exitB);
 		
 		exitB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 fw = new FirstWind();
				 fw.setVisible(true);
				 fw.ex = true;
				 dispose();
			
			}
		});
		return plate;
	}
	
	public Panel plateChoose(){
		Panel plate = new Panel(new GridLayout(3, 3));
		JButton chooseB = new JButton("Browse");
		Label l1 = new Label("Choose file: ", Label.LEFT);
		l1.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l1);
		plate.add(txt);
		chooseB.setSize(2,2);
		chooseB.setBackground(Color.yellow);
		chooseB.setFont(new Font(null,Font.BOLD, 15));
		plate.add(chooseB);
		Label l2 = new Label("Password: ", Label.LEFT);
		l2.setFont(new Font(null,Font.BOLD, 15));
		plate.add(l2);
		pass = new TextField();
		plate.add(pass);
		plate.add(new Label());
		plate.add(errorLabel);
		plate.add(new Label());
		plate.add(new Label());
		
		chooseB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				//setEnabled(false);
				String str = chooseFile();
				txt.setText(str);
				
				// fje za eksport
			}
		});
		
		return plate;
	}
	public ExportView() {
		super("X.509 Authentication Service: EXPORT KEY PAIR");
		
		setBounds(300,150,700,150);
		setResizable(false);
		errorLabel = new Label(" ", Label.LEFT);
		//errorLabel.setBackground(Color.ORANGE);
		errorLabel.setFont(new Font(null,Font.BOLD, 15));
		txt =  new TextField();
		add(plateChoose());
		add(plateExportB(),BorderLayout.SOUTH );
		
	}
	
}
